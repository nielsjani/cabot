package com.cegeka.cabot.temp.oorlogje.mapper;

import com.cegeka.cabot.oorlogje.player.OorlogjeAIGameStateConverter;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;
import org.junit.Test;

import java.util.Set;

import static com.cegeka.cabot.oorlogje.state.Beurt.beurt;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

public class OorlogjeAIGameStateConverterTest {

    private OorlogjeAIGameStateConverter oorlogjeToAlgoValuesMapper = new OorlogjeAIGameStateConverter();

    @Test
    public void toStateValue() {
        Beurt beurt = beurt()
                .withHandkaarten(newArrayList(new Kaart(2), new Kaart(1), new Kaart(4)))
                .withGespeeldeKaartDoorTegenstanderHuidigeBeurt(new Kaart(5));

        Integer uniqueStateValue = oorlogjeToAlgoValuesMapper.toStateValue(beurt);

        assertThat(uniqueStateValue).isEqualTo(31245);
    }

    @Test
    public void toPossibleActionValues() {
        Beurt beurt = beurt()
                .withHandkaarten(newArrayList(new Kaart(4), new Kaart(2), new Kaart(4)));

        Set<Integer> possibleActionValues = oorlogjeToAlgoValuesMapper.toPossibleActionValues(beurt);

        assertThat(possibleActionValues).containsExactlyInAnyOrder(2, 4);
    }

}