package com.example.librarysystem;

public class readersData {
    private Integer id_reader;
    private String fio_reader;
    private String phone_reader;
    private String email_reader;
    private String adress_reader;
    private Integer id_card;


    public readersData(Integer id_reader, String fio_reader, String phone_reader, String email_reader, String adress_reader, Integer id_card){
        this.id_reader = id_reader;
        this.fio_reader = fio_reader;
        this.phone_reader = phone_reader;
        this.email_reader = email_reader;
        this.adress_reader = adress_reader;
        this.id_card = id_card;
    }

    public Integer getId_reader(){return id_reader;}
    public String getFio_reader(){return fio_reader;}
    public String getPhone_reader(){return phone_reader;}
    public String getEmail_reader(){return email_reader;}
    public String getAdress_reader(){return adress_reader;}
    public Integer getId_card(){return id_card;}
}
