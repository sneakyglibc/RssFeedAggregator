package myapp.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Channel {
	
	private StringProperty	name;
	private StringProperty	link;
	private URL	feedUrl;
	private ObservableList<Item>	entries;
	private boolean	loaded;
	private boolean isSavedEntries;
	
	public Channel() {
		this(null, null);
	}
	
	public Channel(String name, String url) {
		this.loaded = false;
		this.entries = FXCollections.observableArrayList();
		this.name = new SimpleStringProperty(name);
		this.link = new SimpleStringProperty(url);
		this.isSavedEntries = false;
	}
	
	public boolean	load() {
		try {
    		this.feedUrl = new URL(this.link.get());
		
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(this.feedUrl));
        
			if (this.name.get().length() == 0) {
				this.name = new SimpleStringProperty(feed.getTitleEx().getValue());
			}
			for (int i = 0; i != feed.getEntries().size(); ++i) {
				SyndEntry entry = feed.getEntries().get(i);
				this.entries.add(new Item(entry.getTitle(), entry.getLink(), entry.getPublishedDate(), entry.getAuthor(), entry.getDescription().getValue()));
			}
			this.loaded = true;
		}
		catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	public boolean	load(String url) {
		try {
			this.feedUrl = new URL(url);
			this.link = new SimpleStringProperty(url);
		
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(this.feedUrl));
        
			for (int i = 0; i != feed.getEntries().size(); ++i) {
				SyndEntry entry = feed.getEntries().get(i);
				this.entries.add(new Item(entry.getTitle(), entry.getLink(), entry.getPublishedDate(), entry.getAuthor(), entry.getDescription().getValue()));
			}
			this.loaded = true;
		}
		catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	public void loadName() {
		if (this.name.get().length() == 0) {
			try {
				this.feedUrl = new URL(this.link.get());
				
				SyndFeedInput input = new SyndFeedInput();
				SyndFeed feed = input.build(new XmlReader(this.feedUrl));
		    
				this.name = new SimpleStringProperty(feed.getTitleEx().getValue());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FeedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addItem(Item i) {
		entries.add(i);
	}
	
	public void deleteItem(Item i) {
		entries.remove(i);
	}
	
	public String	getName() {
		return this.name.get();
	}
	
	public String	getLink() {
		return this.link.get();
	}
	
	public boolean isLoaded() {
		return this.loaded;
	}
	
	public void setAsSavedEntries(boolean b) {
		this.isSavedEntries = b;
	}
	
	public boolean isSavedEntries() {
		return this.isSavedEntries;
	}
	
	public ObservableList<Item>	getEntries() {
		return this.entries;
	}
}
