/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.unisa.gitdm.bean.Project;
import it.unisa.gitdm.experiments.CalculateDeveloperSemanticScattering;
import it.unisa.gitdm.experiments.CalculateDeveloperStructuralScattering;
import it.unisa.gitdm.experiments.Checkout;
import it.unisa.gitdm.source.Git;
import it.unisa.primeLab.Config;
import it.unisa.primeLab.Manager;
import it.unisa.primeLab.ProjectHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pasqualemartiniello
 */
@WebServlet(urlPatterns = {"/AddProjectServlet"})
public class AddProjectServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddProjectServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProjectServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        
        Project toAdd = null;
        String type = request.getParameter("type");
        if(type.equals("section1")) {
            String github = request.getParameter("github");
            String dirName = github.split(".com/")[1].split(".git")[0];
            String[] splitted = dirName.split("/");
            String projName = splitted[splitted.length - 1];
            String dirNameScattering = Config.baseDir + projName + "/scattering/";
            String issueTracker = "bugzilla";
            String issueTrackerUrl = request.getParameter("issueTracker");
            toAdd = new Project(projName, github, null);
            String baseDir = Config.baseDir + projName + "/";
            //Manager.createCsv(github, baseDir, projName, "All", dirNameScattering, issueTracker, issueTrackerUrl, projName, false, false, false);
            
        } else {
            String projName = request.getParameter("projName");
            toAdd = new Project(projName, "", null);
        }
      
        //ProjectHandler.addProject(toAdd);
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
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
