package com.example.librarysystem;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Guest implements Initializable {

    @FXML
    private TextArea book_description1;

    @FXML
    private ImageView book_photo1;

    @FXML
    private TableColumn<?, ?> books_age1;

    @FXML
    private TableColumn<?, ?> books_autor1;

    @FXML
    private TableColumn<?, ?> books_genre1;

    @FXML
    private TableColumn<?, ?> books_id_book1;

    @FXML
    private TableColumn<?, ?> books_isbn1;

    @FXML
    private TableColumn<?, ?> books_izd1;

    @FXML
    private TableColumn<?, ?> books_name1;

    @FXML
    private TableColumn<?, ?> books_quantity1;

    @FXML
    private TableView<booksData> books_table1;

    @FXML
    private Button closeBtn;

    @FXML
    private TextField search_book1;

    @FXML
    private AnchorPane guest_main;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;
    private Image image;
    private double x = 0, y = 0;

    public ObservableList<booksData> addBookListData() {
        ObservableList<booksData> listData = FXCollections.observableArrayList();
        String sql = "SELECT id_book, isbn, name_book, fio_autor, name_publishing, name_age, name_genre, photo_book, description_book, quantity from books b, autors a, publishings p, ages a1, genres g where b.id_genre=g.id_genre and b.id_age=a1.id_age and b.id_publishing=p.id_publishing and b.id_autor = a.id_autor";

        connect = db.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();
            booksData booksD;

            while (rs.next()) {
                booksD = new booksData(rs.getInt("id_book"),
                        rs.getString("isbn"),
                        rs.getString("name_book"),
                        rs.getString("fio_autor"),
                        rs.getString("name_publishing"),
                        rs.getString("name_age"),
                        rs.getString("name_genre"),
                        rs.getString("photo_book"),
                        rs.getString("description_book"),
                        rs.getInt("quantity")
                );
                listData.add(booksD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<booksData> addDataList;

    public void addBookShowListData() {
        addDataList = addBookListData();

        books_id_book1.setCellValueFactory(new PropertyValueFactory<>("id_book"));
        books_isbn1.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        books_name1.setCellValueFactory(new PropertyValueFactory<>("name_book"));
        books_autor1.setCellValueFactory(new PropertyValueFactory<>("fio_autor"));
        books_izd1.setCellValueFactory(new PropertyValueFactory<>("name_publishing"));
        books_age1.setCellValueFactory(new PropertyValueFactory<>("name_age"));
        books_genre1.setCellValueFactory(new PropertyValueFactory<>("name_genre"));
        books_quantity1.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        books_table1.setItems(addDataList);
    }

    public void close() throws IOException {
        // Скрываем текущее окно
        closeBtn.getScene().getWindow().hide();

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

    public void minimize() {
        Stage stage = (Stage) guest_main.getScene().getWindow();
        stage.setIconified(true);
    }

    public void addBookSelect1() {
        // Обработчик событий для TableView
        books_table1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Получаем выбранные книги
                ObservableList<booksData> selectedBooks = books_table1.getSelectionModel().getSelectedItems();

                // Проверяем, была ли выбрана только одна книга
                if (selectedBooks.size() == 1) {
                    // Получаем выбранную книгу
                    booksData selectedBook = selectedBooks.get(0);

                    // Получаем описание книги
                    String bookDescription1 = getBookDescription(selectedBook.getName_book());
                    String authorDescription1 = getAuthorDescription(selectedBook.getFio_autor());
                    String publisherDescription1 = getPublisherDescription(selectedBook.getName_publishing());

                    // Формируем полное описание книги
                    String fullDescription = "Описание книги: " + bookDescription1 + "\n" + "Описание автора: " + authorDescription1 + "\n" + "Описание издательства: " + publisherDescription1;

                    // Отображаем описание книги
                    book_description1.setWrapText(true);
                    book_description1.setText(fullDescription);

                    String url = "file:" + selectedBook.getPhoto_book();

                    image = new Image(url, 200, 148, false, true);
                    book_photo1.setImage(image);
                }
            }
        });
    }

    public String getAuthorDescription(String authorName) {
        String description = "";
        String sql = "SELECT description_autor FROM autors WHERE fio_autor = ?";
        connect = db.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, authorName);
            rs = prepare.executeQuery();

            if (rs.next()) {
                description = rs.getString("description_autor");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }

    public String getPublisherDescription(String publisherName) {
        String description = "";
        String sql = "SELECT description_publishing FROM publishings WHERE name_publishing = ?";
        connect = db.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, publisherName);
            rs = prepare.executeQuery();

            if (rs.next()) {
                description = rs.getString("description_publishing");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }

    public String getBookDescription(String bookName) {
        String description = "";
        String sql = "SELECT description_book FROM books WHERE name_book = ?";
        connect = db.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, bookName);
            rs = prepare.executeQuery();

            if (rs.next()) {
                description = rs.getString("description_book");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }

    public <T> void addSearchListener(TableView<T> table, TextField searchField, ObservableList<T> data, Runnable updateMethod) {
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table.setItems(data);
                updateMethod.run();
            }
            String value = newValue.toLowerCase();
            ObservableList<T> subentries = FXCollections.observableArrayList();

            long count = table.getColumns().stream().count();
            for (int i = 0; i < table.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table.getItems().get(i));
                        break;
                    }
                }
            }
            table.setItems(subentries);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addBookShowListData();
        addSearchListener(books_table1, search_book1, books_table1.getItems(), this::addBookShowListData);
    }
}
