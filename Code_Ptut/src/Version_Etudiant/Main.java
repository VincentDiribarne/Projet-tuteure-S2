package Version_Etudiant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width * 2/3;
        int hauteur = tailleMoniteur.height * 2/3;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Reconstitution - Version Etudiante");
        primaryStage.setScene(new Scene(root, longueur, hauteur));
        primaryStage.setMinWidth(longueur);
        primaryStage.setMinHeight(hauteur);
        primaryStage.setMaximized(true);
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
