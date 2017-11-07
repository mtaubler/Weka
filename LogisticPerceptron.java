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
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;


public class LogisticPerceptron implements Classifier{

	
	
	public LogisticPerceptron(String[] options){
		System.out.print("University of Central Florida\n"
			+ "CAP4630 Artificial Intelligence - Fall 2017\n"
			+	"Logisitc Perceptron Classifier by Matthew Taubler and Brooke Norton\n");
	}
	

	int bias = 1; 
	int epochs = 0;
	double learningConstant = 0; 
	
	// These should all be overrides 
	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double classifyInstance(Instance arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	//soemthing
	// His code but instead of if check i just do a simple chedk fo rnow
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

	@Override
	public Capabilities getCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		String report = "";
		report = "Source file :" 
			+ 		"Number of training epochs: "
			+ 		"Learning rate : "
			+ 		"Lamda Value :   "	
			+			"Final Weights:"; 
		return report;
	}
	
}
