package myapp.view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import myapp.MainApp;

public class AddFeedCtrl {
    @FXML
    private TextField linkField;
    @FXML
    private TextField nameField;

    private Stage dialogStage;
    private MainApp mainApp;
    private boolean isError;
    
    public AddFeedCtrl() {
    	isError = false;
    }
    
    @FXML
    private void initialize() {
    }
    
    @FXML
    private void handleAdd() {
    	try {
    		URL test = new URL(linkField.getText());
	    	HttpPost request = new HttpPost(mainApp.getUrl() + "addFlux");
			request.setEntity(new StringEntity("{" + mainApp.getUserBody() + "\"link\":\"" + linkField.getText() + "\",\"title\":\"" + nameField.getText() + "\"}"));
			HttpResponse response;
			response = mainApp.getClient().execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				
			}
		    else {
		   		Alert alert = new Alert(AlertType.ERROR);
		   		alert.setTitle("Error Dialog");
		   		alert.setHeaderText("Probleme adding Channel to server");
		   		alert.showAndWait();
		   		isError = true;
		   	}
			request.releaseConnection();
		} catch (MalformedURLException e1) {
	   		Alert alert = new Alert(AlertType.ERROR);
	   		alert.setTitle("Error Dialog");
	   		alert.setHeaderText("Invalid Link");
	   		alert.showAndWait();
			// TODO Auto-generated catch block
			e1.printStackTrace();
			isError = true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isError = true;
		} catch (ClientProtocolException e) {
	   		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Can't connect to server");
    		alert.showAndWait();
			// TODO Auto-generated catch block
			e.printStackTrace();
			isError = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isError = true;
		}
        this.dialogStage.close();
    }
    
    @FXML
    private void handleCancel() {
    	this.linkField.clear();
    	this.nameField.clear();
        this.dialogStage.close();
    }
    
    public String getLink() {
    	return this.linkField.getText();
    }
    
    public String getName() {
    	return this.nameField.getText();
    }
    
    public boolean getError() {
    	return this.isError;
    }
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
