package Version_Etudiant.All_Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class Controller_Page_Solution implements Initializable {

	// Solution
	@FXML
	private AnchorPane anchor;
	@FXML
	private Button closeSol;
	Controller_Page_Exercice c = new Controller_Page_Exercice();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//On déclare une TextArea spéciale
		InlineCssTextArea css = new InlineCssTextArea();
		css.appendText(c.contenuTranscription);
		css.setEditable(false);
        css.setPrefSize(500, 250);
        css.setLayoutX(anchor.getLayoutX());
        css.setLayoutX(anchor.getLayoutY());
        
        css.setWrapText(true);
		VirtualizedScrollPane<InlineCssTextArea> vsPane = new VirtualizedScrollPane<InlineCssTextArea>(css);
		anchor.getChildren().add(vsPane);
	}

	@FXML
	public void closeSolution() {
		closeSol.getScene().getWindow().hide();
	}

}
