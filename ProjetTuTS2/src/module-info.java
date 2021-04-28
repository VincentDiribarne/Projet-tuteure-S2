module ProjetTuTS2 {
	requires javafx.controls;
	requires javafx.media;
	requires java.xml;
	requires javafx.web;
	
	opens application to javafx.graphics, javafx.fxml;
}
