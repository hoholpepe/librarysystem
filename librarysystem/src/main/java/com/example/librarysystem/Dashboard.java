package com.example.librarysystem;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Dashboard implements Initializable {
    @FXML
    private TextField ISBN;

    @FXML
    private Label deadline_col;

    @FXML
    private TextField QUANTITY;

    @FXML
    private TextField adress_reader;

    @FXML
    private ComboBox<String> ageCB;

    @FXML
    private Label all_books;

    @FXML
    private ComboBox<String> autorCB;

    @FXML
    private ComboBox<String> autorCB1;

    @FXML
    private TableView<autorsData> autor_table;

    @FXML
    private Button autorsBtn;

    @FXML
    private TableColumn<?, ?> autors_desc;

    @FXML
    private TableColumn<?, ?> autors_fio;

    @FXML
    private TableColumn<?, ?> autors_id;

    @FXML
    private AnchorPane autors_page;

    @FXML
    private TableColumn<?, ?> boh_deadline;

    @FXML
    private TableColumn<?, ?> boh_id_book;

    @FXML
    private TableColumn<?, ?> boh_id_card;

    @FXML
    private TableColumn<?, ?> boh_name_book;

    @FXML
    private TableColumn<?, ?> boh_name_reader;

    @FXML
    private TableView<bohData> boh_table;

    @FXML
    private TextArea book_description;

    @FXML
    private TextArea book_description1;

    @FXML
    private TextField book_name;

    @FXML
    private ImageView book_photo;

    @FXML
    private ImageView book_photo1;

    @FXML
    private Button booksBtn;

    @FXML
    private TableColumn<?, ?> books_age;

    @FXML
    private TableColumn<?, ?> books_age1;

    @FXML
    private TableColumn<?, ?> books_autor;

    @FXML
    private TableColumn<?, ?> books_autor1;

    @FXML
    private TableColumn<?, ?> books_genre;

    @FXML
    private TableColumn<?, ?> books_genre1;

    @FXML
    private TableColumn<?, ?> books_id_book;

    @FXML
    private TableColumn<?, ?> books_id_book1;

    @FXML
    private TableColumn<?, ?> books_isbn;

    @FXML
    private TableColumn<?, ?> books_isbn1;

    @FXML
    private TableColumn<?, ?> books_izd;

    @FXML
    private TableColumn<?, ?> books_izd1;

    @FXML
    private TableColumn<?, ?> books_name;

    @FXML
    private TableColumn<?, ?> books_name1;

    @FXML
    private Label books_on_hands;

    @FXML
    private AnchorPane books_page;

    @FXML
    private TableColumn<?, ?> books_quantity;

    @FXML
    private TableColumn<?, ?> books_quantity1;

    @FXML
    private TableView<booksData> books_table;

    @FXML
    private TableView<booksData> books_table1;

    @FXML
    private DatePicker date_picker;

    @FXML
    private TextArea desc_autor;

    @FXML
    private TextField email_reader;

    @FXML
    private TextField fio_reader;

    @FXML
    private ComboBox<String> genreCB;

    @FXML
    private Button givebookBtn;

    @FXML
    private AnchorPane givebook_page;

    @FXML
    private BarChart<String, Number> graph;

    @FXML
    private Button homeBtn;

    @FXML
    private AnchorPane home_page;

    @FXML
    private TextField id_card;

    @FXML
    private ComboBox<String> izdCB;

    @FXML
    private TableColumn<?, ?> izd_desc;

    @FXML
    private TableColumn<?, ?> izd_id;

    @FXML
    private TableColumn<?, ?> izd_name;

    @FXML
    private TableView<publishingsData> izd_table;

    @FXML
    private Button logoutBtn;

    @FXML
    private TextField name_autor;

    @FXML
    private TableView<readersData> reader_table;

    @FXML
    private Button readersBtn;

    @FXML
    private TableColumn<?, ?> readers_adress;

    @FXML
    private TableColumn<?, ?> readers_email;

    @FXML
    private TableColumn<?, ?> readers_fio;

    @FXML
    private TableColumn<?, ?> readers_id_card;

    @FXML
    private TableColumn<?, ?> readers_id_reader;

    @FXML
    private AnchorPane readers_page;

    @FXML
    private TableColumn<?, ?> readers_phone;

    @FXML
    private Button returnBookBtn;

    @FXML
    private AnchorPane returnbook_page;

    @FXML
    private TextField search_autor;

    @FXML
    private TextField search_boh;

    @FXML
    private TextField search_book;

    @FXML
    private TextField search_book1;

    @FXML
    private TextField search_izd;

    @FXML
    private TextField search_reader;

    @FXML
    private TextField tel_reader;

    @FXML
    private Label username;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet rs;
    private Image image;
    private String path;
    private double x = 0, y = 0;

    // Методы для кнопок навигации и отображения имени пользователя
    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) home_page.getScene().getWindow();
        stage.setIconified(true);
    }

    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтвердите действие");
        alert.setHeaderText(null);
        alert.setContentText("Вы уверены?");
        Optional<ButtonType> option = alert.showAndWait();

        try {
            if (option.get().equals(ButtonType.OK)) {

                logoutBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                scene.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                scene.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });

                scene.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        username.setText(getData.username);
    }

    // Методы для добавления данных в бд
    public void addBookAdd() {
        String sql = "insert into books" + "(id_book, isbn, name_book, description_book, id_autor, id_publishing, id_age, photo_book, id_genre, quantity)" + "VALUES(?,?,?,?,?,?,?,?,?,?)";

        connect = db.connectDb();

        try {
            Alert alert;
            if (ISBN.getText().isEmpty() ||
                    book_name.getText().isEmpty() ||
                    autorCB.getSelectionModel().getSelectedItem() == null ||
                    izdCB.getSelectionModel().getSelectedItem() == null ||
                    ageCB.getSelectionModel().getSelectedItem() == null ||
                    book_description.getText().isEmpty() ||
                    book_photo.getImage().equals(null) ||
                    genreCB.getSelectionModel().getSelectedItem() == null ||
                    QUANTITY.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Заполните все поля");
                alert.showAndWait();
            } else {
                String check = "SELECT isbn from books b where isbn = '" + ISBN.getText() + "'";

                statement = connect.createStatement();
                rs = statement.executeQuery(check);

                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("ISBN " + ISBN.getText() + " уже существует!");
                    alert.showAndWait();
                } else {

                    String authorName = (String) autorCB.getSelectionModel().getSelectedItem();
                    String publisherName = (String) izdCB.getSelectionModel().getSelectedItem();
                    String ageGroup = (String) ageCB.getSelectionModel().getSelectedItem();
                    String genreName = (String) genreCB.getSelectionModel().getSelectedItem();

                    String authorIdQuery = "SELECT id_autor FROM autors WHERE fio_autor = ?";
                    String publisherIdQuery = "SELECT id_publishing FROM publishings WHERE name_publishing = ?";
                    String ageGroupIdQuery = "SELECT id_age FROM ages WHERE name_age = ?";
                    String genreIdQuery = "SELECT id_genre FROM genres WHERE name_genre = ?";

                    PreparedStatement authorIdPstmt = connect.prepareStatement(authorIdQuery);
                    authorIdPstmt.setString(1, authorName);
                    ResultSet authorIdRs = authorIdPstmt.executeQuery();
                    int authorId = 0;
                    if (authorIdRs.next()) {
                        authorId = authorIdRs.getInt("id_autor");
                    }

                    PreparedStatement publisherIdPstmt = connect.prepareStatement(publisherIdQuery);
                    publisherIdPstmt.setString(1, publisherName);
                    ResultSet publisherIdRs = publisherIdPstmt.executeQuery();
                    int publisherId = 0;
                    if (publisherIdRs.next()) {
                        publisherId = publisherIdRs.getInt("id_publishing");
                    }

                    PreparedStatement ageGroupIdPstmt = connect.prepareStatement(ageGroupIdQuery);
                    ageGroupIdPstmt.setString(1, ageGroup);
                    ResultSet ageGroupIdRs = ageGroupIdPstmt.executeQuery();
                    int ageGroupId = 0;
                    if (ageGroupIdRs.next()) {
                        ageGroupId = ageGroupIdRs.getInt("id_age");
                        ageGroupId = Integer.parseInt(String.valueOf(ageGroupId));
                    }

                    PreparedStatement genreIdPstmt = connect.prepareStatement(genreIdQuery);
                    genreIdPstmt.setString(1, genreName);
                    ResultSet genreIdRs = genreIdPstmt.executeQuery();
                    int genreId = 0;
                    if (genreIdRs.next()) {
                        genreId = genreIdRs.getInt("id_genre");
                    }

                    String maxIdQuery = "SELECT MAX(id_book) FROM books";
                    PreparedStatement maxIdPstmt = connect.prepareStatement(maxIdQuery);
                    ResultSet maxIdRs = maxIdPstmt.executeQuery();
                    int maxId = 0;
                    if (maxIdRs.next()) {
                        maxId = maxIdRs.getInt(1);
                    }
                    int newId = maxId + 1;

                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, newId);
                    prepare.setString(2, ISBN.getText());
                    prepare.setString(3, book_name.getText());
                    prepare.setInt(5, authorId);
                    prepare.setInt(6, publisherId);
                    prepare.setInt(7, ageGroupId);
                    prepare.setString(4, book_description.getText());
                    prepare.setInt(9, genreId);
                    prepare.setInt(10, Integer.parseInt(QUANTITY.getText()));

                    String url = path;
                    url = url.replace("\\", "\\\\");

                    prepare.setString(8, url);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Книга успешно добавлена!");
                    alert.showAndWait();

                    addBookShowListData();
                    addBookReset();
                    displayBookCount();
                }
            }

        } catch (Exception e) {
            if (e instanceof SQLException && e.getMessage().contains("ISBN must be exactly 13 digits long and contain only numbers.")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("ISBN должен содержать ровно 13 цифр и состоять только из цифр.");
                alert.showAndWait();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void addAutorAdd() {
        String sql = "insert into autors" + "(id_autor, fio_autor, description_autor)" + "VALUES(?,?,?)";

        connect = db.connectDb();

        try {
            Alert alert;
            if (name_autor.getText().isEmpty() || desc_autor.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Заполните все поля");
                alert.showAndWait();
            } else {
                String check = "SELECT fio_autor from autors a where fio_autor = '" + name_autor.getText() + "'";

                statement = connect.createStatement();
                rs = statement.executeQuery(check);

                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("Автор " + name_autor.getText() + " уже существует!");
                    alert.showAndWait();
                } else {
                    String maxIdQuery = "SELECT MAX(id_autor) FROM autors";
                    PreparedStatement maxIdPstmt = connect.prepareStatement(maxIdQuery);
                    ResultSet maxIdRs = maxIdPstmt.executeQuery();
                    int maxId = 0;
                    if (maxIdRs.next()) {
                        maxId = maxIdRs.getInt(1);
                    }
                    int newId = maxId + 1;

                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, newId);
                    prepare.setString(2, name_autor.getText());
                    prepare.setString(3, desc_autor.getText());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Автор успешно добавлен!");
                    alert.showAndWait();

                    addAutorShowListData();
                    addAutorReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addIzdAdd() {
        String sql = "insert into publishings" + "(id_publishing, name_publishing, description_publishing)" + "VALUES(?,?,?)";

        connect = db.connectDb();

        try {
            Alert alert;
            if (name_autor.getText().isEmpty() || desc_autor.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Заполните все поля");
                alert.showAndWait();
            } else {
                String check = "SELECT name_publishing from publishings a where name_publishing = '" + name_autor.getText() + "'";

                statement = connect.createStatement();
                rs = statement.executeQuery(check);

                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("Издательство " + name_autor.getText() + " уже существует!");
                    alert.showAndWait();
                } else {
                    String maxIdQuery = "SELECT MAX(id_publishing) FROM publishings";
                    PreparedStatement maxIdPstmt = connect.prepareStatement(maxIdQuery);
                    ResultSet maxIdRs = maxIdPstmt.executeQuery();
                    int maxId = 0;
                    if (maxIdRs.next()) {
                        maxId = maxIdRs.getInt(1);
                    }
                    int newId = maxId + 1;

                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, newId);
                    prepare.setString(2, name_autor.getText());
                    prepare.setString(3, desc_autor.getText());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Издательство успешно добавлено!");
                    alert.showAndWait();

                    addIzdShowListData();
                    addAutorReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addReaderAdd() {
        String sql = "insert into readers" + "(id_reader, fio_reader, phone_reader, email_reader, adress_reader, id_card)" + "VALUES(?,?,?,?,?,?)";

        connect = db.connectDb();

        try {
            Alert alert;
            if (fio_reader.getText().isEmpty() || tel_reader.getText().isEmpty() || email_reader.getText().isEmpty() || adress_reader.getText().isEmpty() || id_card.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Заполните все поля");
                alert.showAndWait();
            } else {
                String check = "SELECT id_card from readers r where id_card = '" + id_card.getText() + "'";

                statement = connect.createStatement();
                rs = statement.executeQuery(check);

                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("Читатель с билетом " + id_card.getText() + " уже существует!");
                    alert.showAndWait();
                } else {
                    String maxIdQuery = "SELECT MAX(id_reader) FROM readers";
                    PreparedStatement maxIdPstmt = connect.prepareStatement(maxIdQuery);
                    ResultSet maxIdRs = maxIdPstmt.executeQuery();
                    int maxId = 0;
                    if (maxIdRs.next()) {
                        maxId = maxIdRs.getInt(1);
                    }
                    int newId = maxId + 1;

                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, newId);
                    prepare.setString(2, fio_reader.getText());
                    prepare.setString(3, tel_reader.getText());
                    prepare.setString(4, email_reader.getText());
                    prepare.setString(5, adress_reader.getText());
                    prepare.setInt(6, Integer.parseInt(id_card.getText()));
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Читатель успешно добавлен!");
                    alert.showAndWait();

                    addReaderShowListData();
                    addReaderReset();
                    populateComboBox();
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Читатель с такими данными уже существует")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Читатель с такими данными уже существует");
                alert.showAndWait();
            } else if (e instanceof SQLException && e.getMessage().contains("Phone number must be exactly 11 digits long and contain only numbers.")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Номер телефона должен содержать ровно 11 цифр");
                alert.showAndWait();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void insertData() {
        if (!checkFields()) {
            return;
        }

        // Get the selected items
        ObservableList<booksData> selectedItems = books_table1.getSelectionModel().getSelectedItems();

        // Create SQL-query for inserting data
        String sqlInsert = "INSERT INTO books_on_hands (id_card, id_book, deadline) VALUES (?, ?, ?)";

        // Create SQL-query for updating quantity
        String sqlUpdate = "UPDATE books SET quantity = quantity - 1 WHERE id_book = ?";

        // Create SQL-query for checking quantity
        String sqlCheck = "SELECT quantity FROM books WHERE id_book = ?";

        try {
            // Create PreparedStatement for inserting data
            PreparedStatement pstmtInsert = connect.prepareStatement(sqlInsert);

            // Create PreparedStatement for updating quantity
            PreparedStatement pstmtUpdate = connect.prepareStatement(sqlUpdate);

            // Create PreparedStatement for checking quantity
            PreparedStatement pstmtCheck = connect.prepareStatement(sqlCheck);

            // Iterate over the selected items
            for (booksData item : selectedItems) {
                try {
                    // Get the id_card
                    int id_card = Integer.parseInt(autorCB1.getValue().split("-")[0].trim());

                    // Get the id_book
                    int id_book = item.getId_book();

                    // Get the deadline
                    LocalDate deadline = date_picker.getValue();

                    // Set the value of the parameter for checking quantity
                    pstmtCheck.setInt(1, id_book);

                    // Execute the query
                    ResultSet rs = pstmtCheck.executeQuery();

                    // Check the quantity
                    if (rs.next() && rs.getInt("quantity") > 0) {
                        // Set the values of the parameters for inserting data
                        pstmtInsert.setInt(1, id_card);
                        pstmtInsert.setInt(2, id_book);
                        pstmtInsert.setDate(3, java.sql.Date.valueOf(deadline));

                        // Set the value of the parameter for updating quantity
                        pstmtUpdate.setInt(1, id_book);

                        // Execute the queries
                        pstmtInsert.executeUpdate();
                        pstmtUpdate.executeUpdate();

                        // Clear the parameters
                        pstmtInsert.clearParameters();
                        pstmtUpdate.clearParameters();


                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Успех!");
                        alert.setHeaderText(null);
                        alert.setContentText("Книга(и) успешно выдана(ы) читателю!");
                        alert.showAndWait();
                    } else {
                        // Show an alert if the quantity is 0
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка!");
                        alert.setHeaderText(null);
                        alert.setContentText("Книга с id " + id_book + " не может быть выдана, так как она отсутствует в библиотеке!");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    if (e.getMessage().contains("повторяющееся значение ключа нарушает ограничение уникальности")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка!");
                        int id_book = item.getId_book();
                        alert.setHeaderText("Книга с id " + id_book + " уже выдана этому читателю!");
                        alert.setContentText("Остальные книги были успешно выданы");
                        alert.showAndWait();
                    } else {
                        e.printStackTrace();
                    }
                }
            }
            addBookShowListData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Методы для удаления данных из бд
    public void deleteBook() {
        // Get the selected book
        booksData selectedBook = (booksData) books_table.getSelectionModel().getSelectedItem();

        // Check if a book is selected
        if (selectedBook != null) {
            // Get the ISBN of the selected book
            String isbn = selectedBook.getIsbn();

            // Create the SQL DELETE statement
            String sql = "DELETE FROM books WHERE isbn = ?";

            // Prepare the SQL statement
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, isbn);

                // Show a confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Подтверждение");
                confirmationAlert.setHeaderText("Вы уверены, что хотите удалить книгу?");
                confirmationAlert.setContentText("Это действие нельзя отменить.");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Execute the SQL statement
                    prepare.executeUpdate();

                    // Show a success alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Книга успешно удалена!");
                    alert.showAndWait();

                    // Refresh the book list
                    addBookShowListData();
                    displayBookCount();
                }
            } catch (Exception e) {
                if (e instanceof SQLException && e.getMessage().contains("UPDATE или DELETE в таблице \"books\" нарушает ограничение внешнего ключа")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("Экземпляр этой книги всё ещё есть на руках у читателя");
                    alert.showAndWait();
                } else {
                    e.printStackTrace();
                }
            }
        } else {
            // Show an error alert if no book is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите книгу для удаления");
            alert.showAndWait();
        }
    }

    public void deleteAutor() {
        // Get the selected book
        autorsData selectedAutor = (autorsData) autor_table.getSelectionModel().getSelectedItem();

        // Check if a book is selected
        if (selectedAutor != null) {
            // Get the ISBN of the selected book
            String fio_autor = selectedAutor.getFio_autor();

            // Create the SQL DELETE statement
            String sql = "DELETE FROM autors WHERE fio_autor = ?";

            // Prepare the SQL statement
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, fio_autor);

                // Show a confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Подтверждение");
                confirmationAlert.setHeaderText("Вы уверены, что хотите удалить автора?");
                confirmationAlert.setContentText("Это действие нельзя отменить.");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Execute the SQL statement
                    prepare.executeUpdate();

                    // Show a success alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Автор успешно удален!");
                    alert.showAndWait();

                    // Refresh the book list
                    addAutorShowListData();
                }
            } catch (Exception e) {
                if (e instanceof SQLException && e.getMessage().contains("UPDATE или DELETE в таблице \"autors\" нарушает ограничение внешнего ключа")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("Экземпляр книги этого автора всё ещё есть на руках у читателя");
                    alert.showAndWait();
                } else {
                    e.printStackTrace();
                }
            }
        } else {
            // Show an error alert if no book is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите автора для удаления");
            alert.showAndWait();
        }
    }

    public void deleteIzd() {
        // Get the selected book
        publishingsData selectedIzd = (publishingsData) izd_table.getSelectionModel().getSelectedItem();

        // Check if a book is selected
        if (selectedIzd != null) {
            // Get the ISBN of the selected book
            String name_publishing = selectedIzd.getName_publishing();

            // Create the SQL DELETE statement
            String sql = "DELETE FROM publishings WHERE name_publishing = ?";

            // Prepare the SQL statement
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, name_publishing);

                // Show a confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Подтверждение");
                confirmationAlert.setHeaderText("Вы уверены, что хотите удалить издательство?");
                confirmationAlert.setContentText("Это действие нельзя отменить.");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Execute the SQL statement
                    prepare.executeUpdate();

                    // Show a success alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Издательство успешно удалено!");
                    alert.showAndWait();

                    // Refresh the book list
                    addIzdShowListData();
                }
            }catch (Exception e) {
                if (e instanceof SQLException && e.getMessage().contains("UPDATE или DELETE в таблице \"publishings\" нарушает ограничение внешнего ключа")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("Экземпляр книги этого издательства всё ещё есть на руках у читателя");
                    alert.showAndWait();
                } else {
                    e.printStackTrace();
                }
            }
        } else {
            // Show an error alert if no book is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите издательство для удаления");
            alert.showAndWait();
        }
    }

    public void deleteReader() {
        // Get the selected book
        readersData selectedReader = (readersData) reader_table.getSelectionModel().getSelectedItem();

        // Check if a book is selected
        if (selectedReader != null) {
            // Get the ISBN of the selected book
            String name_reader = selectedReader.getFio_reader();

            // Create the SQL DELETE statement
            String sql = "DELETE FROM readers WHERE fio_reader = ?";

            // Prepare the SQL statement
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, name_reader);

                // Show a confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Подтверждение");
                confirmationAlert.setHeaderText("Вы уверены, что хотите удалить читателя?");
                confirmationAlert.setContentText("Это действие нельзя отменить.");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Execute the SQL statement
                    prepare.executeUpdate();

                    // Show a success alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Читатель успешно удален!");
                    alert.showAndWait();

                    // Refresh the book list
                    addReaderShowListData();
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("UPDATE или DELETE в таблице \"readers\" нарушает ограничение внешнего ключа")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("У пользователя ещё остались книги на руках");
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
            }
        } else {
            // Show an error alert if no book is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите читателя для удаления");
            alert.showAndWait();
        }
    }

    // Методы для редактирования справочников
    public void editBook() throws SQLException {
        // Получить выбранную книгу
        booksData selectedBook = (booksData) books_table.getSelectionModel().getSelectedItem();

        // Проверить, была ли выбрана книга
        if (selectedBook != null) {
            // Получить ID и ISBN выбранной книги
            int id = selectedBook.getId_book();
            String isbn = selectedBook.getIsbn();

            // Получить ID авторов, издателей, возрастных групп и жанров
            String authorName = (String) autorCB.getSelectionModel().getSelectedItem();
            String publisherName = (String) izdCB.getSelectionModel().getSelectedItem();
            String ageGroup = (String) ageCB.getSelectionModel().getSelectedItem();
            String genreName = (String) genreCB.getSelectionModel().getSelectedItem();

            // Запросы для получения ID авторов, издателей, возрастных групп и жанров
            String authorIdQuery = "SELECT id_autor FROM autors WHERE fio_autor = ?";
            String publisherIdQuery = "SELECT id_publishing FROM publishings WHERE name_publishing = ?";
            String ageGroupIdQuery = "SELECT id_age FROM ages WHERE name_age = ?";
            String genreIdQuery = "SELECT id_genre FROM genres WHERE name_genre = ?";

            // Подготовка запросов
            PreparedStatement authorIdPstmt = connect.prepareStatement(authorIdQuery);
            authorIdPstmt.setString(1, authorName);
            ResultSet authorIdRs = authorIdPstmt.executeQuery();
            int authorId = 0;
            if (authorIdRs.next()) {
                authorId = authorIdRs.getInt("id_autor");
            }

            PreparedStatement publisherIdPstmt = connect.prepareStatement(publisherIdQuery);
            publisherIdPstmt.setString(1, publisherName);
            ResultSet publisherIdRs = publisherIdPstmt.executeQuery();
            int publisherId = 0;
            if (publisherIdRs.next()) {
                publisherId = publisherIdRs.getInt("id_publishing");
            }

            PreparedStatement ageGroupIdPstmt = connect.prepareStatement(ageGroupIdQuery);
            ageGroupIdPstmt.setString(1, ageGroup);
            ResultSet ageGroupIdRs = ageGroupIdPstmt.executeQuery();
            int ageGroupId = 0;
            if (ageGroupIdRs.next()) {
                ageGroupId = ageGroupIdRs.getInt("id_age");
                ageGroupId = Integer.parseInt(String.valueOf(ageGroupId));
            }

            PreparedStatement genreIdPstmt = connect.prepareStatement(genreIdQuery);
            genreIdPstmt.setString(1, genreName);
            ResultSet genreIdRs = genreIdPstmt.executeQuery();
            int genreId = 0;
            if (genreIdRs.next()) {
                genreId = genreIdRs.getInt("id_genre");
            }

            // Создать SQL-запрос UPDATE
            String sql = "UPDATE books SET id_book = ?, isbn = ?, name_book = ?, description_book = ?, id_autor = ?, id_publishing = ?, id_age = ?, photo_book = ?, id_genre = ?, quantity =? WHERE isbn = " + "'" + isbn + "'";

            // Подготовить SQL-запрос
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, id);
                prepare.setString(3, book_name.getText());
                prepare.setInt(5, authorId);
                prepare.setInt(6, publisherId);
                prepare.setInt(7, ageGroupId);
                prepare.setInt(9, genreId);
                prepare.setInt(10, Integer.parseInt(QUANTITY.getText()));

                // Получить путь к старой фотографии
                String oldImagePath = selectedBook.getPhoto_book();

                // Проверить, было ли выбрано новое изображение
                if (path != null && !path.equals(oldImagePath)) {
                    // Если было выбрано новое изображение, то обновить путь к изображению
                    prepare.setString(8, path);
                } else {
                    // Если не было выбрано нового изображения, то вставить путь до старой фотографии
                    prepare.setString(8, oldImagePath);
                }

                // Получить описание книги
                String fullDescription = book_description.getText();

                // Разделить полное описание на абзацы
                String[] paragraphs = fullDescription.split("\n");

                // Получить первый абзац
                String bookDescription = paragraphs[0];

                // Удалить первую часть "Описание книги: " из описания книги
                bookDescription = bookDescription.substring("Описание книги: ".length());

                prepare.setString(4, bookDescription);

                prepare.setString(2, ISBN.getText());

                // Показать диалоговое окно подтверждения
                try {
                    prepare.executeUpdate();

                    // Показать диалоговое окно успеха
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Детали книги успешно обновлены!");
                    alert.showAndWait();

                    // Обновить список книг
                    addBookShowListData();
                } catch (Exception e) {
                    if (e instanceof SQLException && e.getMessage().contains("ISBN must be exactly 13 digits long and contain only numbers.")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка!");
                        alert.setHeaderText(null);
                        alert.setContentText("ISBN должен содержать ровно 13 цифр и состоять только из цифр.");
                        alert.showAndWait();
                    } else {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Show an error alert if no book is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите книгу для редактирования");
            alert.showAndWait();
        }
    }

    public void editAutor() throws SQLException {
        // Получить выбранную книгу
        autorsData selectedAutor = (autorsData) autor_table.getSelectionModel().getSelectedItem();

        // Проверить, была ли выбрана книга
        if (selectedAutor != null) {
            // Получить ID и ISBN выбранной книги
            int id = selectedAutor.getId_autor();
            String name = selectedAutor.getFio_autor();

            // Создать SQL-запрос UPDATE
            String sql = "UPDATE autors SET id_autor = ?, fio_autor = ?, description_autor = ? WHERE fio_autor = " + "'" + name + "'";

            // Подготовить SQL-запрос
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, id);
                prepare.setString(2, name_autor.getText());
                prepare.setString(3, desc_autor.getText());

                // Показать диалоговое окно подтверждения
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Подтверждение");
                confirmationAlert.setHeaderText("Вы уверены, что хотите редактировать автора: " + name_autor.getText() + "?");
                confirmationAlert.setContentText("Это действие нельзя отменить.");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Выполнить SQL-запрос
                    prepare.executeUpdate();

                    // Показать диалоговое окно успеха
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Детали автора успешно обновлены!");
                    alert.showAndWait();

                    // Обновить список книг
                    addAutorShowListData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Show an error alert if no book is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите автора для редактирования");
            alert.showAndWait();
        }
    }

    public void editIzd() throws SQLException {
        // Получить выбранную книгу
        publishingsData selectedIzd = (publishingsData) izd_table.getSelectionModel().getSelectedItem();

        // Проверить, была ли выбрана книга
        if (selectedIzd != null) {
            // Получить ID и ISBN выбранной книги
            int id = selectedIzd.getId_publishing();
            String name = selectedIzd.getName_publishing();

            // Создать SQL-запрос UPDATE
            String sql = "UPDATE publishings SET id_publishing = ?, name_publishing = ?, description_publishing = ? WHERE name_publishing = " + "'" + name + "'";

            // Подготовить SQL-запрос
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, id);
                prepare.setString(2, name_autor.getText());
                prepare.setString(3, desc_autor.getText());

                // Показать диалоговое окно подтверждения
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Подтверждение");
                confirmationAlert.setHeaderText("Вы уверены, что хотите редактировать издательство: " + name_autor.getText() + "?");
                confirmationAlert.setContentText("Это действие нельзя отменить.");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Выполнить SQL-запрос
                    prepare.executeUpdate();

                    // Показать диалоговое окно успеха
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Детали издательства успешно обновлены!");
                    alert.showAndWait();

                    // Обновить список книг
                    addIzdShowListData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Show an error alert if no book is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите издательство для редактирования");
            alert.showAndWait();
        }
    }

    public void editReader() throws SQLException {
        // Получить выбранную книгу
        readersData selectedReader = (readersData) reader_table.getSelectionModel().getSelectedItem();

        // Проверить, была ли выбрана книга
        if (selectedReader != null) {
            // Получить ID и ISBN выбранной книги
            int id = selectedReader.getId_reader();
            String name = selectedReader.getFio_reader();

            // Создать SQL-запрос UPDATE
            String sql = "UPDATE readers SET id_reader =?, fio_reader =?, phone_reader =?, email_reader =?, adress_reader =?, id_card =? WHERE fio_reader = " + "'" + name + "'";

            // Подготовить SQL-запрос
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, id);
                prepare.setString(2, fio_reader.getText());
                prepare.setString(3, tel_reader.getText());
                prepare.setString(4, email_reader.getText());
                prepare.setString(5, adress_reader.getText());
                prepare.setInt(6, Integer.parseInt(id_card.getText()));

                // Показать диалоговое окно подтверждения
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Подтверждение");
                confirmationAlert.setHeaderText("Вы уверены, что хотите редактировать читателя: " + fio_reader.getText() + "?");
                confirmationAlert.setContentText("Это действие нельзя отменить.");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Выполнить SQL-запрос
                    prepare.executeUpdate();

                    // Показать диалоговое окно успеха
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех!");
                    alert.setHeaderText(null);
                    alert.setContentText("Детали читателя успешно обновлены!");
                    alert.showAndWait();

                    // Обновить список книг
                    addReaderShowListData();
                }
            } catch (Exception e) {
                if (e instanceof SQLException && e.getMessage().contains("Phone number must be exactly 11 digits long and contain only numbers.")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("Номер телефона должен содержать ровно 11 цифр");
                    alert.showAndWait();
                } else if (e.getMessage().contains("UPDATE или DELETE в таблице \"readers\" нарушает ограничение внешнего ключа")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText("Номер читательского билета невозможно заменить, у читателя есть книги на руках");
                    alert.setContentText("Пожалуйста, оставьте текущий номер для изменения остальных параметров читателя.");
                    alert.showAndWait();
                } else {
                    e.printStackTrace();
                }
            }
        } else {
            // Show an error alert if no book is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите читателя для редактирования");
            alert.showAndWait();
        }

    }

    // Методы для очистки выбора
    public void addBookReset() {
        ISBN.setText("");
        book_name.setText("");
        book_description.setText("");
        autorCB.getSelectionModel().clearSelection();
        izdCB.getSelectionModel().clearSelection();
        ageCB.getSelectionModel().clearSelection();
        genreCB.getSelectionModel().clearSelection();
        book_photo.setImage(null);
        getData.path = "";
        QUANTITY.setText("");
    }

    public void addAutorReset() {
        name_autor.setText("");
        desc_autor.setText("");
    }

    public void addReaderReset() {
        fio_reader.setText("");
        tel_reader.setText("");
        email_reader.setText("");
        adress_reader.setText("");
        id_card.setText("");
    }

    public void clearBohSelection() {
        boh_table.getSelectionModel().clearSelection();
    }

    // Методы для работы с фотографиями книг
    public void addBookInsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(home_page.getScene().getWindow());

        if (file != null) {
            File destDir = new File("src/main/resources/photos");
            File srcFile = file.toPath().toFile();
            File destFile = new File(destDir, srcFile.getName());
            try {
                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            path = destFile.getPath();

            image = new Image(destFile.toURI().toString(), 200, 165, false, true);
            book_photo.setImage(image);
        }
    }

    public void cleanUnusedImages() {
        // Получите список имен файлов из путей в базе данных
        List<String> usedImages = getUsedImagesFromDB();

        // Получите список всех файлов в директории проекта
        File dir = new File("src/main/resources/photos");
        File[] files = dir.listFiles();

        // Сравните эти два списка и удалите все файлы, которые присутствуют в списке файлов в директории проекта, но отсутствуют в списке имен файлов из путей в базе данных
        for (File file : files) {
            if (!usedImages.contains(file.getName())) {
                file.delete();
            }
        }
    }

    private List<String> getUsedImagesFromDB() {
        List<String> usedImages = new ArrayList<>();

        // Создайте SQL-запрос для получения списка путей к файлам из базы данных
        String sql = "SELECT photo_book FROM books";

        try {
            // Создайте PreparedStatement для выполнения запроса
            PreparedStatement pstmt = connect.prepareStatement(sql);

            // Выполните запрос
            ResultSet rs = pstmt.executeQuery();

            // Получите результаты
            while (rs.next()) {
                // Получите путь к файлу
                String path = rs.getString("photo_book");

                // Получите имя файла из пути
                String fileName = path.substring(path.lastIndexOf("\\") + 1);

                // Добавьте имя файла в список
                usedImages.add(fileName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usedImages;
    }

    // Методы для получения данных из бд
    public ObservableList<autorsData> addAutorListData() {
        ObservableList<autorsData> autorList = FXCollections.observableArrayList();
        String sql = "SELECT id_autor, fio_autor, description_autor from autors a";

        connect = db.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();
            autorsData autorsD;

            while (rs.next()) {
                autorsD = new autorsData(rs.getInt("id_autor"),
                        rs.getString("fio_autor"),
                        rs.getString("description_autor")
                );
                autorList.add(autorsD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return autorList;
    }

    private ObservableList<autorsData> addAutorList;

    public void addAutorShowListData() {
        addAutorList = addAutorListData();
        autors_id.setCellValueFactory(new PropertyValueFactory<>("id_autor"));
        autors_fio.setCellValueFactory(new PropertyValueFactory<>("fio_autor"));
        autors_desc.setCellValueFactory(new PropertyValueFactory<>("description_autor"));

        autor_table.setItems(addAutorList);
    }

    public ObservableList<publishingsData> addIzdListData() {
        ObservableList<publishingsData> izdList = FXCollections.observableArrayList();
        String sql = "SELECT id_publishing, name_publishing, description_publishing from publishings p";

        connect = db.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();
            publishingsData publishingsD;

            while (rs.next()) {
                publishingsD = new publishingsData(rs.getInt("id_publishing"),
                        rs.getString("name_publishing"),
                        rs.getString("description_publishing")
                );
                izdList.add(publishingsD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return izdList;
    }

    private ObservableList<publishingsData> addIzdList;

    public void addIzdShowListData() {
        addIzdList = addIzdListData();
        izd_id.setCellValueFactory(new PropertyValueFactory<>("id_publishing"));
        izd_name.setCellValueFactory(new PropertyValueFactory<>("name_publishing"));
        izd_desc.setCellValueFactory(new PropertyValueFactory<>("description_publishing"));

        izd_table.setItems(addIzdList);
    }

    public ObservableList<bohData> addBohListData() {
        ObservableList<bohData> boh_Data = FXCollections.observableArrayList();
        String sql = "SELECT b.id_card, b.id_book, b.deadline, b1.name_book, r.fio_reader from books_on_hands b, books b1, readers r where b.id_book = b1.id_book and b.id_card = r.id_card";

        connect = db.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();
            bohData bohD;

            while (rs.next()) {
                bohD = new bohData(
                        rs.getInt("id_book"),
                        rs.getString("name_book"),
                        rs.getInt("id_card"),
                        rs.getString("fio_reader"),
                        rs.getString("deadline")
                );
                boh_Data.add(bohD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return boh_Data;
    }

    private ObservableList<bohData> addBohList;

    public void addBohShowListData() {
        addBohList = addBohListData();
        boh_id_book.setCellValueFactory(new PropertyValueFactory<>("id_book"));
        boh_name_book.setCellValueFactory(new PropertyValueFactory<>("name_book"));
        boh_id_card.setCellValueFactory(new PropertyValueFactory<>("id_card"));
        boh_name_reader.setCellValueFactory(new PropertyValueFactory<>("name_reader"));
        boh_deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        boh_table.setItems(addBohList);
        boh_table.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
    }

    public void deleteSelectedRows() {
        ObservableList<bohData> selectedItems = boh_table.getSelectionModel().getSelectedItems();
        ArrayList<bohData> rows = new ArrayList<>(selectedItems);

        if (!rows.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтвердите действие");
            alert.setHeaderText("Вернуть книги на баланс библиотеки?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                rows.forEach(row -> {
                    boh_table.getItems().remove(row);
                    deleteFromDatabase(row);

                    // Get the id of the book
                    int id_book = row.getId_book();

                    // Create SQL-query for updating quantity
                    String sqlUpdate = "UPDATE books SET quantity = quantity + 1 WHERE id_book = ?";

                    try {
                        // Create PreparedStatement for updating quantity
                        PreparedStatement pstmtUpdate = connect.prepareStatement(sqlUpdate);

                        // Set the value of the parameter for updating quantity
                        pstmtUpdate.setInt(1, id_book);

                        // Execute the query
                        pstmtUpdate.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    private void deleteFromDatabase(bohData row) {
        String sql = "DELETE FROM books_on_hands WHERE id_card = ? AND id_book = ?";

        try {
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1, row.getId_card());
            prepare.setInt(2, row.getId_book());
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<readersData> addReaderListData() {
        ObservableList<readersData> readerData = FXCollections.observableArrayList();
        String sql = "SELECT id_reader, fio_reader, phone_reader, email_reader, adress_reader, id_card from readers";

        connect = db.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();
            readersData readersD;

            while (rs.next()) {
                readersD = new readersData(rs.getInt("id_reader"),
                        rs.getString("fio_reader"),
                        rs.getString("phone_reader"),
                        rs.getString("email_reader"),
                        rs.getString("adress_reader"),
                        rs.getInt("id_card")
                );
                readerData.add(readersD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return readerData;
    }

    private ObservableList<readersData> addReaderList;

    public void addReaderShowListData() {
        addReaderList = addReaderListData();
        readers_id_reader.setCellValueFactory(new PropertyValueFactory<>("id_reader"));
        readers_fio.setCellValueFactory(new PropertyValueFactory<>("fio_reader"));
        readers_phone.setCellValueFactory(new PropertyValueFactory<>("phone_reader"));
        readers_email.setCellValueFactory(new PropertyValueFactory<>("email_reader"));
        readers_adress.setCellValueFactory(new PropertyValueFactory<>("adress_reader"));
        readers_id_card.setCellValueFactory(new PropertyValueFactory<>("id_card"));

        reader_table.setItems(addReaderList);
    }

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
        books_id_book.setCellValueFactory(new PropertyValueFactory<>("id_book"));
        books_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        books_name.setCellValueFactory(new PropertyValueFactory<>("name_book"));
        books_autor.setCellValueFactory(new PropertyValueFactory<>("fio_autor"));
        books_izd.setCellValueFactory(new PropertyValueFactory<>("name_publishing"));
        books_age.setCellValueFactory(new PropertyValueFactory<>("name_age"));
        books_genre.setCellValueFactory(new PropertyValueFactory<>("name_genre"));
        books_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        books_table.setItems(addDataList);

        books_id_book1.setCellValueFactory(new PropertyValueFactory<>("id_book"));
        books_isbn1.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        books_name1.setCellValueFactory(new PropertyValueFactory<>("name_book"));
        books_autor1.setCellValueFactory(new PropertyValueFactory<>("fio_autor"));
        books_izd1.setCellValueFactory(new PropertyValueFactory<>("name_publishing"));
        books_age1.setCellValueFactory(new PropertyValueFactory<>("name_age"));
        books_genre1.setCellValueFactory(new PropertyValueFactory<>("name_genre"));
        books_quantity1.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        books_table1.setItems(addDataList);
        books_table1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    // Методы для вывода информации о выбранных объектах
    public void addAutorSelect() {
        // Обработчик событий для TableView
        autor_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Получаем выбранную книгу
                autorsData selectedAutor = (autorsData) autor_table.getSelectionModel().getSelectedItem();

                String fio_autor = selectedAutor.getFio_autor();
                String desc = selectedAutor.getDescription_autor();

                // Устанавливаем значения для компонентов интерфейса
                name_autor.setText(fio_autor);
                desc_autor.setWrapText(true);
                desc_autor.setText(desc);
            }
        });
    }

    public void addReaderSelect() {
        // Обработчик событий для TableView
        reader_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Получаем выбранную книгу
                readersData selectedReader = (readersData) reader_table.getSelectionModel().getSelectedItem();

                String fio = selectedReader.getFio_reader();
                String phone = selectedReader.getPhone_reader();
                String email = selectedReader.getEmail_reader();
                String adress = selectedReader.getAdress_reader();
                String card = String.valueOf(selectedReader.getId_card());

                // Устанавливаем значения для компонентов интерфейса
                fio_reader.setText(fio);
                tel_reader.setText(phone);
                email_reader.setText(email);
                adress_reader.setText(adress);
                id_card.setText(card);
            }
        });
    }

    public void addIzdSelect() {
        // Обработчик событий для TableView
        izd_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Получаем выбранную книгу
                publishingsData selectedIzd = (publishingsData) izd_table.getSelectionModel().getSelectedItem();

                String name = selectedIzd.getName_publishing();
                String desc = selectedIzd.getDescription_publishing();

                // Устанавливаем значения для компонентов интерфейса
                name_autor.setText(name);
                desc_autor.setWrapText(true);
                desc_autor.setText(desc);
            }
        });
    }

    public void addBookSelect() {
        // Обработчик событий для TableView
        books_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Получаем выбранную книгу
                booksData selectedBook = (booksData) books_table.getSelectionModel().getSelectedItem();

                if (selectedBook != null) {
                    // Устанавливаем значения для комбобоксов
                    autorCB.setValue(selectedBook.getFio_autor());
                    izdCB.setValue(selectedBook.getName_publishing());
                    ageCB.setValue(selectedBook.getName_age());
                    genreCB.setValue(selectedBook.getName_genre());

                    // Получаем описание книги
                    String bookDescription = getBookDescription(selectedBook.getName_book());

                    // Получаем описание автора и издателя
                    String authorDescription = getAuthorDescription(selectedBook.getFio_autor());
                    String publisherDescription = getPublisherDescription(selectedBook.getName_publishing());

                    // Формируем полное описание книги
                    String fullDescription = "Описание книги: " + bookDescription + "\n" + "Описание автора: " + authorDescription + "\n" + "Описание издательства: " + publisherDescription;

                    // Отображаем описание книги
                    book_description.setWrapText(true);
                    book_description.setText(fullDescription);

                    // Получаем данные о книге
                    String isbn = selectedBook.getIsbn();
                    Integer quantity = selectedBook.getQuantity();
                    String bookName = selectedBook.getName_book();
                    String url = "file:" + selectedBook.getPhoto_book();

                    // Устанавливаем значения для компонентов интерфейса
                    ISBN.setText(isbn);
                    book_name.setText(bookName);
                    image = new Image(url, 200, 165, false, true);
                    book_photo.setImage(image);
                    QUANTITY.setText(String.valueOf(quantity));
                }
            }
        });
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

    // Методы для получения описания объектов
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

    // Метод для заполнения combobox'ов
    public ObservableList<String> getDataFromDatabase(String tableName, String columnName) {
        ObservableList<String> dataList = FXCollections.observableArrayList();
        String sql = "SELECT " + columnName + " FROM " + tableName;
        connect = db.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            while (rs.next()) {
                dataList.add(rs.getString(columnName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // Метод для смены вкладок
    public void switchForm(ActionEvent event) throws SQLException {
        Node[] pages = {home_page, books_page, readers_page, autors_page, givebook_page, returnbook_page};
        Button[] buttons = {homeBtn, booksBtn, readersBtn, autorsBtn, givebookBtn, returnBookBtn};
        String[] styles = {"-fx-background-color:linear-gradient(to right bottom, #6F0EDD, #2BEAF7)", "-fx-background-color:transparent"};

        for (int i = 0; i < pages.length; i++) {
            pages[i].setVisible(event.getSource() == buttons[i]);
            buttons[i].setStyle(styles[i == Arrays.asList(buttons).indexOf(event.getSource()) ? 0 : 1]);
        }

        if (event.getSource() == homeBtn) {
            displayBookCount();
            updateBooksOnHands();
            updateFunctionAndUpdateLabel();
            populateGraph();
        } else if (event.getSource() == booksBtn) {
            addBookShowListData();
        } else if (event.getSource() == readersBtn) {
            addReaderShowListData();
        } else if (event.getSource() == autorsBtn) {
            addAutorShowListData();
            addIzdShowListData();
        } else if (event.getSource() == givebookBtn) {
            addBookShowListData();
            populateComboBox();
            populateGraph();
        } else if (event.getSource() == returnBookBtn) {
            addBohShowListData();
            populateGraph();
        }
    }

    // Метод для заполнения combobox'a с выбором читателя
    public void populateComboBox() {
        String sql = "SELECT id_card, fio_reader FROM readers";
        try {
            // Создаем Statement и ResultSet
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // Создаем ObservableList для хранения данных
            ObservableList<String> data = FXCollections.observableArrayList();

            // Заполняем ObservableList данными из ResultSet
            while (rs.next()) {
                String id_card = rs.getString("id_card");
                String fio_reader = rs.getString("fio_reader");
                data.add(id_card + " - " + fio_reader);
            }

            // Создаем StringConverter для преобразования данных в строку
            StringConverter<String> converter = new StringConverter<String>() {
                @Override
                public String toString(String object) {
                    return object;
                }

                @Override
                public String fromString(String string) {
                    return string;
                }
            };

            // Устанавливаем данные и конвертер для ComboBox
            autorCB1.setItems(data);
            autorCB1.setConverter(converter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для проверки заполненности полей
    private boolean checkFields() {
        ObservableList<booksData> selectedItems = books_table1.getSelectionModel().getSelectedItems();
        if (date_picker.getValue() == null || autorCB1.getValue() == null || selectedItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            if (date_picker.getValue() == null) {
                alert.setContentText("Пожалуйста, выберите дату!");
            } else if (autorCB1.getValue() == null) {
                alert.setContentText("Пожалуйста, выберите читателя!");
            } else {
                alert.setContentText("Пожалуйста, выберите книгу!");
            }
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // Методы для отображения статистики библиотеки
    public void executeProcedure(String storedProcedure, Label label) throws SQLException {
        try (CallableStatement cs = connect.prepareCall(storedProcedure)) {
            cs.registerOutParameter(1, Types.INTEGER);
            cs.execute();
            int result = cs.getInt(1);
            label.setText(String.valueOf(result));
        }
    }

    public void displayBookCount() throws SQLException {
        executeProcedure("{? = call count_books()}", all_books);
    }

    public void updateBooksOnHands() throws SQLException {
        executeProcedure("{? = call count_issued_books()}", books_on_hands);
    }

    public void updateFunctionAndUpdateLabel() throws SQLException {
        executeProcedure("{? = call library.count_expired_books()}", deadline_col);
    }

    // Метод для отрисовки графика
    public void populateGraph() throws SQLException {
        String sql = "SELECT deadline, COUNT(*) AS count FROM library.books_on_hands GROUP BY deadline";
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<XYChart.Data<String, Number>> data = new ArrayList<>();
        while (rs.next()) {
            String date = rs.getString("deadline");
            int count = rs.getInt("count");
            data.add(new XYChart.Data<>(date, count));
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Книги со сроком возврата до данной даты");
        graph.getData().clear(); // очистка графика перед добавлением новых данных
        graph.getData().add(series);
        series.getData().addAll(data);
    }

    // Метод для реализации поиска
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

        addSearchListener(books_table, search_book, books_table.getItems(), this::addBookShowListData);
        addSearchListener(autor_table, search_autor, autor_table.getItems(), this::addAutorShowListData);
        addSearchListener(izd_table, search_izd, izd_table.getItems(), this::addIzdShowListData);
        addSearchListener(books_table1, search_book1, books_table1.getItems(), this::addBookShowListData);
        addSearchListener(reader_table, search_reader, reader_table.getItems(), this::addReaderShowListData);
        addSearchListener(boh_table, search_boh, boh_table.getItems(), this::addBohShowListData);


        ObservableList<String> autorData = getDataFromDatabase("autors", "fio_autor");
        ObservableList<String> izdData = getDataFromDatabase("publishings", "name_publishing");
        ObservableList<String> ageData = getDataFromDatabase("ages", "name_age");
        ObservableList<String> genreData = getDataFromDatabase("genres", "name_genre");

        autorCB.setItems(autorData);
        izdCB.setItems(izdData);
        ageCB.setItems(ageData);
        genreCB.setItems(genreData);

        displayUsername();
        addBookShowListData();
        addAutorShowListData();
        addIzdShowListData();
        addReaderShowListData();
        populateComboBox();
        addBohShowListData();
        try {
            populateGraph();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            displayBookCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            updateBooksOnHands();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cleanUnusedImages();

        date_picker.setValue(LocalDate.now());
        date_picker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 1);
            }
        });
    }
}