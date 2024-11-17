package com.example.librarysystem;

public class bohData {
    private Integer id_book;
    private String name_book;
    private Integer id_card;
    private String name_reader;
    private String deadline;

    public bohData(Integer id_book, String name_book, Integer id_card, String name_reader, String deadline) {
        this.id_book = id_book;
        this.name_book = name_book;
        this.id_card = id_card;
        this.name_reader = name_reader;
        this.deadline = deadline;
    }

    public Integer getId_book(){return id_book;}
    public String getName_book(){return name_book;}
    public Integer getId_card(){return id_card;}
    public String getName_reader(){return name_reader;}
    public String getDeadline(){return deadline;}
}


