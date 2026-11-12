package dam.m6.uf2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtletaDAO implements DAO<Atleta> {
    private final Connection conn;
    public AtletaDAO(Connection conn) { this.conn = conn; }

    @Override public void add(Atleta atleta) {
        String sql = "INSERT INTO deportistas (nombre, cod_deporte) VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, atleta.getName());
            statement.setInt(2, atleta.getSportId());
            statement.executeUpdate();
        } catch (SQLException e) { throw new IllegalStateException("No s'ha pogut afegir l'atleta", e); }
    }

    @Override public List<Atleta> getAll() {
        List<Atleta> athletes = new ArrayList<>();
        String sql = "SELECT cod, nombre, cod_deporte FROM deportistas ORDER BY cod";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int sportId = rs.getInt("cod_deporte");
                boolean sportIsNull = rs.wasNull();
                int id = rs.getInt("cod");
                String name = rs.getString("nombre");
                athletes.add(new Atleta(id, name, sportIsNull ? 0 : sportId));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No s'han pogut llegir els atletes", e);
        }
        return athletes;
    }

    public List<Atleta> getBySport(int sportId) {
        List<Atleta> athletes = new ArrayList<>();
        String sql = "SELECT cod, nombre, cod_deporte FROM deportistas WHERE cod_deporte = ? ORDER BY cod";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, sportId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    athletes.add(new Atleta(rs.getInt("cod"), rs.getString("nombre"), rs.getInt("cod_deporte")));
                }
            }
        } catch (SQLException e) { throw new IllegalStateException("No s'han pogut llegir els atletes", e); }
        return athletes;
    }
}
