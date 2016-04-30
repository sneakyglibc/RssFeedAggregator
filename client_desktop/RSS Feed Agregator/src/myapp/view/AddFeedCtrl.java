package myapp.view;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFeedCtrl {
    @FXML
    private TextField linkField;
    @FXML
    private TextField nameField;

    private Stage dialogStage;
    
    public AddFeedCtrl() {
    }
    
    @FXML
    private void initialize() {
    }
    
    @FXML
    private void handleAdd() {
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
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
