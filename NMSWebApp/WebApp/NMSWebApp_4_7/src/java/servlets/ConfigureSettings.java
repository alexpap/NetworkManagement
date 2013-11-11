package servlets;

import db.DataManager;
import db.Host;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConfigureSettings extends HttpServlet {

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
        String err = "";
        
        if (request.getParameter("new_user")!=null){
            User user = new User();
            if (!request.getParameter("username").isEmpty() && !request.getParameter("password").isEmpty()){
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                if (!request.getParameter("email").isEmpty())
                    user.setEmail(request.getParameter("email"));
                    if (!DataManager.getInstance().AddUser(user)){
                        err = " Cannot add user to database \n";
                    }else err = "User added succesfully";
            }else
                err = " Please give username and/or password \n";
            
            request.setAttribute("error", err);
            request.getRequestDispatcher("/core/configure/configure_settings.jsp").forward(request, response);
            return;
        }
        else if(request.getParameter("remove_user")!= null){
            User user = new User();
            if (!request.getParameter("username").equalsIgnoreCase("admin")){
                if (!request.getParameter("username").isEmpty()){
                    user.setUsername(request.getParameter("username"));
                    if (!DataManager.getInstance().DeleteUser(user)){
                        err = " Cannot delete user \n";
                    }else err = "User deleted succesfully";
                }else 
                    err = " No username inserted \n";
            }else
                err = " Cannot remove Admin \n";
            
            request.setAttribute("error", err);
            request.getRequestDispatcher("/core/configure/configure_settings.jsp").forward(request, response);
            return;
        }
        else if(request.getParameter("change_database")!= null){
            String name = request.getParameter("name");
            String ip = request.getParameter("ip");
            String port = request.getParameter("port");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (!name.isEmpty() && !ip.isEmpty() && !username.isEmpty()){
                if (port.isEmpty()){
                    port = "3306";
                    err += "Using default port: 3306 \n";
                }
                if (!DataManager.getInstance().changeDB(name, ip, port, username, password)){
                    err += " Cannot connect to given database \n";
                } 
                else err = "Database changedS succesfully";
            }else
                err += name+" "+ip+" "+username+" Please add ALL required values \n";
            
            request.setAttribute("error", err);
            request.getRequestDispatcher("/core/configure/configure_settings.jsp").forward(request, response);
            return;
        }
        else{
            request.getRequestDispatcher("/core/configure/configure_settings.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/core/configure/configure_settings.jsp").forward(request, response);
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