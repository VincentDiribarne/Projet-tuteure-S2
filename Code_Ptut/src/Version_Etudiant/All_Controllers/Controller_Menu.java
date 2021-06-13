package Version_Etudiant.All_Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;
import Version_Etudiant.MainEtudiant;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.CheckMenuItem;
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
	
	@FXML private CheckMenuItem dark;
	
	public static boolean isDark = false;
	
	private static Controller_Page_Exercice c;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	//Fonction pour quitter l'application
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}
	
	//M�thode qui permet de se rendre au manuel utilisateur == tuto
	@FXML
	public void tuto() throws MalformedURLException, IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URL("https://docs.google.com/document/d/1r6RBg1hgmUD9whe2_Opq_Uy1BgxdBL1Th0HkQHWxcFo/edit?usp=sharing").toURI());
	}

	//Fonction qui permet � l'�tudiant d'ouvrir un exercice (t�l�charg� au pr�alable)
	@FXML
	public void ouvrir(ActionEvent event) throws IOException {

		FileChooser fileChooser = new FileChooser();
		File selectedFile = new File("");
		fileChooser.setTitle("Ouvrez votre exercice");

		selectedFile = fileChooser.showOpenDialog(null);
		decrypte(selectedFile);

		//On load la page o� il y a l'exercice
		loadExo();
	}

	//Fonction qui permet d'aller � la page o� se trouve l'exercice
	public void loadExo() throws IOException {
		Stage primaryStage = (Stage) recupScene.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML_Files/PageExercice.fxml"));
		Parent root = loader.load();
		c = loader.getController();
		primaryStage.setMaximized(true);
		Scene scene = new Scene(root, MainEtudiant.width, MainEtudiant.height);
		
		darkModeActivation(scene);
		
		primaryStage.setScene(scene);		
		primaryStage.show();
	}

	public static Controller_Page_Exercice getC() {
		return c;
	}

	// Fonction qui converti des bytes en String
	public String chaine(byte[] bytes) {
		// Variable qui contiendra la chaine
		String chaine = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
		return chaine;
	}


	// Fonction qui va load les informations du fichier s�lectionn� dans les
	// diff�rents TextField...
	public void decrypte(File file) throws IOException {

		// Variables pour r�cup�rer les informations du fichier
		String consigne, aide, transcription, caraOccul, nbMin;
		int nombreOctetALire, sensiCasse, mode, solution, motsDecouverts, motsIncomplets, lettre, extension;
		File tmpFile;

		// On ouvre le fichier en lecture
		FileInputStream fin = new FileInputStream(file);

		// On r�cup�re la longueur de la consigne + la consigne
		nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
		consigne = chaine(fin.readNBytes(nombreOctetALire));
		// On met la consigne dans la textField associ�
		Controller_Page_Exercice.contenuConsigne = consigne;

		// On r�cup�re la longueur de la transcription + la transcription
		nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
		transcription = chaine(fin.readNBytes(nombreOctetALire));
		// On met la transcription dans le textField associ�
		Controller_Page_Exercice.contenuTranscription = transcription;

		// On r�cup�re la longueur de l'aide + l'aide
		nombreOctetALire = ByteBuffer.wrap(fin.readNBytes(4)).getInt();
		aide = chaine(fin.readNBytes(nombreOctetALire));
		// On met les aides dans le textField associ�
		Controller_Page_Aides.contenuAide = aide;

		// On r�cup�re le caract�re d'occultation
		caraOccul = chaine(fin.readNBytes(1));
		// On met le caract�re dans le texField associ�
		Controller_Page_Exercice.caractereOccul = caraOccul;

		// On r�cup�re la reponse de sensiCasse 0 = false, 1 = true
		sensiCasse = ByteBuffer.wrap(fin.readNBytes(1)).get();

		// On met la variable associ�e en fonction de la r�ponse
		if (sensiCasse == 1) {
			Controller_Page_Exercice.sensiCasse = true;
		} else {
			Controller_Page_Exercice.sensiCasse = false;
		}

		// On r�cup�re le mode choisi par l'enseignant 0 = entrainement, 1 = evaluation
		mode = ByteBuffer.wrap(fin.readNBytes(1)).get();

		// On met la variable associ�e en fonction de la r�ponse
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

			// On r�cup�re la reponse de l'affiche de la solution 0 = false, 1 = true
			solution = ByteBuffer.wrap(fin.readNBytes(1)).get();

			// On met la variable associ�e en fonction de la r�ponse
			if (solution == 1) {
				Controller_Page_Exercice.solution = true;
			} else {
				Controller_Page_Exercice.solution = false;
			}

			// On r�cup�re la reponse de l'affiche du nombre de mots d�couverts en temps
			// r�el 0 = false, 1 = true
			motsDecouverts = ByteBuffer.wrap(fin.readNBytes(1)).get();

			// On met la variable associ�e en fonction de la r�ponse
			if (motsDecouverts == 1) {
				Controller_Page_Exercice.motDecouverts = true;
			} else {
				Controller_Page_Exercice.motDecouverts = false;
			}

			// On r�cup�re la reponse de l'autorisation du nb min de lettre pour d�couvrir
			// le mot 0 = false, 1 = true
			motsIncomplets = ByteBuffer.wrap(fin.readNBytes(1)).get();

			// On met la variable associ�e en fonction de la r�ponse
			if (motsIncomplets == 1) {
				Controller_Page_Exercice.motIncomplet = true;

				// On r�cup�re la reponse du nb min de lettre pour d�couvrir le mot 2 = 2
				// lettres, 3 = 3 lettres
				lettre = ByteBuffer.wrap(fin.readNBytes(1)).get();

				// On met la variable associ�e en fonction de la r�ponse
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

		//Si c'est un mp3, on doit d�chiffrer l'image
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

			//On r�cup�re ensuite le media
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

	@FXML
	public void aPropos(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) recupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Etudiant/FXML_Files/A_Propos.fxml"));
		Scene scene = new Scene(root, MainEtudiant.width, MainEtudiant.height - 60);
		primaryStage.setScene(scene);
		
		darkModeActivation(scene);
		
		primaryStage.setMaximized(true);
		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(1200);
		primaryStage.show();
	}

	@FXML
	public void retourMenu(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) recuperation.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Etudiant/FXML_Files/Menu.fxml"));
		Scene scene = new Scene(root, MainEtudiant.width, MainEtudiant.height);
		primaryStage.setScene(scene);
		
		darkModeActivation(scene);
		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(1200);
		primaryStage.show();
	}
	
	//M�thode pour passer ou non le darkMode
	@FXML
	public void darkMode() {
		
		if(dark.isSelected()) {
			recupScene.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Etudiant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			recupScene.getScene().getStylesheets().addAll(getClass().getResource("/Version_Etudiant/FXML_Files/darkModeTest.css").toExternalForm());
			isDark = true;
		} else {
			recupScene.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Etudiant/FXML_Files/darkModeTest.css").toExternalForm());
			recupScene.getScene().getStylesheets().addAll(getClass().getResource("/Version_Etudiant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			isDark = false;
		}
	}
	
	//M�thode qui regarde si le darkMode est actif et l'applique en cons�quence � la scene
	public void darkModeActivation(Scene scene) {
		if(isDark) {
			scene.getStylesheets().removeAll(getClass().getResource("/Version_Etudiant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("/Version_Etudiant/FXML_Files/darkModeTest.css").toExternalForm());
			dark.setSelected(true);
		} else {
			scene.getStylesheets().removeAll(getClass().getResource("/Version_Etudiant/FXML_Files/darkModeTest.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("/Version_Etudiant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			dark.setSelected(false);
		}
	}
	
}



