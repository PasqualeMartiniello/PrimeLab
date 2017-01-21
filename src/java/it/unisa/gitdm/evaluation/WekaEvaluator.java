/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.gitdm.evaluation;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.AggregateableEvaluation;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author pasqualemartiniello
 */
public class WekaEvaluator {

 
    public WekaEvaluator(String baseFolderPath, String projectName, Classifier classifier, String classifierName, String modelName) {
        // READ FILE
        /*CODICE VECCHIO
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            Instances data = new Instances(reader);
            data.setClassIndex(data.numAttributes() - 1);
            System.out.println(data.size());
            // dividere istance in train e test
            Instances train = data;
            Instances test = null;

            // EVALUATION
            Evaluation eval = new Evaluation(train);
            //eval.evaluateModel(j48, test);
            // CROSS-VALIDATION
            eval.crossValidateModel(classifier, train, 10, new Random(1));
            System.out.println(eval.toSummaryString());
            System.out.println(eval.toMatrixString());

        } catch (Exception ex) {
            Logger.getLogger(WekaEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        CODICE VECCHIO*/
        String filePath = baseFolderPath + projectName + "/predictors.csv";
        try {
            DataSource source = new DataSource(filePath);
            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            System.out.println("Numero istanze: " + instances.size());
            evaluateModel(baseFolderPath, projectName, classifier, instances, modelName, classifierName);
        } catch (Exception ex) {
            Logger.getLogger(WekaEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public ArrayList<TrainingTestSet> leaveOneOut(String testFileName, ArrayList<String> fileNames) throws Exception {

        ArrayList<TrainingTestSet> dataset = new ArrayList<>();
        DatasetOperations datasetOperations = new DatasetOperations();
        Instances trainingSet = null;

        for (String fileName : fileNames) {
            if (!fileName.equals(testFileName)) {
                if (trainingSet == null) {
                    trainingSet = new Instances(datasetOperations.loadClearInstancesFromCSV(fileName));
                } else {
                    trainingSet.addAll(datasetOperations.loadClearInstancesFromCSV(fileName));
                }
            }
        }

        Instances testSet = datasetOperations.loadClearInstancesFromCSV(testFileName);

        TrainingTestSet tts = new TrainingTestSet();
        tts.setTrainingSet(trainingSet);
        tts.setTestSet(testSet);
        
        dataset.add(tts);

        return dataset;
    }

    
    public static ArrayList<TrainingTestSet> kFolds(Instances data,  int kFolds) throws Exception {
        
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
    
    
    private static void evaluateModel(String baseFolderPath, String projectName, Classifier pClassifier, Instances pInstances, String pModelName, String pClassifierName) throws Exception {

       /*OLD CODES
         // other options
        int folds = 10;

        // randomize data
        Random rand = new Random(42);
        Instances randData = new Instances(pInstances);
        randData.randomize(rand);
        if (randData.classAttribute().isNominal()) {
            randData.stratify(folds);
        }

        // perform cross-validation and add predictions
        Instances predictedData = null;
        Evaluation eval = new Evaluation(randData);

        int positiveValueIndexOfClassFeature = 0;
        for (int n = 0; n < folds; n++) {
            Instances train = randData.trainCV(folds, n);
            Instances test = randData.testCV(folds, n);
            // the above code is used by the StratifiedRemoveFolds filter, the
            // code below by the Explorer/Experimenter:
            // Instances train = randData.trainCV(folds, n, rand);

            int classFeatureIndex = 0;
            for (int i = 0; i < train.numAttributes(); i++) {
                if (train.attribute(i).name().equals("isBuggy")) {
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
            OLD CODES*/

        //Create the Training a Test Set
        
        
        ArrayList<TrainingTestSet> dataSet = kFolds(pInstances, 10);
        
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
            System.out.println(test);
            // build and evaluate classifier
            pClassifier.buildClassifier(train);
            singleEvaluation.evaluateModel(pClassifier, test);
//            if(singleEvaluation == null) {
//                singleEvaluation = new AggregateableEvaluation(singleFoldEvaluation);
//                singleEvaluation.aggregate(singleFoldEvaluation);
//            } else {
//                singleEvaluation.aggregate(singleFoldEvaluation);
 //           }
        }
        
            
            // add predictions
//	        AddClassification filter = new AddClassification();
//	        filter.setClassifier(pClassifier);
//	        filter.setOutputClassification(true);
//	        filter.setOutputDistribution(true);
//	        filter.setOutputErrorFlag(true);
//	        filter.setInputFormat(train);
//	        Filter.useFilter(train, filter); 
//	        Instances pred = Filter.useFilter(test, filter); 
//	        if (predictedData == null)
//	          predictedData = new Instances(pred, 0);
//	        
//	        for (int j = 0; j < pred.numInstances(); j++)
//	          predictedData.add(pred.instance(j));

        double accuracy
                = (singleEvaluation.numTruePositives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numTrueNegatives(positiveValueIndexOfClassFeature))
                / (singleEvaluation.numTruePositives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numFalsePositives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numFalseNegatives(positiveValueIndexOfClassFeature)
                + singleEvaluation.numTrueNegatives(positiveValueIndexOfClassFeature));

        double fmeasure = 2 * ((singleEvaluation.precision(positiveValueIndexOfClassFeature) * singleEvaluation.recall(positiveValueIndexOfClassFeature))
                / (singleEvaluation.precision(positiveValueIndexOfClassFeature) + singleEvaluation.recall(positiveValueIndexOfClassFeature)));
        
        File wekaOutput = new File(baseFolderPath + projectName + "/wekaOutput.csv");
        PrintWriter pw1 = new PrintWriter(wekaOutput);

        pw1.write(accuracy + ";" + singleEvaluation.precision(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.recall(positiveValueIndexOfClassFeature) + ";" + fmeasure + ";" + singleEvaluation.areaUnderROC(positiveValueIndexOfClassFeature));
        
        
        System.out.println(projectName + ";" + pClassifierName + ";" + pModelName + ";" + singleEvaluation.numTruePositives(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.numFalsePositives(positiveValueIndexOfClassFeature) + ";" + singleEvaluation.numFalseNegatives(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.numTrueNegatives(positiveValueIndexOfClassFeature) + ";" + accuracy + ";" + singleEvaluation.precision(positiveValueIndexOfClassFeature) + ";"
                + singleEvaluation.recall(positiveValueIndexOfClassFeature) + ";" + fmeasure + ";" + singleEvaluation.areaUnderROC(positiveValueIndexOfClassFeature) + "\n");
    }
}
