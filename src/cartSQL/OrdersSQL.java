package cartSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import types.Cart;
import types.Customer;
import types.Item;
import types.Order;
import types.Order.ORDER_STAT;

public class OrdersSQL extends ParentSQL {

	@Override
	protected String getDataBase() {
		return "collections";
	}

	public ArrayList<Order> getAllOrders() {
		ArrayList<Order> orders = new ArrayList<>();
		try {
			ResultSet results = readQuery("SELECT * FROM orders");
			
			while(results.next()) {
				orders.add(new Order(
						results.getString("id"),
						results.getString("customerID"),
						results.getString("total"),
						results.getString("address"),
						ORDER_STAT.valueOf(results.getString("status")),
						results.getArray("items")));
			}
			close();
		} catch (Exception e) {
			error(e);
		}
		return orders;
	}
	
	public void addOrder(Customer customer, ArrayList<Item> items, String address) {
		try {
			double total = 0;
			for(Item i : items) {
				total += i.getPrice();
			}
			
			connect();
			PreparedStatement ps = c.prepareStatement(String.format("INSERT INTO orders VALUES(nextval('orderid'), '%s', '%s', ?, %s, '%s');", 
					customer.getNumber(), Order.ORDER_STAT.Processing.toString(), total, address));
			ps.setArray(1, c.createArrayOf("char", items.toArray()));
			ps.executeUpdate();
			ps.close();
			c.close();
		}catch(Exception e) {
			error(e);
		}
	}
	
	public void setItems(Customer customer, String[] items) {
		try {
			connect();
			PreparedStatement ps = c.prepareStatement(String.format("UPDATE carts SET items = ? WHERE customerid='%s';", customer.getNumber()));
			ps.setArray(1, c.createArrayOf("char", items));
			ps.executeUpdate();
			ps.close();
			c.close();
		}catch(Exception e) {
			error(e);
		}
	}
	
	public Cart getCart(Customer customer){
		
		try {
			ResultSet results = readQuery("SELECT * FROM carts WHERE customerid='" + customer.getNumber() + "';");
			
			while(results.next()) {
				Cart cart = new Cart(customer, results.getString("total"), results.getArray("items"));
				return cart;
			}
			
		}catch(Exception e) {
			error(e);
		}
		
		return null;
	}
	
	public boolean createCart(Customer customer) {
		String query = "";
		
		query = String.format(
				"INSERT INTO carts VALUES('%s', '0.00', NULL);", customer.getNumber());
		
		return InsertQuery(query);
	}
}