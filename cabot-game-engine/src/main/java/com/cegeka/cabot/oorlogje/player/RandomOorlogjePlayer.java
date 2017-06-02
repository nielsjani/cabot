package com.cegeka.cabot.oorlogje.player;

import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;

import java.util.Random;
import java.util.Set;

/**
 * Het baseline algoritme dat simpelweg niets anders
 * doet dan de eerste kaart in zijn hand van kaarten spelen
 */
public class RandomOorlogjePlayer implements OorlogjePlayer {

    @Override
    public String name() {
        return "RandomOorlogjePlayer";
    }

    @Override
    public Kaart bepaalActieOmTeSpelen(Beurt beurt, Set<Kaart> toegelatenActies) {
        return toegelatenActies.toArray(new Kaart[toegelatenActies.size()])[new Random().nextInt(toegelatenActies.size())];
    }

    @Override
    public void kenRewardToeVoorGespeeldeActie(Beurt fromBeurt, Kaart gespeeldDoorMLAlgo, Beurt toBeurt, Set<Kaart> toegelatenActiesToBeurt, int reward) {
//        System.out.println(String.format("Kaart gespeeld %s met reward %s", gespeeldDoorMLAlgo.getWaarde(), reward));
        // Do nothing with rewards, i'm pretty dumb
    }
}
