package com.example.apifox.view.table;

public abstract class Column<T> {
    protected String label;
    protected String prop;
    protected Column(String label, String prop) {
        this.label = label;
        this.prop = prop;
    }
    public String getLabel() {
        return this.label;
    }
    public String getProp() {
        return this.prop;
    }

    public abstract String getValue(T row);
}
