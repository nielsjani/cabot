package com.cegeka.cabot;

import com.cegeka.cabot.oorlogje.GameEngineInterface;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
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
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.cegeka.cabot.oorlogje.state.Beurt.beurt;


public class UiController {

    private GameEngineInterface gameEngineInterface;

    @FXML
    private VBox rightPane;

    @FXML
    private Button fetchStartingHandsButton;
    private Button startScanningBotHandButton;
    private Button cardScannedCorrectButton;
    private Button cardScannedIncorrectButton;

    private Label startingPlayerLabel;
    private Label startPlayerLabel2;
    private Beurt beurt;
    private Kaart scannedKaart;
    private Label scannedKaartLabel = new Label("");
    private Button startBeurtButton;


    @FXML
    private ImageView currentFrame;

    private VideoCapture capture = new VideoCapture();
    private ScheduledExecutorService timer;
    // a flag to change the button behavior
    private boolean cameraActive = false;
    // the id of the camera to be used
    private static int cameraId = 0;

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
            System.out.println("Found:");
            System.out.println(result.getText());
            this.scannedKaart = new Kaart(Integer.parseInt(result.getText()));
//            scannedKaartLabel.setText("Card found: " + result.getText());
//            rightPane.getChildren().add(scannedKaartLabel);
//            Utils.onFXThread(scannedKaartLabel.textProperty(), "Card found: " + result.getText());
            Result finalResult = result;
            Platform.runLater(() -> {
                scannedKaartLabel.textProperty().set("Card found: " + finalResult.getText());
            });
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
        startScanningBotHandButton = new Button("Start scanning cards");
        startScanningBotHandButton.setOnAction(startScanningBotHandCards());

        cardScannedCorrectButton = new Button("Correct! Next!");
        cardScannedCorrectButton.setOnAction(confirmScanCorrect());
        cardScannedIncorrectButton = new Button("Try again");
        //TODO: implement me

        startBeurtButton = new Button("PLAY!");
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
            rightPane.getChildren().add(scannedKaartLabel);
            startCamera();
//            String scannedText = new QrCodeScanner().scan2(currentFrame, scannedKaartLabel, rightPane);
//            scannedKaartLabel = new Label("The card I've found: " + scannedText);
//            rightPane.getChildren().add(scannedKaartLabel);
//            beurt.getHandkaarten().add(new Kaart(Integer.parseInt(scannedText)));
        };
    }

//    /**
//     * Stop the acquisition from the camera and release all the resources
//     */
//    private void stopAcquisition() {
//        if (this.timer != null && !this.timer.isShutdown()) {
//            try {
//                // stop the timer
//                this.timer.shutdown();
//                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
//            } catch (InterruptedException e) {
//                // log any exception
//                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
//            }
//        }
//
//        if (this.capture.isOpened()) {
//            // release the camera
//            this.capture.release();
//        }
//    }


//    private void updateImageView(ImageView view, Image image) {
//        Utils.onFXThread(view.imageProperty(), image);
//    }


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
