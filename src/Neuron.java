/* Neuron
    This class is the fundamental building block of the network. It knows its position
    (index) inside the layer, its layer's position inside the network (layerIndex), its
    activation, and its bias. Neuron also holds two Lists of all its upstream and downstream
    Connections. Neuron can calculate its activation by accessing upStream, and update all
    inputs in downStream to 'forwardfeed'. Neuron has "squish" function that employs the
    sigmoid function, currently. It can also add and remove connections from its Lists.

    John McCarroll                                                        1/22/19
*/

import java.util.*;
import java.io.*;

public class Neuron implements Serializable {
    protected int index;
    protected int layerIndex;
    protected Double activation;
    protected ArrayList<Connection> upStream;
    protected ArrayList<Connection> downStream;

    protected Double dCdSquish;
    protected Double dCdAct;

    Neuron(int index, int layerIndex){
        setIndex(index);
        setLayerIndex(layerIndex);

        upStream = new ArrayList<>();
        downStream = new ArrayList<>();
    }

    Neuron(){} // to satisfy Bias hierarchy

    void dActivation(){

        dCdAct = 0.0;
        for (Connection con: downStream){
            dCdAct += con.getWeight()*con.getAct()*(1-con.getAct())*con.getDAct();   //("downstream neuron activation for squish der")*("downstream activation");
        }
    }

    void dSquish(){
        dCdSquish = activation*(1-activation);
    }

    void BP(Double learningRate){
        dSquish();
        dActivation();
        for (Connection con: upStream){
            con.BP(dCdSquish, dCdAct, learningRate);
        }
    }

    Double getDCdAct(){
        return dCdAct;
    }

    void setDCdAct(Double d){
        dCdAct = d;
    }

    /*
    Description:    Iterates through upStream getting weighted activations from previous layer.
                    Sets activation equal to 'squishified' sum of weighted activations and bias.
    Arguments: none
    Returns: none
    */
    public void calculateActivation(){
        Double sum = 0.0;
        for (Connection connection:upStream){
            sum += connection.calculateOutput();
        }
        activation = squish(sum);
    }

    /*
    Description:    Squishes sum of previous layer's activations and bias to a value between
                    0 and 1 using sigmoid function
    Arguments: Double sum
    Returns: Double squished sum
    */
    public Double squish(Double sum){
        return 1/(1 + Math.exp(-sum));
    }

    /*
    Description: Adds a Connection to end of upStream
    Arguments: Connection
    Returns: none
    */
    public void addUpStream(Connection c){
        upStream.add(c);
    }
    /*
    Description: Adds a Connection to end of downStream
    Arguments: Connection
    Returns: none
    */
    public void addDownStream(Connection c){
        downStream.add(c);
    }
    /*
    Description: Removes a Connection from upStream
    Arguments: Connection to be removed
    Returns: none
    */
    public void removeUpStream(Connection con){

        downStream.remove(downStream.indexOf(con));
    }
    /*
    Description: Removes a Connection from downStream
    Arguments: Connection to be removed
    Returns: none
    */
    public void removeDownStream(Connection con){
        downStream.remove(downStream.indexOf(con));
    }


    /*
    Getters and Setters:
    index, layerIndex, activation(getter only), bias, upStream, and downStream
    */
    public void setIndex(int i){
        index = i;
    }

    public int getIndex(){
        return index;
    }

    public void setLayerIndex(int i){
        layerIndex = i;
    }

    public int getLayerIndex(){
        return layerIndex;
    }

    public Double getActivation(){
        return activation;
    }

    public void setActivation(Double d) {activation = d;}

    public void setUpStream(ArrayList<Connection> u){
        upStream = u;
    }

    public void setDownStream(ArrayList<Connection> d){
        downStream = d;
    }

    public ArrayList<Connection> getUpStream() {
        return upStream;
    }

    public ArrayList<Connection> getDownStream() {
        return downStream;
    }
}
