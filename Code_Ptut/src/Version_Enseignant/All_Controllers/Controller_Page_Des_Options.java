package Version_Enseignant.All_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Page_Des_Options implements Initializable{

	@FXML private RadioButton radioButtonEntrainement;
	@FXML private RadioButton radioButtonEvaluation;
	@FXML private RadioButton radioButton2Lettres;
	@FXML private RadioButton radioButton3Lettres;
	@FXML private HBox modeEntrainement;
	@FXML private HBox modeEntrainement1;
	@FXML private HBox modeEntrainement2;
	@FXML private HBox modeEntrainement3;
	@FXML private HBox modeEntrainement4;
	@FXML private HBox modeEvaluation;
	@FXML private TextField CaraOccul;
	@FXML private TextField nbMinute;
	@FXML private CheckBox checkBoxMotIncomplet;

	public static String caraOccul;
	public static String nbMin;

	//Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	//Bouton Quitter qui permet à l'enseignant de quitter l'application (disponible sur toutes les pages)
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	//Bouton Ouvrir qui permet à l'enseignant d'ouvrir un exercice qu'il à déjà créé auparavant
	@FXML
	public void ouvrir(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Ouvrez votre exercice");
		fileChooser.showOpenDialog(null);
		//TODO Chargez l'exercice dans la page
	}

	//Bouton Préférences qui emmène sur la page des paramètres
	@FXML
	public void preferences(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	//Bouton qui fait retourner l'enseignant à la page d'apercu (bouton retour)
	@FXML
	public void pageApercu(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageApercu.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	@FXML
	public void pageEnregistrementFinal(ActionEvent event) throws IOException {
		//Quand on passe à la page suivante, on mémorise les informations des options
		caraOccul = CaraOccul.getText();
		nbMin = nbMinute.getText();

		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/EnregistrementFinal.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	//Bouton DarkMode qui met en darkMode l'application
	@FXML 
	public void darkMode() {
		//TODO faire le DarkMode
	}

	//Gestion de si je sélectionne un mode, l'autre se décoche
	@FXML
	public void selectionModeEvaluation(ActionEvent event) {
		//On fait apparaître ce qui concerne le mode Evaluation
		modeEvaluation.setVisible(true);

		//On cache ce qui concerne le mode Entraînement
		modeEntrainement.setVisible(false);
		modeEntrainement1.setVisible(false);
		modeEntrainement2.setVisible(false);
		modeEntrainement3.setVisible(false);
		modeEntrainement4.setVisible(false);

		//On regarde si l'autre bouton est sélectionné, si c'est le cas on le déselectionne
		if(radioButtonEntrainement.isSelected()) {
			radioButtonEntrainement.setSelected(false);
		}

		//Dans le cas d'une déselection du bouton, on retire ce qui concerne le mode Evaluation
		if(!radioButtonEvaluation.isSelected()) {
			modeEvaluation.setVisible(false);
		}
	}

	@FXML
	public void selectionModeEntrainement(ActionEvent event) {	
		//On fait apparaître ce qui concerne le mode Entrainement
		modeEntrainement.setVisible(true);
		modeEntrainement1.setVisible(true);
		modeEntrainement2.setVisible(true);

		//On cache ce qui concerne le mode Evaluation
		modeEvaluation.setVisible(false);

		//On regarde si l'autre bouton est sélectionné, si c'est le cas on le déselectionne
		if(radioButtonEvaluation.isSelected()) {
			radioButtonEvaluation.setSelected(false);
		}

		//Dans le cas d'une déselection du bouton, on retire ce qui concerne le mode Entrainement
		if(!radioButtonEntrainement.isSelected()) {
			modeEntrainement.setVisible(false);
			modeEntrainement1.setVisible(false);
			modeEntrainement2.setVisible(false);
			modeEntrainement3.setVisible(false);
			modeEntrainement4.setVisible(false);
		}
	}


	//Gestion de si je sélectionne une nombre de lettres minimum autorisé, l'autre se décoche 
	@FXML
	public void selection2Lettres(ActionEvent event) {
		if(radioButton3Lettres.isSelected()) {
			radioButton3Lettres.setSelected(false);
		}
	}

	@FXML
	public void selection3Lettres(ActionEvent event) {
		if(radioButton2Lettres.isSelected()) {
			radioButton2Lettres.setSelected(false);
		}
	}

	//Méthode qui restreint à un caractère, la saisie du caractère d'occultation
	@FXML
	public void RestrictionOne(KeyEvent event) {
		if(CaraOccul.getText().length() > 1) {
			CaraOccul.deletePreviousChar();
		}
	}

	//Méthode qui restreint à la saisie de chiffres uniquement pour la saisie du temps
	@FXML
	public void RestrictionChiffre(KeyEvent event) {
		if(nbMinute.getText().length() > 0) {
			if(!nbMinute.getText().matches("[0-9]*")) {
				nbMinute.deletePreviousChar();
			}
		}
	}

	//Méthode qui enlève ou fait apparaître le choix du nombre de lettre si mot incomplet est coché
	@FXML
	public void motIncomplet(ActionEvent event) {

		//Si on coche le radioButton
		if(!checkBoxMotIncomplet.isDisable()) {
			modeEntrainement3.setVisible(true);
			modeEntrainement4.setVisible(true);
		} 
		//Si on le décoche
		if(checkBoxMotIncomplet.isDisabled()) {
			modeEntrainement3.setVisible(false);
			modeEntrainement4.setVisible(false);
		} 
	}

}
