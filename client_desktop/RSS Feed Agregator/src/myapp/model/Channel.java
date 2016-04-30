package myapp.model;

import java.net.URL;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
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
	private ObservableList<SyndEntry>	entries;
	
	public Channel() {
		this(null, null);
	}
	
	public Channel(String name, String url) {
		this.name = new SimpleStringProperty(name);
		this.link = new SimpleStringProperty(url);
	}
	
	public boolean	load() {
		try {
    		this.feedUrl = new URL(this.link.get());
		
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(this.feedUrl));
        
			if (this.name.get().length() == 0) {
				this.name = new SimpleStringProperty(feed.getTitleEx().getValue());
			}
			this.entries = FXCollections.observableArrayList(feed.getEntries());
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
        
			this.entries = FXCollections.observableArrayList(feed.getEntries());
		}
		catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	public String	getName() {
		return this.name.get();
	}
	
	public String	getLink() {
		return this.link.get();
	}
	
	public ObservableList<SyndEntry>	getEntries() {
		return this.entries;
	}
}
