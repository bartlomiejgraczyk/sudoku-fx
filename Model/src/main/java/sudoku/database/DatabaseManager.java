package sudoku.database;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseManager implements AutoCloseable {
    private final EntityManager entityManager;

    public DatabaseManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public String getNameFromId(long id) {
        entityManager.getTransaction().begin();
        @SuppressWarnings("unchecked")
        List<String> results = entityManager.createQuery(
                "SELECT name FROM Board board WHERE board.id = :i"
        ).setParameter("i", id).getResultList();
        if (!results.isEmpty()) {
            String name = results.get(0);
            entityManager.getTransaction().commit();
            return name;
        }
        return "";
    }

    public boolean checkIfNameExists(String name) {
        entityManager.getTransaction().begin();
        boolean empty = entityManager.createQuery(
                "SELECT board FROM Board board WHERE board.name = :n"
        ).setParameter("n", name).getResultList().isEmpty();
        entityManager.getTransaction().commit();
        return !empty;
    }

    public List<Board> getAllEntries() {
        entityManager.getTransaction().begin();
        @SuppressWarnings("unchecked")
        List<Board> boards = entityManager.createQuery(
                "SELECT board FROM Board board"
        ).getResultList();
        entityManager.getTransaction().commit();
        return boards;
    }

    public void removeData(String name) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Board WHERE name = :n")
                .setParameter("n", name).executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void removeAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Board").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Deprecated
    public void addObject(Board board) {
        entityManager.getTransaction().begin();
        entityManager.persist(board);
        entityManager.getTransaction().commit();
    }

    public void releaseResources() {
        entityManager.close();
    }

    @Override
    public void close() {
        releaseResources();
    }
}
