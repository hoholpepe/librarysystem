package com.example.librarysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Adduser {
    @FXML
    private ComboBox user_role;
    @FXML
    private Button registrate_Btn;

    @FXML
    private TextField user_email;

    @FXML
    private TextField user_key;

    @FXML
    private TextField user_login;

    @FXML
    private TextField user_name;

    @FXML
    private TextField user_password;

    @FXML
    private TextField user_phone;

    private double x = 0;
    private double y = 0;

    @FXML
    private void registerUser(ActionEvent event) {
        String key = user_key.getText();
        if (!"603147".equals(key)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Неверный ключ");
            alert.showAndWait();
            return;
        }

        try {
            if (user_email.getText().isEmpty() ||
                    user_name.getText().isEmpty() ||
                    user_role.getSelectionModel().getSelectedItem() == null ||
                    user_login.getText().isEmpty() ||
                    user_phone.getText().isEmpty() ||
                    user_key.getText().isEmpty() ||
                    user_password.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Заполните все поля");
                alert.showAndWait();
            } else {
                Connection connect = db.connectDb();

                String sqlMaxId = "SELECT MAX(id_user) FROM users";
                PreparedStatement pstmtMaxId = connect.prepareStatement(sqlMaxId);
                ResultSet rs = pstmtMaxId.executeQuery();

                int id_user = 1;
                if (rs.next()) {
                    id_user = rs.getInt(1) + 1;
                }

                String role_name = (String) user_role.getSelectionModel().getSelectedItem();

                String authorIdQuery = "SELECT id_role FROM roles WHERE name_role = ?";

                PreparedStatement authorIdPstmt = connect.prepareStatement(authorIdQuery);
                authorIdPstmt.setString(1, role_name);
                ResultSet authorIdRs = authorIdPstmt.executeQuery();
                int roleId = 0;
                if (authorIdRs.next()) {
                    roleId = authorIdRs.getInt("id_role");
                }

                String sql = "INSERT INTO users (id_user, fio_user, phone_user, email_user, id_role, login, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connect.prepareStatement(sql);
                pstmt.setInt(1, id_user);
                pstmt.setString(2, user_name.getText());
                pstmt.setString(3, user_phone.getText());
                pstmt.setString(4, user_email.getText());
                pstmt.setInt(5, roleId);
                pstmt.setString(6, user_login.getText());
                pstmt.setString(7, user_password.getText());

                pstmt.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех!");
                alert.setHeaderText(null);
                alert.setContentText("Регистрация прошла успешно");
                alert.showAndWait();

                registrate_Btn.getScene().getWindow().hide();

                // Load the hello-view.fxml file
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

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
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Пользователь с такими данными уже существует")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Пользователь с такими данными уже существует");
                alert.showAndWait();
            } else if (e.getMessage().contains("Phone number must be exactly 11 digits long and contain only numbers.")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Номер телефона должен содержать ровно 11 цифр");
                alert.showAndWait();
            } else {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT name_role FROM roles";
        try {
            Connection connect = db.connectDb();
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                roles.add(rs.getString("name_role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public void backtomain() throws IOException {
        // Скрываем текущее окно
        registrate_Btn.getScene().getWindow().hide();

        // Загружаем окно авторизации
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();

        // Создаем новое окно
        Stage stage = new Stage();

        // Устанавливаем сцену для окна
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
        // Отображаем новое окно
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        user_role.getItems().addAll(getRoles());
    }
}
