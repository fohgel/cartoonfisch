package de.fohgel.cartoonfisch.controller;

import java.util.List;

public class Data {

    private String url = "https://storage.cloud.google.com/quickdraw_dataset/full/binary/";

    public void setOhbjectList(List<String> ohbjects) {
        new Puppy("Tommy");
        for (String s: ohbjects){
            new ATDownload(url, s);
        }
    }

    // liste bekannte objekte
    // mit position und größen

    public class Puppy {
        public Puppy(String name) {
            System.out.println("The puppy's name is: " + name);
        }
    }

}
