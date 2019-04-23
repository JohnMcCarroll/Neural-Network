/* Connection
    This class forms the basic connections between neurons in different layers.
    It knows its up stream and down stream neurons, its unique weight, and can calculate
    the weighted activation (output) during "feedforward."

    John McCarroll                                                        1/22/19
*/

import java.io.*;

public class Connection implements Serializable {
    private Neuron fromNeuron;
    private Neuron toNeuron;
    private Double weight;
    private Double input;
    private Double output; //not needed?

    protected Double dCdWeight;

    Connection(Neuron fromNeuron, Neuron toNeuron){
        setFromNeuron(fromNeuron);
        setToNeuron(toNeuron);
        if (Math.random() > 0.5) {
            weight = 1.0;
        } else {
            weight = -1.0;
        }
        weight = weight*Math.random()*3;
    }

    void dWeight(Double dCdSquish, Double dCdAct){
        dCdWeight = input*dCdSquish*dCdAct;
    }

    Double getAct(){
        return toNeuron.getActivation();
    }

    Double getDAct(){
        return toNeuron.getDCdAct();
    }

    void BP(Double dCdSquish, Double dCdAct, Double learningRate){
        dWeight(dCdSquish, dCdAct);

        Double tempWeight = weight;
        weight += dCdWeight*learningRate;
        // bound at 5:
        if (weight > 5.0 || weight < -5.0){
            weight = tempWeight;
        }
    }

    /*
    Description: sets output equal to product of input and weight
    Arguments: none
    Returns: none
    */
    public Double calculateOutput(){
        updateInput();
        return output = input*weight;
    }

    /*
    Description: sets input equal to activation from fromNeuron
    Arguments: none
    Returns: none
    */
    public void updateInput(){
        input = fromNeuron.getActivation();
    }

    /*
    Getters and Setters:
    fromNeuron, toNeuron, weight, and output(getter only)
    */

    public void setFromNeuron(Neuron n){
        fromNeuron = n;
    }

    public Neuron getFromNeuron(){
        return fromNeuron;
    }

    public void setToNeuron(Neuron n){
        toNeuron = n;
    }

    public Neuron getToNeuron(){
        return toNeuron;
    }

    public void setWeight(Double w){
        weight = w;
    }

    public Double getWeight(){
        return weight;
    }

    public Double getOutput(){
        return output;
    }

    public Double getDCdWeight(){
        return dCdWeight;
    }
}