package com.cegeka.cabot;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
import java.util.concurrent.ScheduledExecutorService;

public class QrCodeScanner {

    public String scan2(ImageView currentFrame, Label scannedKaartLabel, VBox rightPane) {
                new WebcamRenderer().startCamera(currentFrame, scannedKaartLabel, rightPane);
                return "";
    }

    public String scan(ImageView currentFrame, ScheduledExecutorService timer) {
        final String[] qrCodeText = {null};

        new Runnable() {
            @Override
            public void run() {

                Webcam webcam = Webcam.getDefault(); // non-default (e.g. USB) webcam can be used too
                webcam.open();


                do {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

                    Result result = null;
                    BufferedImage image = null;

                    if (webcam.isOpen()) {
                        if ((image = webcam.getImage()) == null) {
                            continue;
                        }

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
                        qrCodeText[0] = result.getText();
                        break;
                    }
                } while (true);
            }
        };
        return qrCodeText[0];
    }
}
