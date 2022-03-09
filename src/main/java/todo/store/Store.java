package todo.store;

import todo.models.Task;

import java.util.List;

public interface Store extends AutoCloseable {

    Task addTask(Task task);
    boolean setDone(int id);
    List<Task> findAll();
}
