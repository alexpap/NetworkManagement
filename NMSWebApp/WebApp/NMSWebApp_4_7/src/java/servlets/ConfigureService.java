package servlets;

import db.DataManager;
import db.Host;
import db.Service;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConfigureService extends HttpServlet {

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
        String service_id = request.getParameter("service_id");
        Hashtable<Integer, Service> Services = DataManager.getInstance().GetAllServices();
        request.setAttribute("services", Services);
        
        String err = "";
        
        if (request.getParameter("service_edit")!=null){
            if (service_id == null) {
                request.getRequestDispatcher("/core/configure/configure_service.jsp").forward(request, response);
                return;
            }
            DataManager data = DataManager.getInstance();
            Hashtable<Integer, Service> services = data.GetAllServices();
            Service service = services.get(Integer.parseInt(service_id));
            request.setAttribute("service_to_be_edit", service);
            request.getRequestDispatcher("/core/configure/configure_service_edit.jsp").forward(request, response);
            return;
            
        }
        else if(request.getParameter("service_edited")!= null){
            if (service_id == null) {
                request.getRequestDispatcher("/core/configure/configure_service.jsp").forward(request, response);
                return;
            }
            Service s = new Service(Integer.parseInt(service_id));
            s.setName(request.getParameter("service_name"));
            s.setOid(request.getParameter("service_oid"));
            s.setType(request.getParameter("service_type"));
            s.setDesc(request.getParameter("service_desc"));
            if (!s.getOid().isEmpty() && !s.getName().isEmpty() && !s.getDesc().isEmpty()&& !s.getType().isEmpty()){
                DataManager data = DataManager.getInstance();
                data.UpdateService(s); //update this service to db
                request.setAttribute("services", data.GetAllServices());
            }
            else err += " No input";
            request.setAttribute("error", err);
            if (!err.isEmpty()){
                DataManager data = DataManager.getInstance();
                Hashtable<Integer, Service> services = data.GetAllServices();
                Service service = services.get(Integer.parseInt(service_id));
                request.setAttribute("service_to_be_edit", service);
                request.getRequestDispatcher("/core/configure/configure_service_edit.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("/core/configure/configure_service.jsp").forward(request, response);
            return;
        }
        else if(request.getParameter("service_remove")!= null){
            if (service_id != null) {
                DataManager data = DataManager.getInstance();
                data.DeleteService(new Service(Integer.parseInt(service_id)));
                request.setAttribute("services", data.GetAllServices());
            }           
            request.getRequestDispatcher("/core/configure/configure_service.jsp").forward(request, response);
            return;
        }
        else if(request.getParameter("service_add")!= null){
            request.getRequestDispatcher("/core/configure/configure_service_add.jsp").forward(request, response);
            return;
        }
        else if (request.getParameter("service_added") != null){
            Service s = new Service();
            s.setName(request.getParameter("service_name"));
            s.setType(request.getParameter("service_type"));
            s.setOid(request.getParameter("service_oid"));
            s.setDesc(request.getParameter("service_desc"));
            if (!s.getOid().isEmpty() && !s.getName().isEmpty() && !s.getDesc().isEmpty()&& !s.getType().isEmpty()){
                DataManager data = DataManager.getInstance();
                data.AddService(s); //add this service to db
                request.setAttribute("services", data.GetAllServices());
            }
            else err += " No input";
            request.setAttribute("error", err);
            if (!err.isEmpty()){
                request.getRequestDispatcher("/core/configure/configure_service_add.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("/core/configure/configure_service.jsp").forward(request, response);
            return;
            
        }
        else{
            
            request.getRequestDispatcher("/core/configure/configure_service.jsp").forward(request, response);
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
