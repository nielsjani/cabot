package com.cegeka.cabot.oorlogje.player;

import com.cegeka.cabot.api.AIGameStateConverter;
import com.cegeka.cabot.api.MachineLearningSimplexAlgo;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;


public class AIOorlogjePlayer implements OorlogjePlayer {

    private final MachineLearningSimplexAlgo simplexAlgo;
    private final AIGameStateConverter AIGameStateConverter;

    public AIOorlogjePlayer(MachineLearningSimplexAlgo simplexAlgo, AIGameStateConverter AIGameStateConverter) {
        this.simplexAlgo = simplexAlgo;
        this.AIGameStateConverter = AIGameStateConverter;
    }

    @Override
    public Kaart bepaalActieOmTeSpelen(Beurt beurt) {
        return new Kaart(simplexAlgo.bepaalActie(AIGameStateConverter.toStateValue(beurt), AIGameStateConverter.toPossibleActionValues(beurt)));
    }

    @Override
    public void kenRewardToeVoorGespeeldeActie(Beurt fromBeurt, Beurt toBeurt, Kaart gespeeldDoorMLAlgo, int reward) {
        simplexAlgo.kenRewardToeVoorGekozenActie(
                AIGameStateConverter.toStateValue(fromBeurt),
                AIGameStateConverter.toStateValue(toBeurt),
                gespeeldDoorMLAlgo.getWaarde(),
                AIGameStateConverter.toPossibleActionValues(toBeurt),
                reward);
    }
}
