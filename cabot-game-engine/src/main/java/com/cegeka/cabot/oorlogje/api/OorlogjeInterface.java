package com.cegeka.cabot.oorlogje.api;

import com.cegeka.cabot.api.MachineLearningInterface;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;
import com.cegeka.cabot.oorlogje.reward.RewardCalculator;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatieFactory;

public class OorlogjeInterface {

    private MachineLearningInterface machineLearningInterface;
    private RewardCalculator rewardCalculator;

    public OorlogjeInterface() {
        this.machineLearningInterface = new MachineLearningInterface();
        this.rewardCalculator = new RewardCalculator();
    }

    public StartSituatie createStartSituatie() {
        return new StartSituatieFactory().createStartSituatie();
    }

    public Kaart bepaalTeSpelenKaart(Beurt beurt) {
        Kaart teSpelenKaart = machineLearningInterface.getTeSpelenKaartVoor(beurt);
        if(!beurt.isBotMochtAlsEerste()){
            int reward = rewardCalculator.bepaalRewardVoorGespeeldeKaart(beurt, teSpelenKaart);
            machineLearningInterface.geefRewardVoorBeurt(beurt, teSpelenKaart, reward);
        }
        return teSpelenKaart;
    }

    public void geefRewardVoorBeurtWaarBotMochtBeginnen(Beurt beurt) {
        Kaart gespeeldeKaartDoorBot = beurt.getGespeeldeKaartDoorBotHuidigeBeurt();
        int reward = rewardCalculator.bepaalRewardVoorGespeeldeKaart(beurt, gespeeldeKaartDoorBot);
        machineLearningInterface.geefRewardVoorBeurt(beurt, gespeeldeKaartDoorBot, reward);
    }
}
