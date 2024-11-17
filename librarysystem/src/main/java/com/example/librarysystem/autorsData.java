package com.example.librarysystem;

public class autorsData {

    private Integer id_autor;
    private String fio_autor;
    private String description_autor;

    public autorsData(Integer id_autor, String fio_autor, String description_autor){
        this.id_autor = id_autor;
        this.fio_autor = fio_autor;
        this.description_autor = description_autor;
    }

    public Integer getId_autor(){return id_autor;}
    public String getFio_autor(){return fio_autor;}
    public String getDescription_autor(){return description_autor;}
}
