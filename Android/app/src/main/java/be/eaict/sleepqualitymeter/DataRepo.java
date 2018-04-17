package be.eaict.sleepqualitymeter;

import java.util.ArrayList;
import java.util.List;

public class DataRepo {
    public List<Data> Repo = new ArrayList<>();
    public String Date;

    public DataRepo(String Date, List<Data> repo){
        this.Date = Date;
        this.Repo = repo;
    }

    public void addData(Data data){
        Repo.add(data);
    }
}
