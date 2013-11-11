/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;

/**
 *
 * @author rooty
 */
public class Host implements Serializable{
    private int id;
    private String name, ip, port, trap_port;

    @Override
    public String toString() {
        return "Host{" + "id=" + id + ", name=" + name + ", ip=" + ip + ", port=" + port + ", trap_port=" + trap_port + '}';
    }

    public Host() {
        id = -1; 
        name = ip = port = trap_port = "no-value";
    }

    public Host(int id) {
        this.id = id;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTrap_port() {
        return trap_port;
    }

    public void setTrap_port(String trap_port) {
        this.trap_port = trap_port;
    }
    
}
