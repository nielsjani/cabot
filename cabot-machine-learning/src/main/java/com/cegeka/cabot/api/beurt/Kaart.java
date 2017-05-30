package com.cegeka.cabot.api.beurt;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Kaart {

    public int waarde;

    public Kaart(int waarde) {
        this.waarde = waarde;
    }

    public int getWaarde() {
        return waarde;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
