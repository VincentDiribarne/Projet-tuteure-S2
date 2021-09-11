package Version_Enseignant.All_Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import Version_Enseignant.DeplacementFenetre;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

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

	//Toutes les variables des tooltip
	@FXML private ImageView toolTipOccul;
	@FXML private ImageView toolTipSensi;
	@FXML private ImageView toolTipEntr;
	@FXML private ImageView toolTipEval;
	@FXML private ImageView toolTipNbMin;
	@FXML private ImageView toolTipMotDecouvert;
	@FXML private ImageView toolTipSolution;
	@FXML private ImageView toolTipMotIncomplet;

	@FXML private CheckMenuItem dark;

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
			nbMinute.setDisable(false);
		}

		checkMode();
		
		//Si tous les trucs nécessaires sont cochés on met le bouton enregistrer dispo
		if(!CaraOccul.getText().isEmpty() && (radioButtonEntrainement.isSelected() || (radioButtonEvaluation.isSelected()  && !nbMinute.getText().isEmpty()))) {
			enregistrer.setDisable(false);
		}

	}

	//Listener qui vérifie qu'au moins un mode a été coché avant de passer à la suite
	private void checkMode() {

		//Pour le TextField du caractère d'occultation
		CaraOccul.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(!CaraOccul.getText().isEmpty() && (radioButtonEntrainement.isSelected() || (radioButtonEvaluation.isSelected()  && !nbMinute.getText().isEmpty()))) {
					enregistrer.setDisable(false);
				} else {
					enregistrer.setDisable(true);
				}

			}
		});

		//Pour le radioButton du mode entrainement
		radioButtonEntrainement.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if (!CaraOccul.getText().isEmpty() && (radioButtonEntrainement.isSelected() || (radioButtonEvaluation.isSelected() && !nbMinute.getText().isEmpty()))) { 
					enregistrer.setDisable(false);
				} else {
					enregistrer.setDisable(true);
				}
			}
		});

		//Pour le radioButton du mode évaluation
		radioButtonEvaluation.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if (!CaraOccul.getText().isEmpty() && (radioButtonEntrainement.isSelected() || (radioButtonEvaluation.isSelected() && !nbMinute.getText().isEmpty()))) { 
					enregistrer.setDisable(false);
				} else {
					enregistrer.setDisable(true);
				}
			}
		});


		//Pour le nombre de min
		nbMinute.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

				if(radioButtonEvaluation.isSelected()) {
					if(!CaraOccul.getText().isEmpty() && !nbMinute.getText().isEmpty()) {
						enregistrer.setDisable(false);
					} else {
						enregistrer.setDisable(true);
					}
				}

			}
		});


	}

	//Méthode qui permet de se rendre au manuel utilisateur == tuto
	@FXML
	public void tuto() throws MalformedURLException, IOException, URISyntaxException {
		
		InputStream is = MainEnseignant.class.getResourceAsStream("Manuel_Utilisateur.pdf");

		File pdf = File.createTempFile("Manuel Utilisateur", ".pdf");
		pdf.deleteOnExit();
        OutputStream out = new FileOutputStream(pdf);

        byte[] buffer = new byte[4096];
        int bytesRead = 0;

        while (is.available() != 0) {
            bytesRead = is.read(buffer);
            out.write(buffer, 0, bytesRead);
        }
        
        out.close();
        is.close();
        
        Desktop.getDesktop().open(pdf);

	}


	//Bouton Quitter qui permet à l'enseignant de quitter l'application (disponible sur toutes les pages)
	@FXML
	public void quitter(ActionEvent event) throws IOException {

		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/ConfirmationQuitter.fxml"));
		Scene scene = new Scene(root, 400, 200);
		//On bloque sur cette fenêtre
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);

		//Bordure
		Rectangle rect = new Rectangle(400,200); 
		rect.setArcHeight(20.0); 
		rect.setArcWidth(20.0);  
		root.setClip(rect);

		DeplacementFenetre.deplacementFenetre((Pane) root, primaryStage);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	// Bouton qui fait retourner l'enseignant à la page d'apercu (bouton retour)
	@FXML
	public void pageApercu(ActionEvent event) throws IOException {

		if(!CaraOccul.getText().isEmpty() && CaraOccul.getText() != "") {
			caraOccul = CaraOccul.getText();
		}

		if(!nbMinute.getText().isEmpty() && nbMinute.getText() != "") {
			nbMin = nbMinute.getText();
		}

		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/PageApercu.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
	}

	//Méthode pour retourner au menu
	public void retourMenu() throws IOException {
		Stage stage = (Stage) CaraOccul.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/Menu.fxml"));
		Scene scene = new Scene(root,  MainEnseignant.width, MainEnseignant.height - 60);
		stage.setScene(scene);
		darkModeActivation(scene);
		stage.show();
	}

	@FXML
	public void pageEnregistrementFinal(ActionEvent event) throws IOException {


		// Quand on passe à la page suivante, on mémorise les informations des options
		caraOccul = CaraOccul.getText();
		nbMin = nbMinute.getText();

		retourMenu();

		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/ValidationEnregistrement.fxml"));
		Scene scene = new Scene(root, 320, 150);
		DeplacementFenetre.deplacementFenetre((Pane) root, primaryStage);
		//On bloque sur cette fenêtre
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	@FXML
	public void pageNouvelExo() throws IOException {
		//Réinitialisation des variables
		Controller_Page_Accueil c = new Controller_Page_Accueil();
		c.delete();
		Stage primaryStage = (Stage) CaraOccul.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/NouvelExo.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
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
			nbMinute.setDisable(true);
		}

	}

	@FXML
	public void selectionModeEntrainement(ActionEvent event) {
		// On fait apparaître ce qui concerne le mode Entrainement
		//On enleve disable ce qui concerne le mode Entrainement
		checkBoxMotsDecouverts.setDisable(false);
		checkBoxMotIncomplet.setDisable(false);
		checkBoxSolution.setDisable(false);
		radioButton2Lettres.setDisable(false);
		radioButton3Lettres.setDisable(false);

		// On cache ce qui concerne le mode Evaluation
		nbMinute.setDisable(true);
		entrainement = true;

		// On réinitialise le nombre de minutes
		nbMinute.setText("");

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

			//On coche par défaut les 2 lettres
			radioButton2Lettres.setSelected(true);
			lettres_2 = true;
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

	/////// Toutes les méthodes concernant les toolTip///////////////
	//Méthode pour afficher une tooltip et agrandir l'image
	public void affichageToolTip(ImageView image, String description) {
		Tooltip t = new Tooltip(description);
		image.setFitWidth(image.getFitWidth() + 2);
		image.setFitHeight(image.getFitHeight() + 2);
		Tooltip.install(image, t);
	}

	//Méthode pour rétrcir une image
	public void adaptationImage(ImageView image) {
		image.setFitWidth(image.getFitWidth() - 2);
		image.setFitHeight(image.getFitHeight() - 2);
	}


	//Pour le caractère d'oocultation du texte
	@FXML
	public void tipOcculEnter() {
		affichageToolTip(toolTipOccul, "Ce caractère servira à crypter le script de votre document");
	}

	@FXML
	public void tipOcculExit() {
		adaptationImage(toolTipOccul);
	}

	//Pour la sensibilité à la casse
	@FXML
	public void tipSensiEnter() {
		affichageToolTip(toolTipSensi, "Activer la sensibilité à la casse signifie prendre en compte la différence entre minuscule et majuscule");
	}

	@FXML
	public void tipSensiExit() {
		adaptationImage(toolTipSensi);
	}

	//Pour le mode Evaluation
	@FXML
	public void tipEvalEnter() {
		affichageToolTip(toolTipEval, "Le mode Evaluation n'autorise aucune aide pour l'étudiant");
	}

	@FXML
	public void tipEvalExit() {
		adaptationImage(toolTipEval);
	}

	//Pour le nombre de minutes à rentrer par le professeur
	@FXML
	public void tipMinEnter() {
		affichageToolTip(toolTipNbMin, "Le nombre de minutes dont l'élève disposera pour faire l'exercice");
	}

	@FXML
	public void tipMinExit() {
		adaptationImage(toolTipNbMin);
	}

	//Pour le mode Entrainement
	@FXML
	public void tipEntrEnter() {
		affichageToolTip(toolTipEntr, "Le mode Entraînement autorise ou non ceratiens options (listées ci-dessous)");
	}

	@FXML
	public void tipEntrExit() {
		adaptationImage(toolTipEntr);
	}

	//Pour l'affichage du nombre de mot découvert
	@FXML
	public void tipMotDecouvertEnter() {
		affichageToolTip(toolTipMotDecouvert, "Cette option permet à l'étudiant de voir en temps réel le nombre de mots qu'il a trouvé");
	}

	@FXML
	public void tipMotDecouvertExit() {
		adaptationImage(toolTipMotDecouvert);
	}

	//Pour l'autorisation de l'affichage de la solution
	@FXML
	public void tipSolutionEnter() {
		affichageToolTip(toolTipSolution, "Autoriser à ce que l'étudiant puisse consulter la solution pendant l'exercice");
	}

	@FXML
	public void tipSolutionExit() {
		adaptationImage(toolTipSolution);
	}

	//Pour le remplacement partiel
	@FXML
	public void tipMotIncompletEnter() {
		affichageToolTip(toolTipMotIncomplet, "Autoriser le remplacement partiel des mots à partir d'un nombre minimum de lettres");
	}

	@FXML
	public void tipMotIncompletExit() {
		adaptationImage(toolTipMotIncomplet);
	}

	//Méthode pour passer ou non le darkMode
	@FXML
	public void darkMode() {

		if(dark.isSelected()) {
			CaraOccul.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			CaraOccul.getScene().getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			Controller_Page_Accueil.isDark = true;
		} else {
			CaraOccul.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			CaraOccul.getScene().getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			Controller_Page_Accueil.isDark = false;
		}

	}

	//Méthode qui regarde si le darkMode est actif et l'applique en conséquence à la scene
	public void darkModeActivation(Scene scene) {
		if(Controller_Page_Accueil.isDark) {
			scene.getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			dark.setSelected(true);
		} else {
			scene.getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			dark.setSelected(false);
		}
	}



}
