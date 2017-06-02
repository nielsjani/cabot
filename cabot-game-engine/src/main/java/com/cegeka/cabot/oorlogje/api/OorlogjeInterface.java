package com.cegeka.cabot.oorlogje.api;

import com.cegeka.cabot.oorlogje.player.OorlogjePlayer;
import com.cegeka.cabot.oorlogje.player.RandomOorlogjePlayer;
import com.cegeka.cabot.oorlogje.reward.RewardCalculator;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatieFactory;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;

import static java.util.stream.Collectors.toSet;

public class OorlogjeInterface {

    private OorlogjePlayer oorlogjePlayer;
    private RewardCalculator rewardCalculator;

    public OorlogjeInterface() {
        this.oorlogjePlayer = new RandomOorlogjePlayer();
        this.rewardCalculator = new RewardCalculator();
    }

    public OorlogjeInterface(OorlogjePlayer oorlogjePlayer) {
        this.oorlogjePlayer = oorlogjePlayer;
        this.rewardCalculator = new RewardCalculator();
    }

    public StartSituatie createStartSituatie() {
        return new StartSituatieFactory().createStartSituatie();
    }

    public Kaart bepaalTeSpelenKaart(Beurt beurt) {
        Kaart teSpelenKaart = oorlogjePlayer.bepaalActieOmTeSpelen(beurt, beurt.getHandkaarten().stream().collect(toSet()));
        return teSpelenKaart;
    }

    public void geefRewardVoorBeurt(Beurt fromBeurt, Beurt toBeurt) {
        Kaart gespeeldeKaart = toBeurt.getGespeeldeKaartHuidigeBeurt();
        int reward = rewardCalculator.bepaalRewardVoorGespeeldeKaart(toBeurt, gespeeldeKaart);
        oorlogjePlayer.kenRewardToeVoorGespeeldeActie(fromBeurt, gespeeldeKaart, toBeurt, toBeurt.getHandkaarten().stream().collect(toSet()), reward);
    }
}
