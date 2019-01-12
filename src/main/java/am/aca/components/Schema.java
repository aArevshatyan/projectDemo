package am.aca.components;

import java.util.List;
import java.util.ArrayList;

public class Schema {

    private List<Table> tables;

    public Schema() {
        this.tables = new ArrayList<>();
    }

    public List<Table> getTables() {
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
