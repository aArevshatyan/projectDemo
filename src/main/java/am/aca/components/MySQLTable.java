package am.aca.components;

import java.util.List;
import java.util.ArrayList;

public class MySQLTable {

    private List<Column> columns;
    private List<Constraint> constraints;

    private String tableCatalog ;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String engine;
    private String version;
    private String rowFormat;
    private String tableRows;
    private String avgRowLength;
    private String dataLength;
    private String maxDataLength;
    private String indexLength;
    private String dataFree;
    private String autoIncrement;
    private String createTime;
    private String updateTime;
    private String checkTime;
    private String tableCollation;
    private String checksum;
    private String createOptions;
    private String tableComment;

    public MySQLTable(String name){
        this.tableName = name;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    public MySQLTable(
            String tableCatalog, String tableSchema, String tableName,
            String tableType, String engine, String version, String rowFormat,
            String tableRows, String avgRowLength, String dataLength,
            String maxDataLength, String indexLength, String dataFree,
            String autoIncrement, String createTime, String updateTime,
            String checkTime, String tableCollation, String checksum,
            String createOptions, String tableComment) {

        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();

        this.tableCatalog = tableCatalog;
        this.tableSchema = tableSchema;
        this.tableName = tableName;
        this.tableType = tableType;
        this.engine = engine;
        this.version = version;
        this.rowFormat = rowFormat;
        this.tableRows = tableRows;
        this.avgRowLength = avgRowLength;
        this.dataLength = dataLength;
        this.maxDataLength = maxDataLength;
        this.indexLength = indexLength;
        this.dataFree = dataFree;
        this.autoIncrement = autoIncrement;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.checkTime = checkTime;
        this.tableCollation = tableCollation;
        this.checksum = checksum;
        this.createOptions = createOptions;
        this.tableComment = tableComment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public String getEngine() {
        return engine;
    }

    public String getVersion() {
        return version;
    }

    public String getRowFormat() {
        return rowFormat;
    }

    public String getTableRows() {
        return tableRows;
    }

    public String getAvgRowLength() {
        return avgRowLength;
    }

    public String getDataLength() {
        return dataLength;
    }

    public String getMaxDataLength() {
        return maxDataLength;
    }

    public String getIndexLength() {
        return indexLength;
    }

    public String getDataFree() {
        return dataFree;
    }

    public String getAutoIncrement() {
        return autoIncrement;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public String getTableCollation() {
        return tableCollation;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getCreateOptions() {
        return createOptions;
    }

    public String getTableComment() {
        return tableComment;
    }


    @Override
    public String toString() {
        return "MySQLTable{" +
                "tableName='" + tableName + '\'' +
                '}';
    }
}
