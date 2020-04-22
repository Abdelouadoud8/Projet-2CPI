package com.example.projet_2cpi;

public class Users {

    public String name, image, status, genre;

    public Users(){

    }

    public String getGenre(){ return genre; }

    public void setGenre(String genre){this.genre = genre; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users(String name, String image, String status, String genre) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.genre = genre;
    }
}


