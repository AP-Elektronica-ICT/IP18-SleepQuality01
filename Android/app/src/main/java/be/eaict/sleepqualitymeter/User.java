package be.eaict.sleepqualitymeter;

import java.util.Calendar;

/**
 * Created by sande on 22/02/2018.
 */

public class User {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String country;
    private int weight;
    private String birthdate;

    public User(){

    }

    public User(String id, String firstname, String lastname, String email, String country, int weight, String birthdate){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.country = country;
        this.weight = weight;
        this.birthdate = birthdate;
    }

    public String getId() { return id; }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() { return lastname; }

    public String getEmail() {
        return email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getCountry() {
        return country;
    }

    public int getWeight() {
        return weight;
    }
}
