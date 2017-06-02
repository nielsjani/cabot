package com.cegeka.cabot.oorlogje.player;

import com.cegeka.cabot.api.AIGameStateConverter;
import com.cegeka.cabot.api.MachineLearningSimplexAlgo;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;

import java.util.Set;


public class AIOorlogjePlayer implements OorlogjePlayer {

    private final MachineLearningSimplexAlgo simplexAlgo;
    private final AIGameStateConverter AIGameStateConverter;

    public AIOorlogjePlayer(MachineLearningSimplexAlgo simplexAlgo, AIGameStateConverter AIGameStateConverter) {
        this.simplexAlgo = simplexAlgo;
        this.AIGameStateConverter = AIGameStateConverter;
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
}
