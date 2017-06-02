package com.cegeka.cabot.api;

import java.util.Set;

public interface AIGameStateConverter<State, Actie> {

    Integer toStateValue(State state);

    Set<Integer> toActionValues(Set<Actie> acties);

}
