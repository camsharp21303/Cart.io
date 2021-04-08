package types;

import java.sql.Array;
import java.util.ArrayList;

import cartSQL.ItemsSQL;
import cartSQL.OrdersSQL;

public class Cart {
	private String price;
	private ArrayList<Item> items;
	private Customer customer;
	
	public Cart(Customer customer, String price, Array items) {
		this.customer = customer;
		this.price = price;
		this.items = new ArrayList<>();
		try {
			if(items != null) {
				String[] ids = (String[]) items.getArray();
				for(int i = 0; i < ids.length; i++) {
					this.items.add(new ItemsSQL().getItem(ids[i]));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);
		}
		
	}
	
	public void addItem(Item item) {
		items.add(item);
		new OrdersSQL().setItems(customer, getItemIDs());
	}
	
	public void removeItem(Item item) {
		items.remove(item);
		new OrdersSQL().setItems(customer, getItemIDs());
	}

	public ArrayList<Item> getItems() {
		return items;
	}
	
	public String[] getItemIDs() {
		String[] ids = new String[items.size()];
		
		for(int i = 0; i < items.size(); i++) {
			ids[i] =  items.get(i).getNumber();
		}
		
		return ids;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public String getCustomerID() {
		return customer.getNumber();
	}

	public String getPrice() {
		return price;
	}
}
