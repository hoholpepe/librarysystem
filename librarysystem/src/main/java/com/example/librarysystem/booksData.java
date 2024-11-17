package com.example.librarysystem;

public class booksData {

    private Integer id_book;
    private String isbn;
    private String name_book;
    private String fio_autor;
    private String name_publishing;
    private String name_age;
    private String name_genre;
    private String photo_book;
    private String description_book;
    private Integer quantity;


    public booksData(Integer id_book, String isbn, String name_book, String fio_autor, String name_publishing, String name_age, String name_genre, String photo_book, String description_book, Integer quantity) {
        this.id_book = id_book;
        this.isbn = isbn;
        this.name_book = name_book;
        this.fio_autor = fio_autor;
        this.name_publishing = name_publishing;
        this.name_genre = name_genre;
        this.name_age = name_age;
        this.photo_book = photo_book;
        this.description_book = description_book;
        this.quantity = quantity;
    }

    public Integer getId_book(){
        return id_book;
    }
    public String getIsbn(){
        return isbn;
    }
    public String getName_book(){
        return name_book;
    }
    public String getFio_autor(){
        return fio_autor;
    }
    public String getName_publishing(){
        return name_publishing;
    }
    public String getName_age(){
        return name_age;
    }
    public String getName_genre(){return name_genre;}
    public String getPhoto_book(){
        return photo_book;
    }
    public String getDescription_book(){
        return description_book;
    }
    public Integer getQuantity(){return quantity;}
}
