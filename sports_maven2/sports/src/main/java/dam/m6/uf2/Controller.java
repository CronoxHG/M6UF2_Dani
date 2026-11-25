package dam.m6.uf2;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Controller {
    public static void main(String[] args) {
        MainView view = new MainView();
        try {
            new Controller().run(view);
        } catch (RuntimeException e) {
            view.MostraError("Error de Hibernate: " + e.getMessage());
        } finally {
            HibernateUtil.shutdown();
        }
    }

    private void run(MainView view) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        boolean running = true;
        while (running) {
            switch (view.mainMenu()) {
                case 1:
                    Sport sport = view.EsportForm();
                    if (sport != null) {
                        inTransaction(sessionFactory, session -> session.persist(sport));
                        view.MostraEsportAfegit(sport.getId());
                    }
                    break;
                case 2:
                    List<Sport> sportsForForm = getSports(sessionFactory);
                    int sportId = view.DemanaEsport(sportsForForm);
                    if (sportId >= 0) {
                        String name = view.DemanaNomAtleta();
                        if (!name.isEmpty()) {
                            inTransaction(sessionFactory, session -> {
                                Sport selectedSport = session.get(Sport.class, sportId);
                                if (selectedSport == null) {
                                    throw new IllegalArgumentException("No existe el deporte seleccionado.");
                                }
                                session.persist(new Atleta(name, selectedSport));
                            });
                            view.MostraAtletaAfegit();
                        }
                    }
                    break;
                case 3:
                    List<Sport> sports = getSports(sessionFactory);
                    int selectedId = view.DemanaEsport(sports);
                    if (selectedId >= 0) {
                        List<Atleta> athletes = getAthletesBySport(sessionFactory, selectedId);
                        view.LlistaAtletes(athletes);
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    view.MostraOpcioNoValida();
            }
        }
    }

    private List<Sport> getSports(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            return session.createSelectionQuery("from Sport order by id", Sport.class).getResultList();
        }
    }

    private List<Atleta> getAthletesBySport(SessionFactory sessionFactory, int sportId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createSelectionQuery("from Atleta a where a.sport.id = :sportId order by a.id", Atleta.class)
                    .setParameter("sportId", sportId)
                    .getResultList();
        }
    }

    private void inTransaction(SessionFactory sessionFactory, HibernateAction action) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                action.execute(session);
                transaction.commit();
            } catch (RuntimeException e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @FunctionalInterface
    private interface HibernateAction {
        void execute(Session session);
    }
}
