package vecirky.models;

public class Address {

    private Integer id;

    private String street;

    private Integer numberOfDescriptive;

    private String city;

    private Integer zipCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumberOfDescriptive() {
        return numberOfDescriptive;
    }

    public void setNumberOfDescriptive(Integer numberOfDescriptive) {
        this.numberOfDescriptive = numberOfDescriptive;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return street + " " + numberOfDescriptive + ", " + city + ", " + zipCode;
    }
}
