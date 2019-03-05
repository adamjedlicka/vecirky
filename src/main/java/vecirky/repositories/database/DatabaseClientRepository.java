package vecirky.repositories.database;

import vecirky.C;
import vecirky.models.Client;
import vecirky.repositories.ClientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseClientRepository implements ClientRepository {

    private Connection conn = C.get(Connection.class);

    @Override
    public Optional<Client> findBy(Integer key) {
        Client client = null;
        String sql = "SELECT * FROM clients WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setInt(1, key);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id"));
                client.setTitle(rs.getString("title"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setEmail(rs.getString("email"));
                client.setPhoneNumber(rs.getString("phone_number"));
                client.setDescription(rs.getString("description"));
                client.setVarSymbol(rs.getString("variable_symbol"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return Optional.ofNullable(client);
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM clients;";
        List<Client> clients = new ArrayList<>();

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setTitle(rs.getString("title"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setEmail(rs.getString("email"));
                client.setPhoneNumber(rs.getString("phone_number"));
                client.setDescription(rs.getString("description"));
                client.setVarSymbol(rs.getString("variable_symbol"));
                clients.add(client);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return clients;
    }

    @Override
    public Integer create(Client client) {
        String sql = "INSERT INTO clients "
                + "(title, first_name, last_name, email, phone_number, description, variable_symbol) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, client.getTitle());
            stmnt.setString(2, client.getFirstName());
            stmnt.setString(3, client.getLastName());
            stmnt.setString(4, client.getEmail());
            stmnt.setString(5, client.getPhoneNumber());
            stmnt.setString(6, client.getDescription());
            stmnt.setString(7, client.getVarSymbol());
            stmnt.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        sql = "SELECT last_insert_rowid()";
        try {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);

            client.setId(rs.getInt(1));
            return client.getId();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Client client) {
        String sql = "UPDATE clients SET title = ?, first_name = ?, last_name = ?, email = ?, "
                + "phone_number = ?, description = ?, variable_symbol = ? WHERE id = ?;";
        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, client.getTitle());
            stmnt.setString(2, client.getFirstName());
            stmnt.setString(3, client.getLastName());
            stmnt.setString(4, client.getEmail());
            stmnt.setString(5, client.getPhoneNumber());
            stmnt.setString(6, client.getDescription());
            stmnt.setString(7, client.getVarSymbol());
            stmnt.setInt(8, client.getId());
            stmnt.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Client client) {
        String sql = "DELETE FROM clients WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, client.getId().toString());
            stmnt.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
