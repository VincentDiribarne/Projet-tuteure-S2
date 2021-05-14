package Version_Enseignant.All_Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Enregistrement_Final implements Initializable{


	@FXML private Text recupScene;

	//Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Conteneurs en octets de la consigne
		byte [] contenuConsigne = Controller_Page_Apercu.contenuConsigne.getBytes();
		byte [] longueurConsigne = getLongueur(Controller_Page_Apercu.contenuConsigne);
		
		//Conteneurs en octets de la transcription
		byte [] contenuTranscription = Controller_Page_Apercu.contenuTranscription.getBytes();
		byte [] longueurTranscription = getLongueur(Controller_Page_Apercu.contenuTranscription);
		
		//Conteneurs en octets de l'aide
		byte [] contenuAide = Controller_Page_Apercu.contenuAide.getBytes();
		byte [] longueurAide = getLongueur(Controller_Page_Apercu.contenuAide);
		
		//Caractère d'occultation
		byte [] caraOccul = Controller_Page_Des_Options.caraOccul.getBytes();
		
		//Limite de temps (pour le mode Evaluation)
		byte [] nbMin = Controller_Page_Des_Options.nbMin.getBytes();
		
		//Conteneurs du media
		byte[] contenuMedia = null;
		byte [] longueurMedia = null;
		
		try {
			contenuMedia = URI.create(Controller_Importer_Ressource.contenuMedia.getSource()).toURL().openStream().readAllBytes();
			longueurMedia = getLongueur(URI.create(Controller_Importer_Ressource.contenuMedia.getSource()).toURL().openStream().toString());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		try {
			
			//On ouvre un fichier où on va enregistrer les informations
			//On lui donne l'endroit où il doit être enregistré et le nom
			File file = new File(Controller_Nouvel_Exo.contenuRepertoire, Controller_Nouvel_Exo.contenuNomExo + ".bin");
			FileOutputStream out = new FileOutputStream(file);
			
			//On y écrit la consigne
			out.write(longueurConsigne);
			out.write(contenuConsigne);
			
			//On y écrit la transcription
			out.write(longueurTranscription);
			out.write(contenuTranscription);
			
			//On y écrit les aides
			out.write(longueurAide);
			out.write(contenuAide);
			
			//On y écrit le caractère d'occultation
			out.write(caraOccul);
			
			//On y écrit les données du media
			out.write(longueurMedia);
			out.write(contenuMedia);
	
			//Fermeture du fichier
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			METHDOES GENERALES		////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

	//Bouton Nouveau qui permet de créer un nouvel exercice
	@FXML
	public void pageNouvelExo() throws IOException {
		Stage primaryStage = (Stage) recupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/NouvelExo.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	//Bouton Préférences qui emmène sur la page des paramètres
	@FXML
	public void preferences(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) recupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	//Bouton DarkMode qui met en darkMode l'application
	@FXML 
	public void darkMode() {
		//TODO faire le DarkMode
	}

	@FXML
	public void pageParametres(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) recupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	@FXML
	public void annuler(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) recupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageAccueil.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}
	
	
	private byte[] getLongueur(String chaine) {
		
        int nbCara= 0;
        
        for(int i=0; i< chaine.length();i++){
            nbCara ++;
        }
        return ByteBuffer.allocate(4).putInt(nbCara).array();
    }
	

}
