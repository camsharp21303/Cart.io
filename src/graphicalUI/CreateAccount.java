package graphicalUI;

import application.Main;
import cartSQL.UsersSQL;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import types.Customer;

public class CreateAccount {
	private ImageView logo;
	private Label title;
	private TextField nameTF, emailTF, phoneTF, usernameTF, addressTF;
	private PasswordField passwordTF, confirmPassTF;
	private Button create;
	
	public CreateAccount(Stage stage) {
		logo = new ImageView(Main.class.getResource("cart.png").toExternalForm());
		logo.setFitHeight(150);
		logo.setFitWidth(150);
		
		title = new Label("Create Account");
		
		nameTF = new TextField();
		nameTF.setPromptText("Full Name");
		nameTF.setMaxWidth(250);
		
		emailTF = new TextField();
		emailTF.setPromptText("Email (Optional)");
		emailTF.setMaxWidth(250);
		
		phoneTF = new TextField();
		phoneTF.setPromptText("Phone Number (Optional)");
		phoneTF.setMaxWidth(250);
		
		usernameTF = new TextField();
		usernameTF.setPromptText("Username");
		usernameTF.setMaxWidth(250);
		
		addressTF = new TextField();
		addressTF.setPromptText("Address");
		addressTF.setMaxWidth(250);
		
		passwordTF = new PasswordField();
		passwordTF.setPromptText("Password");
		passwordTF.setMaxWidth(250);
		
		confirmPassTF = new PasswordField();
		confirmPassTF.setPromptText("Confirm Password");
		confirmPassTF.setMaxWidth(250);
		
		create = new Button("Create Account");
		create.setOnAction(e -> createUser());
		create.setDefaultButton(true);
		
		VBox box = new VBox(logo, title, nameTF, emailTF, phoneTF, usernameTF, addressTF, passwordTF, confirmPassTF, create);
		box.getStylesheets().add(Main.class.getResource("custom.css").toExternalForm());
		box.setStyle("-fx-background-color: #FFFFFF;");
		box.setSpacing(10);
		box.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(box, 800, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	private void createUser() {
		if(!passwordTF.getText().equals(confirmPassTF.getText())) return;
		String firstName = "", lastName = "";
		String fullName = nameTF.getText();
		if(fullName.contains(" ")) {
			int pivot= fullName.indexOf(' ');
			firstName = fullName.substring(0, pivot);
			lastName = fullName.substring(pivot+1);
		}
		else {
			firstName = fullName;
		}
		
		Customer customer = new Customer(firstName, lastName, usernameTF.getText(), 
				phoneTF.getText(), emailTF.getText(), addressTF.getText());
		
		new UsersSQL().insertUser(customer, passwordTF.getText());
		System.out.println("Made user");
		
		Main.login(customer);
	}
}
