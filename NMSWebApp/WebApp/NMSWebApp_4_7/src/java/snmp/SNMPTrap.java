package snmp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.MessageProcessingModel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

public class SNMPTrap implements CommandResponder {

    private MultiThreadedMessageDispatcher dispatcher;
    private Snmp snmp_trap_ = null;
    private Address listenAddress;
    private ThreadPool threadPool;

    public SNMPTrap() {
        listenAddress = GenericAddress.parse("udp:0.0.0.0/1162");
        threadPool = ThreadPool.create("Trap", 2);
    } 
    public SNMPTrap(int thdnum) {
        listenAddress = GenericAddress.parse("udp:0.0.0.0/1162");
        threadPool = ThreadPool.create("Trap", thdnum);
    }
    public SNMPTrap(String ip, String port,int thdnum) {
        listenAddress = GenericAddress.parse("udp:"+"ip"+"/"+"port");
        threadPool = ThreadPool.create("Trap", thdnum);
    }

    private void init(){
        
        dispatcher = new MultiThreadedMessageDispatcher(threadPool,new MessageDispatcherImpl());
        TransportMapping<?> transport = null;
        try{
            if (listenAddress instanceof UdpAddress) 
                transport = new DefaultUdpTransportMapping((UdpAddress)listenAddress);//(UdpAddress)listenAddress
            else transport = new DefaultTcpTransportMapping((TcpAddress)listenAddress);
            snmp_trap_ = new Snmp(dispatcher, transport);
            snmp_trap_.getMessageDispatcher().addMessageProcessingModel(new MPv1());
            snmp_trap_.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
            snmp_trap_.getMessageDispatcher().addMessageProcessingModel(new MPv3());
            USM usm = new USM(SecurityProtocols.getInstance(),
                    new OctetString(((MPv3)snmp_trap_.getMessageProcessingModel(MessageProcessingModel.MPv3)).createLocalEngineID()),0);
            SecurityModels.getInstance().addSecurityModel(usm);
            snmp_trap_.listen();
        } catch (IOException ex) {
                Logger.getLogger(SNMPTrap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {
        init();
        snmp_trap_.addCommandResponder(this);    
    }

    // add functionality when trap is received here!!!!
    @Override
    public void processPdu(CommandResponderEvent event) {
        
        //String ip = event.getPeerAddress().toString();
        //ip = ip.substring(0,ip.indexOf("/"));
        //PDUv1 pdU = (PDUv1) event.getPDU();
        //String oid = pdU.getEnterprise().toString();
        //System.out.println("--------RECEIVED A TRAP--------");
        //System.out.println("Sender IP = " + ip);
        //System.out.println("Sender oid = " + oid);
        //System.out.println(event.toString());
    }
}
