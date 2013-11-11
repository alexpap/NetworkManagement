package db;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {
    // private field
    private static DataManager singleton;
    private Connection connect;
    private String driver, dbname, dbusrname, dbpswd, dbip, dbport;

    private DataManager() {
        DataManager.singleton = null;
        this.dbport = "3306";
        this.dbip = "localhost";
        this.dbusrname = "root";
        this.dbpswd = "monopoly";
        this.dbname = "nms_db";
        this.driver = "com.mysql.jdbc.Driver";
        this.connect = null;   
    }    

    public static DataManager getInstance() {
        if(singleton == null){
            singleton = new DataManager();
        }
        return singleton;
    }

    public boolean Connect(){
        try {
            Class.forName(driver);
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://"+ dbip+":"+dbport+"/"+dbname, dbusrname, dbpswd);
        } catch (SQLException ex){
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch ( ClassNotFoundException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    public boolean Connect(String name, String usrname, String pswd, String ip, String port){
        try {
            Class.forName(driver);
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://"+ ip+":"+port+"/"+name,usrname,pswd);
            connection.close();        
        } catch (SQLException ex){
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch ( ClassNotFoundException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean Disconnect(){
        if (connect != null) {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    public boolean changeDB(String name, String ip, String port,String usrname, String pswd) {
        if ( this.Connect(name,usrname,pswd,ip,port) ){
            this.Disconnect();
            this.dbname = name;
            this.dbusrname = usrname;
            this.dbpswd = pswd;
            this.dbip = ip;
            this.dbport = port;
            // save values to file
            return true;
        }
        return false;            
    }
    public boolean AddUser(User user){
        if ( connect == null && this.Connect() == false ) return false;
        try {
            PreparedStatement prepareStatement;
            prepareStatement = connect.prepareStatement("insert into user(`username`,`password`,`email`) values(?, ?, ?)");
            prepareStatement.setString(1,user.getUsername());
            prepareStatement.setString(2,user.getPassword());
            prepareStatement.setString(3,user.getEmail());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }             
        return false;
    }
    
    public User GetUser(String username) {
        if ( connect == null && this.Connect() == false ) return null;
        try {
            ResultSet result;    
            PreparedStatement statement = connect.prepareStatement("select * from user where username = ?");
            statement.setString(1,username);
            result = statement.executeQuery();
            User user = new User();
            if ( result.next() ) {
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));    
                user.setEmail(result.getString("email"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;  
    }   
    public boolean DeleteUser(User user){
        if ( connect == null && this.Connect() == false ) return false;
        try {
            PreparedStatement prepareStatement = connect.prepareStatement("delete from user where username = ?");
            prepareStatement.setString(1, user.getUsername());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }            
        return false;
    }
    
    public Hashtable<Integer,Service> GetAllServices() {
        if ( connect == null && this.Connect() == false ) return null;
        Hashtable<Integer,Service> services = new Hashtable<Integer,Service>();
        try {
            Statement statement = connect.createStatement();
            ResultSet executeQuery = statement.executeQuery("select * from service order by name");   
            while(executeQuery.next()){
                Service serv = new Service();
                serv.setId(executeQuery.getInt("id"));
                serv.setOid(executeQuery.getString("oid"));
                serv.setName(executeQuery.getString("name"));
                serv.setDesc(executeQuery.getString("desc"));
                serv.setType(executeQuery.getString("type"));
                services.put(serv.getId(),serv);                
            }
            return services;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return null;
    }
    
    public Hashtable<Integer,Service> GetAllServices(Host host) {        
        if ( connect == null && this.Connect() == false ) return null;
        Hashtable<Integer,Service> services = new Hashtable<Integer,Service>();
        try {
            String q = "select s.id,s.oid,s.name,s.desc,s.type from service as s,host_has_service where service_id = s.id and host_id=?";
            PreparedStatement statement = connect.prepareStatement(q);
            statement.setInt(1,host.getId());
            ResultSet executeQuery = statement.executeQuery();
            while(executeQuery.next()){
                Service serv = new Service();
                serv.setId(executeQuery.getInt("id"));
                serv.setOid(executeQuery.getString("oid"));
                serv.setName(executeQuery.getString("name"));
                serv.setDesc(executeQuery.getString("desc"));
                serv.setType(executeQuery.getString("type"));
                services.put(serv.getId(),serv);                
            }
            return services;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }     
        return null;
    }
    
    public boolean AddService(Service service){
        if ( connect == null && this.Connect() == false ) return false;
        try {
            PreparedStatement prepareStatement;
            prepareStatement = connect.prepareStatement("insert into service(`name`,`desc`,`oid`,`type`) values(?, ?, ?,?)");
            prepareStatement.setString(1,service.getName());
            prepareStatement.setString(2,service.getDesc());
            prepareStatement.setString(3,service.getOid());
            prepareStatement.setString(4,service.getType());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }             
        return false;
    }
    
    public boolean UpdateService(Service service){
        if ( connect == null && this.Connect() == false ) return false;
        try {
            PreparedStatement prepareStatement;
            prepareStatement = connect.prepareStatement("update service set `name` = ?, `desc` = ?, `oid` = ?, `type` = ? where id = ?");
            prepareStatement.setString(1,service.getName());
            prepareStatement.setString(2,service.getDesc());
            prepareStatement.setString(3,service.getOid());
            prepareStatement.setString(4,service.getType());
            prepareStatement.setInt(5,service.getId());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
         } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
         }             
        return false;
    } 
    
    public boolean DeleteService(Service service){
        if ( connect == null && this.Connect() == false ) return false;
        try {
            PreparedStatement prepareStatement = connect.prepareStatement("delete from service where id = ?");
            prepareStatement.setInt(1, service.getId());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }            
        return false;
    }
    
    public Hashtable<Integer,Host> GetAllHosts() {
        if ( connect == null && this.Connect() == false ) return null;
        Hashtable<Integer,Host> hosts = new Hashtable<Integer,Host>();
        try {
            Statement statement = connect.createStatement();
            ResultSet executeQuery = statement.executeQuery("select * from host");       
            while(executeQuery.next()){
                Host host = new Host();
                host.setId(executeQuery.getInt("id"));
                host.setName(executeQuery.getString("name"));
                host.setIp(executeQuery.getString("ip"));
                host.setPort(executeQuery.getString("port"));
                host.setTrap_port(executeQuery.getString("trap_port"));
                hosts.put(host.getId(),host);
            }
            return hosts;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return null;
    }

    public boolean AddHost(Host host){
        if ( connect == null && this.Connect() == false ) return false;
        PreparedStatement prepareStatement;
        try {
            prepareStatement = connect.prepareStatement("insert into host(name,ip, port, trap_port) values(?, ?, ?, ?)");
            prepareStatement.setString(1,host.getName());
            prepareStatement.setString(2,host.getIp());
            prepareStatement.setString(3,host.getPort());
            prepareStatement.setString(4,host.getTrap_port());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }                       
        return false;
    }
    
    public boolean UpdateHost(Host host){
        if ( connect == null && this.Connect() == false ) return false;
        try {
            PreparedStatement prepareStatement;
            prepareStatement = connect.prepareStatement("update host set `name` = ?, `ip` = ?, `port` = ?, `trap_port` = ? where id = ?");
            prepareStatement.setString(1,host.getName());
            prepareStatement.setString(2,host.getIp());
            prepareStatement.setString(3,host.getPort());
            prepareStatement.setString(4,host.getTrap_port());
            prepareStatement.setInt(5,host.getId());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true; 
         } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
         }             
        return false;
    }
    
    public boolean DeleteHost(Host host){
        if ( connect == null && this.Connect() == false ) return false;

        try {
            PreparedStatement prepareStatement = connect.prepareStatement("delete from host where id = ?");
            prepareStatement.setInt(1, host.getId());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }            
        return false;
    }
    
    public Hashtable<Integer,HostService> GetAllHostService() {
        if ( connect == null && this.Connect() == false ) return null;
        Hashtable<Integer,HostService> hhass = new Hashtable<Integer,HostService>();
        try {
            String q ="select * from host_has_service as hs,host as h,service as s where hs.host_id = h.id and hs.service_id =s.id";
            PreparedStatement statement = connect.prepareStatement(q);
            ResultSet executeQuery = statement.executeQuery();       
            while(executeQuery.next()){
                HostService hh = new HostService();
                Host h = new Host();
                h.setId(executeQuery.getInt("h.id"));
                h.setIp(executeQuery.getString("h.ip"));
                h.setName(executeQuery.getString("h.name"));
                h.setPort(executeQuery.getString("h.port"));
                h.setTrap_port(executeQuery.getString("h.trap_port"));
                hh.setHost(h);
                
                Service s = new Service();
                s.setId(executeQuery.getInt("s.id"));
                s.setDesc(executeQuery.getString("s.desc"));
                s.setName(executeQuery.getString("s.name"));
                s.setOid(executeQuery.getString("s.oid"));
                s.setType(executeQuery.getString("s.type"));
                hh.setService(s);
                
                hh.setId(executeQuery.getInt("id"));
                hh.setTh_value(executeQuery.getString("th_value"));
                hh.setTimeout(executeQuery.getLong("timeout"));
                hh.setActive(executeQuery.getBoolean("active"));
                hhass.put(hh.getId(),hh);
            }
            return hhass;

        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return null;
    }
    
    public Hashtable<Integer,HostService> GetAllHostService(Host host) {
        if ( connect == null && this.Connect() == false ) return null;
        Hashtable<Integer,HostService> hhass = new Hashtable<Integer,HostService>();
        try {
            String q ="select * from host_has_service as hs,host as h,service as s where hs.host_id = h.id and hs.service_id =s.id and h.id = ?";
            PreparedStatement statement = connect.prepareStatement(q);
            statement.setInt(1, host.getId());
            ResultSet executeQuery = statement.executeQuery();       
            while(executeQuery.next()){
              HostService hh = new HostService();
                Host h = new Host();
                h.setId(executeQuery.getInt("h.id"));
                h.setIp(executeQuery.getString("h.ip"));
                h.setName(executeQuery.getString("h.name"));
                h.setPort(executeQuery.getString("h.port"));
                h.setTrap_port(executeQuery.getString("h.trap_port"));
                hh.setHost(h);
                
                Service s = new Service();
                s.setId(executeQuery.getInt("s.id"));
                s.setDesc(executeQuery.getString("s.desc"));
                s.setName(executeQuery.getString("s.name"));
                s.setOid(executeQuery.getString("s.oid"));
                s.setType(executeQuery.getString("s.type"));
                hh.setService(s);
                
                hh.setId(executeQuery.getInt("id"));
                hh.setTh_value(executeQuery.getString("th_value"));
                hh.setTimeout(executeQuery.getLong("timeout"));
                hh.setActive(executeQuery.getBoolean("active"));
                hhass.put(hh.getId(),hh);
            }
            return hhass;

        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return null;
    }
    
    public boolean AddHostService(HostService hh){
        if ( connect == null && this.Connect() == false ) return false;
        PreparedStatement prepareStatement;
        try {
            prepareStatement = connect.prepareStatement("insert into host_has_service(host_id,service_id,th_value,timeout,active) values(?, ?, ?,?,?)");
            prepareStatement.setInt(1,hh.getHost().getId());
            prepareStatement.setInt(2,hh.getService().getId());
            prepareStatement.setString(3,hh.getTh_value());
            prepareStatement.setLong(4,hh.getTimeout());
            prepareStatement.setBoolean(5,hh.isActive());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }             
        return false;
    }
    public boolean UpdateHostService(HostService hh){
        if ( connect == null && this.Connect() == false ) return false;
        PreparedStatement prepareStatement;
        try {
            prepareStatement = connect.prepareStatement("update host_has_service set `th_value` = ? ,`timeout` = ? ,`active` = ? where id = ?");
            prepareStatement.setString(1,hh.getTh_value());
            prepareStatement.setLong(2,hh.getTimeout());
            prepareStatement.setBoolean(3,hh.isActive());
            prepareStatement.setInt(4,hh.getId());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }             
        return false;
    }    
    
    public boolean DeleteHostService(HostService hh){
        if ( connect == null && this.Connect() == false ) return false;
        try {
            PreparedStatement prepareStatement;
            prepareStatement = connect.prepareStatement("delete from host_has_service where id = ?");
            prepareStatement.setInt(1,hh.getId());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }            
        return false;
    }
    
    public ArrayList<Value> GetAllValues(HostService hh, int n) {
        if ( connect == null && this.Connect() == false ) return null;
        ArrayList<Value> vv = new ArrayList<Value>();
        
        try {
            PreparedStatement prepareStatement;
            prepareStatement = connect.prepareStatement("select * from value where hs_id = ? order by timestamp desc");
            prepareStatement.setInt(1,hh.getId());
            ResultSet executeQuery = prepareStatement.executeQuery();
            for( int i = 0; i < n; i++ ){ 
                if ( executeQuery.next() ){
                    Value v = new Value();
                    v.setId(hh.getId());
                    v.setTimestamp(executeQuery.getTimestamp("timestamp"));
                    v.setValue(executeQuery.getString("value"));
                    vv.add(v);
                }
                else break;
            }
            return vv;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return null;
    }
    
    public boolean AddValue(Value v){
        if ( connect == null && this.Connect() == false ) return  false;
        PreparedStatement prepareStatement;
        try {
            prepareStatement = connect.prepareStatement("insert into value (hs_id,value) values (?,?)");
            prepareStatement.setInt(1,v.getId());
            prepareStatement.setString(2,v.getValue());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);             
        }           
        return false;
    }   
}
