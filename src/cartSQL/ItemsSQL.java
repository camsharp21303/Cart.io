package cartSQL;

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
						results.getInt("stock")));
				System.out.println(items.get(items.size()-1).getName());
			}
			close();
		} catch (Exception e) {
			error(e);
		}
		return items;
	}
	
	public boolean insertItem(Item item){
		String insertQuery = String.format("INSERT INTO items VALUES(nextval('nextitemid'), '%s', '%s', '%s', CURRENT_DATE);", item.getName(), item.getPrice(), item.getStock());
		return InsertQuery(insertQuery);
	}
	
	public boolean insertUser(User user, String password) {
		String query = String.format(
				"INSERT INTO customers VALUES(nextval('customernumberseq'), '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
				user.getFname(), user.getLname(), user.getUsername(), user.getPhone(), user.getEmail(),
				password, ((Customer)user).getAddress());
		return InsertQuery(query);
	}
}
