package myapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
	
	private StringProperty	name;
	private StringProperty	link;
	
	public Item() {
		this(null, null);
	}
	
	public Item(String name, String link) {
		this.name = new SimpleStringProperty(name);
		this.link = new SimpleStringProperty(link);
	}
	
	public String	getName() {
		return this.name.get();
	}
	
	public String	getLink() {
		return this.link.get();
	}
}