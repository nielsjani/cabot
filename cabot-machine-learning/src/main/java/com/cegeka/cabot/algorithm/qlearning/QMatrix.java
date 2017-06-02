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
    private Map<Integer, Map<Integer, Double>> qMatrix = new HashMap<>();

    public Map<Integer, Double> getQValueRowForState(Integer state) {
        return qMatrix.get(state);
    }

    public Double getQValue(Pair<Integer, Integer> stateActionPair) {
        Map<Integer, Double> stateRow = qMatrix.get(stateActionPair.getLeft());
        if (stateRow == null) return 0d;
        return Optional.ofNullable(stateRow.get(stateActionPair.getRight())).orElse(0d);
    }

    public void setQValue(Pair<Integer, Integer> stateActionPair, Double newQValue) {
        if (!qMatrix.containsKey(stateActionPair.getLeft())) {
            qMatrix.put(stateActionPair.getLeft(), new HashMap<>());
        }
        qMatrix.get(stateActionPair.getLeft()).put(stateActionPair.getRight(), newQValue);
    }

}
