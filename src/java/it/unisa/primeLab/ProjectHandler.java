package it.unisa.primeLab;

import it.unisa.gitdm.bean.Metric;
import it.unisa.gitdm.bean.Model;
import it.unisa.gitdm.bean.Project;
import it.unisa.gitdm.experiments.CalculateDeveloperSemanticScattering;
import it.unisa.gitdm.experiments.CalculateDeveloperStructuralScattering;
import it.unisa.gitdm.experiments.Checkout;
import it.unisa.gitdm.init.servlet.CalculateBuggyFiles;
import it.unisa.gitdm.init.servlet.CalculatePredictors;
import it.unisa.gitdm.source.Git;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.bayes.NaiveBayes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fabiano
 */
public class ProjectHandler {

    private static ArrayList<Project> allProjects;
    private static Project currentProject;

    public static synchronized Project getCurrentProject() {
        return currentProject;
    }

    public static synchronized void setCurrentProject(Project newProject) {
        if (allProjects == null) {
            readProjects();
        }
        if (allProjects.contains(newProject)) {
            currentProject = allProjects.get(allProjects.indexOf(newProject));
        } 
    }

    public static synchronized ArrayList<Project> getAllProjects() {
        if (allProjects == null) {
            readProjects();
        }
        return allProjects;
    }

    public static synchronized void addProject(Project p) {
        if (allProjects == null) {
            readProjects();
        }
        if (!allProjects.contains(p)) {
            allProjects.add(p);
        }
        saveProjects();
    }

    public static synchronized void updateProject(Project p1, Project p2) {
        if (allProjects == null) {
            readProjects();
        }
        allProjects.set(allProjects.indexOf(p1), p2);
        saveProjects();
    }

    private static void readProjects() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(Config.baseDir + "projects.txt"));
            allProjects = (ArrayList<Project>) in.readObject();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(ProjectHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProjectHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void saveProjects() {
        try {
            FileOutputStream fileOut = new FileOutputStream(Config.baseDir + "projects.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(allProjects);
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
