package cartSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import types.Cart;
import types.Customer;
import types.Employee;
import types.Employee.POSITION;
import types.Item;
import types.Order;
import types.Order.ORDER_STAT;
import types.User;

public class SQLcommands {
	private static Connection c;
	private static Statement stmt;

	public static User login(String loginID, String password) {
		String column = "username";
		if (loginID.contains("@") && loginID.contains("."))
			column = "email";
		if (loginID.matches("[0-9]+") && loginID.length() == 10)
			column = "phone";

		ResultSet customerResult = readQuery(
				"SELECT * FROM customers WHERE " + column + "='" + loginID + "' AND password='" + password + "';",
				"users");
		ResultSet employeeResult = readQuery(
				"SELECT * FROM employees WHERE " + column + "='" + loginID + "' AND password='" + password + "';",
				"users");

		try {
			if (customerResult.next()) {
				return new Customer(customerResult.getString("id"), customerResult.getString("fname"),
						customerResult.getString("lname"), customerResult.getString("username"),
						customerResult.getString("phone"), customerResult.getString("email"),
						customerResult.getString("address"), customerResult.getBytes("image"));
			} else if (employeeResult.next()) {
				Employee.POSITION position = POSITION.STANDARD;
				String level = employeeResult.getString("level");
				if (level.equals("board"))
					position = POSITION.BOARD;
				else if (level.equals("manager"))
					position = POSITION.MANAGER;
				return new Employee(employeeResult.getString("id"), employeeResult.getString("fname"),
						employeeResult.getString("lname"), employeeResult.getString("username"),
						employeeResult.getString("phone"), employeeResult.getString("email"), position,
						employeeResult.getBytes("image"));
			}
		} catch (SQLException e) {
			error(e);
		}

		return null;
	}

	public static boolean createCart(Customer customer) {
		String query = "";

		query = String.format("INSERT INTO carts VALUES('%s', '0.00', NULL);", customer.getNumber());

		return InsertQuery(query, "collections");
	}

	public static Cart getCart(Customer customer) {
		try {
			ResultSet results = readQuery("SELECT * FROM carts WHERE customerid='" + customer.getNumber() + "';",
					"collections");

			while (results.next()) {
				Cart cart = new Cart(customer, results.getString("total"), results.getArray("items"));
				return cart;
			}

		} catch (Exception e) {
			error(e);
		}

		return null;
	}

	public static ArrayList<Order> getAllOrders() {
		ArrayList<Order> orders = new ArrayList<>();
		try {
			ResultSet results = readQuery("SELECT * FROM orders", "collections");

			while (results.next()) {
				orders.add(
						new Order(results.getString("id"), results.getString("customerID"), results.getString("total"),
								results.getString("address"), ORDER_STAT.valueOf(results.getString("status")),
								(String[]) results.getArray("items").getArray()));
			}
			close();
		} catch (Exception e) {
			error(e);
		}
		return orders;
	}

	public static ObservableList<Item> getAllItems() {
		ArrayList<Item> items = new ArrayList<>();
		try {
			ResultSet results = readQuery("SELECT * from items", "inventory");

			while (results.next()) {
				items.add(new Item(results.getString("id"), results.getString("name"), results.getDouble("price"),
						results.getInt("stock"), results.getBytes("image")));
			}
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);
		}
		return FXCollections.observableList(items);
	}

	public static Item getItem(String itemID) {
		try {
			ResultSet results = readQuery("SELECT * FROM items WHERE id='" + itemID + "';", "inventory");

			while (results.next()) {
				return new Item(results.getString("id"), results.getString("name"), results.getDouble("price"),
						results.getInt("stock"), results.getBytes("image"));
			}
			close();
		} catch (Exception e) {
			error(e);
		}

		return null;
	}

	private static ResultSet readQuery(String sql, String database) {
		connect(database);
		try {
			stmt = c.createStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			error(e);
			return null;
		}
	}

	protected static boolean InsertQuery(String sql, String database) {
		try {
			connect(database);
			c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			error(e);
			return false;
		}
		return true;
	}

	private static void connect(String database) {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, "postgres", "darth129");
		} catch (Exception e) {
			error(e);
		}
	}

	private static void close() {
		try {
			stmt.close();
			c.close();
		} catch (SQLException e) {
			error(e);
		}
	}

	private static void error(Exception e) {
		e.printStackTrace();
		System.err.println(e.getClass().getName() + ":" + e.getMessage());
		System.exit(0);
	}
}
