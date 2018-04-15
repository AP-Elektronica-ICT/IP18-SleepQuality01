package be.eaict.sleepqualitymeter;

import java.util.ArrayList;
import java.util.List;

public class DataRepo {
    public List<Data> Repo = new ArrayList<>();
    public String Date;

    public DataRepo(String Date){
        this.Date = Date;
    }

    public void addData(Data data){
        Repo.add(data);
    }
}
