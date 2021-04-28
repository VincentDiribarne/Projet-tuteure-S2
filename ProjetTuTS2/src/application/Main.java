package application;

import javafx.util.Duration;

import java.awt.Desktop;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.web.WebView;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			// Mettre en plein écran V
			int width = (int) Screen.getPrimary().getBounds().getWidth();
			int height = (int) Screen.getPrimary().getBounds().getHeight();

			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, width, height);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);

			primaryStage.setTitle("JavaFX App");

			FileChooser fileChooser = new FileChooser();

			Button button = new Button("Select File");

				button.setOnAction(e -> {
					fileChooser.setTitle("Open Resource File");
					File file = new File("");
					selectedFile = fileChooser.showOpenDialog(primaryStage);

				});

			Media media = new Media(selectedFile.toURI().toURL().toExternalForm());
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			MediaView mediaView = new MediaView(mediaPlayer);

			root.getChildren().add(mediaView);
			mediaView.fitWidthProperty().bind(root.widthProperty());
			mediaView.fitHeightProperty().bind(root.heightProperty());
			mediaPlayer.pause();

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
