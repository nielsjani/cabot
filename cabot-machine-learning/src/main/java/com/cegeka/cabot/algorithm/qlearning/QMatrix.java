package com.cegeka.cabot.algorithm.qlearning;


import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * De Q Matrix: het brein / model van het algoritme
 */
public class QMatrix {

    /**
     * Map<Pair<Integer, Integer>, Double> staat voor
     * Map<Pair<uniqueValueForState, actie>, qValue>
     */
    private Map<Pair<Integer, Integer>, Double> qMatrix = new HashMap<>();

    public Double getQValue(Pair<Integer, Integer> stateActionPair) {
        return Optional.ofNullable(qMatrix.get(stateActionPair)).orElse(0d);
    }

    public void setQValue(Pair<Integer, Integer> stateActionPair, Double newQValue) {
        qMatrix.put(stateActionPair, newQValue);
    }

}
