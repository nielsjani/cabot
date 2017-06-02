package com.cegeka.cabot.oorlogje;

import com.cegeka.cabot.oorlogje.api.OorlogjeInterface;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;

public class GameEngineInterface {

    private OorlogjeInterface gameInterface = new OorlogjeInterface();

    public StartSituatie getStartSituatie() {
        return gameInterface.createStartSituatie();
    }
}