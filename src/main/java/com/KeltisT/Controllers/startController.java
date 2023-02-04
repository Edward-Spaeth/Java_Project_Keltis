package com.KeltisT.Controllers;

import com.KeltisT.Window.SizeOfMonitor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class startController {
    private final SizeOfMonitor sizeOfMonitor = new SizeOfMonitor();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private final double HEIGHT = sizeOfMonitor.getSizeOfMonitor()[0];
    private final double WIDTH = sizeOfMonitor.getSizeOfMonitor()[1];
    private soundController Sounds = new soundController();

    // Start Button
    public void switchToChoosePlayerNumberScene(ActionEvent event) throws IOException {
        Sounds.clickSound();
        root = FXMLLoader.load(getClass().getResource("/Fxml/choosePlayerT.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    // Settings Button

    public void switchToSettingsScene(ActionEvent event) throws IOException{
        Sounds.clickSound();
        root = FXMLLoader.load(getClass().getResource("/Fxml/settings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    // Rules Button

    public void switchToRulesScene(ActionEvent event) throws IOException{
        Sounds.clickSound();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Fxml/rules.fxml"));
        Path filename  = Path.of("src/main/resources/Rules.txt");
        String rulesText = Files.readString(filename);
        Text text = new Text(rulesText);
        text.setFill(Color.GOLD);
        text.setStrokeWidth(0.3);
        text.setFont(Font.font("Papyrus", 25));
        text.setTextAlignment(TextAlignment.LEFT);
        StackPane stack = new StackPane(text);
        stack.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(stack, 250.0);
        AnchorPane.setLeftAnchor(stack, 250.0);
        pane.getChildren().add(stack);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    // Exit Button
    @FXML
    void Exit() {
        Sounds.clickSound();
        Platform.exit();
    }

}
