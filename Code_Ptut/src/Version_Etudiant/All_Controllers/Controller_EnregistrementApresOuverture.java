package Version_Etudiant.All_Controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class Controller_EnregistrementApresOuverture implements Initializable{

	@FXML private TextField nom;
	@FXML private TextField prenom;
	@FXML private TextField repertoire;
	@FXML private Button preEnregistrement;
	
	//Variables pour stocker les informations relatives à l'etudiant
	public static String nomEtudiant;
	public static String prenEtudiant;
	public static String repertoireEtudiant;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//Listener, tant qu'un TextField est vide, le bouton n'est pas disponible
		//Pour le TextField du nom de l'étudiant
		nom.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(nom.getText().isEmpty() || prenom.getText().isEmpty() || repertoire.getText().isEmpty()) {
					preEnregistrement.setDisable(true);
				} else {
					preEnregistrement.setDisable(false);
				}
			}
		});
		
		//Pour le TextField du prénom de l'étudiant
		prenom.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(nom.getText().isEmpty() || prenom.getText().isEmpty() || repertoire.getText().isEmpty()) {
					preEnregistrement.setDisable(true);
				} else {
					preEnregistrement.setDisable(false);
				}
			}
		});
		
		//Pour le TextField du repertoire dans lequel sera enregistré le fichier de l'étudiant
		repertoire.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(nom.getText().isEmpty() || prenom.getText().isEmpty() || repertoire.getText().isEmpty()) {
					preEnregistrement.setDisable(true);
				} else {
					preEnregistrement.setDisable(false);
				}
			}
		});

	}
	
	
	//Méthode qui permet à l'étudiant de choisir le dossier dans lequel l'étudiant verra son exercice enregistré
	@FXML
	public void choixRepertoire(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory;
		directoryChooser.setTitle("Choisissez un répertoire pour l'enregistrement de votre exercice");
		selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory != null) {
			repertoire.setText(selectedDirectory.getAbsolutePath());
		}
	}
	
	//Méthode qui permet de quitter la popUp, un fois les TextFields remplis et de sauvegarder les infos
	@FXML
	public void quitter(ActionEvent event) {
		nomEtudiant = nom.getText();
		prenEtudiant = prenom.getText();
		repertoireEtudiant = repertoire.getText();
		
		nom.getScene().getWindow().hide();
	}
	

}
