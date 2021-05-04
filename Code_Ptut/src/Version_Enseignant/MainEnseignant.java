package Version_Enseignant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainEnseignant extends Application {
	
	static Parent root;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
         root = FXMLLoader.load(getClass().getResource("PageAccueil.fxml"));
         primaryStage.setTitle("Reconstitution - Version Enseignante");
         
         //On affiche le plein écran
         primaryStage.setMaximized(true);

         //On récupère la largeur et la hauteur de l'écran
         //int width = (int) Screen.getPrimary().getBounds().getWidth();
         //int height = (int) Screen.getPrimary().getBounds().getHeight();
         
         //On restreint la largeur et la hauteur de la page
         //primaryStage.setMinWidth(width - 100);
         //primaryStage.setMinHeight(height - 100);
        
         primaryStage.setScene(new Scene(root));
         primaryStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
 
}
