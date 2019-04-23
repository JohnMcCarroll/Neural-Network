/* Layer
    This class represents a layer of neurons within the network. Naturally, it holds an
    ArrayList of all the Neurons within this Layer, and knows its layerIndex within the
    network. For computational purposes (backpropogation), it tracks all the weights,
    activations, and biases within the Layer. The Layer class can add and remove Neurons
    and send and receive information from/to adjacent Layers.

    John McCarroll                                                        1/22/19
*/

import java.util.*;
import java.io.*;

public class Layer implements Serializable {
    protected int layerIndex;
    protected Layer upStream;
    protected Layer downStream;
    protected ArrayList<Neuron> neurons;


    public Layer(int depth, int index, Layer up){
        // sets up layer of specified depth and position
        layerIndex = index;
        upStream = up;
        neurons = new ArrayList<>();
        for (int d = 0; d < depth; d++){
            neurons.add(new Neuron(d, layerIndex));
        }

        // establish connections with upstream layer
        upStream.setDownStream(this);
        for (int u = 0; u < upStream.neurons.size(); u++){
            for (int d = 0; d < depth; d++){
                Connection con = new Connection(upStream.neurons.get(u), neurons.get(d));
                upStream.getNeurons().get(u).addDownStream(con);
                neurons.get(d).addUpStream(con);
            }
        }
        // add bias neuron
        neurons.add(new Bias(depth, layerIndex));
    }

    public Layer(){} //default constr. to satisfy lack InputLayer...

    void BP(Double learningRate){       //call BP() on each neuron except Bias Neuron
        for (int i = 0; i < neurons.size() -1; i++){
            neurons.get(i).BP(learningRate);
        }
    }

    /*
    Description:    Add Neuron to the end of neurons and establishes its Connections
    Arguments: none
    Returns: none
    */
    public void addNeuron(){
        neurons.add(new Neuron(neurons.size(), layerIndex));
        for (Neuron n: upStream.getNeurons()){
            Connection con = new Connection(n, neurons.get(neurons.size()-1));
            n.addDownStream(con);
            neurons.get(neurons.size()-1).addUpStream(con);
        }
        for (Neuron n: downStream.neurons){
            Connection con = new Connection(neurons.get(neurons.size()-1), n);
            n.addUpStream(con);
            neurons.get(neurons.size()-1).addDownStream(con);
        }
    }

    /*
    Description:    removes specified Neuron from neurons, and removes its Connections.
    Arguments: int index of Neuron to be removed
    Returns: none
    */
    public void removeNeuron(int index){
        for (Connection con:neurons.get(index).getUpStream()) {
            con.getFromNeuron().removeDownStream(con);
        }
        for (Connection con:neurons.get(index).getDownStream()) {
            con.getToNeuron().removeUpStream(con);
        }
        neurons.remove(index);
    }

    /*
    Description:    Calls for each Neuron in neurons to calculateActivation()
    Arguments: none
    Returns: none
    */
    public void receive(){
        for (int i = 0; i < neurons.size(); i++){
            neurons.get(i).calculateActivation();
        }
    }

    /*
    Description:    For displaying activations of output layer
    Arguments: none
    Returns: none
    */
    public void displayActivations(){
        for (int i = 0; i < neurons.size(); i++){
            System.out.println("Neuron " + i + ": " + neurons.get(i).getActivation());
        }
    }

    /*
    Getters and Setters:
    layerIndex, upStream, downStream, neurons, upStreamWeights, activations, biases
     */
    public void setLayerIndex(int index){
        layerIndex = index;
    }

    public int getLayerIndex(){
        return layerIndex;
    }

    public void setUpStream(Layer l){
        upStream = l;
    }

    public Layer getUpStream(){
        return upStream;
    }

    public void setDownStream(Layer l){
        downStream = l;
    }

    public Layer getDownStream(){
        return downStream;
    }

    public void setNeurons(ArrayList<Neuron> n){
        neurons = n;
    }

    public ArrayList<Neuron> getNeurons(){
        return neurons;
    }

    //get output
    ArrayList<Double> getActivations(){
        ArrayList<Double> activations = new ArrayList<>();
        for (Neuron n : neurons){
            activations.add(n.getActivation());
        }
        return activations;
    }
}