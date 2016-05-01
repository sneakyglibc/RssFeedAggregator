package myapp.view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myapp.MainApp;
import myapp.model.Channel;
import myapp.model.Item;

public class MenuBarCtrl {
	private MainApp mainApp;
	private Stage dialogStage;
	
	public MenuBarCtrl() {
		
	}
    
    @FXML
	public void addFeed() {
        try {
            // Load Connect View
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AddFeedView.fxml"));
            AnchorPane addFeedDialog = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage subStage = new Stage();
            subStage.setTitle("Add RSS Feed");
            subStage.initOwner(this.dialogStage);
            subStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(addFeedDialog);
            subStage.setScene(scene);

            // Set Controller
            AddFeedCtrl ctrl = loader.getController();
            ctrl.setDialogStage(subStage);
            ctrl.setMainApp(mainApp);
            
            // Show the dialog and wait until the user closes it
            subStage.showAndWait();
            if (ctrl.getError() == false)
            	this.mainApp.addChannel(new Channel(ctrl.getName(), ctrl.getLink()));

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    @FXML
    public void removeFeed() {
    	Channel f = mainApp.getMainViewCtrl().getSelectedFeed();
    	if (f != null && f.isSavedEntries() == false) {
        	try {
    	    	HttpPost request = new HttpPost(mainApp.getUrl() + "remFlux");
    			request.setEntity(new StringEntity("{" + mainApp.getUserBody() + "\"link\":\"" + f.getLink() + "\"}"));
    			HttpResponse response;
    			response = mainApp.getClient().execute(request);
    			if (response.getStatusLine().getStatusCode() == 200) {
    				mainApp.removeChannel(f);
    			}
    		    else {
    		   		Alert alert = new Alert(AlertType.ERROR);
    		   		alert.setTitle("Error Dialog");
    		   		alert.setHeaderText("Probleme removing Channel from server");
    		   		alert.showAndWait();
    		   	}
    			request.releaseConnection();
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ClientProtocolException e) {
    	   		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error Dialog");
        		alert.setHeaderText("Can't connect to server");
        		alert.showAndWait();
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("No Feed selected");
    		alert.showAndWait();
    	}
    }
    
    @FXML
    public void addItem() {
    	Channel c = mainApp.getMainViewCtrl().getSelectedFeed();
    	if (c.isSavedEntries() == true)
    		return;
    	Item i = mainApp.getMainViewCtrl().getSelectedItem();
    	if (i != null) {
        	try {
    	    	HttpPost request = new HttpPost(mainApp.getUrl() + "addItem");
    			request.setEntity(new StringEntity("{" + mainApp.getUserBody() + "\"link\":\"" + i.getLink() + "\",\"title\":\"" + i.getName() + "\"}"));
    			HttpResponse response;
    			response = mainApp.getClient().execute(request);
    			if (response.getStatusLine().getStatusCode() == 200) {
    				mainApp.addItem(i);
    			}
    		    else {
    		   		Alert alert = new Alert(AlertType.ERROR);
    		   		alert.setTitle("Error Dialog");
    		   		alert.setHeaderText("Probleme adding item to server");
    		   		alert.showAndWait();
    		   	}
    			request.releaseConnection();
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ClientProtocolException e) {
    	   		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error Dialog");
        		alert.setHeaderText("Can't connect to server");
        		alert.showAndWait();
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("No Feed selected");
    		alert.showAndWait();
    	}
    }
    
    @FXML
    public void removeItem() {
    	Channel c = mainApp.getMainViewCtrl().getSelectedFeed();
    	if (c.isSavedEntries() == false)
    		return;
    	Item i = mainApp.getMainViewCtrl().getSelectedItem();
    	if (i != null) {
        	try {
    	    	HttpPost request = new HttpPost(mainApp.getUrl() + "remItem");
    			request.setEntity(new StringEntity("{" + mainApp.getUserBody() + "\"link\":\"" + i.getLink() + "\"}"));
    			HttpResponse response;
    			response = mainApp.getClient().execute(request);
    			if (response.getStatusLine().getStatusCode() == 200) {
    				mainApp.removeItem(i);
    			}
    		    else {
    		   		Alert alert = new Alert(AlertType.ERROR);
    		   		alert.setTitle("Error Dialog");
    		   		alert.setHeaderText("Probleme removing item from server");
    		   		alert.showAndWait();
    		   	}
    			request.releaseConnection();
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ClientProtocolException e) {
    	   		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error Dialog");
        		alert.setHeaderText("Can't connect to server");
        		alert.showAndWait();
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("No Feed selected");
    		alert.showAndWait();
    	}
    }
    
    @FXML
    public void close() {
    	this.dialogStage.close();
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
}
