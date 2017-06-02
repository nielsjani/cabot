package com.cegeka.cabot;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QrCodeScanner {

    public String scan(ImageView currentFrame, ScheduledExecutorService timer) {

        Webcam webcam = Webcam.getDefault(); // non-default (e.g. USB) webcam can be used too
        webcam.open();
        String qrCodeText = null;

        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {

                if ((image = webcam.getImage()) == null) {
                    continue;
                }

                renderWebcamOnScreen(currentFrame, webcam);

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    // fall thru, it means there is no QR code in image
                }
            }
            if (result != null) {
                System.out.println("Found:");
                System.out.println(result.getText());
                qrCodeText = result.getText();
                break;
            }
        } while (true);

        return qrCodeText;
    }

    private void renderWebcamOnScreen(ImageView currentFrame, Webcam webcam) {
        ScheduledExecutorService timer;
        Runnable frameGrabber = () -> Platform.runLater(() -> {
            currentFrame.imageProperty().setValue(SwingFXUtils.toFXImage(webcam.getImage(), null));
        });

        timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
    }
}
