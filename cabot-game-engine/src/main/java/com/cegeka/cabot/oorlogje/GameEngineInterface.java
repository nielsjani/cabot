package com.cegeka.cabot.oorlogje;

import com.cegeka.cabot.oorlogje.api.OorlogjeInterface;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;

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