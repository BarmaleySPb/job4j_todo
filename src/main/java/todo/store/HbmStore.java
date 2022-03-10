package todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.models.Task;

import java.util.ArrayList;
import java.util.List;


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
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Task is not added", e);
        }
        return task;
    }

    @Override
    public boolean invertDone(int id) {
        boolean result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Task taskDb = session.get(Task.class, id);
            if (taskDb != null) {
                taskDb.setDone(taskDb.invertDone());
            }
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public List<Task> findAll() {
        List tasks = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            tasks = session.createQuery("from Task").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error finding all tasks", e);
        }
        return tasks;
    }

    @Override
    public Task findById(int id) {
        return findAll().stream().filter(task -> task.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
