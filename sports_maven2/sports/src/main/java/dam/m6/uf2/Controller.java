package dam.m6.uf2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Controller {
    public static void main(String[] args) {
        String config = args.length > 0 ? args[0] : "database.xml";
        try (Connection conn = ConnectionManager.getConnection(config)) {
            if (conn == null) throw new IllegalStateException("No s'ha pogut connectar a PostgreSQL.");
            new Controller().run(conn);
        } catch (SQLException | IllegalStateException e) { System.err.println(e.getMessage()); }
    }
    private void run(Connection conn) {
        MainView view = new MainView();
        SportPgDAO sportDao = new SportPgDAO(conn);
        AtletaDAO atletaDao = new AtletaDAO(conn);
        boolean running = true;
        while (running) {
            switch (view.mainMenu()) {
                case 1:
                    Sport sport = view.EsportForm();
                    if (sport != null) { sportDao.add(sport); System.out.println("Esport afegit."); }
                    break;
                case 2:
                    Atleta athlete = view.AtletaForm(sportDao.getAll());
                    if (athlete != null) { atletaDao.add(athlete); System.out.println("Atleta afegit."); }
                    break;
                case 3:
                    List<Sport> sports = sportDao.getAll();
                    int id = view.DemanaEsport(sports);
                    if (id >= 0) view.LlistaAtletes(atletaDao.getBySport(id));
                    break;
                case 0: running = false; break;
                default: System.out.println("Opció no vàlida.");
            }
        }
    }
}
