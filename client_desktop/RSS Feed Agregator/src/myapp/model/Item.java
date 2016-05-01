package myapp.model;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
	
	private StringProperty	name;
	private StringProperty	link;
	private StringProperty	date;
	private StringProperty	author;
	private StringProperty	description;
	private boolean linkOnly;
	
	public Item() {
		this(null, null, null, null, null);
	}
	
	public Item(String name, String link, Date date, String author, String description) {
		this.name = new SimpleStringProperty(name);
		this.link = new SimpleStringProperty(link);
		if (date != null && author != null && description != null) {
			this.date = new SimpleStringProperty(date.toString());
			this.author = new SimpleStringProperty(author);
			this.description = new SimpleStringProperty(description);
		}
		this.linkOnly = false;
	}
	
	public String	getName() {
		return this.name.get();
	}
	
	public String	getLink() {
		return this.link.get();
	}
	
	public String	getDate() {
		return this.date.get();
	}
	
	public String	getAuthor() {
		return this.author.get();
	}
	
	public String	getDescription() {
		return this.description.get();
	}
	
	public void setAsLinkOnly(boolean b) {
		this.linkOnly = b;
	}
	
	public boolean isLinkOnly() {
		return this.linkOnly;
	}
}