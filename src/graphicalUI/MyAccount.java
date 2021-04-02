package graphicalUI;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Main;
import cartSQL.UsersSQL;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import types.Customer;
import types.User;

public class MyAccount {
	private BorderPane node;
	private Label nameL, numberL, addressL, emailL, phoneL;
	private Button logoutB, editImageB;
	private ImageView accountImage;
	private File imageFile;
	private User user;
	
	public MyAccount(User user) {
		this.user = user;
		node = new BorderPane();
		nameL = new Label(user.getFname() + " " + user.getLname());
		numberL = new Label("Account Number: #" + user.getNumber());
		emailL = new Label("Email: " + user.getEmail());
		phoneL = new Label("Phone: " + user.getPhone());
		
		//editEmailB = new Button("Edit");
		//editPhoneB = new Button("Edit");
		
		if(user.getImage() != null) {
			accountImage = new ImageView(new Image(new ByteArrayInputStream(user.getImage())));
		}
		else {
			System.out.println("No Imag Found");
			accountImage = new ImageView(new Image(Main.class.getResourceAsStream("default.png")));
		}
		accountImage.setFitHeight(150);
		accountImage.setFitWidth(150);
		
		editImageB = new Button("Edit Image");
		editImageB.setOnAction(e -> changeImage());
		
		logoutB = new Button("Logout");
		logoutB.setOnAction(e -> Main.logout());
		
		node.setBottom(logoutB);
		
		VBox userInfo;
		if(user instanceof Customer) {
			addressL = new Label("Address: " + ((Customer)user).getAddress());
			userInfo = new VBox(nameL, numberL, addressL, emailL, phoneL);
		}
		else {
			userInfo = new VBox(nameL, numberL, emailL, phoneL);
		}
		
		VBox imageViewer = new VBox(accountImage, editImageB);
		
		node.setLeft(imageViewer);
		node.setCenter(userInfo);
	}
	
	public void changeImage() {
		FileChooser fileChoose = new FileChooser();
		imageFile = fileChoose.showOpenDialog(Main.getStage());
		try {
			FileInputStream stream = new FileInputStream(imageFile);
			accountImage.setImage(new Image(stream));
			new UsersSQL().updateAccountImage(user, imageFile);
		}catch(FileNotFoundException e){
			System.out.println("File not found");
		}
		
	}
	
	public Node getScene() {
		return node;
	}
}
