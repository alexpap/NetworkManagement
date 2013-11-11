package servlets;

import db.DataManager;
import db.Host;
import db.HostService;
import db.Service;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import snmp.SNMPManager;

public class ConfigureMatch extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(true);
        request.removeAttribute("error");
        User usr = null;
        if ((usr=(User)session.getAttribute("user")) == null ) {
            request.setAttribute("error", "You have to be logged in to configure NMS");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        String hostservice_id = request.getParameter("hostservice_id");
        Hashtable<Integer, Host> Hosts = DataManager.getInstance().GetAllHosts();
        Hashtable<Integer, HostService> HostServices = DataManager.getInstance().GetAllHostService();
        Hashtable<Integer, Service> Services = DataManager.getInstance().GetAllServices();
        
        request.setAttribute("hosts", Hosts);
        request.setAttribute("services", Services);
        request.setAttribute("hostservices", HostServices);
        
        String err = "";
        
        if (request.getParameter("hostservice_edit")!=null){
            if (hostservice_id == null) {
                request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
                return;
            }
            request.setAttribute("host", Hosts.get(Integer.parseInt(request.getParameter("host_id"))));
            request.setAttribute("service", Services.get(Integer.parseInt(request.getParameter("service_id"))));
            request.setAttribute("hostservice", HostServices.get(Integer.parseInt(request.getParameter("hostservice_id"))));
            request.getRequestDispatcher("/core/configure/configure_match_edit.jsp").forward(request, response);
            return;
            
        }else if(request.getParameter("hostservice_start")!= null){
            if (hostservice_id == null) {
                request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
                return;
            }
            SNMPManager snmp = SNMPManager.getInstance();
            snmp.StartHostService(HostServices.get(Integer.parseInt(hostservice_id)));
            DataManager data = DataManager.getInstance();
            HostService hostserv = HostServices.get(Integer.parseInt(hostservice_id));
            hostserv.setActive(true);
            data.UpdateHostService(hostserv);
            request.setAttribute("hostservices", data.GetAllHostService());
            request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
            return;            
        }
        else if(request.getParameter("hostservice_stop")!= null){
            if (hostservice_id == null) {
                request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
                return;
            }
            SNMPManager snmp = SNMPManager.getInstance();
            snmp.StopHostService(HostServices.get(Integer.parseInt(hostservice_id)));
            DataManager data = DataManager.getInstance();
            HostService hostserv = HostServices.get(Integer.parseInt(hostservice_id));
            hostserv.setActive(false);
            data.UpdateHostService(hostserv);
            request.setAttribute("hostservices", data.GetAllHostService());
            request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
            return;            
        }
        else if(request.getParameter("hostservice_startall")!= null){
            SNMPManager snmp = SNMPManager.getInstance();
            DataManager data = DataManager.getInstance();
            Hashtable<Integer, HostService> hostservice = data.GetAllHostService(new Host(Integer.parseInt(request.getParameter("host_id"))));
            snmp.StartAllHostService(hostservice);
            Iterator<Map.Entry<Integer,HostService>> it = hostservice.entrySet().iterator();
            while ( it.hasNext() ) {
                  Map.Entry<Integer,HostService> entry = it.next();
                  HostService hh = entry.getValue();
                  hh.setActive(true);
                  data.UpdateHostService(hh);
            }
            request.setAttribute("hostservices", data.GetAllHostService());
            request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
            return;            
        }
        else if(request.getParameter("hostservice_stopall")!= null){
            SNMPManager snmp = SNMPManager.getInstance();
            DataManager data = DataManager.getInstance();
            Hashtable<Integer, HostService> hostservice = data.GetAllHostService(new Host(Integer.parseInt(request.getParameter("host_id"))));
            snmp.StopAllHostService(hostservice);
            Iterator<Map.Entry<Integer,HostService>> it = hostservice.entrySet().iterator();
            while ( it.hasNext() ) {
                Map.Entry<Integer,HostService> entry = it.next();
                HostService hh = entry.getValue();
                hh.setActive(false);
                data.UpdateHostService(hh);
            }
            request.setAttribute("hostservices", data.GetAllHostService());
            request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
            return;            
        }
        else if(request.getParameter("hostservice_edited")!= null){
            if (hostservice_id == null) {
                request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
                return;
            }            
            Host h = new Host(Integer.parseInt(request.getParameter("host_id")));
            Service s = new Service(Integer.parseInt(request.getParameter("service_id")));
            String thd = request.getParameter("thd_value");
            long timeout = 10;
            if (!request.getParameter("timeout_value").isEmpty())
                timeout = Long.parseLong(request.getParameter("timeout_value"));
                       
            HostService hostservice = new HostService(thd, h, s, timeout);
            hostservice.setId(Integer.parseInt(hostservice_id));
            DataManager data = DataManager.getInstance();
            data.UpdateHostService(hostservice);
            request.setAttribute("hostservices", data.GetAllHostService());
            
            request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
            return;
        }
        else if(request.getParameter("hostservice_remove")!= null){
            if (hostservice_id != null) {
                DataManager data = DataManager.getInstance();
                
                
                data.DeleteHostService(new HostService(Integer.parseInt(hostservice_id)));
                request.setAttribute("hostservices", data.GetAllHostService());
            }           
            request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
            return;
        }else if (request.getParameter("hostservice_add") != null){
            request.setAttribute("host", Hosts.get(Integer.parseInt(request.getParameter("host_id"))));
            request.setAttribute("service", Services.get(Integer.parseInt(request.getParameter("service_id"))));
            
            request.getRequestDispatcher("/core/configure/configure_match_add.jsp").forward(request, response);
            return;
        }
        else if (request.getParameter("hostservice_added") != null){
            
            Host h = new Host(Integer.parseInt(request.getParameter("host_id")));
            Service s = new Service(Integer.parseInt(request.getParameter("service_id")));
            String thd = request.getParameter("thd_value");
            long timeout = 10;
            if (!request.getParameter("timeout_value").isEmpty())
                timeout = Long.parseLong(request.getParameter("timeout_value"));
                       
            HostService hostservice = new HostService(thd, h, s, timeout);
            
            DataManager data = DataManager.getInstance();
            data.AddHostService(hostservice);
            request.setAttribute("hostservices", data.GetAllHostService());
            
            request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
            return;
            
        }
        else{
            
            request.getRequestDispatcher("/core/configure/configure_match.jsp").forward(request, response);
            return;
        }        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
