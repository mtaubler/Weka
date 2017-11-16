/*
 *  CAP 4630 - Fall 2017
 *  Programming Assignment 3
 *  Logistic Perceptron 
 * 	Matthew Taubler & Brooke Norton 
 * 	11/04/2017
 * 
 */

import java.io.BufferedReader;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import weka.classifiers.Classifier;
import weka.core.*; 
import weka.core.Capabilities.*; 
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;



public class LogisticPerceptron implements Classifier{
	
	int bias = 0; 
	String file = ""; 
	int epochs = 0; 
	// Learning Constant (a decimal real value)
	double learningRate = 0; 
	// Logistic function squashing parameter, a decimal real value
	double lambda = 0; 
	double weights[];
	double weightChange = 0;
	int totalWeightUpdates = 0;
	
	public LogisticPerceptron(String[] options){
		// Parse the input string
		file = options[0]; 
		epochs = Integer.parseInt(options[1]);
		learningRate = Double.parseDouble(options[2]);
		lambda = Double.parseDouble(options[3]);
		// Set bias to constant 1
		bias = 1; 

		System.out.print("University of Central Florida\n"
			+ "CAP4630 Artificial Intelligence - Fall 2017\n"
			+	"Logisitc Perceptron Classifier by Matthew Taubler and Brooke Norton\n\n");
			
	}
	
	// Builds classifier with the provided dataset
	@Override
	public void buildClassifier(Instances data) throws Exception {	
		
		// Get number of training instances 
		int numInstances = data.numInstances(); 
		
		// Get data
		//getCapabilities().testWithFail(data);
		data = new Instances(data);
		data.deleteWithMissingClass(); 
		Instance first = data.firstInstance();
		
		// Initialize the weights vector
		weights = new double[data.numAttributes()];
		for(int i = 0; i < first.numAttributes(); i++){
			weights[i] = 0.0;
		}
	
		// Perceptron Rule
		for(int epoch = 0; epoch < epochs; epoch++){
			
			System.out.print("Epoch " + epoch + ": ");
			
			for (int i = 0; i < numInstances; i++){
				
				Instance instance = data.instance(i);
				double expected = predict(instance);
				double actual = getActual(instance);
			
				//System.out.println("comparing actual:" + actual + " to expected: " + expected);
				
				if (actual == expected){
					// Do nothing
					System.out.print(1);
				} else if (actual == 1 || actual == -1){
					System.out.print(0);
					// Change weights
					for (int j = 0; j < weights.length - 1; j++){
						weightChange = (2 * actual) * learningRate * instance.value(j);
						weights[j] += weightChange;
					}
					// Update bias weight
					weights[weights.length - 1] =  (2 * actual) * learningRate * bias;
					totalWeightUpdates++;
				} else {
					System.out.println("Error occurred");
				}
			}
			System.out.println();
		}
	}
	
	public double predict(Instance instance){
		double sum = 0;  
		for (int i = 0; i < weights.length - 1; i++){
			sum += weights[i] * instance.value(i);
		}
		// Account for bias weight -- always 1 in this case
		sum += weights[weights.length - 1] * bias;
		return (sum >= 0) ? 1 : -1; 
	}
	
	// Provided code from lecture slides
	// Return probabilities array of the prediction for the weka core. 
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		double[] result = new double[2];
		if (predict(instance) == 1){
			result[0] = 1;
			result[1] = 0;
		}
		else{
			result[0] = 0;
			result[1] = 1;
		}
		return result;
	}
	
	public double getActual(Instance instance){
		if (instance.value(2) == 0){
			return 1;
		} else {
			return -1;
		}
	}
	
	public String toString(){
		String report = "";
		report = "Source file : " + file + "\n" 
			+ 		"Training epochs: " + epochs + "\n"
			+ 		"Learning rate : " + learningRate + "\n"
			+ 		"Lamda Value : " + lambda + "\n\n"
			+     "Total # weight updates = " + totalWeightUpdates + "\n"
			+			"Final Weights: \n" + weights[0] +"\n" + weights[1] + "\n" + weights[2];
		return report;
	}


	// Empty concrete method
	@Override
	public Capabilities getCapabilities() {
		return null;
	}
	
	//Empty concrete method
	@Override
	public double classifyInstance(Instance arg0) throws Exception {
		return 0;
	}
}

//for(int i = 0; i < weights.length; i++) 
//{
	//weights[i] = ThreadLocalRandom.current().nextDouble(0,1); 
//}


