package Version_Enseignant.All_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Page_Des_Options implements Initializable {

	@FXML
	private RadioButton radioButtonEntrainement;
	@FXML
	private RadioButton radioButtonEvaluation;
	@FXML
	private RadioButton radioButton2Lettres;
	@FXML
	private RadioButton radioButton3Lettres;
	@FXML
	private TextField CaraOccul;
	@FXML
	private TextField nbMinute;
	@FXML
	private CheckBox checkBoxMotIncomplet;
	@FXML
	private CheckBox checkBoxSolution;
	@FXML
	private CheckBox checkBoxMotsDecouverts;
	@FXML
	private CheckBox sensibiliteCasse;
	@FXML
	private Button enregistrer;

	// Variables qui contiennent les informations sur l'exercice
	public static String caraOccul;
	public static String nbMin;
	public static boolean sensiCasse;
	public static boolean entrainement;
	public static boolean evaluation;
	public static boolean solution;
	public static boolean motDecouverts;
	public static boolean motIncomplet;
	public static boolean lettres_2;
	public static boolean lettres_3;

	// Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//Pour la fonction ouvrir
		//On met le caractère d'occultation 
		if(caraOccul != null) {
			CaraOccul.setText(caraOccul);
		}

		//On met la sensibilité à la casse si celle-ci est activée
		if(sensiCasse == true) {
			sensibiliteCasse.setSelected(true);
		}

		//On met le bon mode
		//Entrainement
		if(entrainement == true) {
			radioButtonEntrainement.setSelected(true);

			//On met disable ce qui concerne le mode Evaluation
			nbMinute.setDisable(true);

			//Si l'affichage de la solution est autorisé
			if(solution == true) {
				checkBoxSolution.setSelected(true);
			}

			//Si l'affichage du nombre de mots découverts en temps réel est autorisé
			if(motDecouverts == true) {
				checkBoxMotsDecouverts.setSelected(true);
			}

			//Si l'option mot incomplet est autorisé
			if(motIncomplet ==  true) {
				checkBoxMotIncomplet.setSelected(true);

				//Si c'est pour deux lettres
				if(lettres_2 == true) {
					radioButton2Lettres.setSelected(true);
				}

				//Si c'est pour trois lettres
				if(lettres_3 == true) {
					radioButton3Lettres.setSelected(true);
				}

			}
		}

		//Evaluation
		if(evaluation == true) {
			radioButtonEvaluation.setSelected(true);

			//On met disable ce qui concerne le mode Entrainement
			checkBoxMotsDecouverts.setDisable(true);
			checkBoxMotIncomplet.setDisable(true);
			checkBoxSolution.setDisable(true);
			radioButton2Lettres.setDisable(true);
			radioButton3Lettres.setDisable(true);
			nbMinute.setText(nbMin);
		}

	}

	// Bouton Quitter qui permet à l'enseignant de quitter l'application (disponible
	// sur toutes les pages)
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	// Bouton Ouvrir qui permet à l'enseignant d'ouvrir un exercice qu'il à déjà
	// créé auparavant
	@FXML
	public void ouvrir(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Ouvrez votre exercice");
		fileChooser.showOpenDialog(null);
		// TODO Chargez l'exercice dans la page
	}

	// Bouton Préférences qui emmène sur la page des paramètres
	@FXML
	public void preferences(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	// Bouton qui fait retourner l'enseignant à la page d'apercu (bouton retour)
	@FXML
	public void pageApercu(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageApercu.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	@FXML
	public void pageEnregistrementFinal(ActionEvent event) throws IOException {
		// Quand on passe à la page suivante, on mémorise les informations des options
		caraOccul = CaraOccul.getText();
		nbMin = nbMinute.getText();

		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/EnregistrementFinal.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	// Bouton DarkMode qui met en darkMode l'application
	@FXML
	public void darkMode() {
		// TODO faire le DarkMode
	}

	// Gestion de si je sélectionne un mode, l'autre se décoche
	@FXML
	public void selectionModeEvaluation(ActionEvent event) {
		// On fait apparaître ce qui concerne le mode Evaluation
		nbMinute.setDisable(false);
		evaluation = true;

		// On enlève les sélections du mode entrainement et on passe les variables à false
		checkBoxMotIncomplet.setSelected(false);
		motIncomplet = false;
		checkBoxSolution.setSelected(false);
		solution = false;
		checkBoxMotsDecouverts.setSelected(false);
		motDecouverts = false;
		radioButton2Lettres.setSelected(false);
		lettres_2 = false;
		radioButton3Lettres.setSelected(false);
		lettres_3 = false;

		//On met disable ce qui concerne le mode Entrainement
		checkBoxMotsDecouverts.setDisable(true);
		checkBoxMotIncomplet.setDisable(true);
		checkBoxSolution.setDisable(true);
		radioButton2Lettres.setDisable(true);
		radioButton3Lettres.setDisable(true);

		// On regarde si l'autre bouton est sélectionné, si c'est le cas on le
		// déselectionne
		if (radioButtonEntrainement.isSelected()) {
			radioButtonEntrainement.setSelected(false);
			entrainement = false;
		}

		// Dans le cas d'une déselection du bouton, on retire ce qui concerne le mode
		// Evaluation
		if (!radioButtonEvaluation.isSelected()) {
			evaluation = false;

			//on vide le textField
			nbMinute.setText(null);
		}

	}

	@FXML
	public void selectionModeEntrainement(ActionEvent event) {
		// On fait apparaître ce qui concerne le mode Entrainement
		//On met disable ce qui concerne le mode Entrainement
		checkBoxMotsDecouverts.setDisable(false);
		checkBoxMotIncomplet.setDisable(false);
		checkBoxSolution.setDisable(false);
		radioButton2Lettres.setDisable(false);
		radioButton3Lettres.setDisable(false);

		// On cache ce qui concerne le mode Evaluation
		nbMinute.setDisable(true);
		entrainement = true;

		// On réinitialise le nombre de minutes
		nbMinute.setText(null);

		// On regarde si l'autre bouton est sélectionné, si c'est le cas on le
		// déselectionne
		if (radioButtonEvaluation.isSelected()) {
			radioButtonEvaluation.setSelected(false);
			evaluation = false;
		}

		// Dans le cas d'une déselection du bouton, on retire ce qui concerne le mode
		// Entrainement
		if (!radioButtonEntrainement.isSelected()) {
			
			checkBoxMotsDecouverts.setDisable(true);
			checkBoxMotIncomplet.setDisable(true);
			checkBoxSolution.setDisable(true);
			radioButton2Lettres.setDisable(true);
			radioButton3Lettres.setDisable(true);

			// On enlève les sélections du mode entrainement et on passe les variables à false
			checkBoxMotIncomplet.setSelected(false);
			motIncomplet = false;
			checkBoxSolution.setSelected(false);
			solution = false;
			checkBoxMotsDecouverts.setSelected(false);
			motDecouverts = false;
			radioButton2Lettres.setSelected(false);
			lettres_2 = false;
			radioButton3Lettres.setSelected(false);
			lettres_3 = false;

			entrainement = false;
		}

	}

	// Gestion de si je sélectionne une nombre de lettres minimum autorisé, l'autre
	// se décoche
	@FXML
	public void selection2Lettres(ActionEvent event) {
		if (radioButton3Lettres.isSelected()) {
			radioButton3Lettres.setSelected(false);

			lettres_2 = true;
			lettres_3 = false;
		} else {
			lettres_2 = true;
		}
	}

	@FXML
	public void selection3Lettres(ActionEvent event) {
		if (radioButton2Lettres.isSelected()) {
			radioButton2Lettres.setSelected(false);

			lettres_3 = true;
			lettres_2 = false;
		} 
		else {
			lettres_3 = true;
		}
	}

	// Méthode qui restreint à un caractère, la saisie du caractère d'occultation
	@FXML
	public void RestrictionOne(KeyEvent event) {
		if (CaraOccul.getText().length() > 1) {
			CaraOccul.deletePreviousChar();
		}
	}

	// Méthode qui restreint à la saisie de chiffres uniquement pour la saisie du
	// temps
	@FXML
	public void RestrictionChiffre(KeyEvent event) {
		if (nbMinute.getText().length() > 0) {
			if (!nbMinute.getText().matches("[0-9]*")) {
				nbMinute.deletePreviousChar();
			}
		}
	}

	// Méthode qui enlève ou fait apparaître le choix du nombre de lettre si mot
	// incomplet est coché
	@FXML
	public void motIncomplet(ActionEvent event) {

		// Si on coche le radioButton
		if (checkBoxMotIncomplet.isSelected()) {

			radioButton2Lettres.setDisable(false);
			radioButton3Lettres.setDisable(false);

			// on passe à true
			motIncomplet = true;
		}
		// Si on le décoche
		if (!checkBoxMotIncomplet.isSelected()) {
			// on le cache
			radioButton2Lettres.setDisable(true);
			radioButton3Lettres.setDisable(true);
			// on les déselectionne et on repasse les variables à false
			radioButton2Lettres.setSelected(false);
			radioButton3Lettres.setSelected(false);

			lettres_2 = false;
			lettres_3 = false;

			// on passe à false
			motIncomplet = false;
		}

	}

	// Méthode qui passe à true ou false la variable sensiCasse
	@FXML
	public void sensiCasse(ActionEvent event) {
		// Si la case est cochée
		if (sensibiliteCasse.isSelected()) {
			sensiCasse = true;
		}
		// Dans le cas contraire
		else {
			sensiCasse = false;
		}
	}

	// Méthode qui passe à true ou false la variable solution
	@FXML
	public void affichageSolution(ActionEvent event) {
		// Si la case est cochée
		if (checkBoxSolution.isSelected()) {
			solution = true;
		}
		// Dans le cas contraire
		else {
			solution = false;
		}
	}

	// Méthode qui passe à true ou false la variable motsDécouverts
	@FXML
	public void motDecouverts(ActionEvent event) {
		// Si la case est cochée
		if (checkBoxMotsDecouverts.isSelected()) {
			motDecouverts = true;
		}
		// Dans le cas contraire
		else {
			motDecouverts = false;
		}
	}

}
