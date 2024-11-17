package com.example.librarysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloController {
    @FXML
    private Button closeBtn;

    @FXML
    private TextField login;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField password;

    @FXML
    private Button regBtn;

    @FXML
    private Button guestBtn;

    @FXML
    private AnchorPane main_form;

    private Connection conn;
    private PreparedStatement prepare;
    private ResultSet rs;

    private double x = 0;
    private double y = 0;

public void LogUser(){
    String sql = "SELECT fio_user FROM users WHERE login =? and password =?";

    conn = db.connectDb();

    try {
        prepare = conn.prepareStatement(sql);
        prepare.setString(1, login.getText());
        prepare.setString(2, password.getText());

        rs = prepare.executeQuery();

        Alert alert;
            if(login.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Заполните данные для входа");
                alert.showAndWait();
        }else{
                if(rs.next()){
                    getData.username = rs.getString("fio_user");

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Вы успешно вошли в аккаунт:)");
                    alert.showAndWait();

                    loginBtn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    scene.setOnMousePressed((MouseEvent event) ->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    scene.setOnMouseDragged((MouseEvent event) ->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                        stage.setOpacity(.8);
                    });

                    scene.setOnMouseReleased((MouseEvent event) ->{
                        stage.setOpacity(1);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Неверный логин/пароль");
                    alert.showAndWait();
                }
            }

    } catch (Exception e){e.printStackTrace();}
}

    @FXML
    private void RegUser(ActionEvent event) {
        try {

            regBtn.getScene().getWindow().hide();
            // Load the adduser.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("adduser.fxml"));

            // Create a new stage
            Stage stage = new Stage();

            // Set the scene of the stage
            Scene scene = new Scene(root);

            scene.setOnMousePressed((MouseEvent event1) ->{
                x = event1.getSceneX();
                y = event1.getSceneY();
            });

            scene.setOnMouseDragged((MouseEvent event1) ->{
                stage.setX(event1.getScreenX() - x);
                stage.setY(event1.getScreenY() - y);
                stage.setOpacity(.8);
            });

            scene.setOnMouseReleased((MouseEvent event1) ->{
                stage.setOpacity(1);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Guest(ActionEvent event) {
        try {

            guestBtn.getScene().getWindow().hide();
            // Load the adduser.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("guest.fxml"));

            // Create a new stage
            Stage stage = new Stage();

            // Set the scene of the stage
            Scene scene = new Scene(root);

            scene.setOnMousePressed((MouseEvent event1) ->{
                x = event1.getSceneX();
                y = event1.getSceneY();
            });

            scene.setOnMouseDragged((MouseEvent event1) ->{
                stage.setX(event1.getScreenX() - x);
                stage.setY(event1.getScreenY() - y);
                stage.setOpacity(.8);
            });

            scene.setOnMouseReleased((MouseEvent event1) ->{
                stage.setOpacity(1);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }


}