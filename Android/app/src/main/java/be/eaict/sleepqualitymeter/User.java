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
    private String bithdate;

    public User(String id, String firstname, String lastname, String email, String country, int weight, String bithdate){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.country = country;
        this.weight = weight;
        this.bithdate = bithdate;
    }

    public String getId() { return id; }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() { return lastname; }

    public String getEmail() {
        return email;
    }

    public String getC() {
        return bithdate;
    }

    public String getCountry() {
        return country;
    }

    public int getWeight() {
        return weight;
    }
}
