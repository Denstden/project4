package ua.kiev.univ.courses.project4.hotel.model;

import java.util.Date;

/**
 * Class for busy apartment description.
 */
public class BusyApartment {
    /**
     * Busy apartment id - primary key in database.
     */
    private Long id;
    /**
     * Apartment that is occupied.
     */
    private Apartment apartment;
    /**
     * Date from apartment is occupied.
     */
    private Date dateFrom;
    /**
     * Date to apartment is occupied.
     */
    private Date dateTo;

    public BusyApartment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "BusyApartment{" +
                "id=" + id +
                ", apartment=" + apartment.getId() +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
