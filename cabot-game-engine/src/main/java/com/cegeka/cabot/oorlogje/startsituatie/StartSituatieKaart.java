package com.cegeka.cabot.oorlogje.startsituatie;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.EqualsBuilder;

import static com.cegeka.cabot.oorlogje.state.OorlogjeConstants.MAXIMALE_KAARTWAARDE;
import static com.cegeka.cabot.oorlogje.state.OorlogjeConstants.MINIMALE_KAARTWAARDE;

public class StartSituatieKaart {
    private int waarde;
    private KaartType kaartType;

    public StartSituatieKaart(int waarde, KaartType kaartType) {
        Preconditions.checkNotNull(kaartType);
        Preconditions.checkArgument(waarde >= MINIMALE_KAARTWAARDE  && waarde <= MAXIMALE_KAARTWAARDE);
        this.waarde = waarde;
        this.kaartType = kaartType;
    }

    public int getWaarde() {
        return waarde;
    }

    public KaartType getKaartType() {
        return kaartType;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj, this);
    }
}
