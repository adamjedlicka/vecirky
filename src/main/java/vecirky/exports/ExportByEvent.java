package vecirky.exports;

import vecirky.models.Event;
/**
 * All methods in this class are used to gather relevant event data and produce information.
 */
public class ExportByEvent {


    public String description(Event event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\tPopis:\n");
        stringBuilder.append(event.getDescription());
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }   
    public String eventType(Event event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(event.getEventType());
        stringBuilder.append("\n\n\n");

        return stringBuilder.toString();
    }   
    public String address(Event event) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Lokace:\n");
        stringBuilder.append(event.getAddress().getCity() + "\n");
        stringBuilder.append(event.getAddress().getStreet() + " ");
        stringBuilder.append(event.getAddress().getNumberOfDescriptive());
        stringBuilder.append("\n\n");

        return stringBuilder.toString();
    }   
    public String eventDate(Event event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Datum konání:\n");
        stringBuilder.append(event.getEventDate());
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }   
    public String client(Event event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Klient:\n");
        stringBuilder.append(event.getClient() + "\n");
        stringBuilder.append(event.getClient().getEmail() + "\n");
        stringBuilder.append(event.getClient().getPhoneNumber());
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }    
    public String mainPromoter(Event event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Organizátor:\n");
        stringBuilder.append(event.getMainPromoter() + "\n");
        stringBuilder.append(event.getMainPromoter().getEmail() + "\n");
        stringBuilder.append(event.getMainPromoter().getPhoneNumber());
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }    
    public String price(Event event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cena:\n");
        stringBuilder.append(event.getPrice());
        stringBuilder.append(",-");
        return stringBuilder.toString();
    }    

}