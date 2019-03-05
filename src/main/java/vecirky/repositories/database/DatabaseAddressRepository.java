package vecirky.repositories.database;

import vecirky.C;
import vecirky.models.Address;
import vecirky.repositories.AddressRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseAddressRepository implements AddressRepository {

    private Connection conn = C.get(Connection.class);

    @Override
    public Optional<Address> findBy(Integer key) {
        Address address = null;
        String sql = "SELECT * FROM addresses WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setInt(1, key);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                address = new Address();
                address.setId(rs.getInt("id"));
                address.setCity(rs.getString("city"));
                address.setStreet(rs.getString("street"));
                address.setNumberOfDescriptive(rs.getInt("number_of_descriptive"));
                address.setZipCode(rs.getInt("zip_code"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return Optional.ofNullable(address);
    }

    @Override
    public List<Address> findAll() {
        String sql = "SELECT * FROM addresses;";
        List<Address> addresses = new ArrayList<>();

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt("id"));
                address.setCity(rs.getString("city"));
                address.setStreet(rs.getString("street"));
                address.setNumberOfDescriptive(rs.getInt("number_of_descriptive"));
                address.setZipCode(rs.getInt("zip_code"));
                addresses.add(address);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return addresses;
    }

    @Override
    public Integer create(Address address) {
        String sql = "INSERT INTO addresses (street, number_of_descriptive, city, zip_code) VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, address.getStreet());
            stmnt.setInt(2, address.getNumberOfDescriptive());
            stmnt.setString(3, address.getCity());
            stmnt.setInt(4, address.getZipCode());

            stmnt.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        sql = "SELECT last_insert_rowid()";
        try {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);

            address.setId(rs.getInt(1));
            return address.getId();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Address address) {
        String sql = "UPDATE addresses SET street = ?, number_of_descriptive = ?, city = ?, zip_code = ? WHERE id = ?;";
        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, address.getStreet());
            stmnt.setInt(2, address.getNumberOfDescriptive());
            stmnt.setString(3, address.getCity());
            stmnt.setInt(4, address.getZipCode());
            stmnt.setString(5, address.getId().toString());
            stmnt.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Address address) {
        String sql = "DELETE FROM addresses WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, address.getId().toString());
            stmnt.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
