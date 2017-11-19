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
import java.lang.Math;


public class LogisticPerceptron implements Classifier{
	
	int bias = 0; 
	String file = ""; 
	int epochs = 0; 
	// Learning Constant (a decimal real value)
	double learningRate = 0; 
	// Logistic function squashing parameter, a decimal real value
	double lambda = 0; 
	double e = 2.71828;
	// Weight tracking
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
		data = new Instances(data);
		data.deleteWithMissingClass(); 
		
		// Initialize the weights vector
		weights = new double[data.numAttributes()];
		for (int i = 0; i < data.numAttributes(); i++){
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
						// weightChange = learningRate * (actual - expected) * derivF(net(instance)) * instance.value(j);
						weightChange = (2 * actual) * learningRate * instance.value(j);
						weights[j] += weightChange;
					}
					// Update bias weight
					// weights[weights.length - 1] = weights[weights.length - 1] + learningRate * (actual - expected) * derivF(net(instance)) * bias;
					weights[weights.length - 1] = weights[weights.length - 1] + (2 * actual) * learningRate  * bias;
					totalWeightUpdates++;
				} else {
					System.out.println("Error occurred");
				}
			}
			System.out.println();
		}
	}
	
	public double predict(Instance instance){
		double net = net(instance);
		return (1 / (1 + Math.pow(e, -lambda * net))) > 0 ? 1 : -1; 
	}
	
	public double net(Instance instance){
		double net = 0;  
		for (int i = 0; i < weights.length - 1; i++){
			net += weights[i] * instance.value(i);
		}
		net += weights[weights.length - 1] * bias;
		return net;
	}
	
	public double derivF(double net) {
		return (lambda * Math.pow(e, -lambda * net)) / Math.pow((1 + Math.pow(e, -lambda * net)), 2);
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
			return 1; // a
		} else {
			return -1; // b
		}
	}
	
	public String toString(){
		String report = "";
		report = "Source file : " + file + "\n" 
			+ 		"Training epochs: " + epochs + "\n"
			+ 		"Learning rate : " + learningRate + "\n"
			+ 		"Lamda Value : " + lambda + "\n\n"
			+     	"Total # weight updates = " + totalWeightUpdates + "\n"
			+		"Final Weights: \n" + weights[0] +"\n" + weights[1] + "\n" + weights[2];
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


