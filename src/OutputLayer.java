import java.util.*;

public class OutputLayer extends Layer {

    public OutputLayer(int depth, int index, Layer up){
        // sets up layer of specified depth and position
        layerIndex = index;
        upStream = up;
        neurons = new ArrayList<>();
        for (int d = 0; d < depth; d++){
            neurons.add(new OutputNeuron(d, layerIndex));
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
    }

    void setCost(ArrayList<Double> cost){
        for (Neuron n:neurons){
            OutputNeuron neu = (OutputNeuron) n;
            neu.setCost(cost.get(n.getIndex()));
        }
    }

    ArrayList<Double> getCost(){
        ArrayList<Double> cost = new ArrayList<>();
        for (Neuron n:neurons){
            OutputNeuron neu = (OutputNeuron) n;
            cost.add(neu.getCost());
        }
        return cost;
    }

    /*
        public void receive(){
            for (Neuron n:neurons){
                OutputNeuron neu = (OutputNeuron) n;
                neu.calculateActivation();
            }
        }


        public void displayActivations(){
            for (Neuron n:neurons){
                System.out.println("Neuron " + n.getIndex() + ": " + n.getActivation());
            }
        }
    */
    void BP(Double learningRate){       //call BP() on each neuron
        for (Neuron n:neurons){
            n.BP(learningRate);
        }
    }

}