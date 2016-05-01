package myapp.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import myapp.MainApp;
import myapp.model.Channel;

import com.rometools.rome.feed.synd.SyndEntry;

public class MainViewCtrl {
    
    @FXML
    private ListView<Channel> feedList;
    @FXML
    private ListView<SyndEntry> itemList;
    @FXML
    private WebView webView;
    
    private MainApp mainApp;
    
    public MainViewCtrl() {	
    }
    
    @FXML
    private void initialize() {
    	this.feedList.getSelectionModel().selectedItemProperty().addListener(
    			(observable, oldValue, newValue) -> this.setItemList(newValue));
    	this.itemList.getSelectionModel().selectedItemProperty().addListener(
    			(observable, oldValue, newValue) -> this.loadItem(newValue));
    }
    
    private void _setFeedList(ObservableList<Channel> feedList) {
    	this.feedList.setItems(feedList);
    	this.feedList.setCellFactory(new Callback<ListView<Channel>, ListCell<Channel>>() {
    		@Override
    		public ListCell<Channel> call(ListView<Channel> p) {
    			ListCell<Channel> cell = new ListCell<Channel>() {
    				@Override
    				protected void updateItem(Channel f, boolean b) {
    					super.updateItem(f, b);
    					if (f != null)
    						setText(f.getName());
    				}
    			};
    			return cell;
    		}
    	});
    }

    private void setItemList(Channel channel) {
    	try {
            this.itemList.setItems(channel.getEntries());
            this.itemList.setCellFactory(new Callback<ListView<SyndEntry>, ListCell<SyndEntry>>() {
            	@Override
            	public ListCell<SyndEntry> call(ListView<SyndEntry> p) {
            		ListCell<SyndEntry> cell = new ListCell<SyndEntry>() {
            			@Override
            			protected void updateItem(SyndEntry i, boolean b) {
            				super.updateItem(i, b);
            				if (i != null)
            					setText(i.getTitle());
            				else
            					setText("");
            			}
            		};
            		return cell;
            	}
            });
            this.webView.getEngine().loadContent("");
    	}
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void loadItem(SyndEntry newValue) {
    	if (newValue != null)
    	{
    		String content = "";
    		
    		System.out.println(newValue);
    		content += "<h3><a href=" + newValue.getLink() + ">" + newValue.getTitle() + "</a></h3>";
    		content += "<p>" + newValue.getPublishedDate() + ", ";
    		content += newValue.getAuthor() + "</p>";
    		content += "<p>" + newValue.getDescription().getValue() + "</p>";
    		this.webView.getEngine().loadContent(content);
    	}
    }
    
    public Channel getSelectedFeed() {
    	return this.feedList.getSelectionModel().getSelectedItem();
    }
    
    public void removeFeed(Channel c) {
    	this.feedList.getItems().remove(c);
    }
    
    public void clearItemList() {
    	this.itemList.getItems().clear();
    	this.webView.getEngine().loadContent("");
    }
    
    public void setFeedList(ObservableList<Channel> feedList) {
    	this._setFeedList(feedList);
    }
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
}
