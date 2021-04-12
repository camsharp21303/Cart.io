package graphicalUI;

import java.io.File;

import application.Main;
import cartSQL.UsersSQL;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import types.Customer;

public class CreateAccount {
	private static TextField nameTF, emailTF, phoneTF, usernameTF, addressTF;
	private static PasswordField passwordTF, confirmPassTF;
	private static Label fileName;
	private static File imageFile;
	
	public static void activateCreateAccount() {
		ImageView logo;
		Label title;
		Button create, uploadImage;
		
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
		
		fileName = new Label("No Image");
		uploadImage = new Button("Upload Image");
		uploadImage.setOnAction(e -> chooseImage());
		
		HBox imagePick = new HBox(uploadImage, fileName);
		imagePick.setAlignment(Pos.CENTER);
		
		create = new Button("Create Account");
		create.setOnAction(e -> createUser());
		create.setDefaultButton(true);
		
		VBox box = new VBox(logo, title, nameTF, emailTF, phoneTF, usernameTF, addressTF, passwordTF, confirmPassTF, imagePick, create);
		box.getStylesheets().add(Main.class.getResource("custom.css").toExternalForm());
		box.setStyle("-fx-background-color: #FFFFFF;");
		box.setSpacing(10);
		box.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(box, 800, 600);
		Main.getStage().setScene(scene);
	}
	
	private static void chooseImage() {
		imageFile = new FileChooser().showOpenDialog(Main.getStage());
		fileName.setText(imageFile.getName());
	}
	
	private static void createUser() {
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
				phoneTF.getText(), emailTF.getText(), addressTF.getText(), null);
		
		UsersSQL.insertUser(customer, passwordTF.getText(), imageFile);
		System.out.println("Made user");
		
		Main.login(customer);
	}
}
