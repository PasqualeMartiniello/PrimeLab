package it.unisa.gitdm.init.servlet;

import it.unisa.primeLab.ProjectHandler;
import it.unisa.primeLab.ProjectCrossHandler;
import it.unisa.gitdm.bean.Developer;
import it.unisa.gitdm.bean.DeveloperTree;
import it.unisa.gitdm.bean.Metric;
import it.unisa.gitdm.bean.Model;
import it.unisa.gitdm.bean.MyClassifier;
import it.unisa.gitdm.bean.Project;
import it.unisa.gitdm.bean.ProjectCross;
import it.unisa.gitdm.evaluation.WekaEvaluator;
import it.unisa.gitdm.experiments.CalculateDeveloperSemanticScattering;
import it.unisa.gitdm.experiments.CalculateDeveloperStructuralScattering;
import it.unisa.gitdm.experiments.Checkout;
import it.unisa.gitdm.source.Git;
import it.unisa.primeLab.Config;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.J48;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String repoURL = "https://github.com/apache/ant.git";
        //String repoURL = "https://github.com/fabianopecorelli/provaPerTesi.git";
        String projectName = "ant";
        String where = "/home/sesa/Scrivania/gitdm/";
        String scatteringFolder = "/home/sesa/Scrivania/gitdm/scattering/";
        String issueTracker = "bugzilla";
        String bugzillaUrl = "https://issues.apache.org/bugzilla/";
        //classifier
        J48 j48 = new J48();
        String classifierName = "j48";
        String modelName = "myModel";

        Main.initAndCheckout(repoURL, where, projectName, "All", scatteringFolder, issueTracker, bugzillaUrl, "Ant", false, false, false, j48, classifierName, modelName);
    }

    public static void initAndCheckout(String repoURL, String baseFolder, String projectName, String periodLength,
            String scatteringFolderPath, String issueTracker, String issueTrackerPath, String productName, boolean initRepository, boolean initIssueTracker, boolean isSVN, Classifier classifier, String classifierName, String modelName) throws IOException, InterruptedException {
//        Git.clone(repoURL, isSVN, projectName, baseFolder);
        //      Checkout checkout = new Checkout(projectName, periodLength, baseFolder, scatteringFolderPath, initRepository);
        //    CalculateDeveloperStructuralScattering calculateDeveloperStructuralScattering = new CalculateDeveloperStructuralScattering(projectName, periodLength, scatteringFolderPath);
        //  CalculateDeveloperSemanticScattering calculateDeveloperSemanticScattering = new CalculateDeveloperSemanticScattering(projectName, periodLength, baseFolder, scatteringFolderPath);
//        CalculateBuggyFiles calculateBuggyFiles = new CalculateBuggyFiles(scatteringFolderPath, projectName, issueTracker, issueTrackerPath, productName, initIssueTracker, false, isSVN);
        // CalculatePredictors calculatePredictors = new CalculatePredictors(projectName, issueTracker, issueTrackerPath, productName, periodLength, baseFolder, scatteringFolderPath);
        //       WekaEvaluator we = new WekaEvaluator(baseFolder, projectName, classifier, classifierName, modelName);
        
            ArrayList<Model> models = new ArrayList<Model>();
            ArrayList<Metric> metrics = new ArrayList<Metric>();
            metrics.add(new Metric("CK Metrics"));
            metrics.add(new Metric("Process"));
            metrics.add(new Metric("Scattering"));
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            String now = dateFormat.format(date);
            MyClassifier c1 = new MyClassifier("Naive Baesian");
            c1.setClassifier(new NaiveBayes());
            ArrayList<ProjectCross> p = new ArrayList<ProjectCross>();
            p.add(new ProjectCross("Progetto 1", "link//Progetto 1"));
            p.add(new ProjectCross("Progetto 2", "link//Progetto 2"));
            p.add(new ProjectCross("Progetto 3", "link//Progetto 3"));
            models.add(new Model("AAA", "ant", "https://github.com/apache/ant.git", true, p, metrics, c1, now));
            ArrayList<Metric> metrics2 = metrics;
            metrics2.remove(metrics.get(1));
            MyClassifier c2 = new MyClassifier("Logistic Regression");
            c2.setClassifier(new Logistic());
            models.add(new Model("BBB", "ant", "https://github.com/apache/ant.git", false, null, metrics2, c2, now));
            Project p1 = new Project("https://github.com/apache/ant.git");
            p1.setModels(models);
            p1.setName("ant");
            
            ArrayList<Project> projects = new ArrayList<Project>();
            projects.add(p1);
            try {
            FileOutputStream fileOut = new FileOutputStream(Config.baseDir + "projects.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(projects);
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
            ProjectHandler.setCurrentProject(p1);
            Project curr = ProjectHandler.getCurrentProject();
            System.out.println(curr.getGitURL()+" --- "+curr.getModels());
            //ProjectHandler.addProject(p1);
            
            System.out.println(ProjectCrossHandler.getAllProjects());
                    
    }
}
