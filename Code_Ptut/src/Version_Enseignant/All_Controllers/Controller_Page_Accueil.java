package Version_Enseignant.All_Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Page_Accueil implements Initializable {

	@FXML
	private Text RecupScene;
	@FXML
	private Label recupScene;

	@FXML private CheckMenuItem dark;
	public static boolean isDark = false;

	// Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

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
	public void ouvrir(ActionEvent event) throws IOException {

		FileChooser fileChooser = new FileChooser();
		File selectedFile = new File("");
		fileChooser.setTitle("Ouvrez votre exercice");

		// Appel de la fonction décrypte pour la fichier sélectionné
		selectedFile = fileChooser.showOpenDialog(null);
		decrypte(selectedFile);

		// On met le nom du fichier dans le TextField associé
		Controller_Nouvel_Exo.contenuNomExo = stripExtension(selectedFile);

		// On met le répertoire du fichier dans le TextField associé
		Controller_Nouvel_Exo.contenuRepertoire = stripPath(selectedFile);

		// On load la page d'après
		pageNouvelExo();
	}

	// Méthode qui va enlever l'extension du fichier
	public static String stripExtension(File file) {
		if (file == null) {
			return null;
		}
		String name = file.getName();

		int posPoint = name.lastIndexOf(".");

		if (posPoint == -1) {
			return name;
		}

		return name.substring(0, posPoint);
	}

	public static String stripPath(File file) {
		if (file == null) {
			return null;
		}
		String name = file.getAbsolutePath();

		int posPoint = name.lastIndexOf("\\");

		if (posPoint == -1) {
			return name;
		}

		return name.substring(0, posPoint);
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
		nombreOctetALire = ByteBuffer.wrap(readNBytes(fin, 4)).getInt();
		consigne = chaine(readNBytes(fin, nombreOctetALire));
		// On met la consigne dans la textField associé
		Controller_Page_Apercu.contenuConsigne = consigne;

		// On récupère la longueur de la transcription + la transcription
		nombreOctetALire = ByteBuffer.wrap(readNBytes(fin, 4)).getInt();
		transcription = chaine(readNBytes(fin, nombreOctetALire));
		// On met la transcription dans le textField associé
		Controller_Page_Apercu.contenuTranscription = transcription;

		// On récupère la longueur de l'aide + l'aide
		nombreOctetALire = ByteBuffer.wrap(readNBytes(fin, 4)).getInt();
		aide = chaine(readNBytes(fin, nombreOctetALire));
		// On met les aides dans le textField associé
		Controller_Page_Apercu.contenuAide = aide;

		// On récupère le caractère d'occultation
		caraOccul = chaine(readNBytes(fin, 1));
		// On met le caractère dans le texField associé
		Controller_Page_Des_Options.caraOccul = caraOccul;

		// On récupère la reponse de sensiCasse 0 = false, 1 = true
		sensiCasse = ByteBuffer.wrap(readNBytes(fin, 1)).get();

		// On met la variable associée en fonction de la réponse
		if (sensiCasse == 1) {
			Controller_Page_Des_Options.sensiCasse = true;
		} else {
			Controller_Page_Des_Options.sensiCasse = false;
		}

		// On récupère le mode choisi par l'enseignant 0 = entrainement, 1 = evaluation
		mode = ByteBuffer.wrap(readNBytes(fin, 1)).get();

		// On met la variable associée en fonction de la réponse
		// Mode Evaluation
		if (mode == 1) {
			Controller_Page_Des_Options.evaluation = true;
			Controller_Page_Des_Options.entrainement = false;

			nombreOctetALire = ByteBuffer.wrap(readNBytes(fin, 4)).getInt();
			nbMin = chaine(readNBytes(fin, nombreOctetALire));

			Controller_Page_Des_Options.nbMin = nbMin;

			// Mode Entrainement
		} else {
			Controller_Page_Des_Options.evaluation = false;
			Controller_Page_Des_Options.entrainement = true;

			// On récupère la reponse de l'affiche de la solution 0 = false, 1 = true
			solution = ByteBuffer.wrap(readNBytes(fin, 1)).get();

			// On met la variable associée en fonction de la réponse
			if (solution == 1) {
				Controller_Page_Des_Options.solution = true;
			} else {
				Controller_Page_Des_Options.solution = false;
			}

			// On récupère la reponse de l'affiche du nombre de mots découverts en temps
			// réel 0 = false, 1 = true
			motsDecouverts = ByteBuffer.wrap(readNBytes(fin, 1)).get();

			// On met la variable associée en fonction de la réponse
			if (motsDecouverts == 1) {
				Controller_Page_Des_Options.motDecouverts = true;
			} else {
				Controller_Page_Des_Options.motDecouverts = false;
			}

			// On récupère la reponse de l'autorisation du nb min de lettre pour découvrir
			// le mot 0 = false, 1 = true
			motsIncomplets = ByteBuffer.wrap(readNBytes(fin, 1)).get();

			// On met la variable associée en fonction de la réponse
			if (motsIncomplets == 1) {
				Controller_Page_Des_Options.motIncomplet = true;

				// On récupère la reponse du nb min de lettre pour découvrir le mot 2 = 2
				// lettres, 3 = 3 lettres
				lettre = ByteBuffer.wrap(readNBytes(fin, 1)).get();

				// On met la variable associée en fonction de la réponse
				if (lettre == 2) {
					Controller_Page_Des_Options.lettres_2 = true;
					Controller_Page_Des_Options.lettres_3 = false;
				} else {
					Controller_Page_Des_Options.lettres_2 = false;
					Controller_Page_Des_Options.lettres_3 = true;
				}

			} else {
				Controller_Page_Des_Options.motIncomplet = false;
				Controller_Page_Des_Options.lettres_2 = false;
				Controller_Page_Des_Options.lettres_3 = false;
			}
		}

		// On regarde l'extension du media
		extension = ByteBuffer.wrap(readNBytes(fin, 1)).get();

		// Si c'est un mp3, on doit déchiffrer l'image
		if (extension == 0) {

			nombreOctetALire = ByteBuffer.wrap(readNBytes(fin, 8)).getInt();

			File tmpFileImage = File.createTempFile("data", ".png");
			FileOutputStream ecritureFileImage = new FileOutputStream(tmpFileImage);
			ecritureFileImage.write(readNBytes(fin, nombreOctetALire));
			ecritureFileImage.close();

			Controller_Importer_Ressource.contenuImage = new Image(tmpFileImage.toURI().toString());
			Controller_Importer_Ressource.cheminImg = tmpFileImage.getAbsolutePath();

			// On efface le fichier temporaire
			tmpFileImage.deleteOnExit();

			// On lit le mp3
			nombreOctetALire = ByteBuffer.wrap(readNBytes(fin, 8)).getInt();

			tmpFile = File.createTempFile("data", ".mp3");

		}
		// Sinon c'est un mp4
		else {

			// On récupère ensuite le media
			nombreOctetALire = ByteBuffer.wrap(readNBytes(fin, 8)).getInt();

			//On met à null l'image car il n'y en a pas
			Controller_Importer_Ressource.contenuImage = null;

			tmpFile = File.createTempFile("data", ".mp4");

		}

		FileOutputStream ecritureFile = new FileOutputStream(tmpFile);
		ecritureFile.write(readAllBytes(fin));
		ecritureFile.close();

		Controller_Importer_Ressource.contenuMedia = new Media(tmpFile.toURI().toString());
		Controller_Importer_Ressource.cheminVideo = tmpFile.getAbsolutePath();

		// On efface le fichier temporaire
		tmpFile.deleteOnExit();

		// Fermeture du fichier
		fin.close();
	}

	// Fonction qui converti des bytes en String
	public String chaine(byte[] bytes) {
		// Variable qui contiendra la chaine
		String chaine = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
		return chaine;
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

	// Bouton Nouveau qui permet de créer un nouvel exercice
	@FXML
	public void pageNouvelExo() throws IOException {

		Stage primaryStage = (Stage) RecupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/NouvelExo.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	// Méthode qui va ouvrir la page à propos
	@FXML
	public void aPropos(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) RecupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/A_Propos.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	// Méthode qui permet de retourner au menu depuis la page à propos
	@FXML
	public void retourMenu(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) recupScene.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/Menu.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);

		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(1200);
		primaryStage.show();
	}

	//Méthode pour passer ou non le darkMode
	@FXML
	public void darkMode() {

		if(dark.isSelected()) {
			RecupScene.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			RecupScene.getScene().getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			isDark = true;
		} else {
			RecupScene.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			RecupScene.getScene().getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			isDark = false;
		}
	}

	//Méthode qui regarde si le darkMode est actif et l'applique en conséquence à la scene
	public void darkModeActivation(Scene scene) {
		if(isDark) {
			scene.getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			dark.setSelected(true);
		} else {
			scene.getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			dark.setSelected(false);
		}
	}

	public void delete() {
		//On remet toutes les variables statiques à null
		Controller_Nouvel_Exo.contenuNomExo = null;
		Controller_Nouvel_Exo.contenuRepertoire = null;
		Controller_Importer_Ressource.contenuMedia = null;
		Controller_Importer_Ressource.contenuImage = null;
		Controller_Page_Apercu.contenuAide = null;
		Controller_Page_Apercu.contenuConsigne = null;
		Controller_Page_Apercu.contenuTranscription = null;
		Controller_Page_Des_Options.caraOccul = null;
		Controller_Page_Des_Options.sensiCasse = false;
		Controller_Page_Des_Options.entrainement = false;
		Controller_Page_Des_Options.evaluation = false;
		Controller_Page_Des_Options.lettres_2 = false;
		Controller_Page_Des_Options.lettres_3 = false;
		Controller_Page_Des_Options.motDecouverts = false;
		Controller_Page_Des_Options.motIncomplet = false;
		Controller_Page_Des_Options.solution = false;
		Controller_Page_Des_Options.nbMin = null;
	}

	// Méthode qui va lire n bytes (ne marche pas sous java 1.8 donc on la remet ici
	// telle quel
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

	public static byte[] readNBytes(FileInputStream fin, int len) throws IOException {
		if (len < 0) {
			throw new IllegalArgumentException("len < 0");
		}

		List<byte[]> bufs = null;
		byte[] result = null;
		int total = 0;
		int remaining = len;
		int n;
		do {
			byte[] buf = new byte[Math.min(remaining, DEFAULT_BUFFER_SIZE)];
			int nread = 0;

			// read to EOF which may read more or less than buffer size
			while ((n = fin.read(buf, nread, Math.min(buf.length - nread, remaining))) > 0) {
				nread += n;
				remaining -= n;
			}

			if (nread > 0) {
				if (MAX_BUFFER_SIZE - total < nread) {
					throw new OutOfMemoryError("Required array size too large");
				}
				total += nread;
				if (result == null) {
					result = buf;
				} else {
					if (bufs == null) {
						bufs = new ArrayList<>();
						bufs.add(result);
					}
					bufs.add(buf);
				}
			}
			// if the last call to read returned -1 or the number of bytes
			// requested have been read then break
		} while (n >= 0 && remaining > 0);

		if (bufs == null) {
			if (result == null) {
				return new byte[0];
			}
			return result.length == total ? result : Arrays.copyOf(result, total);
		}

		result = new byte[total];
		int offset = 0;
		remaining = total;
		for (byte[] b : bufs) {
			int count = Math.min(b.length, remaining);
			System.arraycopy(b, 0, result, offset, count);
			offset += count;
			remaining -= count;
		}

		return result;
	}

	public static byte[] readAllBytes(FileInputStream fin) throws IOException {
		return readNBytes(fin, Integer.MAX_VALUE);
	}
}
