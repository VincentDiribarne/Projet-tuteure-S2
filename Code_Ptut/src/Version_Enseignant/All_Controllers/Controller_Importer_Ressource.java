package Version_Enseignant.All_Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class Controller_Importer_Ressource implements Initializable {

	@FXML
	private MediaView mediaView;
	public MediaPlayer mediaPlayer;
	@FXML
	private ImageView imageAudio;
	@FXML
	private Button playPause;
	@FXML
	private Slider progressBar;
	@FXML
	private Button okImport;

	public static Media contenuMedia;
	public static Image contenuImage;

	// Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// On rempli les champs s'il ne sont pas null (si l'enseignant revient en arrière)
		if (contenuMedia != null) {
			mediaPlayer = new MediaPlayer(contenuMedia);
			mediaView.setMediaPlayer(mediaPlayer);

			// On réduit le ImageView
			imageAudio.setFitWidth(5);
			imageAudio.setFitHeight(5);

			// On agrandit le MediaView
			mediaView.setFitWidth(500);
			mediaView.setFitHeight(300);
			
			//On met le bouton disponible
			okImport.setDisable(false);
		}

		if (contenuImage != null) {
			imageAudio.setImage(contenuImage);

			// On réduit le mediaView
			mediaView.setFitWidth(5);
			mediaView.setFitHeight(5);

			// On agrandit le ImageView
			imageAudio.setFitWidth(500);
			imageAudio.setFitHeight(300);

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
		Stage primaryStage = (Stage) playPause.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	// Bouton DarkMode qui met en darkMode l'application
	@FXML
	public void darkMode() {
		// TODO faire le DarkMode
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////       METHDOES SPECIFIQUES A LA PAGE       ////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

	// Depuis la page d'importation, on ouvre l'explorateur de fichiers pour que
	// l'enseignant choisisse la ressource
	@FXML
	public void importerRessource(ActionEvent event) throws IOException {
		
		FileChooser fileChooser = new FileChooser();
		FileChooser fileChooserImage = new FileChooser();
		// La variable path va contenir l'URL du fichier
		String path = "", extension = "";

		// On restreint le choix des extensions
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Documents Média (MP3, MP4)", "*.mp3", "*.mp4"));

		// On crée un fichier qui va contenir l'URL du fichier sélectionné
		File selectedFile = new File("");
		selectedFile = fileChooser.showOpenDialog(null);
		path = selectedFile.toURI().toURL().toExternalForm();
		Media media = new Media(path);
		mediaPlayer = new MediaPlayer(media);

		//On mémorise le contenu du media
		contenuMedia = media;

		// Boucle pour récupérer l'exension du fichier
		for (int i = 0; i < path.length(); i++) {
			if (i > path.length() - 5) {
				extension = extension + path.charAt(i);
			}
		}

		// S'il s'agit d'un fichier video
		if (extension.compareTo(".mp4") == 0) {

			// On réduit le ImageView
			imageAudio.setFitWidth(5);
			imageAudio.setFitHeight(5);

			// On agrandit le MediaView
			mediaView.setFitWidth(500);
			mediaView.setFitHeight(300);

		}

		// S'il s'agit d'un fichier audio
		if (extension.compareTo(".mp3") == 0) {

			// On réduit le mediaView
			mediaView.setFitWidth(5);
			mediaView.setFitHeight(5);

			// On agrandit le ImageView
			imageAudio.setFitWidth(500);
			imageAudio.setFitHeight(300);

			// On restreint le choix des extensions des images
			fileChooserImage.getExtensionFilters()
			.add(new ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg", "*.gif"));
			fileChooserImage.setTitle("Choix d'une image pour la preview de l'audio");
			selectedFile = new File("");
			selectedFile = fileChooserImage.showOpenDialog(null);

			// On crée une image à partir de l'URL du fichier sélectionné
			Image image = new Image(selectedFile.toURI().toURL().toExternalForm());
			// On set l'imageView avec l'image
			imageAudio.setImage(image);

			contenuImage = image;
		}

		// On set le media dans le mediaView
		mediaView.setMediaPlayer(mediaPlayer);
		okImport.setDisable(false);

		// Gestion de la progressBar
		mediaPlayer.setOnReady(new Runnable() {
			@Override
			public void run() {
				progressBar.setMax(mediaPlayer.getTotalDuration().toSeconds());
			}
		});

		InvalidationListener sliderChangeListener = o -> {
			Duration seekTo = Duration.seconds(progressBar.getValue());
			mediaPlayer.seek(seekTo);
		};

		progressBar.valueProperty().addListener(sliderChangeListener);

		mediaPlayer.currentTimeProperty().addListener(l -> {

			progressBar.valueProperty().removeListener(sliderChangeListener);

			Duration currentTime = mediaPlayer.getCurrentTime();
			progressBar.setValue(currentTime.toSeconds());

			progressBar.valueProperty().addListener(sliderChangeListener);
		});

	}

	// Fonction qui permet à l'enseignant de visualiser sa video ou son son
	@FXML
	public void playOrPause() {
		if (mediaPlayer.getStatus() == Status.PAUSED || mediaPlayer.getStatus() == Status.READY) {
			mediaPlayer.play();
			playPause.setText("Pause");
		} else {
			mediaPlayer.pause();
			playPause.setText("Play");
		}
	}

	// Méthode pour charger la page nouvelExo (bouton retour)
	@FXML
	public void pageNouvelExo(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) playPause.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/NouvelExo.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	// Méthode pour charger la page d'aperçu de l'exercice
	@FXML
	public void pageApercu(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) playPause.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageApercu.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}
}
