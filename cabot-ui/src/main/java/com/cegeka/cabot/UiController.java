package com.cegeka.cabot;

import com.cegeka.cabot.oorlogje.GameEngineInterface;
import com.cegeka.cabot.oorlogje.api.OorlogjeInterface;
import com.cegeka.cabot.oorlogje.reward.RewardCalculator;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.cegeka.cabot.oorlogje.state.Beurt.beurt;


public class UiController {

    private GameEngineInterface gameEngineInterface;
    private OorlogjeInterface oorlogjeInterface;

    @FXML
    private VBox rightPane;
    @FXML
    private VBox leftPane;

    @FXML
    private Button fetchStartingHandsButton;
    private Button startScanningBotHandButton;
    private Button cardScannedCorrectButton;

    private Label startingPlayerLabel;
    private Label startPlayerLabel2;
    private Beurt beurt;
    private Kaart scannedKaart;
    private Label scannedKaartLabel = new Label("");
    private Label wieIsAanBeurtLabel = new Label("");
    private Button startBeurtButton;
    private Button scanBotCardButton;
    private Button scanHumanCardButton;


    @FXML
    private ImageView currentFrame;

    private VideoCapture capture = new VideoCapture();
    private ScheduledExecutorService timer;
    // a flag to change the button behavior
    private boolean cameraActive = false;
    // the id of the camera to be used
    private static int cameraId = 0;

    private int botScore = 0;
    private int humanScore = 0;
    private Label scoreboardLabel;
    private boolean botAanZet;

    @FXML
    public void startCamera() {
        if (!this.cameraActive) {
            // start the video capture
            this.capture.open(cameraId);

            // is the video stream available?
            if (this.capture.isOpened()) {
                this.cameraActive = true;

                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = () -> {
                    // effectively grab and process a single frame
                    Mat frame = grabFrame();
                    // convert and show the frame
                    Image imageToShow = Utils.mat2Image(frame);
                    updateImageView(currentFrame, imageToShow);
                    scanQrCode(imageToShow);
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                // update the button content
            } else {
                // log the error
                System.err.println("Impossible to open the camera connection...");
            }
        } else {
            // the camera is not active at this point
            this.cameraActive = false;
            // update again the button content

            // stop the timer
            this.stopAcquisition();
        }
    }

    private void scanQrCode(Image imageToShow) {
        Result result = null;
        LuminanceSource source = new BufferedImageLuminanceSource(SwingFXUtils.fromFXImage(imageToShow, null));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            result = new MultiFormatReader().decode(bitmap);
        } catch (NotFoundException e) {
            // fall thru, it means there is no QR code in image
        }
        if (result != null) {
//            System.out.println("Found:");
//            System.out.println(result.getText());
            this.scannedKaart = new Kaart(Integer.parseInt(result.getText()));
            Result finalResult = result;
            Platform.runLater(() -> scannedKaartLabel.textProperty().set("Card found: " + finalResult.getText()));
        }
    }

    private Mat grabFrame() {
        // init everything
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened()) {
            try {
                // read the current frame
                this.capture.read(frame);

                // if the frame is not empty, process it
                if (!frame.empty()) {
                    //convert img to greyscale
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                }

            } catch (Exception e) {
                // log the error
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    private void stopAcquisition() {
        if (this.timer != null && !this.timer.isShutdown()) {
            try {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened()) {
            // release the camera
            this.capture.release();
        }
    }


    private void updateImageView(ImageView view, Image image) {
        Utils.onFXThread(view.imageProperty(), image);
    }

    protected void setClosed() {
        this.stopAcquisition();
    }


    //OLD

    public UiController() {
        this.gameEngineInterface = new GameEngineInterface();
        oorlogjeInterface = new OorlogjeInterface();
        startScanningBotHandButton = new Button("Start scanning cards");
        startScanningBotHandButton.setOnAction(startScanningBotHandCards());

        cardScannedCorrectButton = new Button("Correct! Next!");
        cardScannedCorrectButton.setOnAction(confirmScanCorrect());

        startBeurtButton = new Button("PLAY!");

        scanBotCardButton = new Button("Played card chosen by bot");
        scanBotCardButton.setOnAction(confirmBotPlayScan());

        scanHumanCardButton = new Button("Correctly scanned human card");
        scanHumanCardButton.setOnAction(confirmHumanPlayScan());
    }

    private EventHandler<ActionEvent> confirmBotPlayScan() {
        return event -> {
            if (isBeurtOver()) {
                handleBeurtOver();
            } else {
                this.botAanZet = false;
                this.switchScanCardButtons();
            }
        };
    }

    private EventHandler<ActionEvent> confirmHumanPlayScan() {
        return event -> {
            this.beurt.withGespeeldeKaartDoorTegenstanderHuidigeBeurt(scannedKaart);
            this.beurt.getTegenstanderGespeeldeKaarten().add(scannedKaart);
            if (isBeurtOver()) {
                handleBeurtOver();
            } else {
                this.botAanZet = true;
                bepaalBotkaartAlsAanZet();
//                Kaart kaart = this.gameEngineInterface.bepaalTeSpelenKaart(beurt);
//                beurt.withGespeeldeKaartHuidigeBeurt(kaart);
//                System.out.println("Play card with value: " + kaart.getWaarde());
                handleBeurtOver();
            }
            //IF BEURT GEDAAN-> bepaal winnaar en zet buttons en alles correct + reset beurt gespeeldekaarten huidige beurt + bepaal starter volgende beurt
            //ANDERS switch buttons
        };
    }

    private void handleBeurtOver() {
        System.out.println("Beurt over");
        bepaalWinnaar();
        updateLabels();
        resetBeurt();
        switchScanCardButtons();
        bepaalBotkaartAlsAanZet();
        checkOfIemandGewonnenIs();
    }

    private void checkOfIemandGewonnenIs() {
        if(botScore >=3){
            System.out.println("BOT WON");
            Platform.runLater(() -> wieIsAanBeurtLabel.textProperty().setValue("I won. As expected."));
        }
        if(humanScore >=3){
            System.out.println("HUMAN WON");
            Platform.runLater(() -> wieIsAanBeurtLabel.textProperty().setValue("Oh, the bag of meat won. His brain might be suitable for recruitment into my core"));
        }
    }

    private void bepaalBotkaartAlsAanZet() {
        if(botAanZet){
            Kaart kaart = this.oorlogjeInterface.bepaalTeSpelenKaart(beurt);
            beurt.withGespeeldeKaartHuidigeBeurt(kaart);
            beurt.getGespeeldeKaarten().add(kaart);
            beurt.getHandkaarten().remove(kaart);
            System.out.println("Play card with value: " + kaart.getWaarde());
        }
    }

    private void updateLabels() {
        Platform.runLater(() -> wieIsAanBeurtLabel.textProperty().setValue(getWieIsAanBeurtText()));
        Platform.runLater(() -> scoreboardLabel.textProperty().setValue(createScoreLabel()));
    }

    private void resetBeurt() {
        this.beurt.withGespeeldeKaartHuidigeBeurt(null);
        this.beurt.withGespeeldeKaartDoorTegenstanderHuidigeBeurt(null);
    }

    private void bepaalWinnaar() {
        boolean botGewonnen = new RewardCalculator().gewonnen(beurt, beurt.getGespeeldeKaartHuidigeBeurt());
        botAanZet = botGewonnen;
        if(botGewonnen){
            botScore++;
            beurt.withIkBegin(true);
        } else {
            humanScore++;
            beurt.withIkBegin(false);
        }
    }

    private void switchScanCardButtons() {
        if(botAanZet){
            this.leftPane.getChildren().remove(scanHumanCardButton);
            this.leftPane.getChildren().remove(scanBotCardButton);
            this.leftPane.getChildren().add(scanBotCardButton);
        } else {
            this.leftPane.getChildren().remove(scanHumanCardButton);
            this.leftPane.getChildren().remove(scanBotCardButton);
            this.leftPane.getChildren().add(scanHumanCardButton);
        }
    }

    private boolean isBeurtOver() {
        return beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt() != null && beurt.getGespeeldeKaartHuidigeBeurt() != null;
    }

    private EventHandler<ActionEvent> confirmScanCorrect() {
        return event -> {
            beurt.getHandkaarten().add(this.scannedKaart);
            this.scannedKaart = null;
            if (beurt.getHandkaarten().size() < 5) {
                scannedKaartLabel.setText("Please show me the next card");
            } else {
                scannedKaartLabel.setText("All hand cards scanned. I am ready to whoop your feeble human butt.");
                rightPane.getChildren().remove(scannedKaartLabel);
                rightPane.getChildren().add(scannedKaartLabel);
                rightPane.getChildren().remove(cardScannedCorrectButton);
                leftPane.getChildren().add(new Label("Score"));
                scoreboardLabel = new Label(createScoreLabel());
                leftPane.getChildren().add(scoreboardLabel);
                wieIsAanBeurtLabel = new Label(getWieIsAanBeurtText());
                leftPane.getChildren().add(wieIsAanBeurtLabel);
                this.botAanZet = this.beurt.isIkBegin();
                if (botAanZet) {
                    Kaart kaart = oorlogjeInterface.bepaalTeSpelenKaart(beurt);
                    beurt.withGespeeldeKaartHuidigeBeurt(kaart);
                    beurt.getGespeeldeKaarten().add(kaart);
                    beurt.getHandkaarten().remove(kaart);
                    System.out.println("Play card with value: " + kaart.getWaarde());
                    leftPane.getChildren().add(this.scanBotCardButton);
                } else {
                    leftPane.getChildren().add(this.scanHumanCardButton);
                }
            }
        };
    }

    private String getWieIsAanBeurtText() {
        return botMoetBeginnenEnNogNietGespeeld() || tegenstanderMoetBeginnenEnHeeftAlGespeeld() ? "Assistent! Play my chosen card!" : "Scan the poorly chosen card of the opponent!";
    }

    private boolean tegenstanderMoetBeginnenEnHeeftAlGespeeld() {
        return !beurt.isIkBegin() && beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt() != null;
    }

    private boolean botMoetBeginnenEnNogNietGespeeld() {
        return beurt.isIkBegin() && beurt.getGespeeldeKaartHuidigeBeurt() == null;
    }

    private String createScoreLabel() {
        return String.format("Masterful Bot: %s - Lowlife scum: %s", this.botScore, this.humanScore);
    }

    private EventHandler<ActionEvent> startScanningBotHandCards() {
        return event -> {
            System.out.println("Hello?");
            rightPane.getChildren().remove(startingPlayerLabel);
            rightPane.getChildren().remove(startPlayerLabel2);
            rightPane.getChildren().remove(startScanningBotHandButton);
            rightPane.getChildren().add(cardScannedCorrectButton);
            rightPane.getChildren().add(scannedKaartLabel);
            startCamera();
        };
    }

    public void fetchHands() {
        StartSituatie startSituatie = gameEngineInterface.getStartSituatie();
        this.beurt = beurt()
                .withIkBegin(startSituatie.isMoetPlayer1Beginnen());
        this.rightPane.getChildren().remove(fetchStartingHandsButton);
        System.out.println("Bot cards");
        startSituatie.getBotKaarten().forEach(botkaart -> System.out.println(botkaart.forUi()));
        System.out.println("Human cards:");
        startSituatie.getMensKaarten().forEach(menskaart -> System.out.println(menskaart.forUi()));
//        startingPlayerLabel = new Label("Starting player:");
//        this.rightPane.getChildren().add(startingPlayerLabel);
//        startPlayerLabel2 = new Label(startSituatie.isMoetPlayer1Beginnen() ? "Glorious Bot plays first" : "Pitiful human starts");
//        this.rightPane.getChildren().add(startPlayerLabel2);
        this.rightPane.getChildren().add(startScanningBotHandButton);
//        startScanningBotHandCards();

    }
}
