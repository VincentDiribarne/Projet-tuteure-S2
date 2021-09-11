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
import Version_Etudiant.DeplacementFenetre;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class Controller_Importer_Ressource implements Initializable {

	@FXML
	private MediaView mediaView;
	public MediaPlayer mediaPlayer;
	public Media media;
	@FXML
	private ImageView imageAudio;
	@FXML
	private Button playPause;
	@FXML
	private Slider progressBar;
	@FXML
	private Button okImport;

	@FXML
	private Slider sliderSon;
	@FXML private ImageView son;
	Image sonCoupe = new Image("/Image/VolumeCoupe.png");
	Image sonPasCoupe = new Image("/Image/Volume.png");
	Image play = new Image("/Image/Play.png");
	Image pause = new Image("/Image/Pause.png");
	
	@FXML private ImageView playPauseVideo;

	public static Media contenuMedia;
	public static Image contenuImage;

	@FXML private CheckMenuItem dark;
	public static String cheminVideo = "";
	public static String cheminImg = "";

	// Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// On rempli les champs s'il ne sont pas null (si l'enseignant revient en arrière)
		if (contenuMedia != null) {
			media = contenuMedia;
			mediaPlayer = new MediaPlayer(contenuMedia);
			mediaView.setMediaPlayer(mediaPlayer);

			// On réduit le ImageView
			imageAudio.setFitWidth(0);
			imageAudio.setFitHeight(0);

			// On agrandit le MediaView
			mediaView.setFitWidth(500);
			mediaView.setFitHeight(300);

			//On met le bouton disponible
			okImport.setDisable(false);
		}

		if (contenuImage != null) {
			imageAudio.setImage(contenuImage);

			// On réduit le mediaView
			mediaView.setFitWidth(0);
			mediaView.setFitHeight(0);

			// On agrandit le ImageView
			imageAudio.setFitWidth(500);
			imageAudio.setFitHeight(300);

		}

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

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////       METHDOES SPECIFIQUES A LA PAGE       ////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

	// Depuis la page d'importation, on ouvre l'explorateur de fichiers pour que
	// l'enseignant choisisse la ressource
	@FXML
	public void importerRessource(ActionEvent event) throws IOException {
		
		//Réinitialisation des variables
		imageAudio.setImage(null);

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
		cheminVideo = selectedFile.getAbsolutePath();
		media = new Media(path);
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
			imageAudio.setFitWidth(0);
			imageAudio.setFitHeight(0);

			// On agrandit le MediaView
			mediaView.setFitWidth(500);
			mediaView.setFitHeight(300);

		}

		// S'il s'agit d'un fichier audio
		if (extension.compareTo(".mp3") == 0) {

			// On réduit le mediaView
			mediaView.setFitWidth(0);
			mediaView.setFitHeight(0);

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
			cheminImg = selectedFile.getAbsolutePath();
			// On set l'imageView avec l'image
			imageAudio.setImage(image);

			contenuImage = image;
		}

		// On set le media dans le mediaView
		mediaView.setMediaPlayer(mediaPlayer);
		okImport.setDisable(false);

		sliderSonChange();
		sliderVideoChange();
		setKeyboardShortcut();
	}

	public void sliderSonChange() {
		// Change le volume sonore selon la valeur du slider
		sliderSon.valueProperty().addListener((o -> {
			mediaPlayer.setVolume(sliderSon.getValue() / 100.0); 

			if(sliderSon.getValue() == 0) {
				son.setImage(sonCoupe);
			} else {
				son.setImage(sonPasCoupe);
			}
		}));
	}

	//Fonction qui permet de mute le son
	@FXML
	public void sonCoupe(MouseEvent event) {

		if(mediaPlayer.getVolume() != 0) {
			son.setImage(sonCoupe);
			mediaPlayer.setVolume(0);
		} else {
			son.setImage(sonPasCoupe);
			mediaPlayer.setVolume(sliderSon.getValue() / 100);
		}

	}

	//Fonction qui fait avancer le slider en fonction de la video
	public void sliderVideoChange() {

		Duration total = media.getDuration();
		progressBar.setMax(total.toSeconds());

		mediaPlayer.setOnReady(new Runnable() {
			@Override
			public void run() {
				Duration total = media.getDuration();
				progressBar.setMax(total.toSeconds());
			}
		});

		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
				progressBar.setValue(newValue.toSeconds());
			}
		});

		progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
			}
		});

		progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
			}
		});
		
	}

	// Fonction qui permet à l'enseignant de visualiser sa video ou son son
	@FXML
	public void playOrPause() {

		sliderSonChange();
		sliderVideoChange();
		setKeyboardShortcut();
		
		if (mediaPlayer.getStatus() == Status.PAUSED || mediaPlayer.getStatus() == Status.READY) {
			mediaPlayer.play();
			playPauseVideo.setImage(pause);
			playPause.setText("Pause");
		} else {
			mediaPlayer.pause();
			playPauseVideo.setImage(play);
			playPause.setText("Play");
		}
	}

	// Méthode pour charger la page nouvelExo (bouton retour)
	@FXML
	public void pageNouvelExo(ActionEvent event) throws IOException {
		if(media != null) {
			mediaPlayer.stop();
		}

		Stage primaryStage = (Stage) playPause.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/NouvelExo.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	// Méthode pour charger la page d'aperçu de l'exercice
	@FXML
	public void pageApercu(ActionEvent event) throws IOException {
		//on récupère le media
		contenuMedia = mediaPlayer.getMedia();
		mediaPlayer.stop();

		Stage primaryStage = (Stage) playPause.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/PageApercu.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	//Méthode pour passer ou non le darkMode
	@FXML
	public void darkMode() {

		if(dark.isSelected()) {
			okImport.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			okImport.getScene().getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			Controller_Page_Accueil.isDark = true;
		} else {
			okImport.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Enseignant/FXML_Files/darkModeTest.css").toExternalForm());
			okImport.getScene().getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
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

	private void setKeyboardShortcut() {
		imageAudio.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.SPACE) {
					if (mediaView.getMediaPlayer().getStatus() == Status.PAUSED) {
						mediaView.getMediaPlayer().play();
					}
					if (mediaView.getMediaPlayer().getStatus() == Status.PLAYING) {
						mediaView.getMediaPlayer().pause();
					}
				}
				if (event.getCode() == KeyCode.RIGHT && mediaView.getMediaPlayer().getTotalDuration().greaterThan(mediaView.getMediaPlayer().getCurrentTime().add(new Duration(5000)))) {
					mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getCurrentTime().add(new Duration(5000)));
				}
				if (event.getCode() == KeyCode.LEFT && new Duration(0).lessThan(mediaView.getMediaPlayer().getCurrentTime().subtract(new Duration(5000)))) {
					mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getCurrentTime().subtract(new Duration(5000)));
				}
				if (event.getCode() == KeyCode.UP && mediaView.getMediaPlayer().getVolume() <= 1-0.1) {
					sliderSon.setValue(sliderSon.getValue() + 3);
				}
				if (event.getCode() == KeyCode.DOWN && mediaView.getMediaPlayer().getVolume() >= 0 + 0.1) {
					sliderSon.setValue(sliderSon.getValue() - 3);
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
	
	//Bouton Nouveau qui permet de créer un nouvel exercice
	@FXML
	public void pageNouvelExoNouv() throws IOException {
		
		//Réinitialisation des variables
		Controller_Page_Accueil c = new Controller_Page_Accueil();
		c.delete();
		
		Stage primaryStage = (Stage) playPause.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/NouvelExo.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();

	}
}
