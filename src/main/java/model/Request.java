package model;

import java.util.Date;

/**
 * Class for request description.
 */
public class Request {
    /**
     * Request id - primary key in database.
     */
    private Long id;
    /**
     * User who has left the request.
     */
    private User client;
    /**
     * The desired number of seats in the room.
     */
    private Integer countPlaces;
    /**
     * Class apartments.
     */
    private ApartmentClass apartmentClass;
    /**
     * Desired date of settlement.
     */
    private Date busyFrom;
    /**
     * Desired date of eviction.
     */
    private Date busyTo;

    public Request() {
    }

    public Request(User client, Integer countPlaces, ApartmentClass apartmentClass, Date busyFrom, Date busyTo) {
        this.client = client;
        this.countPlaces = countPlaces;
        this.apartmentClass = apartmentClass;
        this.busyFrom = busyFrom;
        this.busyTo = busyTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Integer getCountPlaces() {
        return countPlaces;
    }

    public void setCountPlaces(Integer countPlaces) {
        this.countPlaces = countPlaces;
    }

    public ApartmentClass getApartmentClass() {
        return apartmentClass;
    }

    public void setApartmentClass(ApartmentClass apartmentClass) {
        this.apartmentClass = apartmentClass;
    }

    public Date getBusyFrom() {
        return busyFrom;
    }

    public void setBusyFrom(Date busyFrom) {
        this.busyFrom = busyFrom;
    }

    public Date getBusyTo() {
        return busyTo;
    }

    public void setBusyTo(Date busyTo) {
        this.busyTo = busyTo;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", client=" + client.getId() +
                ", countPlaces=" + countPlaces +
                ", apartmentClass=" + apartmentClass +
                ", busyFrom=" + busyFrom +
                ", busyTo=" + busyTo +
                '}';
    }
}
