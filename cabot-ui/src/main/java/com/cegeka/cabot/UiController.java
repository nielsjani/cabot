package com.cegeka.cabot;

import com.cegeka.cabot.oorlogje.GameEngineInterface;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.opencv.highgui.VideoCapture;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.cegeka.cabot.oorlogje.state.Beurt.beurt;


public class UiController {

    private GameEngineInterface gameEngineInterface;

    @FXML
    private VBox rightPane;
    @FXML
    private ImageView currentFrame;
    @FXML
    private Button fetchStartingHandsButton;
    private Button startScanningBotHandButton;
    private Button cardScannedCorrectButton;
    private Button cardScannedIncorrectButton;

    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;
    // the OpenCV object that performs the video capture
    private VideoCapture capture = new VideoCapture();
    private Label startingPlayerLabel;
    private Label startPlayerLabel2;
    private Beurt beurt;
    private Kaart scannedKaart;
    private Label scannedKaartLabel = new Label();

    public UiController() {
        this.gameEngineInterface = new GameEngineInterface();
        startScanningBotHandButton = new Button("Start scanning cards");
        startScanningBotHandButton.setOnAction(startScanningBotHandCards());

        cardScannedCorrectButton = new Button("Correct! Next!");
        cardScannedCorrectButton.setOnAction(confirmScanCorrect());
        cardScannedIncorrectButton = new Button("Try again");
    }

    private EventHandler<ActionEvent> confirmScanCorrect() {
        return event -> {
            beurt.getHandkaarten().add(scannedKaart);
            this.scannedKaart = null;
            if (beurt.getHandkaarten().size() < 5) {
                scannedKaartLabel.setText("Please show me the next card");
                rightPane.getChildren().remove(scannedKaartLabel);
                rightPane.getChildren().add(scannedKaartLabel);
                String scannedText = new QrCodeScanner().scan(currentFrame, timer);
                scannedKaartLabel = new Label(scannedText);
                rightPane.getChildren().remove(scannedKaartLabel);
                rightPane.getChildren().add(scannedKaartLabel);
                this.scannedKaart = new Kaart(Integer.parseInt(scannedText));
            } else {
                scannedKaartLabel.setText("All hand cards scanned. I am ready to whoop your feeble human butt.");
            }
        };
    }

    private EventHandler<ActionEvent> startScanningBotHandCards() {
        return event -> {
            rightPane.getChildren().remove(startingPlayerLabel);
            rightPane.getChildren().remove(startPlayerLabel2);
            rightPane.getChildren().remove(startScanningBotHandButton);
            rightPane.getChildren().add(cardScannedCorrectButton);
            rightPane.getChildren().add(cardScannedIncorrectButton);
            String scannedText = new QrCodeScanner().scan(currentFrame, timer);
            scannedKaartLabel = new Label("The card I've found: " + scannedText);
            rightPane.getChildren().add(scannedKaartLabel);
            this.scannedKaart = new Kaart(Integer.parseInt(scannedText));
//            beurt.getHandkaarten().add(new Kaart(Integer.parseInt(scannedText)));
        };
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
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

    /**
     * Update the {@link ImageView} in the JavaFX main thread
     *
     * @param view  the {@link ImageView} to update
     * @param image the {@link Image} to show
     */
    private void updateImageView(ImageView view, Image image) {
        Utils.onFXThread(view.imageProperty(), image);
    }

    protected void setClosed() {
        this.stopAcquisition();
    }

    public void fetchHands(ActionEvent actionEvent) {
        StartSituatie startSituatie = gameEngineInterface.getStartSituatie();
        this.beurt = beurt()
                .withIkBegin(startSituatie.isMoetPlayer1Beginnen());
        this.rightPane.getChildren().remove(fetchStartingHandsButton);
        System.out.println("Bot cards");
        startSituatie.getBotKaarten().forEach(botkaart -> System.out.println(botkaart.forUi()));
        System.out.println("Human cards:");
        startSituatie.getMensKaarten().forEach(menskaart -> System.out.println(menskaart.forUi()));
        startingPlayerLabel = new Label("Starting player:");
        this.rightPane.getChildren().add(startingPlayerLabel);
        startPlayerLabel2 = new Label(startSituatie.isMoetPlayer1Beginnen() ? "Glorious Bot plays first" : "Pitiful human starts");
        this.rightPane.getChildren().add(startPlayerLabel2);
        this.rightPane.getChildren().add(startScanningBotHandButton);

    }
}
