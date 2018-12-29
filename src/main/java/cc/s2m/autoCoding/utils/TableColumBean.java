package cc.s2m.autoCoding.utils;

import java.io.Serializable;

public class TableColumBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String type;
    private String javaProp;
    private String javaPropGet;
    private String javaPropSet;
    private String javaType;
    private boolean canNull;
    private boolean isKey = false;
    private boolean isAutoIncrement  = false;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean getCanNull() {
        return canNull;
    }
    public void setCanNull(boolean canNull) {
        this.canNull = canNull;
    }
    public boolean getIsKey() {
        return isKey;
    }
    public void setKey(boolean isKey) {
        this.isKey = isKey;
    }
    public String getJavaProp() {
        return javaProp;
    }
    public void setJavaProp(String javaProp) {
        this.javaProp = javaProp;
    }
    public String getJavaType() {
        return javaType;
    }
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
    public boolean getIsAutoIncrement() {
        return isAutoIncrement;
    }
    public void setAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }
    public String getJavaPropGet() {
        return javaPropGet;
    }
    public void setJavaPropGet(String javaPropGet) {
        this.javaPropGet = javaPropGet;
    }
    public String getJavaPropSet() {
        return javaPropSet;
    }
    public void setJavaPropSet(String javaPropSet) {
        this.javaPropSet = javaPropSet;
    }
}
