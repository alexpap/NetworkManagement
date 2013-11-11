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
public class Service implements Serializable{
    private int id;
    private String name,oid,type,desc;

    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", name=" + name + ", oid=" + oid + ", type=" + type + ", desc=" + desc + '}';
    }

    public Service() {
        id = -1;
        name = oid = type = desc ="no-value";
    }

    public Service(int id) {
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

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}
