package vecirky.repositories.database;

import vecirky.C;
import vecirky.models.Event;
import vecirky.models.Promoter;
import vecirky.repositories.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseEventRepository implements EventRepository {

    private Connection conn = C.get(Connection.class);

    private EventTypeRepository eventTypeRepo = C.get(EventTypeRepository.class);

    private PromoterRepository promoterRepo = C.get(PromoterRepository.class);

    private ClientRepository clientRepo = C.get(ClientRepository.class);

    private AddressRepository addressRepo = C.get(AddressRepository.class);

    @Override
    public Optional<Event> findBy(Integer key) {
        String sql = "SELECT * FROM events WHERE id = ?;";

        Event event = new Event();

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setInt(1, key);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                event.setId(rs.getInt("id"));
                event.setDescription(rs.getString("description"));
                event.setTasks(rs.getString("tasks"));
                event.setEventDate(rs.getDate("event_date"));
                event.setCancellationDate(Optional.ofNullable(rs.getDate("cancellation_date")));
                event.setPrice(rs.getInt("price"));

                event.setClient(clientRepo.findBy(rs.getInt("client_id")).get());
                event.setMainPromoter(promoterRepo.findBy(rs.getInt("promoter_id")).get());
                event.setEventType(eventTypeRepo.findBy(rs.getInt("event_type_id")).get());
                event.setAddress(addressRepo.findBy(rs.getInt("address_id")).get());

                loadPromoters(event);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return Optional.of(event);
    }

    @Override
    public List<Event> findAll() {
        String sql = "SELECT * FROM events;";

        List<Event> events = new ArrayList<>();

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Event event = new Event();

                event.setId(rs.getInt("id"));
                event.setDescription(rs.getString("description"));
                event.setTasks(rs.getString("tasks"));
                event.setEventDate(rs.getDate("event_date"));
                event.setCancellationDate(Optional.ofNullable(rs.getDate("cancellation_date")));
                event.setPrice(rs.getInt("price"));

                event.setClient(clientRepo.findBy(rs.getInt("client_id")).get());
                event.setMainPromoter(promoterRepo.findBy(rs.getInt("promoter_id")).get());
                event.setEventType(eventTypeRepo.findBy(rs.getInt("event_type_id")).get());
                event.setAddress(addressRepo.findBy(rs.getInt("address_id")).get());

                loadPromoters(event);

                events.add(event);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return events;
    }

    @Override
    public Integer create(Event event) {
        String sql = "INSERT INTO events (description, tasks, event_date, cancellation_date, price, "
                + "client_id, promoter_id, event_type_id, address_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, event.getDescription());
            stmnt.setString(2, event.getTasks());
            stmnt.setDate(3, new Date(event.getEventDate().getTime()));

            if (event.getCancellationDate().isPresent()) {
                stmnt.setDate(4, new Date(event.getCancellationDate().get().getTime()));
            } else {
                stmnt.setString(4, null);
            }

            stmnt.setInt(5, event.getPrice());
            stmnt.setInt(6, event.getClient().getId());
            stmnt.setInt(7, event.getMainPromoter().getId());
            stmnt.setInt(8, event.getEventType().getId());
            stmnt.setInt(9, event.getAddress().getId());

            stmnt.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

        sql = "SELECT last_insert_rowid()";
        try {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);

            event.setId(rs.getInt(1));

            syncPromoters(event);

            return event.getId();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Event event) {
        String sql = "UPDATE events SET "
                + "description = ?, tasks = ?, event_date = ?, cancellation_date = ?, price = ?, "
                + "client_id = ?, promoter_id = ?, event_type_id = ?, address_id = ? WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, event.getDescription());
            stmnt.setString(2, event.getTasks());
            stmnt.setDate(3, new Date(event.getEventDate().getTime()));

            if (event.getCancellationDate().isPresent()) {
                stmnt.setDate(4, new Date(event.getCancellationDate().get().getTime()));
            } else {
                stmnt.setString(4, null);
            }

            stmnt.setInt(5, event.getPrice());
            stmnt.setInt(6, event.getClient().getId());
            stmnt.setInt(7, event.getMainPromoter().getId());
            stmnt.setInt(8, event.getEventType().getId());
            stmnt.setInt(9, event.getAddress().getId());
            stmnt.setInt(10, event.getId());

            stmnt.execute();

            syncPromoters(event);

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Event event) {
        String sql = "DELETE FROM events WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setInt(1, event.getId());
            stmnt.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean syncPromoters(Event event) {
        String sql = "DELETE FROM events_promoters WHERE event_id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setInt(1, event.getId());
            stmnt.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        sql = "INSERT INTO events_promoters (event_id, promoter_id) VALUES (?, ?);";

        for (Promoter promoter : event.getMinorPromoters()) {
            try {
                PreparedStatement stmnt = conn.prepareStatement(sql);
                stmnt.setInt(1, event.getId());
                stmnt.setInt(2, promoter.getId());
                stmnt.execute();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }

        return true;
    }

    private boolean loadPromoters(Event event) {
        String sql = "SELECT * FROM events_promoters WHERE event_id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setInt(1, event.getId());
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Promoter promoter = promoterRepo.findBy(rs.getInt("promoter_id")).get();
                event.getMinorPromoters().add(promoter);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }
}
