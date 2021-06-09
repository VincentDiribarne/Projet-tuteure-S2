package Version_Etudiant.All_Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller_Page_Solution implements Initializable {

	// Solution
	@FXML
	private TextArea affichageSolution;
	@FXML
	private Button closeSol;
	public static String contenuSolution;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		affichageSolution.setWrapText(true);
		affichageSolution.setText(Controller_Page_Exercice.contenuTranscription);

	}

	@FXML
	public void closeSolution() {
		closeSol.getScene().getWindow().hide();
	}

}
