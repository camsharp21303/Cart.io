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
				System.out.println(items.get(items.size()-1).getName());
			}
			close();
		} catch (Exception e) {
			error(e);
		}
		return items;
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
