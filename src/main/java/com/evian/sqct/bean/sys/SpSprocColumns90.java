package com.evian.sqct.bean.sys;

/**
 * ClassName:SpSprocColumns90
 * Package:com.evian.sqct.bean.sys
 * Description:系统存储过程 sys.sp_sproc_columns_90 查询存储过程的参数
 *
 * @Date:2020/6/2 17:34
 * @Author:XHX
 */
public class SpSprocColumns90 {

    private String PROCEDURE_QUALIFIER;
    private String PROCEDURE_OWNER;
    /** 存储过程昵称 */
    private String PROCEDURE_NAME;
    /** 字段名 */
    private String COLUMN_NAME;
    /** 列类型
     *  5 系统回复参数
     *  2 输出参数
     *  1 入参
     * */
    private Short COLUMN_TYPE;
    /** 数据类型 */
    private Short DATA_TYPE;

    private String TYPE_NAME;
    private Integer PRECISION;
    private Integer LENGTH;
    private Short SCALE;
    private Short RADIX;
    /**
     * 是否可以为空
     * 0 NO
     * 1 YES
     * */
    private Short NULLABLE;
    private Integer REMARKS;
    private Integer COLUMN_DEF;
    private Short SQL_DATA_TYPE;
    private Short SQL_DATETIME_SUB;
    private Integer CHAR_OCTET_LENGTH;
    private Integer ORDINAL_POSITION;
    /**
     * 是否允许为空
     */
    private String IS_NULLABLE;
    private String SS_UDT_CATALOG_NAME;
    private String SS_UDT_SCHEMA_NAME;
    private String SS_UDT_ASSEMBLY_TYPE_NAME;
    private String SS_XML_SCHEMACOLLECTION_CATALOG_NAME;
    private String SS_XML_SCHEMACOLLECTION_SCHEMA_NAME;
    private String SS_XML_SCHEMACOLLECTION_NAME;
    private Short SS_DATA_TYPE;

    public String getPROCEDURE_QUALIFIER() {
        return PROCEDURE_QUALIFIER;
    }

    public void setPROCEDURE_QUALIFIER(String PROCEDURE_QUALIFIER) {
        this.PROCEDURE_QUALIFIER = PROCEDURE_QUALIFIER;
    }

    public String getPROCEDURE_OWNER() {
        return PROCEDURE_OWNER;
    }

    public void setPROCEDURE_OWNER(String PROCEDURE_OWNER) {
        this.PROCEDURE_OWNER = PROCEDURE_OWNER;
    }

    public String getPROCEDURE_NAME() {
        return PROCEDURE_NAME;
    }

    public void setPROCEDURE_NAME(String PROCEDURE_NAME) {
        this.PROCEDURE_NAME = PROCEDURE_NAME;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public Short getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    public void setCOLUMN_TYPE(Short COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE;
    }

    public Short getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(Short DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }

    public Integer getPRECISION() {
        return PRECISION;
    }

    public void setPRECISION(Integer PRECISION) {
        this.PRECISION = PRECISION;
    }

    public Integer getLENGTH() {
        return LENGTH;
    }

    public void setLENGTH(Integer LENGTH) {
        this.LENGTH = LENGTH;
    }

    public Short getSCALE() {
        return SCALE;
    }

    public void setSCALE(Short SCALE) {
        this.SCALE = SCALE;
    }

    public Short getRADIX() {
        return RADIX;
    }

    public void setRADIX(Short RADIX) {
        this.RADIX = RADIX;
    }

    public Short getNULLABLE() {
        return NULLABLE;
    }

    public void setNULLABLE(Short NULLABLE) {
        this.NULLABLE = NULLABLE;
    }

    public Integer getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(Integer REMARKS) {
        this.REMARKS = REMARKS;
    }

    public Integer getCOLUMN_DEF() {
        return COLUMN_DEF;
    }

    public void setCOLUMN_DEF(Integer COLUMN_DEF) {
        this.COLUMN_DEF = COLUMN_DEF;
    }

    public Short getSQL_DATA_TYPE() {
        return SQL_DATA_TYPE;
    }

    public void setSQL_DATA_TYPE(Short SQL_DATA_TYPE) {
        this.SQL_DATA_TYPE = SQL_DATA_TYPE;
    }

    public Short getSQL_DATETIME_SUB() {
        return SQL_DATETIME_SUB;
    }

    public void setSQL_DATETIME_SUB(Short SQL_DATETIME_SUB) {
        this.SQL_DATETIME_SUB = SQL_DATETIME_SUB;
    }

    public Integer getCHAR_OCTET_LENGTH() {
        return CHAR_OCTET_LENGTH;
    }

    public void setCHAR_OCTET_LENGTH(Integer CHAR_OCTET_LENGTH) {
        this.CHAR_OCTET_LENGTH = CHAR_OCTET_LENGTH;
    }

    public Integer getORDINAL_POSITION() {
        return ORDINAL_POSITION;
    }

    public void setORDINAL_POSITION(Integer ORDINAL_POSITION) {
        this.ORDINAL_POSITION = ORDINAL_POSITION;
    }

    public String getIS_NULLABLE() {
        return IS_NULLABLE;
    }

    public void setIS_NULLABLE(String IS_NULLABLE) {
        this.IS_NULLABLE = IS_NULLABLE;
    }

    public String getSS_UDT_CATALOG_NAME() {
        return SS_UDT_CATALOG_NAME;
    }

    public void setSS_UDT_CATALOG_NAME(String SS_UDT_CATALOG_NAME) {
        this.SS_UDT_CATALOG_NAME = SS_UDT_CATALOG_NAME;
    }

    public String getSS_UDT_SCHEMA_NAME() {
        return SS_UDT_SCHEMA_NAME;
    }

    public void setSS_UDT_SCHEMA_NAME(String SS_UDT_SCHEMA_NAME) {
        this.SS_UDT_SCHEMA_NAME = SS_UDT_SCHEMA_NAME;
    }

    public String getSS_UDT_ASSEMBLY_TYPE_NAME() {
        return SS_UDT_ASSEMBLY_TYPE_NAME;
    }

    public void setSS_UDT_ASSEMBLY_TYPE_NAME(String SS_UDT_ASSEMBLY_TYPE_NAME) {
        this.SS_UDT_ASSEMBLY_TYPE_NAME = SS_UDT_ASSEMBLY_TYPE_NAME;
    }

    public String getSS_XML_SCHEMACOLLECTION_CATALOG_NAME() {
        return SS_XML_SCHEMACOLLECTION_CATALOG_NAME;
    }

    public void setSS_XML_SCHEMACOLLECTION_CATALOG_NAME(String SS_XML_SCHEMACOLLECTION_CATALOG_NAME) {
        this.SS_XML_SCHEMACOLLECTION_CATALOG_NAME = SS_XML_SCHEMACOLLECTION_CATALOG_NAME;
    }

    public String getSS_XML_SCHEMACOLLECTION_SCHEMA_NAME() {
        return SS_XML_SCHEMACOLLECTION_SCHEMA_NAME;
    }

    public void setSS_XML_SCHEMACOLLECTION_SCHEMA_NAME(String SS_XML_SCHEMACOLLECTION_SCHEMA_NAME) {
        this.SS_XML_SCHEMACOLLECTION_SCHEMA_NAME = SS_XML_SCHEMACOLLECTION_SCHEMA_NAME;
    }

    public String getSS_XML_SCHEMACOLLECTION_NAME() {
        return SS_XML_SCHEMACOLLECTION_NAME;
    }

    public void setSS_XML_SCHEMACOLLECTION_NAME(String SS_XML_SCHEMACOLLECTION_NAME) {
        this.SS_XML_SCHEMACOLLECTION_NAME = SS_XML_SCHEMACOLLECTION_NAME;
    }

    public Short getSS_DATA_TYPE() {
        return SS_DATA_TYPE;
    }

    public void setSS_DATA_TYPE(Short SS_DATA_TYPE) {
        this.SS_DATA_TYPE = SS_DATA_TYPE;
    }

    @Override
    public String toString() {
        return "SpSprocColumns90 [" +
                "PROCEDURE_QUALIFIER=" + PROCEDURE_QUALIFIER +
                ", PROCEDURE_OWNER=" + PROCEDURE_OWNER +
                ", PROCEDURE_NAME=" + PROCEDURE_NAME +
                ", COLUMN_NAME=" + COLUMN_NAME +
                ", COLUMN_TYPE=" + COLUMN_TYPE +
                ", DATA_TYPE=" + DATA_TYPE +
                ", TYPE_NAME=" + TYPE_NAME +
                ", PRECISION=" + PRECISION +
                ", LENGTH=" + LENGTH +
                ", SCALE=" + SCALE +
                ", RADIX=" + RADIX +
                ", NULLABLE=" + NULLABLE +
                ", REMARKS=" + REMARKS +
                ", COLUMN_DEF=" + COLUMN_DEF +
                ", SQL_DATA_TYPE=" + SQL_DATA_TYPE +
                ", SQL_DATETIME_SUB=" + SQL_DATETIME_SUB +
                ", CHAR_OCTET_LENGTH=" + CHAR_OCTET_LENGTH +
                ", ORDINAL_POSITION=" + ORDINAL_POSITION +
                ", IS_NULLABLE=" + IS_NULLABLE +
                ", SS_UDT_CATALOG_NAME=" + SS_UDT_CATALOG_NAME +
                ", SS_UDT_SCHEMA_NAME=" + SS_UDT_SCHEMA_NAME +
                ", SS_UDT_ASSEMBLY_TYPE_NAME=" + SS_UDT_ASSEMBLY_TYPE_NAME +
                ", SS_XML_SCHEMACOLLECTION_CATALOG_NAME=" + SS_XML_SCHEMACOLLECTION_CATALOG_NAME +
                ", SS_XML_SCHEMACOLLECTION_SCHEMA_NAME=" + SS_XML_SCHEMACOLLECTION_SCHEMA_NAME +
                ", SS_XML_SCHEMACOLLECTION_NAME=" + SS_XML_SCHEMACOLLECTION_NAME +
                ", SS_DATA_TYPE=" + SS_DATA_TYPE +
                ']';
    }
}
