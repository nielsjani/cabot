package com.cegeka.cabot.api.beurt;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toSet;

public class BeurtToAlgoValuesMapper {

    public static Integer toStateValue(Beurt beurt) {
        return createUniqueRepresentationForState(createState(beurt));
    }

    public static Set<Integer> toPossibleActionValues(Beurt beurt) {
        return beurt.getHandkaarten().stream().map(Kaart::getWaarde).collect(toSet());
    }


    private static List<Integer> createState(Beurt beurt) {
        List<Integer> kaartenInHandGesorteerd = beurt.getHandkaarten().stream().map(Kaart::getWaarde).sorted(comparingInt(k -> k)).collect(Collectors.toList());
        voegGespeeldeKaartDoorTegenstanderToeAanEindeVanHand(beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt(), kaartenInHandGesorteerd);
        return kaartenInHandGesorteerd;

    }

    private static void voegGespeeldeKaartDoorTegenstanderToeAanEindeVanHand(Kaart gespeeldDoorTegenstander, List<Integer> kaartenInHandGesorteerd) {
        if (gespeeldDoorTegenstander != null) {
            kaartenInHandGesorteerd.add(gespeeldDoorTegenstander.getWaarde());
        }
    }


    /**
     * Genereert een unieke representatie voor de state
     * @param state
     *        kaarten in de hand, oplopend gespeeld gevolgd door de door tegenstander gespeelde kaart
     * @return een uniek nummer dat de state
     *          e.g. 31452
     *          waar 3 het aantal kaarten in de hand aanduid
     *          145 de kaarten in de hand zijn, oplopend gesorteerd
     *          2 de gespeelde kaart is van de tegenstander
     */
    private static int createUniqueRepresentationForState(List<Integer> state) {
        List<Integer> handCountPlusCards = newArrayList(state.size()-1);
        handCountPlusCards.addAll(state);
        return IntStream.range(0, handCountPlusCards.size())
                .mapToObj(i -> Pair.of(handCountPlusCards.get(i), i))
                .mapToInt(valueAndIndex ->  Double.valueOf(valueAndIndex.getLeft() * Math.pow(10, (handCountPlusCards.size() - 1 - valueAndIndex.getRight()))).intValue())
                .sum()
        ;
//        return parseInt(String.format("%s%s",
//                state.size()-1,
//                state.stream().map(Object::toString).collect(Collectors.joining())));
    }


}
