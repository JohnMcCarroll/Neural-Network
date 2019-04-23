import java.util.ArrayList;

public class InputLayer extends Layer {
    /*
    Description:    Overridden constructor for Input Layer. Does not establish upStream.
    Arguments: int depth (amount of Neurons in Layer)
    Returns: none
    */
    public InputLayer(int depth){
        // sets up input layer of specified depth and position
        layerIndex = 0;
        neurons = new ArrayList<>();
        for (int d = 0; d < depth; d++){
            neurons.add(new Neuron(d, layerIndex));
        }
        //add bias
        neurons.add(new Bias(depth, 0));
    }

    /*
    Description:    Calls for each Neuron in neurons to calculateActivation(), updates activations,
                    biases, and upStreamWeights.
    Arguments: none
    Returns: none
    */
    public void receive(ArrayList<Double> input){

        if (input.size() == neurons.size() /*-1*/){         //FOR 1-1 TESTING, ADD BACK IN
            for (int i = 0; i < input.size(); i++){
                neurons.get(i).setActivation(input.get(i));
            }
        } else {
            System.out.println("Incompatible sizes of Input ArrayList and neurons in InputLayer");
        }
    }
}