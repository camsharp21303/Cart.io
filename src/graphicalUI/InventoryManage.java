package graphicalUI;

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
import types.Employee;
import types.Item;

public class InventoryManage {
	private TextField searchTF, newNameTF, newPriceTF, newStockTF;
	private Button searchB, addItemB, addStockB, minusStockB, deleteItemB;
	private TableView<Item> table;
	private TableColumn<Item, String> numberC, nameC, priceC, stockC;
	private BorderPane scene;
	
	public InventoryManage(Employee employee) {
		table = new TableView<>();
		
		ArrayList<Item> items = new ItemsSQL().getAllItems();
		numberC = new TableColumn<>("Item Number");
		numberC.setCellValueFactory(new PropertyValueFactory<>("number"));
		table.getColumns().add(numberC);
		 nameC = new TableColumn<>("Item Name");
		nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
		table.getColumns().add(nameC);
		priceC = new TableColumn<>("Price");
		priceC.setCellValueFactory(new PropertyValueFactory<>("price"));
		table.getColumns().add(priceC);
		stockC = new TableColumn<>("Stock");
		stockC.setCellValueFactory(new PropertyValueFactory<>("stock"));
		table.getColumns().add(stockC);
		
		table.setItems(FXCollections.observableList(items));
		
		newNameTF = new TextField();
		newNameTF.setPromptText("Item Name");
		
		newPriceTF = new TextField();
		newPriceTF.setPromptText("Item Price");
		
		newStockTF = new TextField();
		newStockTF.setPromptText("Item Stock");
		
		addItemB = new Button("Add Item");
		addItemB.setOnAction(e -> newItem());
		
		HBox bottom = new HBox(newNameTF, newPriceTF, newStockTF, addItemB);
		bottom.setSpacing(30);
		
		scene = new BorderPane();
		scene.setCenter(table);
		scene.setBottom(bottom);
		scene.getStylesheets().add(Main.class.getResource("custom.css").toExternalForm());
	}
	
	public Node getScene() {
		return scene;
	}
	
	public void newItem() {
		new ItemsSQL().insertItem(new Item(newNameTF.getText(),
				"", newPriceTF.getText(), Integer.parseInt(newStockTF.getText())));
		ArrayList<Item> items = new ItemsSQL().getAllItems();
		table.setItems(FXCollections.observableList(items));
	}
}
