package com.cegeka.cabot.algorithm.baseline;

import com.cegeka.cabot.api.MachineLearningInterface;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;

/**
 * Het baseline algoritme dat simpelweg niets anders
 * doet dan de eerste kaart in zijn hand van kaarten spelen
 */
public class BaseLineAlgo implements MachineLearningInterface {

    @Override
    public Kaart bepaalKaartOmTeSpelen(Beurt beurt, Kaart gespeeldDoorTegenstander) {
        return beurt.getBotHandkaarten().get(0);
    }

    @Override
    public void kenRewardToeVoorGespeeldeKaart(Beurt beurt, Kaart gespeeldDoorMLAlgo, int reward) {
        // Do nothing with rewards, i'm pretty dumb
    }
}
