package com.cegeka.cabot.oorlogje.api;

import com.cegeka.cabot.algorithm.baseline.BaseLineAlgo;
import com.cegeka.cabot.api.MachineLearningAlgo;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;
import com.cegeka.cabot.oorlogje.reward.RewardCalculator;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatieFactory;

public class OorlogjeInterface {

    private MachineLearningAlgo machineLearningAlgo;
    private RewardCalculator rewardCalculator;

    public OorlogjeInterface() {
        this.machineLearningAlgo = new BaseLineAlgo();
        this.rewardCalculator = new RewardCalculator();
    }

    public OorlogjeInterface(MachineLearningAlgo machineLearningAlgo) {
        this.machineLearningAlgo = machineLearningAlgo;
        this.rewardCalculator = new RewardCalculator();
    }

    public StartSituatie createStartSituatie() {
        return new StartSituatieFactory().createStartSituatie();
    }

    public Kaart bepaalTeSpelenKaart(Beurt beurt) {
        Kaart teSpelenKaart = machineLearningAlgo.bepaalKaartOmTeSpelen(beurt, beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt());
        //TODO: waarschijnlijk moeten we hier ook altijd reward geven, onafhankelijk van wie mocht starten.
//        // anders heeft bot geen enkel scenario voor wanneer hij mocht beginnen
//        //rewardCalculator.bepaalRewardVoorBotAlsEersteAanZetEnTegenstanderNogGeenKaartGelegd(beurt, teSpelenKaart)
//        if (!beurt.isIkBegin()) {
//            int reward = rewardCalculator.bepaalRewardVoorGespeeldeKaart(beurt, teSpelenKaart);
//            machineLearningAlgo.kenRewardToeVoorGespeeldeKaart(beurtVoordatKaartGespeeldIs, beurt, teSpelenKaart, reward);
//        }
        return teSpelenKaart;
    }

    public void geefRewardVoorBeurt(Beurt fromBeurt, Beurt toBeurt) {
        Kaart gespeeldeKaart = toBeurt.getGespeeldeKaartHuidigeBeurt();
        int reward = rewardCalculator.bepaalRewardVoorGespeeldeKaart(toBeurt, gespeeldeKaart);
        machineLearningAlgo.kenRewardToeVoorGespeeldeKaart(fromBeurt, toBeurt, gespeeldeKaart, reward);
    }
}
