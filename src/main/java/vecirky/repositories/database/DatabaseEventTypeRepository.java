package vecirky.repositories.database;

import vecirky.C;
import vecirky.models.EventType;
import vecirky.repositories.EventTypeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseEventTypeRepository implements EventTypeRepository {

    private Connection conn = C.get(Connection.class);

    @Override
    public Optional<EventType> findBy(Integer key) {
        String sql = "SELECT * FROM event_types WHERE id = ?;";
        EventType eventType = null;

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setInt(1, key);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                eventType = new EventType();
                eventType.setId(rs.getInt("id"));
                eventType.setName(rs.getString("name"));
                eventType.setDescription(rs.getString("description"));
                eventType.setTasks(rs.getString("tasks"));
                eventType.setPrice(rs.getInt("price"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return Optional.ofNullable(eventType);
    }

    @Override
    public List<EventType> findAll() {
        String sql = "SELECT * FROM event_types;";
        List<EventType> eventTypes = new ArrayList<>();

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                EventType eventType = new EventType();
                eventType.setId(rs.getInt("id"));
                eventType.setName(rs.getString("name"));
                eventType.setDescription(rs.getString("description"));
                eventType.setTasks(rs.getString("tasks"));
                eventType.setPrice(rs.getInt("price"));
                eventTypes.add(eventType);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return eventTypes;
    }

    @Override
    public Integer create(EventType eventType) {
        String sql = "INSERT INTO event_types (name, description, tasks, price) VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, eventType.getName());
            stmnt.setString(2, eventType.getDescription());
            stmnt.setString(3, eventType.getTasks());
            stmnt.setInt(4, eventType.getPrice());

            stmnt.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        sql = "SELECT last_insert_rowid()";
        try {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);

            eventType.setId(rs.getInt(1));
            return eventType.getId();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(EventType eventType) {
        String sql = "UPDATE event_types SET name = ?, description = ?, tasks = ?, price = ? WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, eventType.getName());
            stmnt.setString(2, eventType.getDescription());
            stmnt.setString(3, eventType.getTasks());
            stmnt.setInt(4, eventType.getPrice());
            stmnt.setInt(5, eventType.getId());
            stmnt.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(EventType eventType) {
        String sql = "DELETE FROM event_types WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setInt(1, eventType.getId());
            stmnt.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
