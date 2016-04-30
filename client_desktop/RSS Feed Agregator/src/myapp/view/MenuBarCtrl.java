package myapp.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myapp.MainApp;
import myapp.model.Channel;

public class MenuBarCtrl {
	private MainApp mainApp;
	private Stage dialogStage;
	
	public MenuBarCtrl() {
		
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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
            
            // Show the dialog and wait until the user closes it
            subStage.showAndWait();
            if (ctrl.getLink() != "")
            	this.mainApp.addChannel(new Channel(ctrl.getName(), ctrl.getLink()));

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
