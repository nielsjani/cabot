package com.cegeka.cabot.oorlogje.startsituatie;

import java.util.List;
import java.util.Random;

import static com.cegeka.cabot.oorlogje.startsituatie.KaartType.*;
import static com.google.common.collect.Lists.newArrayList;

public class StartSituatieFactory {

    private List<StartSituatieKaart> startSituatieKaarten;

    public StartSituatieFactory() {
        startSituatieKaarten = newArrayList(
                new StartSituatieKaart(1, HARTEN),
                new StartSituatieKaart(2, HARTEN),
                new StartSituatieKaart(3, HARTEN),
                new StartSituatieKaart(4, HARTEN),
                new StartSituatieKaart(5, HARTEN),

                new StartSituatieKaart(1, RUITEN),
                new StartSituatieKaart(2, RUITEN),
                new StartSituatieKaart(3, RUITEN),
                new StartSituatieKaart(4, RUITEN),
                new StartSituatieKaart(5, RUITEN),

                new StartSituatieKaart(1, KLAVEREN),
                new StartSituatieKaart(2, KLAVEREN),
                new StartSituatieKaart(3, KLAVEREN),
                new StartSituatieKaart(4, KLAVEREN),
                new StartSituatieKaart(5, KLAVEREN),

                new StartSituatieKaart(1, SCHOPPEN),
                new StartSituatieKaart(2, SCHOPPEN),
                new StartSituatieKaart(3, SCHOPPEN),
                new StartSituatieKaart(4, SCHOPPEN),
                new StartSituatieKaart(5, SCHOPPEN)
        );
    }

    // Implementing Fisher–Yates shuffle
    private List<StartSituatieKaart> schudKaarten() {
        for (int i = this.startSituatieKaarten.size() - 1; i > 0; i--)
        {
            int index = new Random().nextInt(i + 1);
            StartSituatieKaart situatieKaart = this.startSituatieKaarten.get(index);
            this.startSituatieKaarten.set(index,this.startSituatieKaarten.get(i));
            this.startSituatieKaarten.set(i,situatieKaart);
        }
        return this.startSituatieKaarten;
    }

    public StartSituatie createStartSituatie() {
        List<StartSituatieKaart> geschuddeKaarten = schudKaarten();
        return new StartSituatie(geschuddeKaarten.subList(0,5), geschuddeKaarten.subList(5,10), new Random().nextBoolean());
    }
}
