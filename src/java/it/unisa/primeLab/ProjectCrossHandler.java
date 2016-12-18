/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.primeLab;

import it.unisa.gitdm.bean.ProjectCross;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author pasqualemartiniello
 */
public class ProjectCrossHandler {
    
    
    private static ArrayList<ProjectCross> allProjects;

    public static synchronized ArrayList<ProjectCross> getAllProjects() {
        if (allProjects == null) {
            readProjects();
        }
        return allProjects;
    }

    public static synchronized void addProject(ProjectCross p) {
        if (allProjects == null) {
            readProjects();
        }
        if (!allProjects.contains(p)) {
            allProjects.add(p);
        }
        saveProjects();
    }

    public static synchronized void updateProject(ProjectCross p1, ProjectCross p2) {
        if (allProjects == null) {
            readProjects();
        }
        allProjects.set(allProjects.indexOf(p1), p2);
        saveProjects();
    }

    private static void readProjects() {
       
        try {
            if(new File(Config.baseDir + "projectsCross.txt").exists()) {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(Config.baseDir + "projectsCross.txt"));
                allProjects = (ArrayList<ProjectCross>) in.readObject();
                in.close();
            } else {
                allProjects = new ArrayList<ProjectCross>();
            }
        } catch (IOException ex) {
            Logger.getLogger(ProjectHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProjectHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void saveProjects() {
        try {
            FileOutputStream fileOut = new FileOutputStream(Config.baseDir + "projectsCross.txt");
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
