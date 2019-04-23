/* Network
    This class manages all of the lower level component that comprise the neural network and handles
    data management of weights, biases, and activations. It has an ArrayList of its layers, a
    trainingData variable to manage the IO aspect of teaching, input, output, and expected output.
    Network also has "metadata" instance variables of multidimensional ArrayLists that track each
    neuron's (and neuron's connnection's) activatoins, biases, and weights across a block of
    training trials, aptly named biases, weights, activationData, and costData. The multidimensional
    ArrayLists dCdBias, dCdWeight, and dCdAct respectively hold the partial derivatives to the cost
    function of each bias, weight, and activation for backpropogation.
    The constructor is passed a "plan" int array that it used to construct the network - the size of
    the array indicates how many layers it will have, and the number at each index corresponds to how
    many neurons will be placed in the corresponding layer. runBlock() iterates through a block of
    training data from trainingData and stores the results, then calls backpropogates and implements
    the partial derivatives. Backpropogate() calculates the partial derivatives of the cost function
    with respect to weights, biases, and activation and stores them in dCdBias, dCdWeight, and dCdAct.

    John McCarroll                                                              2/2/19
 */

import java.util.*;
import java.io.*;

public class Network implements Serializable {
    private static final long serialVersionUID = -7266476760327796085;
    private ArrayList<Layer> layers;
    private Double learningRate;
    //private ArrayList<Double> cost;       //maybe needed when trans to minibatch?

    /*
    Description: constructs Network to given int[] specifications (each element in int[] is number
                    of neurons in that layer)
    Arguments: int[] plan
    Returns: none
    */
    public Network(int[] plan, Double learningRate){
        layers = new ArrayList<>();

        Layer prev = new Layer();
        for (int i = 0; i < plan.length; i++){
            if (i < 1){
                layers.add(new InputLayer(plan[0]));
            } else if (i == plan.length - 1){
                layers.add(new OutputLayer(plan[i],i,prev));
            } else {
                layers.add(new Layer(plan[i], i, prev));
            }
            prev = layers.get(i);
        }

        //set learningRate
        this.learningRate = learningRate;
    }

    /*
    Description: iterates through a block of trials provided by TrainingData, collecting data on
                    each neurons activity, then implements backpropogation and adjusts weights/biases
    Arguments: TrainingData object with desired data
    Returns: none
    */
    public void runBlock(HashMap<ArrayList<Double>, ArrayList<Double>> data){

        for (int r = 0; r < 2; r++) {
            // shuffle keys
            List keys = new ArrayList(data.keySet());
            Collections.shuffle(keys);
                for (Object key : keys) {
                    ArrayList<Double> theKey = (ArrayList<Double>) key;
                    // rotate clockwise and mirror horizontal once to cover all possible game states after NN takes top left corner
                    ArrayList<Double> in = mirrorInput(rotateInput(theKey, r), r);
                    ArrayList<Double> out = mirrorOutput(rotateOutput(data.get(theKey), r), r);

                    forwardFeed(in);
                    BP(out);

                    // calc cost func
                    OutputLayer lay = (OutputLayer) layers.get(layers.size() - 1);
                    Double sum = 0.0;
                    for (Double d : lay.getCost()) {
                        sum += Math.pow(d, 2.0);
                    }

                    // save to file
                    storeCost(sum);

                    // display
                    System.out.println("input");
                    display(in);
                    System.out.println("output");
                    display(out);
                }
        }
    }

    public ArrayList<Double> rotateInput(ArrayList<Double> input, int count){
        // use count to determine how many 90 degree rotations to loop through
        //swap corners and edges of input_copy array in loop. - declare one temp var

        ArrayList<Double> input_copy = new ArrayList<>(19);
        for (int i = 0; i < 19; i++) {
            input_copy.add(0.0);
        }

        Collections.copy(input_copy,input);

        for (int i = 0; i < count; i++){
            Double temp = input_copy.get(0);
            input_copy.set(0,input_copy.get(6));
            input_copy.set(6, input_copy.get(8));
            input_copy.set(8, input_copy.get(2));
            input_copy.set(2, temp);

            temp = input_copy.get(1);
            input_copy.set(1,input_copy.get(3));
            input_copy.set(3, input_copy.get(7));
            input_copy.set(7, input_copy.get(5));
            input_copy.set(5, temp);

            temp = input_copy.get(9);
            input_copy.set(9,input_copy.get(15));
            input_copy.set(15, input_copy.get(17));
            input_copy.set(17, input_copy.get(11));
            input_copy.set(11, temp);

            temp = input_copy.get(10);
            input_copy.set(10,input_copy.get(12));
            input_copy.set(12, input_copy.get(16));
            input_copy.set(16, input_copy.get(14));
            input_copy.set(14, temp);
        }

        return input_copy;
    }

    public ArrayList<Double> rotateOutput(ArrayList<Double> output, int count){
        // use count to determine how many 90 degree rotations to loop through
        //swap corners and edges of output_copy array in loop. - declare one temp var
        ArrayList<Double> output_copy = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            output_copy.add(0.0);
        }

        Collections.copy(output_copy,output);

        for (int i = 0; i < count; i++){
            Double temp = output_copy.get(0);
            output_copy.set(0,output_copy.get(6));
            output_copy.set(6, output_copy.get(8));
            output_copy.set(8, output_copy.get(2));
            output_copy.set(2, temp);

            temp = output_copy.get(1);
            output_copy.set(1,output_copy.get(3));
            output_copy.set(3, output_copy.get(7));
            output_copy.set(7, output_copy.get(5));
            output_copy.set(5, temp);
        }

        return output_copy;
    }

    public ArrayList<Double> mirrorInput(ArrayList<Double> input, int count){
        // swap col 0 & 2 values in each row (every 3 elements)
        ArrayList<Double> input_copy = new ArrayList<>(19);
        for (int i = 0; i < 19; i++) {
            input_copy.add(0.0);
        }

        // copy array to preserve data
        Collections.copy(input_copy,input);

        if (count == 1){

            //for (int i = 0; i < count; i++){
            Double temp = input_copy.get(0);
            input_copy.set(0,input_copy.get(2));
            input_copy.set(2, temp);

            temp = input_copy.get(3);
            input_copy.set(3, input_copy.get(5));
            input_copy.set(5, temp);

            temp = input_copy.get(6);
            input_copy.set(6, input_copy.get(8));
            input_copy.set(8, temp);

            temp = input_copy.get(9);
            input_copy.set(9, input_copy.get(11));
            input_copy.set(11, temp);

            temp = input_copy.get(12);
            input_copy.set(12, input_copy.get(14));
            input_copy.set(14, temp);

            temp = input_copy.get(15);
            input_copy.set(15, input_copy.get(17));
            input_copy.set(17, temp);

        }

        return input_copy;
    }

    public ArrayList<Double> mirrorOutput(ArrayList<Double> output, int count){         // integrate into run block and fucking TRAIN
        // swap col 0 & 2 values in each row (every 3 elements)
        ArrayList<Double> output_copy = new ArrayList<>(9);
        // PUT THIS SHIT IN A WHILE LOOP YOU NEANDERTHAL
        for (int i = 0; i < 9; i++) {
            output_copy.add(0.0);
        }

        Collections.copy(output_copy, output);

        if (count == 1){

            Double temp = output_copy.get(0);
            output_copy.set(0,output_copy.get(2));
            output_copy.set(2, temp);

            temp = output_copy.get(3);
            output_copy.set(3, output_copy.get(5));
            output_copy.set(5, temp);

            temp = output_copy.get(6);
            output_copy.set(6, output_copy.get(8));
            output_copy.set(8, temp);

        }

        return output_copy;
    }
        /*
    public void runBlock(ArrayList<ArrayList<Double>> input, ArrayList<ArrayList<Double>> expectedOutput){

        Double sum = 0.0;
        //System.out.println("INPUT: " + input.size() + " OUTPUT: " + expectedOutput.size());

        for (int i = 0; i < input.size(); i++){
            forwardFeed(input.get(i));
            BP(expectedOutput.get(i));

            // calc cost func
            OutputLayer lay = (OutputLayer) layers.get(layers.size()-1);
            for (Double d:lay.getCost()){
                sum += Math.pow(d, 2.0);
            }
        }

        // display
        storeCost(sum / input.size());

    } */

    /*
    Description: Iterates through layers, calling receive() to feedforward data of trial. Updates
                    activations and cost ArrayLists
    Arguments: ArrayList<> trialInput - activations of input layer
    Returns: none
     */
    public void forwardFeed(ArrayList<Double> trialInput){
        // iterate through layers calling receive()

        for (int i = 0; i < layers.size(); i++){
            if (i == 0){
                // give input layer ArrayList<Double> of input from trainingData
                InputLayer l = (InputLayer) layers.get(0);
                l.receive(trialInput);
            } else {
                layers.get(i).receive();
            }
        }
        //display(); //testing
    }

    void BP(ArrayList<Double> expectedOutput){
        // calculate cost - has each OutputNeuron calculate cost

        for (int j = 0; j < layers.get(layers.size()-1).getNeurons().size(); j++){
            OutputNeuron n = (OutputNeuron) layers.get(layers.size()-1).getNeurons().get(j);
            n.setCost(expectedOutput.get(j) - n.getActivation());
        }

        // BP
        for (int l = layers.size()-1; l >= 0; l--){
            for (int n = 0; n < layers.get(l).getNeurons().size()-1; n++){  //iterate through each layers' neurons calling BP(), excluding the bias neuron
                layers.get(l).getNeurons().get(n).BP(learningRate);
            }
            if (l == layers.size()-1){
                layers.get(l).getNeurons().get(layers.get(l).getNeurons().size()-1).BP(learningRate);       //in order to get last neuron of output layer
            }
        }
    }

    public static void display(ArrayList<Double> input){
        // display activations
        //layers.get(layers.size()-1).displayActivations();

        //display input
        if (input.size() == 19) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (input.get(row * 3 + col) == 1) {
                        System.out.print("[X] ");
                    } else if (input.get(row * 3 + col + 9) == 1) {    //maybe +9?
                        System.out.print("[O] ");
                    } else {
                        System.out.print("[ ] ");
                    }
                }
                System.out.println("");
            }
            System.out.println("");
        }

        //display output
        if (input.size() == 9) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (input.get(row * 3 + col) == 1) {
                        System.out.print("[X] ");
                    } else {
                        System.out.print("[ ] ");
                    }
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

    void save(String filename){    //might not be risky?
        try {
            FileOutputStream fileStream = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(this);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Network load(String filename){    //might not be risky?
        Network n = new Network(new int[0],0.0);
        try {
            FileInputStream fileStream = new FileInputStream(filename);
            ObjectInputStream os = new ObjectInputStream(fileStream);
            n = (Network) os.readObject();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    void storeCost(Double sum){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("TTTC4.2_LR0.1.txt", true));
            writer.write(sum.toString());
            writer.newLine();
            writer.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addLayer(int depth, int index){
        //
        layers.add(index, new Layer(depth, index, layers.get(index-1)));
        layers.get(index-1).setDownStream(layers.get(index));
        layers.get(index+1).setUpStream(layers.get(index));
        //set up new Neuron array with new Connections
        layers.get(index).getNeurons().clear();
        for (int i = 0; i < depth; i++){
            layers.get(index).addNeuron();
        }
    }

    public void removeLayer(int index){ // ***broken atm, does not est. connections
        //
        layers.remove(index);
        layers.get(index-1).setDownStream(layers.get(index));
        layers.get(index).setUpStream(layers.get(index-1));
        // establish Connections
    }

    void setLearningRate(Double d){
        learningRate = d;
    }

    Double getLearningRate(){
        return learningRate;
    }

    ArrayList<Double> getOutput(){
        return layers.get(layers.size()-1).getActivations();
    }

    HashMap<ArrayList<Double>, ArrayList<Double>> getData(){
        GenData data = new GenData();
        try {
            FileInputStream fileStream = new FileInputStream("trainingdata.ml");
            ObjectInputStream is = new ObjectInputStream(fileStream);
            data = (GenData) is.readObject();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.getData();
    }

    public ArrayList<Layer> getLayers(){
        return layers;
    }
}