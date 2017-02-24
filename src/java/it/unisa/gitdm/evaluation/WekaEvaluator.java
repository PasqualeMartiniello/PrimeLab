/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.gitdm.evaluation;

import it.unisa.gitdm.bean.Model;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.AggregateableEvaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author pasqualemartiniello
 */
public class WekaEvaluator {

 
    public WekaEvaluator(String baseFolderPath, String projectName, String classifierName, String modelName, ArrayList<String> projects) {
       
        try {
            
            AbstractClassifier classifier = null;
            switch(classifierName) {
                case "Decision Table Majority": classifier = new DecisionTable(); break;
                case "Logistic Regretion": classifier = new Logistic(); break;
                case "Multi Layer Perceptron": classifier = new MultilayerPerceptron(); break;
                case "Naive Baesian": classifier = new NaiveBayes(); break;
                case "Random Forest": classifier = new RandomForest(); break;
                case "Decision Tree": classifier = new J48(); break;
            }
            
            String projectPath = baseFolderPath + projectName + "/predictors.csv";
            DataSource sourceFile = new DataSource(projectPath);
            Instances testSet = sourceFile.getDataSet();
            testSet.setClassIndex(testSet.numAttributes() - 1);
            testSet.deleteAttributeAt(0);
            
            for(Instance instance : testSet){
                for(int i = 0; i < instance.numValues(); i++){
                    if(Double.isInfinite(instance.value(i))){
                        instance.setValue(i, 1000000000);
                    }
                }
            }
            
            if(projects == null){
                evaluateModelForWithin(baseFolderPath, projectName, classifier, testSet, modelName, classifierName);
            } else {
                Instances trainingSet = null;
                for (String pros : projects) {
                    String filePathPros = baseFolderPath + pros + "/predictors.csv";
                    
                    DataSource sources = new DataSource(filePathPros);
                    if (trainingSet == null) {
                        trainingSet = new Instances(sources.getDataSet());
                    } else {
                        trainingSet.addAll(sources.getDataSet());
                    }
                }
                trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
                trainingSet.deleteAttributeAt(0);
                for(Instance instance : trainingSet){
                for(int i = 0; i < instance.numValues(); i++){
                    if(Double.isInfinite(instance.value(i))){
                        instance.setValue(i, 1000000000);
                    }
                }
            }
                evaluateModelForCross(baseFolderPath, projectName, classifier, testSet, trainingSet, modelName, classifierName);
            }
        } catch (Exception ex) {
            Logger.getLogger(WekaEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void evaluateModelForCross(String baseFolderPath, String projectName, Classifier pClassifier, Instances pTestSet, Instances pTrainingSet, String pModelName, String pClassifierName) throws Exception {

        Evaluation singleEvaluation = new Evaluation(pTrainingSet);
        int positiveValueIndexOfClassFeature = 0;
        
        int classFeatureIndex = 0;
        for (int i = 0; i < pTestSet.numAttributes(); i++) {
            if (pTestSet.attribute(i).name().equals("isBuggy")) {
                classFeatureIndex = i;
                break;
            }
        }
        Attribute classFeature = pTrainingSet.attribute(classFeatureIndex);
        for (int i = 0; i < classFeature.numValues(); i++) {
            if (classFeature.value(i).equals("TRUE")) {
                positiveValueIndexOfClassFeature = i;
            }
        }

        pTrainingSet.setClassIndex(classFeatureIndex);
        pTestSet.setClassIndex(classFeatureIndex);
        pClassifier.buildClassifier(pTrainingSet);  
        singleEvaluation.evaluateModel(pClassifier, pTestSet);
        
        double accuracy
                = (singleEvaluation.numTruePositives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numTrueNegatives(positiveValueIndexOfClassFeature))
                / (singleEvaluation.numTruePositives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numFalsePositives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numFalseNegatives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numTrueNegatives(positiveValueIndexOfClassFeature));

        double fmeasure = 2 * ((singleEvaluation.precision(positiveValueIndexOfClassFeature) * singleEvaluation.recall(positiveValueIndexOfClassFeature))
                / (singleEvaluation.precision(positiveValueIndexOfClassFeature) + singleEvaluation.recall(positiveValueIndexOfClassFeature)));
         
        File wekaOutput = new File(baseFolderPath + projectName + "/models/" + pModelName);
        wekaOutput.mkdirs();
        PrintWriter pw1 = new PrintWriter(wekaOutput + "/wekaOutput.csv");
        
        pw1.write(accuracy + ";" + singleEvaluation.precision(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.recall(positiveValueIndexOfClassFeature) + ";" + fmeasure + ";" + singleEvaluation.areaUnderROC(positiveValueIndexOfClassFeature));
        
        
        System.out.println(projectName + ";" + pClassifierName + ";" + pModelName + ";" + singleEvaluation.numTruePositives(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.numFalsePositives(positiveValueIndexOfClassFeature) + ";" + singleEvaluation.numFalseNegatives(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.numTrueNegatives(positiveValueIndexOfClassFeature) + ";" + accuracy + ";" + singleEvaluation.precision(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.recall(positiveValueIndexOfClassFeature) + ";" + fmeasure + ";" + singleEvaluation.areaUnderROC(positiveValueIndexOfClassFeature) + "\n");
        
        pw1.close();
    }
    
    private static void evaluateModelForWithin(String baseFolderPath, String projectName, Classifier pClassifier, Instances pDataSet, String pModelName, String pClassifierName) throws Exception{
        
        ArrayList<TrainingTestSet> dataSet = kFolds(pDataSet, 10);
        
        AggregateableEvaluation singleEvaluation = null;

        int positiveValueIndexOfClassFeature = 0;
        for(int n = 0; n < dataSet.size(); n++) {
            Instances train = dataSet.get(n).getTrainingSet();
            Instances test = dataSet.get(n).getTestSet();
            Evaluation singleFoldEvaluation = new Evaluation(train);
            int classFeatureIndex = 0;
            for (int i = 0; i < test.numAttributes(); i++) {
                if (test.attribute(i).name().equals("isBuggy")) {
                     classFeatureIndex = i;
                     break;
                }
            }
            Attribute classFeature = train.attribute(classFeatureIndex);
            for (int i = 0; i < classFeature.numValues(); i++) {
                if (classFeature.value(i).equals("TRUE")) {
                    positiveValueIndexOfClassFeature = i;
                }
            }
            train.setClassIndex(classFeatureIndex);
            test.setClassIndex(classFeatureIndex);
          
            pClassifier.buildClassifier(train);
            singleFoldEvaluation.evaluateModel(pClassifier, test);
            if(singleEvaluation == null) {
                singleEvaluation = new AggregateableEvaluation(singleFoldEvaluation);
                singleEvaluation.aggregate(singleFoldEvaluation);
            } else {
                singleEvaluation.aggregate(singleFoldEvaluation);
            }
        }
        
        double accuracy
                = (singleEvaluation.numTruePositives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numTrueNegatives(positiveValueIndexOfClassFeature))
                / (singleEvaluation.numTruePositives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numFalsePositives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numFalseNegatives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numTrueNegatives(positiveValueIndexOfClassFeature));

        double fmeasure = 2 * ((singleEvaluation.precision(positiveValueIndexOfClassFeature) * singleEvaluation.recall(positiveValueIndexOfClassFeature))
                / (singleEvaluation.precision(positiveValueIndexOfClassFeature) + singleEvaluation.recall(positiveValueIndexOfClassFeature)));
        
        File wekaOutput = new File(baseFolderPath + projectName + "/models/" + pModelName);
        wekaOutput.mkdirs();
        PrintWriter pw1 = new PrintWriter(wekaOutput + "/wekaOutput.csv");
        
        pw1.write(accuracy + ";" + singleEvaluation.precision(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.recall(positiveValueIndexOfClassFeature) + ";" + fmeasure + ";" + singleEvaluation.areaUnderROC(positiveValueIndexOfClassFeature));
        
        
        System.out.println(projectName + ";" + pClassifierName + ";" + pModelName + ";" + singleEvaluation.numTruePositives(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.numFalsePositives(positiveValueIndexOfClassFeature) + ";" + singleEvaluation.numFalseNegatives(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.numTrueNegatives(positiveValueIndexOfClassFeature) + ";" + accuracy + ";" + singleEvaluation.precision(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.recall(positiveValueIndexOfClassFeature) + ";" + fmeasure + ";" + singleEvaluation.areaUnderROC(positiveValueIndexOfClassFeature) + "\n");
        
        pw1.close();
    }

    private static ArrayList<TrainingTestSet> kFolds(Instances data,  int kFolds) throws Exception {
        
        ArrayList<TrainingTestSet> dataSet = new ArrayList<>();
                
        Random rand = new Random(42);
        Instances randData = new Instances(data);
        randData.randomize(rand);
        if (randData.classAttribute().isNominal()) {
            randData.stratify(kFolds);
        }
        
        //eval = new Evaluation(randData);
        
        for (int n = 0; n < kFolds; n++) {
            TrainingTestSet tts = new TrainingTestSet();
            Instances trainingSet = randData.trainCV(kFolds, n);
            Instances testSet = randData.testCV(kFolds, n);
            tts.setTrainingSet(trainingSet);
            tts.setTestSet(testSet);
            dataSet.add(tts);
        }
        
        return dataSet;
    }
}
