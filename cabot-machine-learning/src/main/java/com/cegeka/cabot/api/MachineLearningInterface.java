package com.cegeka.cabot.api;

import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;

public class MachineLearningInterface {

    public Kaart getTeSpelenKaartVoor(Beurt beurt){
        return null;
    }

    public void geefRewardVoorBeurt(Beurt beurt, Kaart gespeeldeKaart, int reward){
        System.out.println("Received reward: " + reward);
    }
}
