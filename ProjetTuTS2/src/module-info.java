module ProjetTuTS2 {
	requires javafx.controls;
	requires javafx.media;
	requires java.xml;
	requires javafx.web;
	requires javafx.graphics;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
}
