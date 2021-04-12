package types;

import java.sql.Array;
import java.util.ArrayList;

import cartSQL.ItemsSQL;

public class Order {
	public enum ORDER_STAT{
		Delivered, Shipped, Processing, Canceled
	}
	
	private ArrayList<Item> items;
	private String id, customerID, price, address;
	private ORDER_STAT status;
	
	public Order(String id, String customerID, String price, String address, ORDER_STAT status, Array items) {
		this.id = id;
		this.customerID = customerID;
		this.price = price;
		this.address = address;
		this.items = new ArrayList<>();
		try {
			if(items != null) {
				String[] ids = (String[]) items.getArray();
				for(int i = 0; i < ids.length; i++) {
					this.items.add(new ItemsSQL().getItem(ids[i]));
				}
			}
			else {
				System.out.println("No items found in cart");
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);
		}
		this.status = status;
	}
	
	public Order(String customerID, String price, String address, ORDER_STAT status, Array items) {
		this(null, customerID, price, address, status, items);
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ORDER_STAT getStatus() {
		return status;
	}
	public void setStatus(ORDER_STAT status) {
		this.status = status;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public String getId() {
		return id;
	}
	public String getCustomerID() {
		return customerID;
	}
	public static ArrayList<String> getStatusOptions(){
		ArrayList<String> options = new ArrayList<String>();
		
		for(ORDER_STAT o : ORDER_STAT.values()) {
			options.add(o.toString());
		}
		
		return options;
	}
}
