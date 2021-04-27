package graphicalUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import types.Customer;
import types.Employee;
import types.Employee.POSITION;
import types.User;

public class CreateAccountAdvanced {

	private static TextField nameTF, emailTF, phoneTF, usernameTF, addressTF;
	private static PasswordField passwordTF, confirmPassTF;
	private static ComboBox<String> newTypeCB;
	private static VBox scene;

	public static Node getNode(Employee user) {
		Label title;
		Button create;

		title = new Label("Create Account");

		ObservableList<String> options = FXCollections.observableArrayList("Customer");
		if (user.getPosition() == POSITION.MANAGER || user.getPosition() == POSITION.BOARD) {
			options.add("Employee");
			options.add("Manager");
		}
		newTypeCB = new ComboBox<>(options);
		newTypeCB.setValue("Customer");
		newTypeCB.setMaxWidth(250);
		newTypeCB.setOnAction(e -> changeType());

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

		scene = new VBox(title, newTypeCB, nameTF, emailTF, phoneTF, usernameTF, addressTF, passwordTF, confirmPassTF,
				create);
		scene.setStyle("-fx-background-color: #FFFFFF;");
		scene.setSpacing(10);
		scene.setAlignment(Pos.CENTER);

		return scene;
	}

	private static void changeType() {
		String selection = newTypeCB.getSelectionModel().getSelectedItem();
		if (selection.equals("Manager") || selection.equals("Employee")) {
			scene.getChildren().remove(addressTF);
		} else {
			scene.getChildren().add(6, addressTF);
		}
	}

	private static void createUser() {
		if (!passwordTF.getText().equals(confirmPassTF.getText()))
			return;
		String firstName = "", lastName = "";
		String fullName = nameTF.getText();
		if (fullName.contains(" ")) {
			int pivot = fullName.indexOf(' ');
			firstName = fullName.substring(0, pivot);
			lastName = fullName.substring(pivot + 1);
		} else {
			firstName = fullName;
		}

		User newUser;

		switch (newTypeCB.getValue()) {
		case "Employee":
			newUser = new Employee(firstName, lastName, usernameTF.getText(), phoneTF.getText(), emailTF.getText(),
					POSITION.STANDARD, null);
			break;
		case "Manager":
			newUser = new Employee(firstName, lastName, usernameTF.getText(), phoneTF.getText(), emailTF.getText(),
					POSITION.MANAGER, null);
			break;
		default:
			newUser = new Customer(firstName, lastName, usernameTF.getText(), phoneTF.getText(), emailTF.getText(),
					addressTF.getText(), null);
		}

		newUser.commit(passwordTF.getText(), null);
		
	}
}
