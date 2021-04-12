package cartSQL;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import types.Item;

public class ItemsSQL extends ParentSQL {

	public static ObservableList<Item> getAllItems() {
		ArrayList<Item> items = new ArrayList<>();
		try {
			ResultSet results = readQuery("SELECT * FROM items", "inventory");
			
			while(results.next()) {
				items.add(new Item(
						results.getString("id"),
						results.getString("name"),
						results.getDouble("price"),
						results.getInt("stock"),
						results.getBytes("image")));
			}
			close();
		} catch (Exception e) {
			error(e);
		}
		return FXCollections.observableList(items);
	}
	
	public static Item getItem(String itemID) {
		try {
			ResultSet results = readQuery("SELECT * FROM items WHERE id='" + itemID +"';", "inventory");
			
			while(results.next()) {
				return new Item(
						results.getString("id"),
						results.getString("name"),
						results.getDouble("price"),
						results.getInt("stock"),
						results.getBytes("image"));
			}
			close();
		}catch (Exception e) {
			error(e);
		}
		
		return null;
	}
	
	public static boolean editItem(String key, String id, String value) {
		return InsertQuery(String.format("UPDATE items SET %s='%s' WHERE id='%s';", key, value, id), "inventory");
	}
	
	public static boolean removeItem(Item item) {
		return InsertQuery("DELETE FROM items WHERE id='" + item.getNumber() + "';", "inventory");
	}
	
	public static boolean insertItem(Item item, File file) {
		String sql = String.format("INSERT INTO items VALUES(nextval('nextitemid'), '%s', '%s', ?, '%s');", item.getName(), item.getPrice(), item.getStock());
		return InsertQueryImage(sql, file, "inventory");
	}
}
