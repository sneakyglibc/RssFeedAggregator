package myapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myapp.model.Channel;
import myapp.view.ConnectDialogCtrl;
import myapp.view.MainViewCtrl;
import myapp.view.MenuBarCtrl;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Channel> channels = FXCollections.observableArrayList();

    public MainApp() {
        channels.add(new Channel("Google", "http://news.google.fr/news?cf=all&hl=fr&pz=1&ned=fr&output=rss"));
        channels.add(new Channel("Yahoo", "https://fr.news.yahoo.com/rss/technologies"));
        channels.add(new Channel("Youporn", "http://www.youporn.com/rss/"));
        
        for (int i = 0; i != 3; ++i)
        	channels.get(i).load();
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        boolean connected = showConnectDialog();
        if (connected == true)
        {
        	this.primaryStage.setTitle("RSS Feed Agregator");
        	initRootLayout();
        	showMainView();
        }
        else
        	this.primaryStage.close();
    }

    public void addChannel(Channel chan) {
    	chan.load();
    	this.channels.add(chan);
    }
    
    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            MenuBarCtrl ctrl = loader.getController();
            ctrl.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private boolean	showConnectDialog() {
        try {
            // Load Connect View
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ConnectDialog.fxml"));
            AnchorPane connectDialog = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Connect");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(connectDialog);
            dialogStage.setScene(scene);

            // Set Controller
            ConnectDialogCtrl ctrl = loader.getController();
            ctrl.setDialogStage(dialogStage);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
            return ctrl.isConnected();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private void showMainView() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainView.fxml"));
            AnchorPane mainView = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(mainView);

            MainViewCtrl ctrl = loader.getController();
            ctrl.setFeedList(this.channels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
