package vecirky.repositories.database;

import vecirky.C;
import vecirky.models.Promoter;
import vecirky.repositories.PromoterRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabasePromoterRepository implements PromoterRepository {

    private Connection conn = C.get(Connection.class);

    @Override
    public Optional<Promoter> findBy(Integer key) {
        Promoter promoter = null;
        String sql = "SELECT * FROM promoters WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setInt(1, key);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                promoter = new Promoter();
                promoter.setId(rs.getInt("id"));
                promoter.setTitle(rs.getString("title"));
                promoter.setFirstName(rs.getString("first_name"));
                promoter.setLastName(rs.getString("last_name"));
                promoter.setEmail(rs.getString("email"));
                promoter.setPhoneNumber(rs.getString("phone_number"));
                promoter.setBankAccount(rs.getString("bank_account"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return Optional.ofNullable(promoter);
    }

    @Override
    public List<Promoter> findAll() {
        String sql = "SELECT * FROM promoters;";
        List<Promoter> promoters = new ArrayList<>();

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Promoter promoter = new Promoter();
                promoter.setId(rs.getInt("id"));
                promoter.setTitle(rs.getString("title"));
                promoter.setFirstName(rs.getString("first_name"));
                promoter.setLastName(rs.getString("last_name"));
                promoter.setEmail(rs.getString("email"));
                promoter.setPhoneNumber(rs.getString("phone_number"));
                promoter.setBankAccount(rs.getString("bank_account"));
                promoters.add(promoter);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return promoters;
    }

    @Override
    public Integer create(Promoter promoter) {
        String sql = "INSERT INTO promoters (title, first_name, last_name, email, phone_number, bank_account) "
                + "VALUES (?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, promoter.getTitle());
            stmnt.setString(2, promoter.getFirstName());
            stmnt.setString(3, promoter.getLastName());
            stmnt.setString(4, promoter.getEmail());
            stmnt.setString(5, promoter.getPhoneNumber());
            stmnt.setString(6, promoter.getBankAccount());

            stmnt.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        sql = "SELECT last_insert_rowid()";
        try {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);

            promoter.setId(rs.getInt(1));
            return promoter.getId();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Promoter promoter) {
        String sql = "UPDATE promoters SET title = ?, first_name = ?, last_name = ?, email = ?, "
                + " phone_number = ?, bank_account = ? WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, promoter.getTitle());
            stmnt.setString(2, promoter.getFirstName());
            stmnt.setString(3, promoter.getLastName());
            stmnt.setString(4, promoter.getEmail());
            stmnt.setString(5, promoter.getPhoneNumber());
            stmnt.setString(6, promoter.getBankAccount());
            stmnt.setString(7, promoter.getId().toString());
            stmnt.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Promoter promoter) {
        String sql = "DELETE FROM promoters WHERE id = ?;";

        try {
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, promoter.getId().toString());
            stmnt.execute();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
