package vecirky.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Event {

    private Integer id;

    private String description;

    private String tasks;

    private Date eventDate;

    private Optional<Date> cancellationDate;

    private Integer price;

    private Address address;

    private EventType eventType;

    private Client client;

    private Promoter mainPromoter;

    private List<Promoter> minorPromoters;

    public Event() {
        eventDate = new Date();
        cancellationDate = Optional.empty();
        address = new Address();
        minorPromoters = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Optional<Date> getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Optional<Date> cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Promoter getMainPromoter() {
        return mainPromoter;
    }

    public void setMainPromoter(Promoter mainPromoter) {
        this.mainPromoter = mainPromoter;
    }

    public List<Promoter> getMinorPromoters() {
        return minorPromoters;
    }

    public void setMinorPromoters(List<Promoter> minorPromoters) {
        this.minorPromoters = minorPromoters;
    }

    @Override
    public String toString() {
        return eventType.getName() + " " + eventDate.toString();
    }

}
