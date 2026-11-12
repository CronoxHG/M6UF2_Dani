package dam.m6.uf2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SportPgDAO implements DAO<Sport> {
    private final Connection conn;
    public SportPgDAO(Connection conn) { this.conn = conn; }

    @Override public void add(Sport sport) {
        String sql = "INSERT INTO deportes (nombre) VALUES (?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, sport.getName());
            statement.executeUpdate();
        } catch (SQLException e) { throw new IllegalStateException("No s'ha pogut afegir l'esport", e); }
    }

    @Override public List<Sport> getAll() {
        List<Sport> sports = new ArrayList<>();
        String sql = "SELECT cod, nombre FROM deportes ORDER BY cod";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) sports.add(new Sport(rs.getInt("cod"), rs.getString("nombre")));
        } catch (SQLException e) { throw new IllegalStateException("No s'han pogut llegir els esports", e); }
        return sports;
    }
}
