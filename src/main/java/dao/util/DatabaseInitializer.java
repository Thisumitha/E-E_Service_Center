

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            executeSqlScript(session, "/data.sql");
        }
    }

    private static void executeSqlScript(Session session, String scriptPath) {
        try (InputStream inputStream = DatabaseInitializer.class.getResourceAsStream(scriptPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String scriptContent = reader.lines().collect(Collectors.joining("\n"));

            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(scriptContent).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
