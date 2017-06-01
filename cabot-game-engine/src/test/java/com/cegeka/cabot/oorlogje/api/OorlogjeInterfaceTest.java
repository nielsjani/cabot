package com.cegeka.cabot.oorlogje.api;

import com.cegeka.cabot.UnitTest;
import com.cegeka.cabot.api.MachineLearningAlgo;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;
import com.cegeka.cabot.reward.RewardCalculator;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static com.cegeka.cabot.api.beurt.Beurt.beurt;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OorlogjeInterfaceTest extends UnitTest{

    @InjectMocks
    private OorlogjeInterface oorlogjeInterface;
    @Mock
    private MachineLearningAlgo machineLearningAlgo;
    @Mock
    private RewardCalculator rewardCalculator;

    @Test
    public void bepaalTeSpelenKaart_ShouldCallMachineLearningInterfaceToGetCorrectCardAndGiveFeedbackAboutReward() {
        Kaart teSpelenKaartBepaaldDoorMachine = new Kaart(3);
        Beurt beurt = beurt().withGespeeldeKaarten(newArrayList())
                .withHandkaarten(newArrayList(new Kaart(2), teSpelenKaartBepaaldDoorMachine, new Kaart(1)))
                .withTegenstanderGespeeldeKaarten(newArrayList());
        when(machineLearningAlgo.bepaalKaartOmTeSpelen(beurt, beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt())).thenReturn(teSpelenKaartBepaaldDoorMachine);
        when(rewardCalculator.bepaalRewardVoorGespeeldeKaart(beurt, teSpelenKaartBepaaldDoorMachine)).thenReturn(41);

        Kaart kaartTeSpelen = oorlogjeInterface.bepaalTeSpelenKaart(beurt);

        assertThat(kaartTeSpelen).isEqualTo(teSpelenKaartBepaaldDoorMachine);
        verify(machineLearningAlgo).bepaalKaartOmTeSpelen(beurt, beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt());
        verify(machineLearningAlgo).kenRewardToeVoorGespeeldeKaart(Mockito.any(Beurt.class), beurt, teSpelenKaartBepaaldDoorMachine, 41);
    }
}