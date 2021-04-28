package application;

import javafx.util.Duration;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.*;
import javafx.scene.web.WebView;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setFullScreen(true);
			//Tests intégrer une video
			
			/*String MEDIA_URL = "https://youtu.be/S3xJxvJGI8o?autoplay=1%22";

			Media media = new Media(MEDIA_URL);
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			MediaView mediaView = new MediaView(mediaPlayer);

			root.getChildren().add(mediaView);
			mediaPlayer.play();

			primaryStage.setScene(scene);
			primaryStage.show();*/

			/*WebView webview = new WebView();
			webview.getEngine().load("https://youtu.be/S3xJxvJGI8o?autoplay=1");
			webview.setPrefSize(640, 390);

			primaryStage.setScene(new Scene(webview));*/
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
