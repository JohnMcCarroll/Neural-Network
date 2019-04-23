import java.util.*;

public class Bias extends Neuron {
    Bias (int index, int layerIndex){
        setIndex(index);
        setLayerIndex(layerIndex);

        downStream = new ArrayList<>();
        activation = 1.0;
    }

    public void calculateActivation(){} //override to do nothing - bias activation always 1.0

    void BP(Double learningRate){}
}
