package com.cegeka.cabot.algorithm.qlearning.mapper;

import com.cegeka.cabot.UnitTest;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;
import com.cegeka.cabot.api.mapper.oorlogje.OorlogjeBeurtToAlgoValuesMapper;
import org.junit.Test;

import java.util.Set;

import static com.cegeka.cabot.api.beurt.Beurt.beurt;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

public class BeurtToAlgoValuesMapperTest extends UnitTest{

    @Test
    public void toStateValue() {
        Beurt beurt = beurt()
                .withHandkaarten(newArrayList(new Kaart(2), new Kaart(1), new Kaart(4)))
                .withGespeeldeKaartDoorTegenstanderHuidigeBeurt(new Kaart(5));

        Integer uniqueStateValue = OorlogjeBeurtToAlgoValuesMapper.toStateValue(beurt);

        assertThat(uniqueStateValue).isEqualTo(31245);
    }

    @Test
    public void toPossibleActionValues() {
        Beurt beurt = beurt()
                .withHandkaarten(newArrayList(new Kaart(4), new Kaart(2), new Kaart(4)));

        Set<Integer> possibleActionValues = OorlogjeBeurtToAlgoValuesMapper.toPossibleActionValues(beurt);

        assertThat(possibleActionValues).containsExactlyInAnyOrder(2, 4);
    }


}