package be.eaict.sleepqualitymeter;

import java.util.Calendar;

/**
 * Created by sande on 22/02/2018.
 */

public class User {
    private String id;
    private String name;
    private String email;
    private String country;
    private int weight;
    private Calendar c;

    public User(String id, String name, String email, String country, int weight, Calendar c){
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.weight = weight;
        this.c = c;
    }

    public String getId() { return id; }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Calendar getC() {
        return c;
    }

    public String getCountry() {
        return country;
    }

    public int getWeight() {
        return weight;
    }
}
