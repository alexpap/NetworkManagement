package snmp;

import db.DataManager;
import db.HostService;
import db.Value;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SNMPThread extends Thread{
    private HostService hostservice;
    private Snmp snmp;
    private TransportMapping<?> transport;
    private long timeout;
    
    private  boolean running;
    private final Object lock = new Object();

    public SNMPThread() {
        running = true;
        timeout = 5;
    }

    public SNMPThread(HostService hs) {
        this.hostservice = hs;
        running = true;
        if (hs.getTimeout() > 0)
            timeout = hs.getTimeout();
        else timeout = 5;
    }

    @Override
    public void run() {
        DataManager data = DataManager.getInstance();
        //System.out.println("IN THREAD === "+hostservice.toString());
        if ( hostservice.getService().getType().equalsIgnoreCase("SET")) {
            this.SnmpSet();
            Value value = new Value();
            value.setId(hostservice.getId());
            value.setValue(hostservice.getTh_value());
            data.AddValue(value);
        }
        else if (hostservice.getService().getType().equalsIgnoreCase("GET")) {
            while (true){
                try {
                    //System.out.println(" -> "+this.SnmpGet() + " " + this.running);
                    String val = this.SnmpGet();
                    //System.out.println(" ---> "+val);
                    Value value = new Value();
                    value.setValue(val);
                    value.setId(hostservice.getId());
                    data.AddValue(value);
                    Thread.sleep(timeout*1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SNMPThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                synchronized(lock) {
                    if (!this.running) {
                        this.Stop();
                        break;
                    }
                }
            }
        }
        hostservice.setActive(false);
        data.UpdateHostService(hostservice);
        return;
    }
    
    public void Stop(){
        synchronized(lock) {
            this.running = false;
            //System.out.println(" <--->" + this.running);
        }
    }
   
   private String SnmpGet() {
        try {
            String strResponse="";
            ResponseEvent response;
            Snmp snmp2;
            Address tHost = new UdpAddress(hostservice.getHost().getIp() +"/"+hostservice.getHost().getPort());
            TransportMapping<?> transport2 = new DefaultUdpTransportMapping();

            transport2.listen();

            CommunityTarget comtarget = new CommunityTarget();
            comtarget.setCommunity(new OctetString("public"));
            comtarget.setVersion(SnmpConstants.version1);
            comtarget.setAddress(tHost);
            comtarget.setRetries(1);
            comtarget.setTimeout(5000);

            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(hostservice.getService().getOid())));
            pdu.setType(PDU.GET); 

            snmp2 = new Snmp(transport2);
            response = snmp2.get(pdu,comtarget);
            if(response != null) {
                if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
                    PDU pduresponse=response.getResponse();
                    strResponse=pduresponse.getVariableBindings().firstElement().toString();
                    if(strResponse.contains("=")) {
                        int len = strResponse.indexOf("=");
                        strResponse=strResponse.substring(len+1, strResponse.length());
                    }
                    if (strResponse.isEmpty()) strResponse = "No Value";
                }
            } else {
                return "No-Response";
            }
            snmp2.close();
            return strResponse;
        } catch (IOException ex) {
            Logger.getLogger(SNMPThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void SnmpSet(){
        try {
            Address tHost = GenericAddress.parse(hostservice.getHost().getIp() + "/" + hostservice.getHost().getPort() );
            Snmp snmp2;
            TransportMapping<?> transport2 = new DefaultUdpTransportMapping();
            snmp2 = new Snmp(transport2);
            transport2.listen();
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));
            target.setAddress(tHost);
            target.setRetries(1);
            target.setTimeout(5000);
            target.setVersion(SnmpConstants.version1); //Set the correct SNMP version here
            PDU pdu = new PDU();
            //Depending on the MIB attribute type, appropriate casting can be done here
            pdu.add(new VariableBinding(new OID(hostservice.getService().getOid()), new OctetString(hostservice.getTh_value())));
            pdu.setType(PDU.SET);
            ResponseListener listener;
            listener = new ResponseListener() {
                @Override
                public void onResponse(ResponseEvent event) {
                   PDU strResponse;
                   String result;
                   ((Snmp)event.getSource()).cancel(event.getRequest(), this);
                   strResponse = event.getResponse();
                   if (strResponse!= null) {
                       result = strResponse.getErrorStatusText();
                       System.out.println("Set Status is: "+result);
                   }
                }};
            snmp2.send(pdu, target, null, listener);
            snmp2.close();
        } catch (IOException ex) {
            Logger.getLogger(SNMPThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
