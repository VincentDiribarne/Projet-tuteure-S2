package Version_Enseignant;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DeplacementFenetre {
    public static void deplacementFenetre(Pane root, Stage primaryStage){
        class Delta { double x, y; }

        final Delta dragDelta = new Delta();
        root.setOnMousePressed(mouseEvent -> {
            dragDelta.x = primaryStage.getX() - mouseEvent.getScreenX();
            dragDelta.y = primaryStage.getY() - mouseEvent.getScreenY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() + dragDelta.x);
            primaryStage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });
    }

}
