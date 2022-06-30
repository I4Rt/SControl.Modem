package com.i4rt.demo.model.algs;

public class FixPoint {
    private double doubleData;
    private Long fixPointData;

    public double getDoubleData() {
        return doubleData;
    }

    public Long getFixPointData() {
        return fixPointData;
    }
    public Long getFixPointDataHEX() {
        return fixPointData;
    }

    public FixPoint(String rowData) {
        try{
            this.fixPointData = Long.parseLong(rowData);
            this.doubleData = fixPointData / 92233720.0;
        } catch (NumberFormatException e) {
            this.doubleData = Double.parseDouble(rowData);
            this.fixPointData = Math.round(doubleData * 92233720);
        }
    }


}
