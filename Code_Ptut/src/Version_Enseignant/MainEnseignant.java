package Version_Enseignant;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainEnseignant extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	/* Parent root = FXMLLoader.load(getClass().getResource("Enseignant.fxml"));
         primaryStage.setTitle("Reconstitution - Version Enseignante");
         
         //On affiche le plein écran
         primaryStage.setMaximized(true);
         primaryStage.setScene(new Scene(root));
         primaryStage.show();
         
         //On récupère la largeur et la hauteur de l'écran
         int width = (int) Screen.getPrimary().getBounds().getWidth();
         int height = (int) Screen.getPrimary().getBounds().getHeight();
         
         //On restreint la largeur et la hauteur de la page
         primaryStage.setMinWidth(width - 100);
         primaryStage.setMinHeight(height - 100);
       
         
		//Tests
         MediaView mediaView = null;
         Ressource r = new Ressource("Test", mediaView);*/
 
    	final File file = new File("C:\\Users\\basti\\Downloads\\test.mp4"); 
        final Media media = new Media(file.toURI().toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media); 
        final MediaView mediaView = new MediaView(mediaPlayer); 
        final StackPane root = new StackPane(); 
        root.getChildren().setAll(mediaView); 
        final Scene scene = new Scene(root, 400, 400);
        
       /* Button pause = new Button("Pause");
        Button play = new Button("Play");
        Button plus1 = new Button("+1");
        Button moins1 = new Button("-1");
        
        root.getChildren().addAll(pause,play,plus1,moins1);
        pause.setTranslateX(50);
        plus1.setTranslateX(100);
        moins1.setTranslateX(150);
        
        //Evenement boutons (avec scene builder)
        pause.setOnAction(actionEvent -> mediaPlayer.pause());
        play.setOnAction(actionEvent -> mediaPlayer.play());
        
        plus1.setOnAction(actionEvent -> mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(1))));
        moins1.setOnAction(actionEvent -> mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-1))));
        
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Lecture d'une vidéo"); 

        
        mediaView.fitWidthProperty().bind(root.widthProperty()); 
        mediaView.fitHeightProperty().bind(root.heightProperty()); 
        mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.9);*/
        
        Label label = new Label("Votre mode d'exercice: ");
        label.setTranslateY(-20);
        // Group
        ToggleGroup group = new ToggleGroup();
 
        // Radio 1: Male
        RadioButton button1 = new RadioButton("Mode Entraînement");
        button1.setToggleGroup(group);
        button1.setSelected(true);
 
        // Radio 3: Female.
        RadioButton button2 = new RadioButton("Mode Évaluation");
        button2.setToggleGroup(group);
        button2.setTranslateX(150);
        
        root.getChildren().addAll(label, button1, button2);
        
        primaryStage.setScene(scene); 
        primaryStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
 
}
