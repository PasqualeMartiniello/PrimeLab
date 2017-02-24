/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import it.unisa.gitdm.bean.Evaluation;
import it.unisa.gitdm.bean.EvaluationSummary;
import it.unisa.gitdm.bean.Metric;
import it.unisa.gitdm.bean.Model;
import it.unisa.gitdm.bean.Project;
import it.unisa.primeLab.Config;
import it.unisa.primeLab.ProjectHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * @author fabiano
 */
@WebServlet(urlPatterns = {"/CompareServlet"})
public class CompareServlet extends HttpServlet {

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
            out.println("<title>Servlet CompareServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CompareServlet at " + request.getContextPath() + "</h1>");
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
            String[] modelToCompare = request.getParameterValues("table_records");
            ArrayList<String> projectNames = new ArrayList<String>();
            ArrayList<String> githubs = new ArrayList<String>();
            ArrayList<String> metrics = new ArrayList<String>();
            ArrayList<String> isCross = new ArrayList<String>();
            ArrayList<String> projects = new ArrayList<String>();
            ArrayList<String> classifiers = new ArrayList<String>();
            
            for(String s : modelToCompare){
                String[] split = s.split("-");
                projectNames.add(split[0]);
                isCross.add(split[1]);
                projects.add(split[2]);
                githubs.add(split[3]);
                metrics.add(split[4]);
                classifiers.add(split[5]);
            }
            
            ArrayList<EvaluationSummary> evaluations = new ArrayList<EvaluationSummary>();
            
            for(int i = 0; i < projectNames.size(); i++){
                String projName = projectNames.get(i);
                String isCrossS = isCross.get(i);
                String githubS = githubs.get(i);
                boolean check;
                ArrayList<String> projectS;
                
                if(isCrossS.equals("true")){
                    check = true;
                    String stringProjects = projects.get(i);
                    stringProjects = stringProjects.replace("[", "");
                    stringProjects = stringProjects.replace("]","");
                    String[] split = stringProjects.split(";");
                    projectS = new ArrayList<String>();
                    for(String p : split) {
                        projectS.add(p);
                    }
                } else {
                    check = false;
                    projectS = null;
                }
                String stringMetrics = metrics.get(i);
                ArrayList<Metric> metricS = new ArrayList<Metric>();
                stringMetrics = stringMetrics.replace("[", "");
                stringMetrics = stringMetrics.replace("]", "");
                String[] split = stringMetrics.split(",");
                for(String s : split){
                    if (s.charAt(0) == ' '){
                        s = s.replace(" ", "");
                    }
                    metricS.add(new Metric(s));
                }
                String classifierName = classifiers.get(i);
                Model newModel = new Model("Model", projName, githubS, check, projectS, metricS, classifierName, "");
                Model toSave = null;
               
                for (Project p : ProjectHandler.getAllProjects()) {
                    if (p.getModels() != null) {
                        for (Model m : p.getModels()) {
                            System.out.println(newModel + " --- " + m + " --- " + newModel.equals(m));
                            if (m.equals(newModel)) {
                                toSave = m;
                            }
                        }
                    }
                }
                String projFolderPath = Config.baseDir + projName;

                evaluations.add(DataExtractor.getEvaluation(projFolderPath, projName, toSave).getEvaluationSummary());
                
            }
            Thread.sleep(1500);
            String json = new Gson().toJson(evaluations);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } catch (InterruptedException ex) {
            Logger.getLogger(CompareServlet.class.getName()).log(Level.SEVERE, null, ex);
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
