package cartSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import types.Cart;
import types.Customer;
import types.Order;

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
				
			}
			close();
		} catch (Exception e) {
			error(e);
		}
		return orders;
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
