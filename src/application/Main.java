package application;

import graphicalUI.AppPane;
import graphicalUI.Login;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import types.User;

public class Main extends Application {
	// window handle
	private static Stage window;

	// launch app
	public static void main(String[] args) {
		launch(args);
	}

	// returns window handle
	public static Stage getStage() {
		return window;
	}

	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		window.setTitle("Cart.IO");
		window.getIcons().add(new Image(Main.class.getResourceAsStream("cart.png")));
		Login.activateLogin();

		window.show();
	}

	public static void login(User user) {
		
		AppPane.activateAppPane(user);
	}

	public static void logout() {
		Login.activateLogin();
	}
}