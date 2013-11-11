package snmp;

import db.HostService;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SNMPManager {
    private static SNMPManager singleton = null;

    private Map<Integer, SNMPThread> threads;
    
    private SNMPManager() {
        this.threads = new Hashtable<Integer, SNMPThread>();
    }
    
    public static SNMPManager getInstance( ){
        if(singleton == null){
            singleton = new SNMPManager();
        }
        return singleton;
    } 
    
    public boolean StartHostService(HostService hs) {
        threads.put(hs.getId(), new SNMPThread(hs));
        threads.get(hs.getId()).start();
        return true;
    }
    
    public boolean StartAllHostService(Map<Integer,HostService> hs) {
        Iterator<Map.Entry<Integer,HostService>> it = hs.entrySet().iterator();
        while ( it.hasNext() ) {
            Map.Entry<Integer,HostService> entry = it.next();
            threads.put(entry.getKey(), new SNMPThread(entry.getValue()));
            threads.get(entry.getKey()).start();
        }
        return true;
    }
    
    public boolean StopHostService(HostService hs) {
         threads.get(hs.getId()).Stop();
        try {
            threads.get(hs.getId()).join();
        } catch (InterruptedException ex) {
            Logger.getLogger(SNMPManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public boolean StopAllHostService(Map<Integer,HostService> hs) {
        Iterator<Map.Entry<Integer,HostService>> it = hs.entrySet().iterator();
        while ( it.hasNext() ) {
            Map.Entry<Integer,HostService> entry = it.next();
            if (entry.getValue().isActive()){
                threads.get(entry.getKey()).Stop();
                try {
                    SNMPThread thread = threads.get(entry.getKey());
                    if (thread!=null) thread.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(SNMPManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;
    }
    
}
