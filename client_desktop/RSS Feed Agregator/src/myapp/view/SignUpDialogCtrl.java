package myapp.view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import myapp.MainApp;

public class SignUpDialogCtrl {
	
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
	
    private boolean connected = false;
    private Stage dialogStage;
    private MainApp mainApp;
    
    public SignUpDialogCtrl() {
    }
    
    @FXML
    private void initialize() {
    }
    
    @FXML
    private void handleSignUp() {
    	try {
    		if (this.checkInput()) {
	    		HttpPost request = new HttpPost(mainApp.getUrl() + "signup");
				request.setEntity(new StringEntity("{\"email\":\"" + loginField.getText() + "\",\"password\":\"" + passwordField.getText() + "\"}"));
				HttpResponse response;
				response = mainApp.getClient().execute(request);
				if (response.getStatusLine().getStatusCode() == 200) {
					this.connected = true;
				}
		    	else {
		    		Alert alert = new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Dialog");
		    		alert.setHeaderText("Can't Sign Up");
		    		alert.showAndWait();
		    	}
				request.releaseConnection();
    		}
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
    	dialogStage.close();
    }
    
    @FXML
    private void handleCancel() {
        this.connected = false;
        dialogStage.close();
    }
    
    public boolean isConnected() {
    	return this.connected;
    }
    
    private boolean checkInput() {
    	if (loginField.getText().length() <= 0) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Invalid email");
    		alert.showAndWait();
    		return false;
    	}
    	if (passwordField.getText().length() < 6) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Password must at least 6 characters long");
    		alert.showAndWait();
    		return false;
    	}
    	if (passwordField.getText().equals(confirmPasswordField.getText()))
    		return true;
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Passwords don't match");
    		alert.showAndWait();
    	}
    	return false;
    }
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
