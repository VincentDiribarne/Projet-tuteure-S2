package Version_Enseignant;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller implements Initializable{

	//Id des variables présentes dans les fichiers FXML
	//Page NouvelExo où l'on précise le nom de l'exercice et le lieu d'enregistrement
	@FXML private TextField repertoire;
	@FXML private TextField NomExo;
	@FXML private Button OkNouvelExo;
	//Page d'importation de la ressource 
	@FXML private MediaView mediaView;
	public MediaPlayer mediaPlayer;
	@FXML private ImageView imageAudio;
	@FXML private Button playPause;
	//Page Apercu
	@FXML private TextField texteConsigne;
	@FXML private TextField texteTranscription;
	@FXML private TextField texteAide;
	@FXML private MediaView MediaViewApercu;
	@FXML private Button okApercu;
	//Page des Options
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
	//Page de parametres
	@FXML ListView<String> listePolice = new ListView<>();


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			INITIALISATION		////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> polices = FXCollections.observableArrayList("Arial", "Avant Garde", "Avenir","BebasBell Gothic", "Benguiat Gothic", "Bitstream Vera Sans", "Calibri");
		listePolice.setItems(polices);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			METHDOES GENERALES		////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Méthode pour passer d'une scène à l'autre
	private void changerScene (Parent root) {
		Stage stage = (Stage) MainEnseignant.root.getScene().getWindow();
		MainEnseignant.root = root;
		stage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		stage.show();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////		METHDOES DISPONIBLES SUR TOUTES LES PAGES		////////////////////////////////
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
		fileChooser.setTitle("Open Resource File");
		fileChooser.showOpenDialog(null);
		//TODO Chargez l'exercice dans la page
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////	  PAGE NOUVEL EXO (NOM + LIEU D'ENREGISTREMENT)		////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Bouton Nouveau qui permet à l'enseignant de démarrer la création d'un nouvel exercice
	@FXML
	public void PageNouvelExo(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("NouvelExo.fxml"));
		changerScene(root);
	}

	
	//Bouton annuler pour revenir à la page d'accueil (depuis la page d'enregistrement final et depuis la page nouvel exo)
	@FXML
	public void annuler(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PageAccueil.fxml"));
		changerScene(root);
	}


	//Méthode pour choisir le répertoire dans lequel l'enseignant enregistrera son fichier
	@FXML
	public void choisirRepertoire(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory;
		directoryChooser.setTitle("Choisissez un répertoire pour l'enregistrement");
		selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory != null) {
			repertoire.setText(selectedDirectory.getAbsolutePath());
		}
		verifRempli();
	}
	
	
	//Méthode qui vérifie si les textFields sont rempli afin d'activer le bouton Ok
	@FXML
	public void verifRempli() {
		
		//Pour la page de nouvelExo
		if(!NomExo.getText().trim().isEmpty() && !repertoire.getText().trim().isEmpty()) {
			OkNouvelExo.setDisable(false);
		}
		
		if(NomExo.getText().trim().isEmpty()) {
			OkNouvelExo.setDisable(true);
		}	
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////		PAGE D'IMPORTATION DE LA RESSOURCE		////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Méthode pour se diriger à la page où l'enseignant importe la ressource
	@FXML
	public void pageImporterRessource(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ImporterRessource.fxml"));
		changerScene(root);
	}

	
	//Depuis la page d'importation, on ouvre l'explorateur de fichiers pour que l'enseignant choisisse la ressource
	@FXML
	public void importerRessource(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		FileChooser fileChooserImage = new FileChooser();
		//La variable path va contenir l'URL du fichier
		String path = "", extension = "";
		
		//On restreint le choix des extensions
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Documents Média (MP3, MP4)", "*.mp3","*.mp4"));

		//On crée un fichier qui va contenir l'URL du fichier sélectionné
		File selectedFile = new File("");
		selectedFile = fileChooser.showOpenDialog(null);
		path = selectedFile.toURI().toURL().toExternalForm();
		Media media = new Media(path);
		mediaPlayer = new MediaPlayer(media);
		
		//Boucle pour récupérer l'exension du fichier
		for(int i = 0; i < path.length(); i++) {
			if(i > path.length() - 5) {
				extension = extension + path.charAt(i);
			}
		}
		
		//S'il s'agit d'un fichier video
		if(extension.compareTo(".mp4") == 0) {
			
			//On réduit le ImageView
			imageAudio.setFitWidth(5);
			imageAudio.setFitHeight(5);
			
			//On agrandit le MediaView
			mediaView.setFitWidth(500);
			mediaView.setFitHeight(300);
			
			mediaView.setMediaPlayer(mediaPlayer);
		}
		
		//S'il s'agit d'un fichier audio
		if(extension.compareTo(".mp3") == 0) {
			
			//On réduit le mediaView
			mediaView.setFitWidth(5);
			mediaView.setFitHeight(5);
			
			//On agrandit le ImageView
			imageAudio.setFitWidth(500);
			imageAudio.setFitHeight(300);
			
			//On restreint le choix des extensions des images
			fileChooserImage.getExtensionFilters().add(new ExtensionFilter("Images", "*.jpg","*.png","*.jpeg","*.gif"));
			fileChooserImage.setTitle("Choix d'une image pour la preview de l'audio");
			selectedFile = new File("");
			selectedFile = fileChooserImage.showOpenDialog(null);
			
			//On crée une image à partir de l'URL du fichier sélectionné
			Image image = new Image(selectedFile.toURI().toURL().toExternalForm());
			//On set l'imageView avec l'image
			imageAudio.setImage(image);
			//On set le media dans le mediaView
			mediaView.setMediaPlayer(mediaPlayer);
			mediaPlayer.play();
		}
	}
	
	//Fonction qui permet à l'enseignant de visualiser sa video ou son son
	@FXML
	public void playOrPause() {
		if(mediaPlayer.getStatus() == Status.PAUSED || mediaPlayer.getStatus() == Status.READY) {
            mediaPlayer.play();
            playPause.setText("Pause");
        }
          else{
            mediaPlayer.pause();
            playPause.setText("Play");
        }
    } 
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			EDITION DE L'EXERCICE		////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Méthode pour se diriger vers la page Apercu depuis la page d'importation
	@FXML
	public void pageApercu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PageApercu.fxml"));
		changerScene(root);
	}
	
	//Pour éditer la consigne
	//Lorsqu'on appuie sur Valider, un effet s'applique au TextField
	@FXML
	public void validationConsigne(ActionEvent event) {
		texteConsigne.setEditable(false);
		texteConsigne.setOpacity(0.5);
	}

	//Lorsque le professeur veut rééditer la consigne, l'effet disparaît sur le TextField
	@FXML
	public void editionConsigne(MouseEvent event) {
		texteConsigne.setEditable(true);
		texteConsigne.setOpacity(1);
	}
	
	//Pour éditer la transcription
	//Lorsqu'on appuie sur Valider, un effet s'applique au TextField
	@FXML
	public void validationTranscription(ActionEvent event) {
		texteTranscription.setEditable(false);
		texteTranscription.setOpacity(0.5);
	}

	//Lorsque le professeur veut rééditer la transcription, l'effet disparaît sur le TextField
	@FXML
	public void editionTranscription(MouseEvent event) {
		texteTranscription.setEditable(true);
		texteTranscription.setOpacity(1);
	}
	
	//Pour éditer les aides
	//Lorsqu'on appuie sur Valider, un effet s'applique au TextField
	@FXML
	public void validationAide(ActionEvent event) {
		texteAide.setEditable(false);
		texteAide.setOpacity(0.5);
	}

	//Lorsque le professeur veut rééditer les aides, l'effet disparaît sur le TextField
	@FXML
	public void editionAide(MouseEvent event) {
		texteAide.setEditable(true);
		texteAide.setOpacity(1);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			GESTION DES OPTIONS 		////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Chargement de la page des Options
	@FXML
	public void pageOptions(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PageOptions.fxml"));
		changerScene(root);
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
		modeEntrainement3.setVisible(true);
		modeEntrainement4.setVisible(true);
		
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
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			PAGE D'ENREGISTREMENT 		////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@FXML
	public void pageEnregistrementFinal(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("EnregistrementFinal.fxml"));
		changerScene(root);
		
        //Enregistrement
        /*FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
        .addAll(
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        fileChooser.setTitle(NomExo.getText());
        fileChooser.showSaveDialog(null);*/
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////		GESTION DES PARAMETRES 		////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@FXML
	public void pageParametres(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PageDesParametres.fxml"));
		changerScene(root);
	}

	
	/*@FXML
	public void darkMode(ActionEvent event) {
		
	}*/

}
