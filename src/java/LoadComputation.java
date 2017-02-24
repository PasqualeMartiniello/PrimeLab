/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import it.unisa.gitdm.bean.Evaluation;
import it.unisa.gitdm.bean.Metric;
import it.unisa.gitdm.bean.Model;
import it.unisa.gitdm.bean.MyClassifier;
import it.unisa.gitdm.bean.Project;
import it.unisa.primeLab.Config;
import it.unisa.primeLab.ProjectHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(urlPatterns = {"/LoadComputation"})
public class LoadComputation extends HttpServlet {

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
            out.println("<title>Servlet LoadComputation</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadComputation at " + request.getContextPath() + "</h1>");
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
            String projName = request.getParameter("projName");
            String isCrossS = request.getParameter("isCross");
            String github = request.getParameter("github");
            boolean isCross;
            ArrayList<String> projects;
            
            if(isCrossS.equals("true")){
                isCross = true;
                String stringProjects = request.getParameter("projects");
                stringProjects = stringProjects.replace("[", "");
                stringProjects = stringProjects.replace("]","");
                String[] split = stringProjects.split(";");
                projects = new ArrayList<String>();
                for(String p : split) {
                    projects.add(p);
                }
            } else {
                isCross = false;
                projects = null;
            }
            String stringMetrics = request.getParameter("metrics");
            ArrayList<Metric> metrics = new ArrayList<Metric>();
            stringMetrics = stringMetrics.replace("[", "");
            stringMetrics = stringMetrics.replace("]", "");
            String[] split = stringMetrics.split(",");
            for(String s : split){
                if (s.charAt(0) == ' '){
                    s = s.replace(" ", "");
                }
                metrics.add(new Metric(s));
            }
            String classifierName = request.getParameterValues("classifier")[0];
            Model newModel = new Model("Model", projName, github, isCross, projects, metrics, classifierName, "");
            Model toSave = null;
            
            for(Project p: ProjectHandler.getAllProjects()){
                if(p.getModels() != null) {
                    for(Model m : p.getModels()){
                        System.out.println(newModel+" --- "+m+" --- "+newModel.equals(m));
                        if(m.equals(newModel)){
                            toSave = m;
                        }
                    }
                }
            }
            
            String dirName = github.split(".com/")[1].split(".git")[0];
            String[] splitted = dirName.split("/");
            projName = splitted[splitted.length - 1];
            String projFolderPath = Config.baseDir + projName;
            Thread.sleep(1500);
            Evaluation eval = DataExtractor.getEvaluation(projFolderPath, projName, toSave);
            ArrayList<Object> toParse = new ArrayList<Object>();
            toParse.add(eval.getEvaluationSummary());
            toParse.add(toSave);
            toParse.add(eval.getAnalyzedClasses());
            String json = new Gson().toJson(toParse);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoadComputation.class.getName()).log(Level.SEVERE, null, ex);
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
