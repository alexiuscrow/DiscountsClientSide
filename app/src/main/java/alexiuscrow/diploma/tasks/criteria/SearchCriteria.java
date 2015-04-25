package alexiuscrow.diploma.tasks.criteria;

/**
 * Created by Alexiuscrow on 25.04.2015.
 */
public class SearchCriteria {
    private Double lat = null;
    private Double lng = null;
    private Double radius = null;

    public SearchCriteria(Double lat, Double lng, Double radius) {
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
    }

    public SearchCriteria(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
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

    public Boolean hasRadius(){
        if (radius != null) return true;
        else return false;
    }

    @Override
    public String toString() {
        if (radius != null)
            return "SearchCriteria{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    ", radius=" + radius +
                    '}';
        else
            return "SearchCriteria{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
    }
}
