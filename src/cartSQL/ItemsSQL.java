package cartSQL;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;

import types.Item;

public class ItemsSQL extends ParentSQL {

	@Override
	protected String getDataBase() {
		return "inventory";
	}

	public ArrayList<Item> getAllItems() {
		ArrayList<Item> items = new ArrayList<>();
		try {
			ResultSet results = readQuery("SELECT * FROM items");
			
			while(results.next()) {
				items.add(new Item(
						results.getString("id"),
						results.getString("name"),
						results.getString("lastSold"),
						results.getString("price"),
						results.getInt("stock"),
						results.getBytes("image")));
			}
			close();
		} catch (Exception e) {
			error(e);
		}
		return items;
	}
	
	public Item getItem(String itemID) {
		try {
			ResultSet results = readQuery("SELECT * FROM items WHERE id='" + itemID +"';");
			
			while(results.next()) {
				return new Item(
						results.getString("id"),
						results.getString("name"),
						results.getString("lastSold"),
						results.getString("price"),
						results.getInt("stock"),
						results.getBytes("image"));
			}
			close();
		}catch (Exception e) {
			error(e);
		}
		
		return null;
	}
	
	public boolean editItem(String key, String id, String value) {
		try {
			connect();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.executeUpdate(String.format("UPDATE items SET %s='%s' WHERE id='%s';", key, value, id));
			c.commit();
			return true;
		}catch(Exception e) {
			error(e);
		}
		return false;
	}
	
	public boolean removeItem(Item item) {
		return false;
	}
	
	public boolean insertItem(Item item, File file) {
		String sql = String.format("INSERT INTO items VALUES(nextval('nextitemid'), '%s', '%s', '%s', CURRENT_DATE, ?);", item.getName(), item.getPrice(), item.getStock());
		return InsertQueryImage(sql, file);
	}

	//Update data
	/*public void update() {
		try {
			connect();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE items SET price = 5.00 WHERE id='1';";
			stmt.executeUpdate(sql);
			c.commit();
		}catch(Exception e) {
			error(e);
		}
	}*/
}
