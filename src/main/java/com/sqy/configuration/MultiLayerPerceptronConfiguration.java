package com.sqy.configuration;

import com.sqy.activasion.ActivationFunction;
import com.sqy.activasion.SigmoidActivationFunctionFunction;
import com.sqy.loss.CrossEntropyLossFunction;
import com.sqy.loss.LossFunction;

public final class MultiLayerPerceptronConfiguration {
    public static final ActivationFunction defaultActivationFunction = new SigmoidActivationFunctionFunction();
    public static final LossFunction defaultLossFunction = new CrossEntropyLossFunction();
    public static final int[] HIDDEN_LAYER_SIZES = { 256, 64 };
}
