package ua.kiev.univ.courses.project4.hotel.model;

/**
 * Class for request apartment.
 */
public class Apartment {
    /**
     * Apartment id - primary key in database.
     */
    private Long id;
    /**
     * Number of places in the room.
     */
    private Integer countPlaces;
    /**
     * Apartment class.
     */
    private ApartmentClass apartmentClass;
    /**
     * Price for apartment for 1 day.
     */
    private Double price;
    /**
     * Number of apartment.
     */
    private Integer number;

    public Apartment() {
    }

    public Apartment(Integer countPlaces, ApartmentClass apartmentClass, Integer number) {
        this.countPlaces = countPlaces;
        this.apartmentClass = apartmentClass;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", countPlaces=" + countPlaces +
                ", apartmentClass=" + apartmentClass +
                ", price=" + price +
                ", number=" + number +
                '}';
    }
}
