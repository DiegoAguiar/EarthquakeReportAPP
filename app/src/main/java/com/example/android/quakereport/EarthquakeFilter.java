package com.example.android.quakereport;

/**
 * Created by diego.almeida on 18/05/2017.
 */
public class EarthquakeFilter {
    private String minMagnitude,format,limit,orderBy;

    public EarthquakeFilter(String format, String minMagnitude, String limit, String orderBy) {
        this.format = format;
        this.minMagnitude = minMagnitude;
        this.limit = limit;
        this.orderBy = orderBy;
    }

    public String getMinMagnitude() {
        return minMagnitude;
    }

    public String getFormat() {
        return format;
    }

    public String getLimit() {
        return limit;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
