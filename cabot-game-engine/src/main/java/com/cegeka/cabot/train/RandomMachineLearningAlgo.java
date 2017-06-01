package com.cegeka.cabot.train;

import com.cegeka.cabot.api.MachineLearningAlgo;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;

import java.util.Random;

public class RandomMachineLearningAlgo implements MachineLearningAlgo {

    @Override
    public Kaart bepaalKaartOmTeSpelen(Beurt beurt, Kaart gespeeldDoorTegenstander) {
        int randomCardToPlay = new Random().nextInt(beurt.getHandkaarten().size());
        return beurt.getHandkaarten().get(randomCardToPlay);
    }

    @Override
    public void kenRewardToeVoorGespeeldeKaart(Beurt fromBeurt, Beurt toBeurt, Kaart gespeeldDoorMLAlgo, int reward) {

    }
}
