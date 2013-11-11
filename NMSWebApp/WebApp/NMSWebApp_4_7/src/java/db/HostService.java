package db;

import java.io.Serializable;

public class HostService implements Serializable {
   private int id;
   private String th_value;
   private Host host;
   private Service service;
   private long timeout;
   boolean active;
   
    public HostService() {
        id=-1 ;
        th_value = "no-value";
        host = null; 
        service = null;
        active = false;
    }

    public HostService(String th_value, Host host, Service service, long timeout) {
        this.th_value = th_value;
        this.host = host;
        this.service = service;
        this.timeout = timeout;
        this.active = false;
    }

    public HostService(int id) {
        this.id = id;
        this.active = false;
    }
    
    @Override
    public String toString() {
        return "HostService{" + "id=" + id + ", th_value=" + th_value + ", host=" + host + ", service=" + service + ", timeout=" + timeout + '}';
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
 
    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTh_value() {
        return th_value;
    }

    public void setTh_value(String th_value) {
        this.th_value = th_value;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
   
}
