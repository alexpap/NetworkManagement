/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DataManager;
import db.Host;
import db.HostService;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author andreas
 */
public class MonitorHost extends HttpServlet {

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
            request.setAttribute("error", "You have to be logged in to monitor NMS");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        String hostservice_id = request.getParameter("hostservice_id");
        request.setAttribute("hosts", DataManager.getInstance().GetAllHosts());
        request.setAttribute("services", DataManager.getInstance().GetAllServices());
        request.setAttribute("hostservices", DataManager.getInstance().GetAllHostService());

        String err = "";
        
        if (request.getParameter("hostservice_monitor")!=null){
            if (hostservice_id == null) {
                request.getRequestDispatcher("/core/monitor/monitor_host.jsp").forward(request, response);
                return;
            }
            HostService hh = DataManager.getInstance().GetAllHostService().get(Integer.parseInt(hostservice_id));
            request.setAttribute("host",DataManager.getInstance().GetAllHosts().get(hh.getHost().getId()));
            request.setAttribute("service",DataManager.getInstance().GetAllServices().get(hh.getService().getId()));
            request.setAttribute("values",DataManager.getInstance().GetAllValues(hh, 10));
            
            request.getRequestDispatcher("/core/monitor/monitor_host_service_value.jsp").forward(request, response);
            return;
            
        }else{
            
            request.getRequestDispatcher("/core/monitor/monitor_host.jsp").forward(request, response);
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
