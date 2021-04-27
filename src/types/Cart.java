package types;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import cartSQL.ParentSQL;
import cartSQL.SQLcommands;

public class Cart extends ParentSQL {
	private String price;
	private ArrayList<Item> items;
	private Customer customer;

	public Cart(Customer customer, String price, Array items) {
		this.customer = customer;
		this.price = price;
		this.items = new ArrayList<>();
		try {
			if (items != null) {
				String[] ids = (String[]) items.getArray();
				for (int i = 0; i < ids.length; i++) {
					this.items.add(SQLcommands.getItem(ids[i]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);
		}
	}

	public Cart(Customer customer) {
		InsertQuery(String.format("INSERT INTO carts VALUES('%s', 0.00, NULL)", customer.getNumber().strip()));
		this.customer = customer;
		this.price = "0.00";
		this.items = new ArrayList<>();
	}

	public void addItem(Item item) {
		items.add(item);
		setItems(getItemIDs());
	}

	public void removeItem(Item item) {
		int size = items.size();
		for (int i = 0; i < size; i++) {
			if (items.get(i).getNumber().equals(item.getNumber())) {
				items.remove(i);
				size--;
				i--;
			}
		}
		setItems(getItemIDs());
	}

	public void removeItem(Item item, int num) {
		int size = items.size();
		for (int i = 0; i < size; i++) {
			if (items.get(i).getNumber().equals(item.getNumber()) && num > 0) {
				items.remove(i);
				size--;
				i--;
				num--;
			}
		}
		setItems(getItemIDs());
	}

	private void setItems(String[] items) {
		try {
			connect();
			PreparedStatement ps = c.prepareStatement(
					String.format("UPDATE carts SET items = ? WHERE customerid='%s';", customer.getNumber()));
			ps.setArray(1, c.createArrayOf("text", items));
			ps.executeUpdate();
			ps.close();
			c.close();
		} catch (Exception e) {
			error(e);
		}
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public String[] getItemIDs() {
		String[] ids = new String[items.size()];

		for (int i = 0; i < items.size(); i++) {
			ids[i] = items.get(i).getNumber();
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
	
	public void clearItems() {
		items.clear();
		setItems(new String[0]);
	}

	@Override
	protected String getDatabase() {
		// TODO Auto-generated method stub
		return "collections";
	}
}
