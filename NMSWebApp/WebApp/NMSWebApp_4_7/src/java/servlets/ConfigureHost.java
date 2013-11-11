package servlets;

import db.DataManager;
import db.Host;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConfigureHost extends HttpServlet {

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
        String host_id = request.getParameter("host_id");
        Hashtable<Integer, Host> Hosts = DataManager.getInstance().GetAllHosts();
        request.setAttribute("hosts", Hosts);
        
        String err = "";
        
        if (request.getParameter("host_edit")!=null){
            if (host_id == null) {
                request.getRequestDispatcher("/core/configure/configure_host.jsp").forward(request, response);
                return;
            }
            DataManager data = DataManager.getInstance();
            Hashtable<Integer, Host> hosts = data.GetAllHosts();
            Host host = hosts.get(Integer.parseInt(host_id));
            request.setAttribute("host_to_be_edit", host);
            request.getRequestDispatcher("/core/configure/configure_host_edit.jsp").forward(request, response);
            return;
            
        }
        else if(request.getParameter("host_edited")!= null){
            if (host_id == null) {
                request.getRequestDispatcher("/core/configure/configure_host.jsp").forward(request, response);
                return;
            }
            Host h = new Host(Integer.parseInt(host_id));            
            h.setName(request.getParameter("host_name"));
            h.setIp(request.getParameter("host_ip"));
            h.setPort(request.getParameter("host_port"));
            h.setTrap_port(request.getParameter("host_trap_port"));
            if (h.getIp() != null && !h.getName().isEmpty() && !h.getTrap_port().isEmpty() && h.getPort() != null){
                DataManager data = DataManager.getInstance();
                if (!h.getIp().isEmpty() && h.getIp().matches("[1-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}")) // check ip
                    if (!h.getPort().isEmpty() && h.getPort().matches("[0-9]+")){      // check 
                        data.UpdateHost(h); //update this host to db
                        request.setAttribute("hosts", data.GetAllHosts());
                    }
                    else err += " Port failure";
                else err+= " Ip failure";
            }
            else err += " No input";
            request.setAttribute("error", err);
            if (!err.isEmpty()){
                DataManager data = DataManager.getInstance();
                Hashtable<Integer, Host> hosts = data.GetAllHosts();
                Host host = hosts.get(Integer.parseInt(host_id));
                request.setAttribute("host_to_be_edit", host);
                request.getRequestDispatcher("/core/configure/configure_host_edit.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("/core/configure/configure_host.jsp").forward(request, response);
            return;
        }
        else if(request.getParameter("host_remove")!= null){
            if (host_id != null) {
                DataManager data = DataManager.getInstance();
                data.DeleteHost(new Host(Integer.parseInt(host_id)));
                request.setAttribute("hosts", data.GetAllHosts());
            }           
            request.getRequestDispatcher("/core/configure/configure_host.jsp").forward(request, response);
            return;
        }
        else if(request.getParameter("host_add")!= null){
            request.getRequestDispatcher("/core/configure/configure_host_add.jsp").forward(request, response);
            return;
        }
        else if (request.getParameter("host_added") != null){
            Host h = new Host();
            h.setName(request.getParameter("host_name"));
            h.setIp(request.getParameter("host_ip"));
            h.setPort(request.getParameter("host_port"));
            h.setTrap_port(request.getParameter("host_trap_port"));
            if (h.getIp() != null && !h.getName().isEmpty() && !h.getTrap_port().isEmpty() && h.getPort() != null){
                DataManager data = DataManager.getInstance();
                if (!h.getIp().isEmpty() && h.getIp().matches("[1-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}")) // check ip
                    if (!h.getPort().isEmpty() && h.getPort().matches("[0-9]+")){      // check 
                        data.AddHost(h);    //add this host to db
                        request.setAttribute("hosts", data.GetAllHosts());
                    }
                    else err += " Port failure";
                else err+= " Ip failure";
            }
            else err += " No input";
            request.setAttribute("error", err);
            if (!err.isEmpty()){
                request.getRequestDispatcher("/core/configure/configure_host_add.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("/core/configure/configure_host.jsp").forward(request, response);
            return;
            
        }
        else{
            
            request.getRequestDispatcher("/core/configure/configure_host.jsp").forward(request, response);
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
