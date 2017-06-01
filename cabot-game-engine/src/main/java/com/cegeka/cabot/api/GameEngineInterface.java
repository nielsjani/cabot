package com.cegeka.cabot.api;

import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;
import com.cegeka.cabot.oorlogje.api.OorlogjeInterface;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;

public class GameEngineInterface {

    private OorlogjeInterface gameInterface = new OorlogjeInterface();

    public StartSituatie getStartSituatie() {
        return gameInterface.createStartSituatie();
    }

    public Kaart bepaalTeSpelenKaart(Beurt beurt){
        return gameInterface.bepaalTeSpelenKaart(beurt);
    }

    public void geefRewardVoorBeurtWaarBotMochtBeginnen(Beurt beurt) {
//        gameInterface.geefRewardVoorBeurt(beurt);
    }
}