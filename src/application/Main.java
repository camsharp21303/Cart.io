package application;
	
import cartSQL.ItemsSQL;
import graphicalUI.AppPane;
import graphicalUI.Login;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import types.User;

public class Main extends Application{
	private static Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		window.setTitle("Cart.IO");
		window.getIcons().add(new Image(getClass().getResourceAsStream("cart.png")));
		new Login(window);
		
		window.show();
	}
	
	public static void login(User user) {
		System.out.println("hello");
		new AppPane(user, window);
	}
	
	public static void logout() {
		new Login(window);
	}

}