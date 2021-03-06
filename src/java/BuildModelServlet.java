/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.*;
import it.unisa.primeLab.Config;
import it.unisa.gitdm.bean.Evaluation;
import it.unisa.gitdm.bean.Metric;
import it.unisa.gitdm.bean.Model;
import it.unisa.gitdm.bean.MyClassifier;
import it.unisa.gitdm.bean.Project;
import it.unisa.gitdm.evaluation.WekaEvaluator;
import it.unisa.primeLab.Manager;
import it.unisa.primeLab.ProjectHandler;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.Double;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabiano
 */
@WebServlet(name = "BuildModelServlet", urlPatterns = {"/BuildModelServlet"})
public class BuildModelServlet extends HttpServlet {

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
            out.println("<title>Servlet BuildModelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BuildModelServlet at " + request.getContextPath() + "</h1>");
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
        
        try {
            HttpSession session = request.getSession(true);
            // GET REQUEST PARAMETER
            String github = request.getParameter("github");
            Project curr = new Project(github);
            ProjectHandler.setCurrentProject(curr);
            String issueTracker = request.getParameter("issueTracker");
            
            boolean isCross = false;
            String[] checkedProjects = null;
            if(request.getParameterValues("typeProject")[0].equals("Cross Project")){
                isCross = true;
                checkedProjects = request.getParameterValues("projects");
            }
            
            ArrayList<String> projects = null;
            if(isCross) {
                projects = new ArrayList<String>();
                for(String p : checkedProjects) {
                    projects.add(p);
                }
            }
            String[] checkedMetrics = request.getParameterValues("metrics");
            ArrayList<Metric> metrics = new ArrayList<Metric>();
            for (String s : checkedMetrics) {
                metrics.add(new Metric(s));
            }
            String classifierName = request.getParameterValues("classifier")[0];
            String dirName = github.split(".com/")[1].split(".git")[0];
            String[] splitted = dirName.split("/");
            String projName = splitted[splitted.length - 1];
            String projFolderPath = Config.baseDir + projName;
            Model model = ModelBuilder.buildModel(projName, github, isCross, projects, metrics, classifierName);
            
            int modelsNum = 0;
            File files = new File(Config.baseDir + projName + "/models/");
            for(File f : files.listFiles()){
                modelsNum++;
            }
            if(model.getName().equals("Model"+modelsNum)) {
                if (isCross) {
                    new WekaEvaluator(Config.baseDir, projName, classifierName, model.getName(), projects);
                } else {
                    new WekaEvaluator(Config.baseDir, projName, classifierName, model.getName(), null);
                }
            }
            
            Thread.sleep(3000);
            Evaluation eval = DataExtractor.getEvaluation(projFolderPath, projName, model);
            ArrayList<Object> toParse = new ArrayList<Object>();
            
            toParse.add(eval.getEvaluationSummary());
            toParse.add(model);
            toParse.add(eval.getAnalyzedClasses());
            String json = new Gson().toJson(toParse);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } catch (InterruptedException ex) {
            Logger.getLogger(BuildModelServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
