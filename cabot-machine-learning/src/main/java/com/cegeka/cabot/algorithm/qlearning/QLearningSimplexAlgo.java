package com.cegeka.cabot.algorithm.qlearning;

import com.cegeka.cabot.api.MachineLearningSimplexAlgo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;
import java.util.Set;

public class QLearningSimplexAlgo implements MachineLearningSimplexAlgo {

    /**
     * Alpha is the learning rate. The learning rate determines to what extent the newly acquired information will
     * override the old information. A factor of 0 will make the agent not learn anything, while a factor of 1 would
     * make the agent consider only the most recent information.
     */
    private static final float ALPHA = 0.1f;
    /**
     * Gamma is the discount factor and determines the importance of future rewards. A factor of 0 will make the agent
     * “myopic” (or short-sighted) by only considering current rewards, while a factor approaching 1 will make it
     * strive for a long-term high reward.
     */
    private static final float GAMMA = 0.9f;

    private boolean inLearningMode = true;
    private QMatrix qMatrix = new QMatrix();

    @Override
    public Integer bepaalActie(Integer state, Set<Integer> mogelijkeActies) {
        if (inLearningMode) {
            return getRandomValueFromSet(mogelijkeActies);
        }
        Pair<Integer, Double> gekozenPair = mogelijkeActies.stream()
                .map(actie -> Pair.of(actie, qMatrix.getQValue(Pair.of(state, actie))))
                .reduce(Pair.of(0, Double.NEGATIVE_INFINITY), (p1, p2) -> {
                    if (p1.getRight() > p2.getRight()) return p1;
                    return p2;
                });
        Integer gekozenActie = gekozenPair.getLeft();
        if (!mogelijkeActies.contains(gekozenActie)) {
            throw new RuntimeException("Actie " + gekozenActie + " is niet toegelaten in state " + state);
        }
        return gekozenActie;
    }

    @Override
    public void kenRewardToeVoorGekozenActie(Integer fromState, Integer gekozenActie, Integer toState, Set<Integer> toStatemogelijkeActies, Integer reward) {
        if (inLearningMode) {
            Pair<Integer, Integer> fromStateActionPair = Pair.of(fromState, gekozenActie);
            Double oldQValue = qMatrix.getQValue(fromStateActionPair);

            Double newQValue = oldQValue + ALPHA * ( reward + (GAMMA * getMaxQValueForStateActions(toState, toStatemogelijkeActies)) - oldQValue);

            qMatrix.setQValue(fromStateActionPair, newQValue);
        }
    }

    private Double getMaxQValueForStateActions(Integer toState, Set<Integer> toStatemogelijkeActies) {
        return toStatemogelijkeActies.stream()
                .map(actie -> Pair.of(toState, actie))
                .mapToDouble(stateActionPair -> qMatrix.getQValue(stateActionPair))
                .max().orElse(0d);
    }

    private Integer getRandomValueFromSet(Set<Integer> intSet) {
        return intSet.toArray(new Integer[intSet.size()])[new Random().nextInt(intSet.size())];
    }

    @Override
    public void setInLearningMode(boolean inLearningMode) {
        this.inLearningMode = inLearningMode;
    }
}
