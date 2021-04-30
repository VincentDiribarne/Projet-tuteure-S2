package Version_Enseignant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainEnseignant extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	 Parent root = FXMLLoader.load(getClass().getResource("Enseignant.fxml"));
         primaryStage.setTitle("Reconstitution - Version Enseignante");
         
         //On affiche le plein écran
         primaryStage.setMaximized(true);
         primaryStage.setScene(new Scene(root));
         primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
