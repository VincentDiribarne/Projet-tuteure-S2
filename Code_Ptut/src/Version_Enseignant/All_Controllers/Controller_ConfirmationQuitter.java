package Version_Enseignant.All_Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller_ConfirmationQuitter{

	@FXML private Button recupScene;
	
	@FXML
	public void annuler() {
		recupScene.getScene().getWindow().hide();
	}
	
	@FXML
	public void quitter() {
		Platform.exit();
	}

}
