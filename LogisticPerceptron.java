/*
 *  CAP 4630 - Fall 2017
 *  Programming Assignment 3
 *  Logistic Perceptron 
 * 	Matthew Taubler & Brooke Norton 
 * 	11/04/2017
 * 
 */

import java.io.BufferedReader;

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
	

	double learningConstant = 0; 
	
	// Builds classifier with the provided dataset
	@Override
	public void buildClassifier(Instances data) throws Exception {
		double weight = 0;
		double weightChange = 0;
		double output = 0;
		// Check for instances num is 10. 
		int numInstances = data.numInstances(); 
		//getCapabilities().testWithFail(data);
		data = new Instances(data);
		data.deleteWithMissingClass(); 
		
		for(int epoch = 0; epoch < epochs; epoch++){
			System.out.print("Epoch " + (epoch + 1) + ": ");
			for (int i = 0; i < epoch; i++){
				output = weight * data.get(i).value(0);
				if (output >= 0){
					output = 1;
				}else {
					output = 0;
				}
				System.out.print(output); 
			}
			weightChange = learningRate * (data.get(epoch).value(1) - output)* data.get(epoch).value(0);
			weight = weight + weightChange;
			System.out.println();
		}

	}

	// Empty concrete method
	@Override
	public double classifyInstance(Instance arg0) throws Exception {
		return 0;
	}

	// His code but instead of if check i just do a simple check for now
	// Return probabilities array of the prediction for the weka core. 
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		double[] result = new double[2];
		if(result[0] == 0){
			result[0] = 1;
			result[1] = 0;
		}
		else{
			result[0] = 0;
			result[1]= 1;
		}
		return result;
	}

	// Empty concrete method
	@Override
	public Capabilities getCapabilities() {
		return null;
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
	
}
