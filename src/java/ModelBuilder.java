
import it.unisa.gitdm.bean.Metric;
import it.unisa.gitdm.bean.Model;
import it.unisa.gitdm.bean.MyClassifier;
import it.unisa.gitdm.bean.ProjectCross;
import it.unisa.primeLab.ProjectHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
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

    public static Model buildModel(String projName, String projURL, boolean isCross, ArrayList<ProjectCross> projects, ArrayList<Metric> metrics, MyClassifier classifier) {
        ArrayList<Model> models = ProjectHandler.getCurrentProject().getModels();
        Model inputModel = new Model("Model", projName, projURL, isCross, projects, metrics, classifier,"");
        if (models != null) {
            for (Model model: models){
                System.out.println(model+" --- "+inputModel+" --- "+model.equals(inputModel));
                if (model.equals(inputModel)) {
                    return model;
                }
            }
        } 
        return null;
    }
    
}
