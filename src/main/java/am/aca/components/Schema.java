package am.aca.components;


import java.util.*;

public class Schema<T> {

    private List<T> tables;

    public Schema() {
        this.tables = new ArrayList<>();
    }

    public List<T> getTables() {
        return tables;
    }

    //private List<Procedure> procedures;
    //private List<View> views ;
    //private List<Trigger> triggers ;
    //sequences
    //synonyms
    //indexes
    //clusters
    //database links
    //snapshots
    //functions
    //packages

}
