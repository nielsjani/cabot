package com.cegeka.cabot.algorithm.baseline;

import com.cegeka.cabot.api.MachineLearningAlgo;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;

/**
 * Het baseline algoritme dat simpelweg niets anders
 * doet dan de eerste kaart in zijn hand van kaarten spelen
 */
public class BaseLineAlgo implements MachineLearningAlgo {

    @Override
    public Kaart bepaalKaartOmTeSpelen(Beurt beurt, Kaart gespeeldDoorTegenstander) {
        return beurt.getHandkaarten().get(0);
    }

    @Override
    public void kenRewardToeVoorGespeeldeKaart(Beurt fromBeurt, Beurt toBeurt, Kaart gespeeldDoorMLAlgo, int reward) {
//        System.out.println(String.format("Kaart gespeeld %s met reward %s", gespeeldDoorMLAlgo.getWaarde(), reward));
        // Do nothing with rewards, i'm pretty dumb
    }
}
