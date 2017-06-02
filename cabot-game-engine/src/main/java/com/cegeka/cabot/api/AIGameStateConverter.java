package com.cegeka.cabot.api;

import java.util.Set;

public interface AIGameStateConverter {

    <T> Integer toStateValue(T beurt);

    <T> Set<Integer> toPossibleActionValues(T beurt);

}
