package cartSQL;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import types.Customer;
import types.Item;
import types.User;

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
		try {
			connect();
			FileInputStream stream = new FileInputStream(file);
			PreparedStatement ps = c.prepareStatement(
					String.format("INSERT INTO items VALUES(nextval('nextitemid'), '%s', '%s', '%s', CURRENT_DATE, ?);", item.getName(), item.getPrice(), item.getStock()));
			ps.setBinaryStream(1, stream, file.length());
			ps.executeUpdate();
			ps.close();
			stream.close();
			c.close();
		}catch(Exception e) {
			error(e);
		}
		return false;
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
