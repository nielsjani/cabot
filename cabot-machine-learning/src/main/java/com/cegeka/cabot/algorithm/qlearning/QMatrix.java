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
    private Map<Integer, Map<Integer, Pair<Double, Integer>>> qMatrix = new HashMap<>();

    public Map<Integer, Pair<Double, Integer>> getQValueRowForState(Integer state) {
        return qMatrix.get(state);
    }

    public Pair<Double, Integer> getQValue(Pair<Integer, Integer> stateActionPair) {
        Map<Integer, Pair<Double, Integer>> stateRow = qMatrix.get(stateActionPair.getLeft());
        if (stateRow == null) return Pair.of(0d, 0);
        return Optional.ofNullable(stateRow.get(stateActionPair.getRight())).orElse(Pair.of(0d, 0));
    }

    public void setQValue(Pair<Integer, Integer> stateActionPair, Double newQValue) {
        if (!qMatrix.containsKey(stateActionPair.getLeft())) {
            qMatrix.put(stateActionPair.getLeft(), new HashMap<>());
        }
        Pair<Double, Integer> currentValue = qMatrix.get(stateActionPair.getLeft()).get(stateActionPair.getRight());
        if (currentValue == null) currentValue = Pair.of(0d, 0);
        qMatrix.get(stateActionPair.getLeft()).put(stateActionPair.getRight(), Pair.of(newQValue, currentValue.getRight() + 1));
    }

}
