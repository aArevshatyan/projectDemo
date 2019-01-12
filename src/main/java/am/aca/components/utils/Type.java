package am.aca.components.utils;

public enum Type {
    CHAR, //length
    VARCHAR, //length
    TINYTEXT,
    TEXT,
    MEDIUMTEXT,
    LONGTEXT,
    TINYINT, //length
    SMALLINT, //length
    MEDIUMINT, //length
    INT, //length
    BIGINT, //length
    FLOAT, //length, decimals
    DOUBLE, //length , decimals
    DECIMAL, //length , decimals
    DATE,
    DATETIME,
    TIMESTAMP,
    TIME,
    ENUM,
    SET;

    private final Integer length ;
    private final Integer decimals;
    Type(Integer length, Integer decimals) {
        this.length = length;
        this.decimals = decimals;
    }
    Type(Integer length) {
        this.length = length;
        this.decimals = null;
    }
    Type() {
        this.length = null;
        this.decimals = null;
    }
}
// source http://www.peachpit.com/articles/article.aspx?p=1752305&seqNum=2