package com.KeltisT;

import com.KeltisT.Window.SizeOfMonitor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private final SizeOfMonitor sizeOfMonitor = new SizeOfMonitor();

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/start.fxml"));
        Scene scene = new Scene(root);


        stage.setMaximized(true);
        stage = sizeOfMonitor.setStageSize(stage);
        stage.setScene(scene);
        stage.show();
    }


}
