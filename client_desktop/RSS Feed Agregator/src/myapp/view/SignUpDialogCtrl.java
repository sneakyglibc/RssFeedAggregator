package myapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpDialogCtrl {
	
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
	
    private boolean connected = false;
    private Stage dialogStage;
    
    public SignUpDialogCtrl() {
    }
    
    @FXML
    private void initialize() {
    }
    
    @FXML
    private void handleSignUp() {
    	this.connected = true;
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
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
