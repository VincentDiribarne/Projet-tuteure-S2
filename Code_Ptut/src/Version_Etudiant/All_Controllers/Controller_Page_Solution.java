package Version_Etudiant.All_Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Controller_Page_Solution implements Initializable {

	// Solution
	@FXML
	private TextFlow solution;
	@FXML
	private Button closeSol;
	@FXML
	private ScrollPane scrollPane;
	Controller_Page_Exercice c = Controller_Menu.getC();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		/*for (int i = 0; i < c.getLesMots().size(); i++) {
		    Text text = new Text(c.getLesMots().get(i));
		    
		    if (c.getLesMots().get(i).equals(c.getLesMotsEtudiant().get(i))) {
		        text.setFill(Color.GREEN);
		        } else {
		        text.setFill(Color.RED);
		    }
		    text.setText(text.getText() + " ");
		    solution.getChildren().add(text);
		}
		
		solution.setStyle("-fx-border-color:  #848484; -fx-background-color:  #F6F6F6; -fx-padding: 5,5,5,5; -fx-border-radius: 15;");
		
        solution.prefWidthProperty().bind(scrollPane.widthProperty().subtract(15));*/
		
		String[] encrypted = c.getEncryptedText().split("[ \\t\\n\\x0B\\f\\r]");
		String[] clear = c.getClearText().split("[ \\t\\n\\x0B\\f\\r]");
		int length = 0;
		for (int i = 0; i < clear.length; i++) {
			Text t = new Text(clear[i]);
			if (clear[i].equals(encrypted[i])) {
				t.setFill(Color.GREEN);
			}else {
				t.setFill(Color.RED);
			}
			solution.getChildren().add(t);
			if (length + clear[i].length() < c.getClearText().length()) {
				length += clear[i].length();
			}
			if (Character.isWhitespace(c.getClearText().charAt(length)) || Character.isSpaceChar(c.getClearText().charAt(length))) {
				solution.getChildren().add(new Text(""+c.getClearText().charAt(length)));
			}
			length++;
		}

	}

	@FXML
	public void closeSolution() {
		closeSol.getScene().getWindow().hide();
	}

}
