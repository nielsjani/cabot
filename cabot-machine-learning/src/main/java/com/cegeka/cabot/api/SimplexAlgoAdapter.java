package com.cegeka.cabot.api;

import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;

import static com.cegeka.cabot.api.mapper.oorlogje.OorlogjeBeurtToAlgoValuesMapper.toPossibleActionValues;
import static com.cegeka.cabot.api.mapper.oorlogje.OorlogjeBeurtToAlgoValuesMapper.toStateValue;


public class SimplexAlgoAdapter implements MachineLearningAlgo {

    private final MachineLearningSimplexAlgo simplexAlgo;

    public SimplexAlgoAdapter(MachineLearningSimplexAlgo simplexAlgo) {
        this.simplexAlgo = simplexAlgo;
    }

    @Override
    public Kaart bepaalKaartOmTeSpelen(Beurt beurt, Kaart gespeeldDoorTegenstander) {
        return new Kaart(simplexAlgo.bepaalActie(toStateValue(beurt), toPossibleActionValues(beurt)));
    }

    @Override
    public void kenRewardToeVoorGespeeldeKaart(Beurt fromBeurt, Beurt toBeurt, Kaart gespeeldDoorMLAlgo, int reward) {
        simplexAlgo.kenRewardToeVoorGekozenActie(toStateValue(fromBeurt), toStateValue(toBeurt), gespeeldDoorMLAlgo.getWaarde(), toPossibleActionValues(toBeurt), reward);
    }
}
