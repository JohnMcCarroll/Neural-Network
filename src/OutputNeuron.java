import java.util.*;

public class OutputNeuron extends Neuron {
    public Double cost;

    OutputNeuron(int index, int layerIndex){
        setIndex(index);
        setLayerIndex(layerIndex);

        upStream = new ArrayList<>();
    }

    @Override
    void dActivation(){
        dCdAct = cost;
    }

    void setCost(Double d){
        cost = d;
    }

    Double getCost(){
        return cost;
    }
}
