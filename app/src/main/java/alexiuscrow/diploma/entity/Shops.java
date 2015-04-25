package alexiuscrow.diploma.entity;

import java.util.HashSet;
import java.util.Set;


public class Shops {
    protected Integer id;
    protected String name;
    protected Categories category;
    protected Double latitude;
    protected Double longitude;
    protected String address;
    private Double distance;
    private Set<Discounts> discounts = new HashSet<Discounts>(0);

    public Shops() {}

    public Shops(Integer id, String name, Categories category, Double latitude,
                 Double longitude, String address) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }


    public Shops(Integer id, String name, Categories category, Double latitude,
                 Double longitude, String address,
                 Double distance, Set<Discounts> discounts) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.distance = distance;
        this.discounts = discounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public Set<Discounts> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<Discounts> discounts) {
        this.discounts = discounts;
    }

    @Override
    public String toString() {
        return String
                .format("Shops [id=%s, name=%s, category=%s, latitude=%s, longitude=%s, address=%s, distance=%s, discounts=%s]",
                        id, name, category, latitude, longitude,
                        address, distance, discounts);
    }


}
