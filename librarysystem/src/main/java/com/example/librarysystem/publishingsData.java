package com.example.librarysystem;

public class publishingsData {

    private Integer id_publishing;
    private String name_publishing;
    private String description_publishing;

    public publishingsData(Integer id_publishing, String name_publishing, String description_publishing){
        this.id_publishing = id_publishing;
        this.name_publishing = name_publishing;
        this.description_publishing = description_publishing;
    }

    public Integer getId_publishing(){return id_publishing;}
    public String getName_publishing(){return name_publishing;}
    public String getDescription_publishing(){return description_publishing;}
}
