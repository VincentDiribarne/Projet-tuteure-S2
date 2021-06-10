package Version_Etudiant.All_Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller_TempsEcoule {

	@FXML private Label recupScene;
	
	@FXML private Button recupScene1;

	@FXML
	public void retourAccueil() throws IOException {
		recupScene.getScene().getWindow().hide();
	}
	
	@FXML
	public void retourAccueil1() throws IOException {
		recupScene1.getScene().getWindow().hide();
	}

}
