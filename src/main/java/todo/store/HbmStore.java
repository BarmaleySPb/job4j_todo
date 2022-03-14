package todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.models.Role;
import todo.models.Task;
import todo.models.User;

import java.util.List;
import java.util.function.Function;


public class HbmStore implements Store {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private static final Logger LOG = LoggerFactory.getLogger(HbmStore.class.getName());

    private static final class Lazy {
        private static final Store INST = new HbmStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Task addTask(Task task) {
        this.tx(session -> session.save(task));
        return task;
    }

    @Override
    public User addUser(User user) {
        this.tx(session -> session.save(user));
        return user;
    }

    @Override
    public Role addRole(Role role) {
        this.tx(session -> session.save(role));
        return role;
    }

    @Override
    public void invertDone(int id) {
        this.tx(session -> {
            Task task = session.get(Task.class, id);
            task.setDone(task.invertDone());
            return null;
        });
    }

    @Override
    public List<Task> findAll() {
        return this.tx(
                session -> session.createQuery("from Task").list()
        );
    }

    @Override
    public Task findById(int id) {
        return this.tx(session -> session.get(Task.class, id));
    }

    @Override
    public User findUserByEmail(String email) {
        return (User) this.tx(session -> session.createQuery("from User i where i.email = :key")
                .setParameter("key", email)
                .uniqueResult());
    }

    @Override
    public Role findRole(int id) {
        return this.tx(session -> session.get(Role.class, id));
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error("Error: ", e);
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
