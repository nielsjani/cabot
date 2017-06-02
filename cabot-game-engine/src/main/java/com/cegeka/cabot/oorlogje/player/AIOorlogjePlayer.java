package com.cegeka.cabot.oorlogje.player;

import com.cegeka.cabot.algorithm.qlearning.QLearningSimplexAlgo;
import com.cegeka.cabot.api.AIGameStateConverter;
import com.cegeka.cabot.api.MachineLearningSimplexAlgo;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;

import java.util.Set;


public class AIOorlogjePlayer implements OorlogjePlayer {

    private final MachineLearningSimplexAlgo simplexAlgo = new QLearningSimplexAlgo();
    private final AIGameStateConverter AIGameStateConverter = new OorlogjeAIGameStateConverter();
    private String name = "AIOorlogjePlayer";

    public AIOorlogjePlayer() {
    }

    public AIOorlogjePlayer(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Kaart bepaalActieOmTeSpelen(Beurt beurt, Set<Kaart> toegelatenActies) {
        return new Kaart(simplexAlgo.bepaalActie(AIGameStateConverter.toStateValue(beurt), AIGameStateConverter.toActionValues(toegelatenActies)));
    }

    @Override
    public void kenRewardToeVoorGespeeldeActie(Beurt fromBeurt, Kaart gespeeldeActieFromBeurt, Beurt toBeurt, Set<Kaart> toegelatenActiesToBeurt, int reward) {
        simplexAlgo.kenRewardToeVoorGekozenActie(
                AIGameStateConverter.toStateValue(fromBeurt),
                AIGameStateConverter.toStateValue(toBeurt),
                gespeeldeActieFromBeurt.getWaarde(),
                AIGameStateConverter.toActionValues(toegelatenActiesToBeurt),
                reward);
    }


    public void setInLearningMode(boolean inLearningMode) {
        simplexAlgo.setInLearningMode(inLearningMode);
    }
}
