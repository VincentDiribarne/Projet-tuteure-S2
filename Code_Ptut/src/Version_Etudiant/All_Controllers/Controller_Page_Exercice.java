package Version_Etudiant.All_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Controller_Page_Exercice implements Initializable{

	//Variables qui vont contenir les différentes informations sur l'exercice
	//Informations textuelles
	public static String contenuTranscription;
	public static String contenuConsigne;
	public static Media contenuMedia;
	public static String caractereOccul;

	//Options
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

	@FXML private Button playPause;
	MediaPlayer mediaPlayer = new MediaPlayer(contenuMedia);

	private ArrayList<String> lesMots = new ArrayList<>();
	private ArrayList<String> lesMotsEtudiant = new ArrayList<>();

	private Timeline timer;
	private Integer sec = 0;
	private Integer min;
	private boolean timerEstDeclenche = false;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		int i, check = 0;
		String mot = "", m = "";

		transcription.setWrapText(true);

		//On load les informations de l'exercice dans les composants
		if(contenuTranscription != null) {

			//Pour la transcription, on utilise le caractère d'occultation
			for(i = 0; i < contenuTranscription.length(); i++) {

				//Si cela correspond bien à la regex, on occulte le mot
				if(okRegex(contenuTranscription.charAt(i)) == true) {
					transcription.setText(transcription.getText() + caractereOccul);
					mot += contenuTranscription.charAt(i);
				} else {
					transcription.setText(transcription.getText() + contenuTranscription.charAt(i));
					mot = "";
				}
			}

			for(int j = 0; j < contenuTranscription.length(); j++) {
				if(contenuTranscription.charAt(j) != ' ') {
					if(okRegex(contenuTranscription.charAt(j))) {
						m += caractereOccul;
					} else {
						check = 1;
					}
				} else {
					if(m != "") {
						lesMotsEtudiant.add(m);
						if(check == 1) {
							lesMotsEtudiant.add(contenuTranscription.charAt(j - 1) + "");
							check = 0;
						}
					}		
					m = "";
				}
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
		} 
		//Sinon cela veut dire que l'on est en mode Entrainement
		else {
			titleTime.setText("Temps Ecoulé");
			min = 00;
			time.setText("00:00");
		}


		//On fait apparaître une fenêtre pour que l'étudiant rentre son nom et prénom en vue du futur enregistrement
		try {
			popUpEnregistrement();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Fonction qui play/pause le media
	@FXML
	public void playMedia(ActionEvent event) {
		//Si on veut mettre en pause la vidéo
		if(playPause.getText().compareTo("Play") == 0) {
			playPause.setText("Pause");
			mediaPlayer.play();
		} else {
			playPause.setText("Play");
			mediaPlayer.pause();
		}


		if(timerEstDeclenche == false) {
			gestionTimer();
			timerEstDeclenche = true;
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


	//Méthode qui fait apparaître la popUp pour que l'étudiant rentre ses infos pour l'enregistrement
	public void popUpEnregistrement() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/EnregistrementApresouverture.fxml"));
		Stage stage = new Stage();
		//On bloque sur cette fenêtre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		//On bloque le resize
		stage.setResizable(false);
		stage.setScene(new Scene(root, 500, 300));
		stage.show();
	}

	//Méthode pour quitter l'application
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	//Méthode qui permet à l'étudiant de proposer un mot, et affichage ou non dans le texte occulté si le mot est présent
	@FXML
	public void propositionMot(ActionEvent event) {

		if(timerEstDeclenche == false) {
			gestionTimer();
			timerEstDeclenche = true;
		}


		String mot = motPropose.getText();
		transcription.setText("");
		System.out.println(lesMotsEtudiant);
		System.out.println(lesMots);

		for(String motCompa : lesMots) {

			if(motCompa.compareTo(mot) == 0) {

			}

		}

		//On réécrit le texte où l'étudiant à trouvé les mots
		for(String m : lesMotsEtudiant) {
			if(m == caractereOccul) {
				transcription.setText(transcription.getText() + m + " ");
			}
			else {
				transcription.deletePreviousChar();
				transcription.setText(transcription.getText() + m + " ");
			}
		}

		//On réinitialise le TextField
		motPropose.setText(null);
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
									loadEnregistrement();
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
								sec=00;
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
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/TempsEcoule.fxml"));
		Stage stage = new Stage();
		//On bloque sur cette fenêtre
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		//On bloque le resize
		stage.setResizable(false);
		stage.setScene(new Scene(root, 500, 300));
		stage.show();
	}

}
