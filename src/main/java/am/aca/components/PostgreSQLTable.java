package am.aca.components;

import java.util.List;
import java.util.ArrayList;

public class PostgreSQLTable {

    private List<Column> columns;
    private List<Constraint> constraints;

    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String selfReferencingColumnName;
    private String referenceGeneration;
    private String userDefinedTypeCatalog;
    private String userDefinedTypeSchema;
    private String userDefinedTypeName;
    private String isInsertableInto;
    private String isTyped;
    private String commitAction;

    public PostgreSQLTable(String name){
        this.tableName = name;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    public PostgreSQLTable(
            String tableCatalog, String tableSchema, String tableName,
            String tableType, String selfReferencingColumnName, String referenceGeneration,
            String userDefinedTypeCatalog, String userDefinedTypeSchema, String userDefinedTypeName,
            String isInsertableInto, String isTyped, String commitAction) {

        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();

        this.tableCatalog = tableCatalog;
        this.tableSchema = tableSchema;
        this.tableName = tableName;
        this.tableType = tableType;
        this.selfReferencingColumnName = selfReferencingColumnName;
        this.referenceGeneration = referenceGeneration;
        this.userDefinedTypeCatalog = userDefinedTypeCatalog;
        this.userDefinedTypeSchema = userDefinedTypeSchema;
        this.userDefinedTypeName = userDefinedTypeName;
        this.isInsertableInto = isInsertableInto;
        this.isTyped = isTyped;
        this.commitAction = commitAction;
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

    public String getSelfReferencingColumnName() {
        return selfReferencingColumnName;
    }

    public String getReferenceGeneration() {
        return referenceGeneration;
    }

    public String getUserDefinedTypeCatalog() {
        return userDefinedTypeCatalog;
    }

    public String getUserDefinedTypeSchema() {
        return userDefinedTypeSchema;
    }

    public String getUserDefinedTypeName() {
        return userDefinedTypeName;
    }

    public String getIsInsertableInto() {
        return isInsertableInto;
    }

    public String getIsTyped() {
        return isTyped;
    }

    public String getCommitAction() {
        return commitAction;
    }

    @Override
    public String toString() {
        return "PostgreSQLTable{" +
                "tableName='" + tableName + '\'' +
                '}';
    }
}
