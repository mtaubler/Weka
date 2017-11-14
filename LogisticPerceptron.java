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
	
	public LogisticPerceptron(String[] options){
		
		file = options[0]; 
		epochs = Integer.parseInt(options[1]);
		learningRate = Double.parseDouble(options[2]);
		lambda = Double.parseDouble(options[3]);
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
	
		// Initialize the weights
		weights = new double[numInstances];
		for(int i = 0; i < weights.length; i++) {
			weights[i] = ThreadLocalRandom.current().nextDouble(0,1); 
		}
	
		// Perceptron Model
		for(int epoch = 0; epoch < epochs; epoch++){
			
			System.out.print("Epoch " + (epoch + 1) + ": ");
			
			for (int i = 0; i < numInstances; i++){
				
				Instance instance = data.instance(i); 
				
				double result [] = distributionForInstance(instance);
				int comparison = (int)(result[0] - result[1]);
				System.out.print(comparison);
				
				weightChange = learningRate * (instance.value(1) - comparison) * instance.value(0);
				for (int j = 0; j < weights.length; j++){
					weights[j] += weightChange;
				}
			}
			System.out.println();
		}
	}
	
	public double predict(Instance instance){
		double result = 0;  
		for (int i = 0; i < weights.length; i++){
			result += bias * weights[i] * instance.value(0);
		}
		return (result >= 0) ? 1 : -1; 
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
	
	public String toString(){
		String report = "";
		report = "Source file : " + file + "\n" 
			+ 		"Training epochs: " + epochs + "\n"
			+ 		"Learning rate : " + learningRate + "\n"
			+ 		"Lamda Value : " + lambda + "\n\n"
			+     "Total # weight updates = \n"
			+			"Final Weights: " + "\n"; 
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



