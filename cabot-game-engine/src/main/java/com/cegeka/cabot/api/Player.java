package com.cegeka.cabot.api;

import java.util.Set;

public interface Player<State,Actie>  {

    String name();

    Actie bepaalActieOmTeSpelen(State state, Set<Actie> toegelatenActies);

    void kenRewardToeVoorGespeeldeActie(State fromState, Actie gespeeldeActieFromState, State toState, Set<Actie> toegelatenActiesToState, int reward);

}
