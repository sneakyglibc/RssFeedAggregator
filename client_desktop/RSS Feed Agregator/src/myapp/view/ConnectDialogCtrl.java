package myapp.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        this.connected = true;
        this.dialogStage.close();
    }
    
    @FXML
    private void handleCancel() {
        this.connected = false;
        this.dialogStage.close();
    }
    
    public boolean isConnected() {
    	return this.connected;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
