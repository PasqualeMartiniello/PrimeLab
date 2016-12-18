/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.unisa.primeLab.Config;
import it.unisa.gitdm.bean.Evaluation;
import it.unisa.gitdm.bean.Metric;
import it.unisa.gitdm.bean.Model;
import it.unisa.gitdm.bean.MyClassifier;
import it.unisa.gitdm.bean.Project;
import it.unisa.gitdm.bean.ProjectCross;
import it.unisa.gitdm.init.servlet.*;
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
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;

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
        
        // GET REQUEST PARAMETER
        String github = request.getParameter("github");
        Project curr = new Project(github);
        ProjectHandler.setCurrentProject(curr);
        String issueTracker = request.getParameterValues("issueTracker")[0];
        
        boolean isCross = false;
        String[] checkedProjects = null;
        if(request.getParameterValues("typeProject")[0].equals("Cross Project")){
            isCross = true;
            checkedProjects = request.getParameterValues("projects");
        }
        
        ArrayList<ProjectCross> projects = null;
        if(isCross) {
            projects = new ArrayList<ProjectCross>();
            for(String p : checkedProjects) {
                projects.add(new ProjectCross(p, "link//" + p));
            }
        } else {
           System.out.println("Non sono cross, ma sono Within");
        }
        
        
        String[] checkedMetrics = request.getParameterValues("metrics");
        ArrayList<Metric> metrics = new ArrayList<Metric>();
        //System.out.println(issueTracker);
        for (String s : checkedMetrics) {
           // System.out.println(s);
            metrics.add(new Metric(s));
        }
        System.out.println(metrics);
        MyClassifier classifier = ClassifierBuilder.buildClassifier(request.getParameterValues("classifier")[0]);
        System.out.println(classifier);
        String dirName = github.split(".com/")[1].split(".git")[0];
        String[] splitted = dirName.split("/");
        String projName = splitted[splitted.length - 1];
        String projFolderPath = "" + Config.baseDir + projName;
        String clonePath = "" + Config.baseDir + projName + "/" + projName;
        Model model = ModelBuilder.buildModel(curr.getName(), curr.getGitURL(), isCross, projects, metrics, classifier);
        if (model == null) { // calculate evaluation
            session.setAttribute("messaggio2", "Model not found");
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/servletResponse.jsp");
            rd.forward(request, response);
        }
        else{   //SHOW EXISTING EVALUATION
            Evaluation eval = DataExtractor.getEvaluation(projFolderPath, projName, model);
            session.setAttribute("accuracy", eval.getEvaluationSummary().getAccuracy());
            session.setAttribute("precision", eval.getEvaluationSummary().getPrecision());
            session.setAttribute("recall", eval.getEvaluationSummary().getRecall());
            session.setAttribute("fmeasure", eval.getEvaluationSummary().getFmeasure());
            session.setAttribute("areaUnderROC", eval.getEvaluationSummary().getAreaUnderRoc());
            session.setAttribute("modello", model);
            session.setAttribute("predictors", eval.getAnalyzedClasses());
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/prediction.jsp");
            rd.forward(request, response);
        }
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
