package Version_Etudiant.All_Controllers;

import java.awt.Desktop;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Version_Etudiant.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Duration;

public class Controller_Page_Exercice implements Initializable{

	//Variables qui vont contenir les différentes informations sur l'exercice
	//Informations textuelles
	public static String contenuTranscription;
	public static String contenuConsigne;
	public static Media contenuMedia;
	public static String caractereOccul;

	//Options de l'exercice
	public static boolean sensiCasse;
	public static boolean entrainement;
	public static boolean evaluation;
	public static String nbMin;
	public static boolean solution;
	public static boolean motDecouverts;
	public static boolean motIncomplet;
	public static boolean lettres_2;
	public static boolean lettres_3;
	public static Image contenuImage;

	//TextFields et autre composants qui contiennent les informations de l'exercice
	@FXML private TextArea transcription;
	@FXML private TextArea consigne;
	@FXML private ImageView imageView;
	@FXML private MediaView mediaView;
	@FXML private Text time;
	@FXML private Label titleTime;
	@FXML private TextField motPropose;

	//Ce qui concerne le media
	@FXML private ImageView firstPlay;
	@FXML private ImageView playOrPause;
	MediaPlayer mediaPlayer = new MediaPlayer(contenuMedia);
	Image play = new Image("/Image/Play.png");
	Image pause = new Image("/Image/Pause.png");
	Image sonCoupe = new Image("/Image/VolumeCoupe.png");
	Image sonPasCoupe = new Image("/Image/Volume.png");
	@FXML private Slider sliderSon;
	@FXML private Slider sliderVideo;
	@FXML private ImageView son;

	//Gestion du timer
	private Timeline timer;
	private Integer sec = 0;
	private Integer min;
	private boolean timerEstDeclenche = false;

	//Autres boutons
	@FXML private Button ButtonAide;
	@FXML private Button ButtonSolution;
	@FXML private ImageView alertSolution;

	//Listes des mots pour l'étudiant
	private ArrayList<String> lesMots = new ArrayList<>();
	private ArrayList<String> lesMotsSensiCasse = new ArrayList<>();
	private ArrayList<String> lesMotsEtudiant = new ArrayList<>();
	private ArrayList<Integer> estDecouvert = new ArrayList<>();
	private String encryptedText;
	
	public String getEncryptedText() {
		return encryptedText;
	}

	public String getClearText() {
		return clearText;
	}


	private String clearText = contenuTranscription;
	public int numberPartialReplacement;

	//Tout ce qui concerne la barre de progression
	@FXML private ProgressBar progressBar;
	@FXML private Label pourcentageMots;
	@FXML private Label labelMotsDecouverts;
	private float nbMotsDecouverts = 0;
	private float nbMotsTotal;

	//Tooltip
	@FXML private ImageView questionConsigne;
	@FXML private ImageView questionTranscription;
	@FXML private ImageView questionProposition;

	@FXML private CheckMenuItem dark;
	@FXML private Button validateButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		int i;
		String mot = "", motCrypte = "";

		encryptedText = encryptText();

		//On fait en sorte à ce que le texte ne dépasse pas du cadre
		transcription.setWrapText(true);

		/*//On affiche le texte crypté dans le TextField
		if(contenuTranscription != null) {

			//Pour la transcription, on utilise le caractere d'occultation
			for(i = 0; i < contenuTranscription.length(); i++) {

				//Si cela correspond bien à la regex, on occulte le mot
				if(okRegex(contenuTranscription.charAt(i)) == true) {
					transcription.setText(transcription.getText() + caractereOccul);
					mot += contenuTranscription.charAt(i);
					motCrypte += caractereOccul;
				} else {
					transcription.setText(transcription.getText() + contenuTranscription.charAt(i));

					if(mot != "") {
						lesMots.add(mot);
						lesMotsEtudiant.add(motCrypte);
					} else {
						lesMots.add(contenuTranscription.charAt(i - 1) + "");
						lesMotsEtudiant.add(contenuTranscription.charAt(i - 1) + "");
					}

					mot = "";
					motCrypte = "";
				}
			}

			//Si on arrive à la fin de la boucle, on ajoute quand meme le dernier mot
			lesMots.add(mot);
			lesMotsEtudiant.add(motCrypte);
		}*/

		//On initialise la liste estDecouvert
		for(String w : lesMots) {
			if(regexPoint(w)) {
				estDecouvert.add(1);
			} else {
				estDecouvert.add(0);
			}
		}

		//On passe les mots comparatifs en minuscule dans une autre liste
		for(String word : lesMots) {
			lesMotsSensiCasse.add(word.toLowerCase());

			if(!regexPoint(word)) {
				nbMotsTotal++;
			}
		}

		//On load la consigne
		if(contenuConsigne != null) {
			consigne.setText(contenuConsigne);
		}

		//On load le media
		if(contenuMedia != null) {
			mediaView.setMediaPlayer(mediaPlayer);
		}

		//On load l'image quand il s'agit d'un mp3
		if(contenuImage != null) {
			imageView.setImage(contenuImage);
		}

		//On load le temps nécessaire si c'est en mode Evaluation
		if(evaluation == true) {
			min = Integer.parseInt(nbMin);
			time.setText(min + ":" + sec);

			//On masque les boutons qui ne sont présent que ne mode entrainement
			ButtonAide.setVisible(false);
			ButtonSolution.setVisible(false);
			alertSolution.setVisible(false);
			//Si l'enseignant n'a pas souhaité l'affichage de mots découverts en temps réel
			progressBar.setVisible(false);
			pourcentageMots.setVisible(false);
			labelMotsDecouverts.setVisible(false);

		} 
		//Sinon cela veut dire que l'on est en mode Entrainement
		else {

			titleTime.setText("Temps Ecoulé");
			min = 00;
			time.setText("00:00");

			//Si l'enseignant n'a pas souhaité autoriser l'affichage de la solution
			if(solution == false) {
				ButtonSolution.setVisible(false);
				alertSolution.setVisible(false);
			}

			//Si l'enseignant n'a pas souhaité l'affichage de mots découverts en temps réel
			if(motDecouverts == false) {
				progressBar.setVisible(false);
				pourcentageMots.setVisible(false);
				labelMotsDecouverts.setVisible(false);
			}

			if(lettres_2 == true) {
				numberPartialReplacement = 2;
			} else if(lettres_3 == true){
				numberPartialReplacement = 3;
			} else {
				numberPartialReplacement = 0;
			}

		}

		//On fait apparaître une fenêtre pour que l'étudiant rentre son nom et prénom en vue du futur enregistrement
		//Note : Seulement si l'exercice est en mode Entrainement
		try {
			if(evaluation == true) {
				popUpEnregistrement();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		sliderSonChange();
		sliderVideoChange();

		validateButton.setOnAction(ActionEvent -> {
			try {
				verify(motPropose.getText());
				motPropose.setText("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		transcription.setText(encryptedText);

	}

	private String encryptText() {
		String constructString = "";
		for (String string : clearText.split("")) {
			if (string.matches("[a-zA-Z]") || (string.matches("[0-9]"))) {
				constructString += caractereOccul;
			}else {
				constructString += string;
			}
		}
		return constructString;
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

	//Fonction qui fait avancer le slider en fonction de la video
	public void sliderVideoChange() {

		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {
				sliderVideo.setMax(mediaPlayer.getTotalDuration().toSeconds());
			}
		});

		// Ecoute sur le slider. Quand il est modifié, modifie le temps du media player.
		InvalidationListener sliderChangeListener = o -> {
			Duration seekTo = Duration.seconds(sliderVideo.getValue());
			mediaPlayer.seek(seekTo);
		};
		sliderVideo.valueProperty().addListener(sliderChangeListener);

		// Lie le temps du media player au slider
		mediaPlayer.currentTimeProperty().addListener(l -> {

			sliderVideo.valueProperty().removeListener(sliderChangeListener);

			// Met a jour la valeur de temps du média avec la position du slider.
			Duration currentTime = mediaPlayer.getCurrentTime();
			sliderVideo.setValue(currentTime.toSeconds());

			// Réactivation de l'écoute du slider
			sliderVideo.valueProperty().addListener(sliderChangeListener);

		});
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

	//Fonction qui lance le media pour la premiere fois 
	@FXML
	public void firstPlay(MouseEvent event) {

		mediaPlayer.play();
		setKeyboardShortcut();
		

		if(timerEstDeclenche == false) {
			gestionTimer();
			timerEstDeclenche = true;
		}

		firstPlay.setVisible(false);
	}

	//Fonction qui play / pause le media
	@FXML
	public void playOrPause(MouseEvent event) {

		if(mediaPlayer.getStatus() == Status.PLAYING) {
			mediaPlayer.pause();
			playOrPause.setImage(play);
		}

		if(mediaPlayer.getStatus() == Status.PAUSED) {
			mediaPlayer.play();
			playOrPause.setImage(pause);
		}

	}

	//Fonction qui regarde si le caractère est compatible avec la regex (toutes les lettres et chiffres)
	private boolean okRegex(char caractere) {
		String s = "" + caractere;
		if(s.matches("[a-zA-Z0-9]")) {
			return true;
		} 
		return false;
	}

	//Fonction qui regarde si le mot contient un caractère de ponctuation
	private boolean regexPoint(String mot) {

		for(int i = 0; i < mot.length(); i++) {
			if((mot.charAt(i) + "").matches("[.,;!?:]")) {
				return true;
			}
		}
		return false;
	}

	//Méthode qui permet de se rendre au manuel utilisateur == tuto
	@FXML
	public void tuto() throws MalformedURLException, IOException, URISyntaxException {

		InputStream is = MainEtudiant.class.getResourceAsStream("Manuel_Utilisateur.pdf");

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

	//Méthode qui fait apparaître la popUp pour que l'étudiant rentre ses infos pour l'enregistrement
	public void popUpEnregistrement() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Etudiant/FXML_Files/EnregistrementApresOuverture.fxml"));
		Stage stage = new Stage();
		Rectangle rect = new Rectangle(900,500);
		rect.setArcHeight(20.0);
		rect.setArcWidth(20.0);
		root.setClip(rect);

		//On bloque sur cette fenêtre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(root, 900, 500);
		scene.setFill(Color.TRANSPARENT);
		darkModeActivation(scene);

		//On bloque le resize
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		DeplacementFenetre.deplacementFenetre((Pane) root, stage);
	}

	//Méthode qui regarde si le darkMode est actif et l'applique en conséquence à la scene
	public void darkModeActivation(Scene scene) {
		if(Controller_Menu.isDark) {
			scene.getStylesheets().removeAll(getClass().getResource("/Version_Etudiant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("/Version_Etudiant/FXML_Files/darkModeTest.css").toExternalForm());
			dark.setSelected(true);
		} else {
			scene.getStylesheets().removeAll(getClass().getResource("/Version_Etudiant/FXML_Files/darkModeTest.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("/Version_Etudiant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			dark.setSelected(false);
		}
	}

	//Méthode pur afficher l'aide proposée par l'enseignant
	@FXML
	public void affichageAide() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Etudiant/FXML_Files/Aides.fxml"));
		Stage stage = new Stage();
		Rectangle rect = new Rectangle(400,600);
		rect.setArcHeight(20.0);
		rect.setArcWidth(20.0);
		root.setClip(rect);

		//On bloque sur cette fenêtre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(root, 400, 600);
		scene.setFill(Color.TRANSPARENT);
		darkModeActivation(scene);

		stage.setScene(scene);
		stage.show();
		DeplacementFenetre.deplacementFenetre((Pane) root, stage);
	}

	//Méthode pour afficher la solution
	@FXML
	public void affichageSolution() throws IOException {

		retourMenu();

		Parent root = FXMLLoader.load(getClass().getResource("/Version_Etudiant/FXML_Files/Solution.fxml"));
		Stage stage = new Stage();
		Rectangle rect = new Rectangle(600,400);
		rect.setArcHeight(20.0);
		rect.setArcWidth(20.0);
		root.setClip(rect);

		//On bloque sur cette fenêtre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(root, 600, 400);
		scene.setFill(Color.TRANSPARENT);
		darkModeActivation(scene);

		stage.setScene(scene);
		stage.show();
		DeplacementFenetre.deplacementFenetre((Pane) root, stage);

	}

	private void verify(String text) throws IOException {
		if (text == null) {
			return;
		}
		String[] encrypted = encryptedText.split("[ \\t\\n\\x0B\\f\\r]");
		String[] clear = clearText.split("[ \\t\\n\\x0B\\f\\r]");
		Pattern punctionLessPattern = Pattern.compile("[^\\p{Punct}&&[^'-]]*");
		Matcher clearMatcher;
		for (int i = 0; i < clear.length; i++) {
			clearMatcher = punctionLessPattern.matcher(clear[i]);
			if (clearMatcher.find() && clearMatcher.group(0).toLowerCase().equals(text.toLowerCase())) {
				if (sensiCasse && !clearMatcher.group(0).equals(text)) {
					continue;
				}
				encrypted[i]=clear[i];
			}
			Pattern numberCharPattern = Pattern.compile(".{4,}");
			Matcher numberCharMatcher = numberCharPattern.matcher(clear[i]);
			if (numberCharMatcher.find() && numberPartialReplacement > 0 && text.length() >= numberPartialReplacement 
					&& encrypted[i].substring(0,text.length()).contains(""+caractereOccul) 
					&& numberCharMatcher.group().substring(0,text.length()).equals(text)) {

				encrypted[i] = numberCharMatcher.group(0).substring(0,text.length());
				for (int j = text.length(); j < clearMatcher.group(0).length(); j++) {
					encrypted[i] += caractereOccul;
				}
				encrypted[i] += clear[i].substring(clearMatcher.group(0).length());
			}
		}
		encryptedText = "";
		int length =0;
		for (int i = 0; i < encrypted.length; i++) {
			encryptedText += encrypted[i];
			if (length + clear[i].length() < clearText.length()) {
				length += clear[i].length();
			}
			if (Character.isWhitespace(clearText.charAt(length)) || Character.isSpaceChar(clearText.charAt(length))) {
				encryptedText += clearText.charAt(length);
			}
			length++;
		}

		int ok = 0;

		if(motDecouverts) {
			int numberWord = clear.length;
			int numberFoundWord = 0;
			for (String string : encrypted) {
				if (!string.contains(caractereOccul)) {
					numberFoundWord++;
				}
			}
			progressBar.setProgress( (double) numberFoundWord / (double) numberWord);
			pourcentageMots.setText(Math.round(( (double) numberFoundWord / (double) numberWord) * 100)  + "%");

			if (Math.round(( (double) numberFoundWord / (double) numberWord) * 100) == 100){
				ok = 1;
			}
		}

		if(ok == 1) {

			//Si c'est le cas, on enregistre son exercice, puis on load une popUp
			retourMenu();

			if(evaluation == true) {
				finExercice();
				enregistrementExo();
			}
		}

		transcription.setText(encryptedText);
	}


	//Méthode pour quitter l'application
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	//Méthode qui permet à l'étudiant de proposer un mot, et affichage ou non dans le texte occulté si le mot est présent
	/*@FXML
	public void propositionMot() throws IOException {

		String mot = motPropose.getText(), word = "";
		int remplacementPartiel = 0, cpt = 0;
		boolean check = true;

		//On set la variable en fonction du nombre de lettres autorisées
		if(lettres_2 == true) {
			remplacementPartiel = 2;
		} else if (lettres_3 == true) {
			remplacementPartiel = 3;
		}

		//Si la sensibilité à la casse n'est pas activée, on met le mot en minuscule
		if(sensiCasse == false) {
			mot = mot.toLowerCase();

			for(int i = 0; i < lesMotsSensiCasse.size(); i++) {

				check = true;

				//Si le mot correspond exactement 
				if(lesMotsSensiCasse.get(i).compareTo(mot) == 0) {
					lesMotsEtudiant.set(i, lesMots.get(i));
					estDecouvert.set(i, 1);
					nbMotsDecouverts++;

					//Gestion de la progressBar
					progressBar.setProgress(nbMotsDecouverts / nbMotsTotal);
					pourcentageMots.setText(Math.round((nbMotsDecouverts / nbMotsTotal) * 100)  + "%");
				}

				//Si le remplacement partiel est active et que l'étudiant n'a pas encore trouve le mot
				if(motIncomplet == true && estDecouvert.get(i) == 0) {

					if(mot.length() >= remplacementPartiel && lesMotsSensiCasse.get(i).length() >= 4) {

						for(int j = 0; j < mot.length(); j++) {
							if(mot.charAt(j) == lesMotsSensiCasse.get(i).charAt(j)) {
								cpt ++;
							} else {
								check = false;
								break;
							}
						}
					}

					//Si les premières lettres sont les mêmes
					if(cpt >= remplacementPartiel && check == true) {

						//On "crypte" le mot
						word = mot;

						for(int z = 0; z < lesMotsSensiCasse.get(i).length() - mot.length(); z++) {
							word += caractereOccul;
						}
						lesMotsEtudiant.set(i, word);
					}

					//On réinitialise le compteur
					cpt = 0;

				}
			}


			//Si la sensibilité à la casse est activée
		} else {

			for(int i = 0; i < lesMots.size(); i++) {

				check = true;

				if(lesMots.get(i).compareTo(mot) == 0) {
					lesMotsEtudiant.set(i, mot);
					estDecouvert.set(i, 1);
					nbMotsDecouverts++;

					//Gestion de la progressBar
					progressBar.setProgress(nbMotsDecouverts / nbMotsTotal);
					pourcentageMots.setText(Math.round((nbMotsDecouverts / nbMotsTotal) * 100)  + "%");
				}

				//Si le remplacement partiel est active
				if(motIncomplet == true && estDecouvert.get(i) == 0) {

					if(mot.length() >= remplacementPartiel && lesMots.get(i).length() >= 4) {

						for(int j = 0; j < mot.length(); j++) {
							if(mot.charAt(j) == lesMots.get(i).charAt(j)) {
								cpt ++;
							} else {
								check = false;
								break;
							}
						}
					}

					//Si les premières lettres sont les mêmes
					if(cpt >= remplacementPartiel && check == true) {

						//On "crypte" le mot
						word = mot;

						for(int z = 0; z < lesMots.get(i).length() - mot.length(); z++) {
							word += caractereOccul;
						}
						lesMotsEtudiant.set(i, word);
					}

					//On réinitialise le compteur
					cpt = 0;
				}
			}
		}

		//Si c'est la première fois que l'étudiant propose un mot, le timer se déclenche
		if(timerEstDeclenche == false) {
			gestionTimer();
			timerEstDeclenche = true;
		}

		//On réinitialise le TextField et la transcription
		motPropose.setText("");
		transcription.setText("");

		//On met à jour la transcription grâce à la liste des mots de l'étudiant		
		for(int o = 0; o < lesMotsEtudiant.size(); o++) {

			word = lesMotsEtudiant.get(o);

			//Si c'est le premier mot, on ne met pas d'espace avant
			if(o == 0) {
				transcription.setText(word);
			}
			//Si cela correspond à de la ponctuation
			else if(regexPoint(word)){
				transcription.setText(transcription.getText() + word);
			}
			//Si c'est un mot "normal"
			else {
				transcription.setText(transcription.getText() + " " + word);
			}
		}

		//On regarde si l'étudiant a terminé l'exercice
		if(estTermine()) {

			//Si c'est le cas, on enregistre son exercice, puis on load une popUp
			retourMenu();
			finExercice();
			enregistrementExo();
		}
	}*/

	public ArrayList<String> getLesMots() {
		return lesMots;
	}

	public ArrayList<String> getLesMotsEtudiant() {
		return lesMotsEtudiant;
	}

	//Méthode qui regarde si l'étudiant a fini l'exercice
	public boolean estTermine() {

		//L'exercice est terminé s'il l'étudiant a découvert tous les mots
		if(Math.round((nbMotsDecouverts / nbMotsTotal) * 100) == 100){
			mediaPlayer.stop();
			return true;
		} else {
			return false;
		}

	}

	//Méthode qui va load le temps écoulé pour le mode évaluation
	public void finExercice() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Etudiant/FXML_Files/ValidationEnregistrement.fxml"));
		//On bloque sur cette fenêtre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		DeplacementFenetre.deplacementFenetre((Pane) root, stage);
		Scene scene = new Scene(root, 350, 180);
		stage.setScene(scene);
		darkModeActivation(scene);
		stage.show();
	}


	//Méthode permettant de créer un timer pour que l'étudiant voit le temps qui défile en mode Evaluation
	public void gestionTimer() {
		timer = new Timeline();
		timer.setCycleCount(Timeline.INDEFINITE);

		if(evaluation == true) {
			timer.getKeyFrames().add(
					new KeyFrame(Duration.seconds(1),
							new EventHandler<ActionEvent>() {
						// KeyFrame event handler
						@Override    
						public void handle(ActionEvent arg0) {
							sec--;
							if (sec < 0) {
								min--;
								sec=59;
							}
							// update timerLabel
							time.setText(min +":"+ sec +"s");

							//S'il ne reste plus de temps, on load la fenetre d'enregistrement
							if (sec <= 0 && min<=0) {
								timer.stop();
								try {
									retourMenu();
									loadEnregistrement();
									enregistrementExo();
								} catch (IOException e) {
									e.printStackTrace();
								}
								return;
							}

						}
					}));
			timer.playFromStart();
		}

		if(entrainement == true) {
			timer.getKeyFrames().add(
					new KeyFrame(Duration.seconds(1),
							new EventHandler<ActionEvent>() {
						// KeyFrame event handler
						@Override    
						public void handle(ActionEvent arg0) {
							sec++;
							if (sec > 59) {
								min++;
								sec = 00;
							}

							// update timerLabel
							time.setText(min +":"+ sec +"s");

						}
					}));
			timer.playFromStart();
		}
	}

	//Méthode qui survient lorsque le timer est écoulé en mode Evaluation
	public void loadEnregistrement() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Etudiant/FXML_Files/TempsEcoule.fxml"));
		//On bloque sur cette fenêtre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		DeplacementFenetre.deplacementFenetre((Pane) root, stage);
		//On bloque le resize
		stage.setResizable(false);
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		darkModeActivation(scene);
		stage.show();
	}

	//Méthode qui va enregistrer l'exercice de l'étudiant
	public void enregistrementExo() throws IOException {

		File file = new File(Controller_EnregistrementApresOuverture.repertoireEtudiant + "\\" + Controller_EnregistrementApresOuverture.nomExo
				+ "_" + Controller_EnregistrementApresOuverture.nomEtudiant + "_" + Controller_EnregistrementApresOuverture.prenEtudiant + ".rct");
		FileWriter fwrite = new FileWriter(file);
		BufferedWriter buffer = new BufferedWriter(fwrite);

		buffer.write(transcription.getText());
		buffer.newLine();
		buffer.write(Double.toString(Math.round((nbMotsDecouverts / nbMotsTotal) * 100)) + '%');

		buffer.close();
		fwrite.close();
	}

	//Méthode pour retourner au menu
	public void retourMenu() throws IOException {
		Stage stage = (Stage) alertSolution.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Etudiant/FXML_Files/Menu.fxml"));
		Scene scene = new Scene(root,  MainEtudiant.width, MainEtudiant.height - 60);
		stage.setScene(scene);
		darkModeActivation(scene);
		stage.show();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//Méthode pour affiicher une toolTip consigne + redimension d'image
	@FXML
	public void tipConsigneEnter() {
		Tooltip t = new Tooltip("Il s'agit de la consigne donnée par le professeur");
		questionConsigne.setFitWidth(questionConsigne.getFitWidth() + 2);
		questionConsigne.setFitHeight(questionConsigne.getFitHeight() + 2);
		Tooltip.install(questionConsigne, t);
	}

	//Méthode pour redimensionner l'image de la consigne quand on sort du champ
	@FXML
	public void tipConsigneExit() {
		questionConsigne.setFitWidth(questionConsigne.getFitWidth() - 2);
		questionConsigne.setFitHeight(questionConsigne.getFitHeight() - 2);
	}

	//Méthode pour affiicher une toolTip transcription + redimension d'image
	@FXML
	public void tipTranscriptionEnter() {
		Tooltip t = new Tooltip("Il s'agit du script de la vidéo que vous devez essayer de retrouver");
		questionTranscription.setFitWidth(questionTranscription.getFitWidth() + 2);
		questionTranscription.setFitHeight(questionTranscription.getFitHeight() + 2);
		Tooltip.install(questionTranscription, t);
	}

	//Méthode pour redimensionner l'image de la transcription quand on sort du champ
	@FXML
	public void tipTranscriptionExit() {
		questionTranscription.setFitWidth(questionTranscription.getFitWidth() - 2);
		questionTranscription.setFitHeight(questionTranscription.getFitHeight() - 2);
	}

	//Méthode pour affiicher une toolTip transcription + redimension d'image
	@FXML
	public void tipPropositionEnter() {
		Tooltip t = new Tooltip("Rentrez ici les mots que vous pensez entendre dans le document audio ou vidéo");
		questionProposition.setFitWidth(questionProposition.getFitWidth() + 2);
		questionProposition.setFitHeight(questionProposition.getFitHeight() + 2);
		Tooltip.install(questionProposition, t);
	}

	//Méthode pour redimensionner l'image de la transcription quand on sort du champ
	@FXML
	public void tipPropositionExit() {
		questionProposition.setFitWidth(questionProposition.getFitWidth() - 2);
		questionProposition.setFitHeight(questionProposition.getFitHeight() - 2);
	}

	//Méthode pour passer ou non le darkMode
	@FXML
	public void darkMode() {

		if(dark.isSelected()) {
			ButtonAide.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Etudiant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			ButtonAide.getScene().getStylesheets().addAll(getClass().getResource("/Version_Etudiant/FXML_Files/darkModeTest.css").toExternalForm());
			Controller_Menu.isDark = true;
		} else {
			ButtonAide.getScene().getStylesheets().removeAll(getClass().getResource("/Version_Etudiant/FXML_Files/darkModeTest.css").toExternalForm());
			ButtonAide.getScene().getStylesheets().addAll(getClass().getResource("/Version_Etudiant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			Controller_Menu.isDark = false;
		}
	}


	private void setKeyboardShortcut() {
		ButtonAide.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println(ButtonAide.getScene().focusOwnerProperty().get());
				 if ((ButtonAide.getScene().focusOwnerProperty().get() instanceof TextField)) {
	                    if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
	                    	if(!motPropose.getText().isEmpty()) {
	    						try {
	    							verify(motPropose.getText());
	    							motPropose.setText("");
	    						} catch (IOException e) {
	    							e.printStackTrace();
	    						}
	    					}
	                    }
	                }
				 else if (event.getCode() == KeyCode.SPACE) {
					if (mediaView.getMediaPlayer().getStatus() == Status.PAUSED) {
						mediaView.getMediaPlayer().play();
						playOrPause.setImage(pause);
					}
					if (mediaView.getMediaPlayer().getStatus() == Status.PLAYING) {
						mediaView.getMediaPlayer().pause();
						playOrPause.setImage(play);
					}

				}
				if (event.getCode() == KeyCode.RIGHT && mediaView.getMediaPlayer().getTotalDuration().greaterThan(mediaView.getMediaPlayer().getCurrentTime().add(new Duration(5000)))) {
					mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getCurrentTime().add(new Duration(5000)));
				}
				if (event.getCode() == KeyCode.LEFT && new Duration(0).lessThan(mediaView.getMediaPlayer().getCurrentTime().subtract(new Duration(5000)))) {
					mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getCurrentTime().subtract(new Duration(5000)));
				}
				if (event.getCode() == KeyCode.UP) {
					sliderSon.setValue(sliderSon.getValue() + 3);
				}
				if (event.getCode() == KeyCode.DOWN) {
					sliderSon.setValue(sliderSon.getValue() - 3);
				}
			}

		});
		
		ButtonAide.getScene().addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				 if ((ButtonAide.getScene().focusOwnerProperty().get() instanceof TextField)) {
	                    if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
	    						motPropose.setText("");
	                    }
	                }
			}
		});
	}
}
