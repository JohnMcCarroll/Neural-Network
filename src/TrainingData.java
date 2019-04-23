/* TrainingData
    This class houses the paired input data that is used for teaching the neural network. The class
    has mutlidimensional arrays for expectedOutput, input, and a hashmap that pairs the two. Its
    constructor takes two two-dimensional arrays, one for input data and one for the desired output of
    the network.

    Plans: this class will later be expanded to support self-learning. It will store each game state
    with the move that was played. In the event of a win, all moves will be considered optimal, if
    the NN loses, all moves will be considered wrong. Each game will proving a training block.
 */

import java.util.*;

public class TrainingData {
    private ArrayList<ArrayList<Double>> expectedOutput;
    private ArrayList<ArrayList<Double>> input;
    private HashMap<ArrayList<Double>, ArrayList<Double>> data;
    private int numberOfTrials;

    public TrainingData(ArrayList<ArrayList<Double>> i, ArrayList<ArrayList<Double>> eo){
        expectedOutput = eo;
        input = i;
        numberOfTrials = input.size();
        data = new HashMap<>();
        if (expectedOutput.size() == input.size()){
            for (int j = 0; j < input.size(); j++){
                data.put(input.get(j), expectedOutput.get(j));
            }
        } else {
            System.out.println("Incompatible sizes of Input and ExpectedOutput");
        }
    }

    /*
       Getters and Setters:
       input, expectedOutput, and trainingData (getter only)
     */
    public ArrayList<ArrayList<Double>> getInput(){
        return input;
    }

    public void setInput(ArrayList<ArrayList<Double>> i){
        input = i;
    }

    public ArrayList<ArrayList<Double>> getExpectedOutput(){
        return expectedOutput;
    }

    public void setExpectedOutput(ArrayList<ArrayList<Double>> eo){
        expectedOutput = eo;
    }

    public HashMap<ArrayList<Double>, ArrayList<Double>> getTrainingData(){
        return data;
    }

    public int getNumberOfTrials(){
        return numberOfTrials;
    }
}