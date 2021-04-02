package graphicalUI;

import application.Main;
import cartSQL.UsersSQL;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import types.User;

public class Login {
	private static TextField username, password;
	
	public static void activateLogin() {
		Button login;
		ImageView logo;
		Hyperlink createAccount;
		
		username = new TextField();
		username.setPromptText("Username/Email/Phone");
		username.setMaxWidth(250);
		
		password = new PasswordField();
		password.setPromptText("Password");
		password.setMaxWidth(250);
		
		login = new Button("Login");
		login.setPrefWidth(100);
		login.setOnAction(e -> login());
		login.setDefaultButton(true);
		
		logo = new ImageView(Main.class.getResource("cart.png").toExternalForm());
		logo.setFitHeight(150);
		logo.setFitWidth(150);
		
		createAccount = new Hyperlink("Create Account");
		createAccount.setOnAction(e -> CreateAccount.activateCreateAccount());
		
		VBox box = new VBox(logo, username, password, login, createAccount);
		box.getStylesheets().add(Main.class.getResource("custom.css").toExternalForm());
		box.setStyle("-fx-background-color: #FFFFFF;");
		box.setSpacing(50);
		box.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(box, 800, 600);
		Main.getStage().setScene(scene);
	}
	
	public static void login() {
		String userString = username.getText();
		String passString = password.getText();
		User user = new UsersSQL().login(userString, passString);
		if(user != null) Main.login(user);
	}
}
