package todo.store;

import org.junit.Test;
import todo.models.Task;

import java.util.List;

import static org.junit.Assert.*;

public class HbmStoreTest {

    @Test
    public void whenAddItem() {
        Store store = new HbmStore();
        store.addTask(new Task("description"));
        List<Task> tasks = store.findAll();
        assertEquals(tasks.size(), 1);
        assertEquals(tasks.get(0).getDescription(), "description");
    }

    @Test
    public void whenFindAll() {
        Store store = new HbmStore();
        Task firstTask = store.addTask(new Task("first item"));
        Task secondTask = store.addTask(new Task("second item"));
        assertEquals(store.findAll(), List.of(firstTask, secondTask));
    }

    @Test
    public void whenSetDone() {
        Store store = new HbmStore();
        Task task = store.addTask(new Task("description"));
        assertFalse(task.isDone());
        store.invertDone(task.getId());
        assertTrue(store.findById(task.getId()).isDone());
        store.invertDone(task.getId());
        assertFalse(store.findById(task.getId()).isDone());
    }
}