package com.cegeka.cabot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application
{
	/**
	 * The main class for a JavaFX application. It creates and handles the main
	 * window with its resources (style, graphics, etc.).
	 * 
	 * This application looks for any tennis ball in the camera video stream and
	 * try to select them according to their HSV values. Found tennis balls are
	 * framed with a blue line.
	 * 
	 * @author <a href="mailto:luigi.derussis@polito.it">Luigi De Russis</a>
	 * @version 2.0 (2017-03-10)
	 * @since 1.0 (2015-01-13)
	 * 
	 */
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui.fxml"));
			BorderPane root = loader.load();
			root.setStyle("-fx-background-color: whitesmoke;");
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
			primaryStage.setTitle("Object Recognition");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// set the proper behavior on closing the application
			UiController controller = loader.getController();
			primaryStage.setOnCloseRequest((we -> controller.setClosed()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{

		File lib = null;
		String os = System.getProperty("os.name");
		String bitness = System.getProperty("sun.arch.data.model");

		if (os.toUpperCase().contains("WINDOWS")) {
			if (bitness.endsWith("64")) {
				lib = new File("libs//x64//" + System.mapLibraryName("opencv_java2411"));
			} else {
				lib = new File("libs//x86//" + System.mapLibraryName("opencv_java2411"));
			}
		}

		System.load(lib.getAbsolutePath());

		launch(args);
	}
}
