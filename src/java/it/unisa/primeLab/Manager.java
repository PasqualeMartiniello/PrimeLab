/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.primeLab;

import it.unisa.gitdm.experiments.CalculateDeveloperSemanticScattering;
import it.unisa.gitdm.experiments.CalculateDeveloperStructuralScattering;
import it.unisa.gitdm.experiments.Checkout;
import it.unisa.gitdm.init.servlet.CalculateBuggyFiles;
import it.unisa.gitdm.init.servlet.CalculatePredictors;
import it.unisa.gitdm.source.Git;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pasqualemartiniello
 */
public class Manager {
    
    
    public static void createCsv(String repoURL, String baseFolder, String projectName, String periodLength, String scatteringFolderPath, String issueTracker, String issueTrackerPath, String productName, boolean initRepository, boolean initIssueTracker, boolean isSVN){
        try {
            Git.clone(repoURL, isSVN, projectName, baseFolder);
            new Checkout(projectName, periodLength, baseFolder, scatteringFolderPath, initRepository);
            new CalculateDeveloperStructuralScattering(projectName, periodLength, scatteringFolderPath);
            new CalculateDeveloperSemanticScattering(projectName, periodLength, baseFolder, scatteringFolderPath);
            new CalculateBuggyFiles(scatteringFolderPath, projectName, issueTracker, issueTrackerPath, productName, initIssueTracker, false, isSVN);
            new CalculatePredictors(projectName, issueTracker, issueTrackerPath, productName, periodLength, baseFolder, scatteringFolderPath);
        } catch (IOException ex) {
            Logger.getLogger(ProjectHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProjectHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
