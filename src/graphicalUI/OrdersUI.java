package graphicalUI;

import java.util.ArrayList;

import cartSQL.OrdersSQL;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import types.Order;
import types.Order.ORDER_STAT;

public class OrdersUI {
	@SuppressWarnings("unchecked")
	public static Node getNode() {
		TableColumn<Order, String> idC, customerC, priceC, addressC;
		TableColumn<Order, ORDER_STAT> statusC;
		
		TableView<Order> table = new TableView<>();
		table.setEditable(true);
		
		ArrayList<Order> orders = OrdersSQL.getAllOrders();
		
		idC = new TableColumn<Order, String>("Order ID");
		idC.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		customerC = new TableColumn<Order, String>("Customer ID");
		customerC.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		
		priceC = new TableColumn<Order, String>("Price");
		priceC.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		addressC = new TableColumn<Order, String>("Address");
		addressC.setCellValueFactory(new PropertyValueFactory<>("address"));
		
		statusC = new TableColumn<Order, ORDER_STAT>("Status");
		statusC.setCellValueFactory(new PropertyValueFactory<Order, ORDER_STAT>("status"));
		statusC.setCellFactory(ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList(Order.ORDER_STAT.values())));
		statusC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order,ORDER_STAT>>() {
			@Override
			public void handle(CellEditEvent<Order, ORDER_STAT> arg0) {
				OrdersSQL.updateOrderStatus(
						arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()), arg0.getNewValue());
			}
		});
		
		table.getColumns().addAll(idC, customerC, priceC, addressC, statusC);
		table.setItems(FXCollections.observableList(orders));
		
		BorderPane scene = new BorderPane(table);
		
		return scene;
	}
}
