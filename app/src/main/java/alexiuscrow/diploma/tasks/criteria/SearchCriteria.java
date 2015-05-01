package alexiuscrow.diploma.tasks.criteria;

import alexiuscrow.diploma.util.geo.TravelMode;

/**
 * Created by Alexiuscrow on 25.04.2015.
 */

public class SearchCriteria {
    private Double lat = null;
    private Double lng = null;
    private Double radius = null;
    private TravelMode mode = null;

    public SearchCriteria(Double lat, Double lng, Double radius) {
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
    }

    public SearchCriteria(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public SearchCriteria(Double lat, Double lng, TravelMode mode, Double radius) {
        this.lat = lat;
        this.lng = lng;
        this.mode = mode;
        this.radius = radius;
    }

    public SearchCriteria(Double lat, Double lng, TravelMode mode) {
        this.lat = lat;
        this.lng = lng;
        this.mode = mode;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Double getRadius() {
        return radius;
    }

    public TravelMode getMode() {
        return mode;
    }

    public Boolean hasRadius(){
        if (radius != null) return true;
        else return false;
    }

    public Boolean hasMode(){
        if (mode != null) return true;
        else return false;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", radius=" + radius +
                ", mode=" + mode +
                '}';
    }
}
