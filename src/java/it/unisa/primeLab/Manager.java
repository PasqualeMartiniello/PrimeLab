/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.primeLab;

import it.unisa.gitdm.bean.Project;
import it.unisa.gitdm.evaluation.WekaEvaluator;
import it.unisa.gitdm.experiments.CalculateDeveloperSemanticScattering;
import it.unisa.gitdm.experiments.CalculateDeveloperStructuralScattering;
import it.unisa.gitdm.experiments.Checkout;
import it.unisa.gitdm.init.servlet.CalculateBuggyFiles;
import it.unisa.gitdm.init.servlet.CalculatePredictors;
import it.unisa.gitdm.source.Git;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author pasqualemartiniello
 */
public class Manager implements Runnable{
    
    String repoURL;
    String baseFolder; 
    String projectName;
    String periodLength;
    String scatteringFolderPath;
    String issueTracker;
    String issueTrackerPath;
    String productName;
    boolean initRepository;
    boolean initIssueTracker;
    boolean isSVN;
    String emailTo;
    
    public Manager(String repoURL, String baseFolder, String projectName, String periodLength, String scatteringFolderPath, String issueTracker, String issueTrackerPath, String productName, boolean initRepository, boolean initIssueTracker, boolean isSVN, String emailTo){
        this.repoURL = repoURL;
        this.baseFolder = baseFolder;
        this.projectName = projectName;
        this.periodLength = periodLength;
        this.scatteringFolderPath = scatteringFolderPath;
        this.issueTracker = issueTracker;
        this.issueTrackerPath = issueTrackerPath;
        this.productName = productName;
        this.initRepository = initRepository;
        this.initIssueTracker = initIssueTracker;
        this.isSVN = isSVN;
        this.emailTo = emailTo;
    }
    
    public void run(){
            createCsv(repoURL, baseFolder, projectName, periodLength, scatteringFolderPath, issueTracker, issueTrackerPath, productName, initRepository, initIssueTracker, isSVN);
            sendEmail(emailTo);
            ProjectHandler.addProject(new Project(projectName, repoURL, null));
            new File(baseFolder + "/models/").mkdir();    
        
    }
    
    private static void createCsv(String repoURL, String baseFolder, String projectName, String periodLength, String scatteringFolderPath, String issueTracker, String issueTrackerPath, String productName, boolean initRepository, boolean initIssueTracker, boolean isSVN){
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
 
    private static void sendEmail(String toEmail) {
        String from = ""/*Insert the email*/;
        String host = " smtp.live.com";
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.live.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getDefaultInstance(props, null);
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("PrimeLab Notification Message");
            message.setText("Thanks for your waiting. \nYour file is ready. Now you can start using it for calculate a new prediction\n Thanks for using PrimaLab");
            Transport.send(message, from, "");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    
}
