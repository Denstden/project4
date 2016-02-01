package ua.kiev.univ.courses.project4.hotel.model;

/**
 * Class for invoice description.
 */
public class Invoice {
    /**
     * Invoice id - primary key in database.
     */
    private Long id;
    /**
     * User for whom the payment is fixed.
     */
    private User client;
    /**
     * Selected apartment.
     */
    private Apartment apartment;
    /**
     * Accommodation price.
     */
    private Double price;

    public Invoice() {
    }

    public Invoice(User client, Apartment apartment, Double price) {
        this.client = client;
        this.apartment = apartment;
        this.price = price;
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

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", client=" + client.getId() +
                ", apartment=" + apartment.getId() +
                ", price=" + price +
                '}';
    }
}
