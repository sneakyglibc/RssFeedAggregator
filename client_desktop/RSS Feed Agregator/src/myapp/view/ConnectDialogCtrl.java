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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myapp.MainApp;

public class ConnectDialogCtrl {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
	
    private boolean connected = false;
    private Stage dialogStage;
    private MainApp mainApp;
    
    public ConnectDialogCtrl() {
    }
    
    @FXML
    private void initialize() {
    }
    
    @FXML
    private void handleSignUp() {
        try {
            // Load Connect View
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SignUpDialog.fxml"));
            AnchorPane signUpDialog = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage subStage = new Stage();
            subStage.setTitle("Sign Up");
            subStage.initOwner(this.dialogStage);
            subStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(signUpDialog);
            subStage.setScene(scene);

            // Set Controller
            SignUpDialogCtrl ctrl = loader.getController();
            ctrl.setDialogStage(subStage);
            ctrl.setMainApp(mainApp);
            
            // Show the dialog and wait until the user closes it
            subStage.showAndWait();
            this.connected = ctrl.isConnected();
            if (this.connected)
            	this.dialogStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleConnect() {
    	try {
	    	HttpPost request = new HttpPost(mainApp.getUrl() + "login");
			request.setEntity(new StringEntity("{\"email\":\"" + loginField.getText() + "\",\"password\":\"" + passwordField.getText() + "\"}"));
			HttpResponse response;
			response = mainApp.getClient().execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				this.connected = true;
				this.mainApp.setUser(loginField.getText(), passwordField.getText());
		        this.dialogStage.close();
			}
		   	else {
		   		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error Dialog");
	    		alert.setHeaderText("Invalid Login/Password");
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
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    private void handleCancel() {
        this.connected = false;
        this.dialogStage.close();
    }
    
    public boolean isConnected() {
    	return this.connected;
    }
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
