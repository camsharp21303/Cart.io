package graphicalUI;

import java.util.ArrayList;

import cartSQL.SQLcommands;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import types.Order;
import types.Order.ORDER_STAT;

public class OrdersUI {
	@SuppressWarnings("unchecked")
	public static Node getNode() {
		TableColumn<Order, String> idC, customerC, priceC, addressC;
		TableColumn<Order, ORDER_STAT> statusC;

		TableView<Order> table = new TableView<>();
		table.setEditable(true);

		ArrayList<Order> orders = SQLcommands.getAllOrders();

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
		statusC.setCellFactory(
				ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList(Order.ORDER_STAT.values())));
		statusC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, ORDER_STAT>>() {
			@Override
			public void handle(CellEditEvent<Order, ORDER_STAT> arg0) {
				arg0.getTableView().getItems().get(arg0.getTablePosition().getRow()).setStatus(arg0.getNewValue());
			}
		});

		TableColumn<Order, Void> viewC = new TableColumn<Order, Void>("View");
		Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {

			@Override
			public TableCell<Order, Void> call(TableColumn<Order, Void> arg0) {
				TableCell<Order, Void> cell = new TableCell<Order, Void>() {
					private Button del = new Button("View");
					{
						del.setOnAction(e -> {
							Order order = getTableView().getItems().get(getIndex());
							new OrderItemView(order);
							getTableView().setItems(FXCollections.observableList(SQLcommands.getAllOrders()));
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
		viewC.setCellFactory(cellFactory);

		table.getColumns().addAll(idC, customerC, priceC, addressC, statusC, viewC);
		table.setItems(FXCollections.observableList(orders));

		BorderPane scene = new BorderPane(table);

		return scene;
	}
}
