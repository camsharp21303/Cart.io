package graphicalUI;

import java.io.File;
import java.util.ArrayList;

import application.Main;
import cartSQL.ItemsSQL;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
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
		table.setEditable(true);
		
		ArrayList<Item> items = new ItemsSQL().getAllItems();
		
		numberC = new TableColumn<>("Item Number");
		numberC.setCellValueFactory(new PropertyValueFactory<>("number"));
		table.getColumns().add(numberC);
		
		nameC = new TableColumn<>("Item Name");
		nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameC.isEditable();
		table.getColumns().add(nameC);
		
		priceC = new TableColumn<>("Price");
		priceC.setCellValueFactory(new PropertyValueFactory<>("priceString"));
		priceC.setCellFactory(TextFieldTableCell.forTableColumn());
		priceC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item,String>>() {
			@Override
			public void handle(CellEditEvent<Item, String> arg0) {
				new ItemsSQL().editItem("price", 
						arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getNumber()
						, arg0.getNewValue().toString());
			}
		});
		table.getColumns().add(priceC);
		
		stockC = new TableColumn<>("Stock");
		stockC.setCellValueFactory(new PropertyValueFactory<>("stockString"));
		stockC.setCellFactory(TextFieldTableCell.forTableColumn());
		stockC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item,String>>() {
			@Override
			public void handle(CellEditEvent<Item, String> arg0) {
				new ItemsSQL().editItem("stock", 
						arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).getNumber()
						, arg0.getNewValue());
				
			}
		});
		table.getColumns().add(stockC);
		
		TableColumn<Item, Void> deleteC = new TableColumn<Item, Void>("Delete");
		Callback<TableColumn<Item, Void>, TableCell<Item, Void>> cellFactory = new Callback<TableColumn<Item,Void>, TableCell<Item,Void>>() {

			@Override
			public TableCell<Item, Void> call(TableColumn<Item, Void> arg0) {
				TableCell<Item, Void> cell = new TableCell<Item, Void>() {
					private Button del = new Button("Delete"); {
						del.setOnAction(e -> {
							new ItemsSQL().removeItem(getTableView().getItems().get(getIndex()));
							getTableView().getItems().remove(getIndex());
						});
					}
					@Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(del);
                        }
                    }
                };
				return cell;
			}
		};
		deleteC.setCellFactory(cellFactory);
		table.getColumns().add(deleteC);
		
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
		new ItemsSQL().insertItem(
				new Item(newNameTF.getText(),
				Float.parseFloat(newPriceTF.getText()), 
				Integer.parseInt(newStockTF.getText()), null),
				imageFile);
		ArrayList<Item> items = new ItemsSQL().getAllItems();
		table.setItems(FXCollections.observableList(items));
	}
}
