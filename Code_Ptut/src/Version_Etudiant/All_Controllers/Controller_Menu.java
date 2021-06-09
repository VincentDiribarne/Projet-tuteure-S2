package Version_Etudiant.All_Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;
import Version_Etudiant.MainEtudiant;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Menu implements Initializable{

	//Menu
	@FXML private Text recupScene;
	@FXML private ImageView handicap;

	//A propos
	@FXML private Label recuperation;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	//Fonction pour quitter l'application
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	//Fonction qui permet à l'étudiant d'ouvrir un exercice (téléchargé au préalable)
	@FXML
	public void ouvrir(ActionEvent event) throws IOException {

		FileChooser fileChooser = new FileChooser();
		File selectedFile = new File("");
		fileChooser.setTitle("Ouvrez votre exercice");

		selectedFile = fileChooser.showOpenDialog(null);
		decrypte(selectedFile);

		//On load la page où il y a l'exercice
		loadExo();
	}

	//Fonction qui permet d'aller à la page où se trouve l'exercice
	public void loadExo() throws IOException {
		Stage primaryStage = (Stage) recupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageExercice.fxml"));
		primaryStage.setMaximized(true);
		primaryStage.setScene(new Scene(root, MainEtudiant.width, MainEtudiant.height));
		primaryStage.show();
	}

	// Fonction qui converti des bytes en String
	public String chaine(byte[] bytes) {
		// Variable qui contiendra la chaine
		String chaine = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
		return chaine;
	}



	// Fonction qui va load les informations du fichier sélectionné dans les
	// différents TextField...
	public void decrypte(File file) throws IOException {

		// Variables pour récupérer les informations du fichier
		String consigne, aide, transcription, caraOccul, nbMin;
		int nombreOctetALire, sensiCasse, mode, solution, motsDecouverts, motsIncomplets, lettre, extension;
		File tmpFile;

		// On ouvre le fichier en lecture
		FileInputStream fin = new FileInputStream(file);

		// On récupère la longueur de la consigne + la consigne
		nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
		consigne = chaine(fin.readNBytes(nombreOctetALire));
		// On met la consigne dans la textField associé
		Controller_Page_Exercice.contenuConsigne = consigne;

		// On récupère la longueur de la transcription + la transcription
		nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
		transcription = chaine(fin.readNBytes(nombreOctetALire));
		// On met la transcription dans le textField associé
		Controller_Page_Exercice.contenuTranscription = transcription;

		// On récupère la longueur de l'aide + l'aide
		nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
		aide = chaine(fin.readNBytes(nombreOctetALire));
		// On met les aides dans le textField associé
		Controller_Page_Aides.contenuAide = aide;

		// On récupère le caractère d'occultation
		caraOccul = chaine(fin.readNBytes(1));
		// On met le caractère dans le texField associé
		Controller_Page_Exercice.caractereOccul = caraOccul;

		// On récupère la reponse de sensiCasse 0 = false, 1 = true
		sensiCasse = ByteBuffer.wrap(fin.readNBytes(1)).get();

		// On met la variable associée en fonction de la réponse
		if (sensiCasse == 1) {
			Controller_Page_Exercice.sensiCasse = true;
		} else {
			Controller_Page_Exercice.sensiCasse = false;
		}

		// On récupère le mode choisi par l'enseignant 0 = entrainement, 1 = evaluation
		mode = ByteBuffer.wrap(fin.readNBytes(1)).get();

		// On met la variable associée en fonction de la réponse
		// Mode Evaluation
		if (mode == 1) {
			Controller_Page_Exercice.evaluation = true;
			Controller_Page_Exercice.entrainement = false;

			nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
			nbMin = chaine(fin.readNBytes(nombreOctetALire));

			Controller_Page_Exercice.nbMin = nbMin;

			// Mode Entrainement
		} else {
			Controller_Page_Exercice.evaluation = false;
			Controller_Page_Exercice.entrainement = true;

			// On récupère la reponse de l'affiche de la solution 0 = false, 1 = true
			solution = ByteBuffer.wrap(fin.readNBytes(1)).get();

			// On met la variable associée en fonction de la réponse
			if (solution == 1) {
				Controller_Page_Exercice.solution = true;
			} else {
				Controller_Page_Exercice.solution = false;
			}

			// On récupère la reponse de l'affiche du nombre de mots découverts en temps
			// réel 0 = false, 1 = true
			motsDecouverts = ByteBuffer.wrap(fin.readNBytes(1)).get();

			// On met la variable associée en fonction de la réponse
			if (motsDecouverts == 1) {
				Controller_Page_Exercice.motDecouverts = true;
			} else {
				Controller_Page_Exercice.motDecouverts = false;
			}

			// On récupère la reponse de l'autorisation du nb min de lettre pour découvrir
			// le mot 0 = false, 1 = true
			motsIncomplets = ByteBuffer.wrap(fin.readNBytes(1)).get();

			// On met la variable associée en fonction de la réponse
			if (motsIncomplets == 1) {
				Controller_Page_Exercice.motIncomplet = true;

				// On récupère la reponse du nb min de lettre pour découvrir le mot 2 = 2
				// lettres, 3 = 3 lettres
				lettre = ByteBuffer.wrap(fin.readNBytes(1)).get();

				// On met la variable associée en fonction de la réponse
				if (lettre == 2) {
					Controller_Page_Exercice.lettres_2 = true;
					Controller_Page_Exercice.lettres_3 = false;
				} else {
					Controller_Page_Exercice.lettres_2 = false;
					Controller_Page_Exercice.lettres_3 = true;
				}

			} else {
				Controller_Page_Exercice.motIncomplet = false;
				Controller_Page_Exercice.lettres_2 = false;
				Controller_Page_Exercice.lettres_3 = false;
			}
		}

		//On regarde l'extension du media
		extension = ByteBuffer.wrap(fin.readNBytes(1)).get();

		//Si c'est un mp3, on doit déchiffrer l'image
		if(extension == 0) {

			nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(8)).getInt();

			File tmpFileImage = File.createTempFile("data", ".png");
			FileOutputStream ecritureFileImage = new FileOutputStream(tmpFileImage);
			ecritureFileImage.write(fin.readNBytes(nombreOctetALire));
			ecritureFileImage.close();

			Controller_Page_Exercice.contenuImage = new Image(tmpFileImage.toURI().toString());

			//On efface le fichier temporaire
			tmpFileImage.deleteOnExit();

			//On lit le mp3
			nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(8)).getInt();

			tmpFile = File.createTempFile("data", ".mp3");

		} 
		//Sinon c'est un mp4
		else {

			//On récupère ensuite le media
			nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(8)).getInt();

			tmpFile = File.createTempFile("data", ".mp4");

		}

		FileOutputStream ecritureFile = new FileOutputStream(tmpFile);
		ecritureFile.write(fin.readAllBytes());
		ecritureFile.close();

		Controller_Page_Exercice.contenuMedia = new Media(tmpFile.toURI().toString());

		//On efface le fichier temporaire
		tmpFile.deleteOnExit();

		// Fermeture du fichier
		fin.close();
	}

	/*@FXML
	public void aPropos(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) recupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/A_Propos.fxml"));
		primaryStage.setScene(new Scene(root, MainEtudiant.width, MainEtudiant.height));
		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(1200);
		primaryStage.show();
	}*/

	/*@FXML
		public void retourMenu(ActionEvent event) throws IOException {
			Stage primaryStage = (Stage) recuperation.getScene().getWindow();
	        Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/Menu.fxml"));
	        primaryStage.setScene(new Scene(root, MainEtudiant.width, MainEtudiant.height));
	        primaryStage.setMinHeight(800);
	        primaryStage.setMinWidth(1200);
	        primaryStage.show();
		}*/

	@FXML
	public void grossissementHandicap() {
		handicap.setFitWidth(handicap.getFitWidth() + 25);
		handicap.setFitHeight(handicap.getFitHeight() + 25);
	}

	@FXML
	public void retrecissementHandicap() {
		handicap.setFitWidth(handicap.getFitWidth() - 25);
		handicap.setFitHeight(handicap.getFitHeight() - 25);
	}
}



