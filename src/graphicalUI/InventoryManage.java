package graphicalUI;

import java.io.File;
import java.util.ArrayList;

import application.Main;
import cartSQL.ItemsSQL;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import types.Employee;
import types.Item;

public class InventoryManage {
	private static TextField searchTF, newNameTF, newPriceTF, newStockTF;
	private static File imageFile;
	private static TableView<Item> table;
	
	public static Node getNode(Employee employee) {
		Button searchB, addItemB, uploadImageB;
		TableColumn<Item, String> numberC, nameC, priceC, stockC;
		
		table = new TableView<>();
		
		ArrayList<Item> items = new ItemsSQL().getAllItems();
		
		numberC = new TableColumn<>("Item Number");
		numberC.setCellValueFactory(new PropertyValueFactory<>("number"));
		table.getColumns().add(numberC);
		
		nameC = new TableColumn<>("Item Name");
		nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameC.isEditable();
		table.getColumns().add(nameC);
		
		priceC = new TableColumn<>("Price");
		priceC.setCellValueFactory(new PropertyValueFactory<>("price"));
		table.getColumns().add(priceC);
		stockC = new TableColumn<>("Stock");
		stockC.setCellValueFactory(new PropertyValueFactory<>("stock"));
		table.getColumns().add(stockC);
		
		table.setItems(FXCollections.observableList(items));
		
		uploadImageB = new Button("Upload Image");
		uploadImageB.setOnAction(e -> chooseImage());
		
		newNameTF = new TextField();
		newNameTF.setPromptText("Item Name");
		
		newPriceTF = new TextField();
		newPriceTF.setPromptText("Item Price");
		
		newStockTF = new TextField();
		newStockTF.setPromptText("Item Stock");
		
		addItemB = new Button("Add Item");
		addItemB.setOnAction(e -> newItem());
		
		HBox bottom = new HBox(uploadImageB, newNameTF, newPriceTF, newStockTF, addItemB);
		bottom.setSpacing(30);
		
		searchTF = new TextField();
		searchTF.setPromptText("Item ID/name");
		
		searchB = new Button("Search");
		
		HBox top = new HBox(searchTF, searchB);
		top.setSpacing(30);
		
		BorderPane scene = new BorderPane();
		scene.setCenter(table);
		scene.setBottom(bottom);
		scene.setTop(top);
		
		return scene;
	}
	
	private static void chooseImage() {
		imageFile = new FileChooser().showOpenDialog(Main.getStage());
	}
	
	public static void newItem() {
		new ItemsSQL().insertItem(new Item(newNameTF.getText(),
				"", newPriceTF.getText(), Integer.parseInt(newStockTF.getText()), null)
				, imageFile);
		ArrayList<Item> items = new ItemsSQL().getAllItems();
		table.setItems(FXCollections.observableList(items));
	}
}
