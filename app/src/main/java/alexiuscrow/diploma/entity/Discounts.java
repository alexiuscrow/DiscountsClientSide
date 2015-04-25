package alexiuscrow.diploma.entity;

import java.util.Date;


public class Discounts {
    private Integer id;
    private String title;
    private Date startDate;
    private Date endDate;
    private String description;
    private String imageUrl;

    public Discounts() {
    }

    public Discounts(Integer id, String title, Date startDate, Date endDate,
                     String description, String imageUrl) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return String
                .format("Discounts [id=%s, title=%s, startDate=%s, endDate=%s, description=%s, imageUrl=%s]",
                        id, title, startDate, endDate, description, imageUrl);
    }

}
