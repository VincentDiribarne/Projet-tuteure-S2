package Version_Etudiant.All_Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class Controller_Page_Aides {

    @FXML private ListView listViewAide;
    @FXML private Button close;

    @FXML
    public void closeAide() {
        close.getScene().getWindow().hide();
    }
}
