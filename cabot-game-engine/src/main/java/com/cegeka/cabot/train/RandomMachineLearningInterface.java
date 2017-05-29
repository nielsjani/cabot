package com.cegeka.cabot.train;

import com.cegeka.cabot.api.MachineLearningInterface;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;

import java.util.Random;

public class RandomMachineLearningInterface extends MachineLearningInterface{

    @Override
    public Kaart getTeSpelenKaartVoor(Beurt beurt) {
        int randomCardToPlay = new Random().nextInt(beurt.getBotHandkaarten().size());
        return beurt.getBotHandkaarten().get(randomCardToPlay);
    }
}
