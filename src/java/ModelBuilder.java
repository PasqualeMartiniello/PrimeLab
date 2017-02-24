
import it.unisa.gitdm.bean.Metric;
import it.unisa.gitdm.bean.Model;
import it.unisa.gitdm.bean.MyClassifier;
import it.unisa.gitdm.bean.Project;
import it.unisa.primeLab.Config;
import it.unisa.primeLab.ProjectHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fabiano
 */
public class ModelBuilder {

    public static Model buildModel(String projName, String projURL, boolean isCross, ArrayList<String> projects, ArrayList<Metric> metrics, String classifier) {
        Project currentProject = ProjectHandler.getCurrentProject();
        int modelsNum = 0;
        File files = new File(Config.baseDir + projName + "/models/");
        for(File f : files.listFiles()){
            modelsNum++;
        }
       
        ArrayList<Model> models = currentProject.getModels();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Model inputModel = new Model("Model"+modelsNum, projName, projURL, isCross, projects, metrics, classifier,dateFormat.format(new Date()));
        if (models != null) {
            for (Model model: models){
                System.out.println(model+" --- "+inputModel+" --- "+model.equals(inputModel));
                if (model.equals(inputModel)) {
                    return model;
                }
            }
            models.add(inputModel);
            Project toUpdate = currentProject;
            toUpdate.setModels(models);
            ProjectHandler.updateProject(currentProject, toUpdate);
            return inputModel;
        } else {
            models = new ArrayList<Model>();
            models.add(inputModel);
            Project toUpdate = currentProject;
            toUpdate.setModels(models);
            ProjectHandler.updateProject(currentProject, toUpdate);
            return inputModel;
        }        
    }
    
}
