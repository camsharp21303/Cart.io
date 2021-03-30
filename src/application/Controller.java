package application;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {
	public Button login;
	
	public void sayHello(ActionEvent action) {
		System.out.println("Hello world");
	}
	
	public void tabClick(ActionEvent action) {
		login = (Button) action.getSource();
		login.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #40d13e;");
	}
}
