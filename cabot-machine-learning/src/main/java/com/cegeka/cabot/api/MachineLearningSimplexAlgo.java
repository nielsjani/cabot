package com.cegeka.cabot.api;

import java.util.Set;

public interface MachineLearningSimplexAlgo {

    Integer bepaalActie(Integer state, Set<Integer> mogelijkeActies);

    void kenRewardToeVoorGekozenActie(Integer fromState, Integer toState, Integer gekozenActie, Set<Integer> toStatemogelijkeActies, Integer reward);
}
