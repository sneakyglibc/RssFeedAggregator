package myapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myapp.model.Channel;
import myapp.model.User;
import myapp.view.ConnectDialogCtrl;
import myapp.view.MainViewCtrl;
import myapp.view.MenuBarCtrl;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private HttpClient client;
    private String url;
    private MainViewCtrl mainViewCtrl;
    private User user;

    private ObservableList<Channel> channels = FXCollections.observableArrayList();

    public MainApp() {
    	/*
        channels.add(new Channel("Google", "http://news.google.fr/news?cf=all&hl=fr&pz=1&ned=fr&output=rss"));
        channels.add(new Channel("Yahoo", "https://fr.news.yahoo.com/rss/technologies"));
        channels.add(new Channel("Youporn", "http://www.youporn.com/rss/"));
        
        for (int i = 0; i != 3; ++i)
        	channels.get(i).load();
        	*/
    	client = HttpClientBuilder.create().build();
    	url = new String("http://127.0.0.1:3000/");
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

    public String getUrl() {
    	return this.url;
    }

    public void setUser(String name, String pwd) {
    	this.user = new User(name, pwd);
    }
    
    public String getUserBody() {
    	return this.user.getBody();
    }
    
    public HttpClient getClient() {
    	return this.client;
    }
    
    public MainViewCtrl getMainViewCtrl() {
    	return this.mainViewCtrl;
    }
    
    public void addChannel(Channel chan) {
    	chan.load();
    	this.channels.add(chan);
    }
    
    public void removeChannel(Channel chan) {
    	this.channels.remove(chan);
    }
    
    private void getUserFeeds() {
    	try {
	    	HttpPost request = new HttpPost(getUrl() + "getListFlux");
			request.setEntity(new StringEntity("{" + getUserBody() + "}"));
			HttpResponse response;
			response = getClient().execute(request);
			
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
            ctrl.setDialogStage(primaryStage);
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
            ctrl.setMainApp(this);
            
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

            mainViewCtrl = loader.getController();
            mainViewCtrl.setFeedList(this.channels);
            mainViewCtrl.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
