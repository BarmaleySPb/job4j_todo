package todo.store;

import todo.models.Task;

import java.util.List;

public interface Store extends AutoCloseable {

    Task addTask(Task task);
    void invertDone(int id);
    List<Task> findAll();
    Task findById(int id);
}
