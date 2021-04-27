package graphicalUI;

import java.util.ArrayList;

import application.Main;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import types.Item;
import types.Order;
import types.Order.ORDER_STAT;

public class OrderItemView extends VBox {
	@SuppressWarnings("unchecked")
	public OrderItemView(Order order) {
		super();
		TableView<Item> table = new TableView<Item>();
		TableColumn<Item, String> numberC, nameC, priceC, stockC;
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setTitle("Order Number: " + order.getId());

		ArrayList<Item> items = order.getItems();

		numberC = new TableColumn<>("Item Number");
		numberC.setCellValueFactory(new PropertyValueFactory<>("number"));

		nameC = new TableColumn<>("Item Name");
		nameC.setCellValueFactory(new PropertyValueFactory<>("name"));

		priceC = new TableColumn<>("Price");
		priceC.setCellValueFactory(new PropertyValueFactory<>("priceString"));

		stockC = new TableColumn<>("Stock");
		stockC.setCellValueFactory(new PropertyValueFactory<>("stockString"));

		table.getColumns().addAll(numberC, nameC, priceC, stockC);

		table.setItems(FXCollections.observableList(items));

		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> popup.close());

		Button finish = new Button("Finish");
		finish.setOnAction(e -> {
			order.setStatus(ORDER_STAT.Shipped);
			popup.close();
		});

		getChildren().addAll(table, cancel, finish);
		setAlignment(Pos.CENTER);
		setSpacing(15);

		getStylesheets().add(Main.class.getResource("custom.css").toExternalForm());

		popup.setScene(new Scene(this, 400, 300));
		popup.showAndWait();
	}
}
