package todo.store;

import org.junit.Test;
import todo.models.Category;
import todo.models.Role;
import todo.models.Task;
import todo.models.User;

import java.util.List;

import static org.junit.Assert.*;

public class HbmStoreTest {

    @Test
    public void whenAddItem() {
        Store store = new HbmStore();
        String[] categories = {"1", "2", "3"};
        store.addTask(new Task("description"), categories);
        List<Task> tasks = store.findAll();
        assertEquals(tasks.size(), 1);
        assertEquals(tasks.get(0).getDescription(), "description");
    }

    @Test
    public void whenAddUser() {
        Store store = new HbmStore();
        Role role = Role.of("user");
        store.addRole(role);
        User user = User.of("Vasya", role);
        user.setEmail("aaa@aaa.aa");
        user.setPassword("asd");
        store.addUser(user);
        assertEquals(store.findUserByEmail("aaa@aaa.aa"), user);
    }

    @Test
    public void whenFindAll() {
        Store store = new HbmStore();
        String[] categories = {"1", "2"};
        Task firstTask = store.addTask(new Task("first item"), categories);
        Task secondTask = store.addTask(new Task("second item"), categories);
        assertEquals(store.findAll(), List.of(firstTask, secondTask));
    }

    @Test
    public void whenSetDone() {
        Store store = new HbmStore();
        String[] categories = {"1", "2"};
        Task task = store.addTask(new Task("description"), categories);
        assertFalse(task.isDone());
        store.invertDone(task.getId());
        assertTrue(store.findById(task.getId()).isDone());
        store.invertDone(task.getId());
        assertFalse(store.findById(task.getId()).isDone());
    }

    @Test
    public void whenAddCategoryAndFindAllCategories() {
        Store store = HbmStore.instOf();
        Category firstCategory = Category.of("first category");
        Category secondCategory = Category.of("second category");
        store.addCategory(firstCategory);
        store.addCategory(secondCategory);
        assertEquals(store.findAllCategories(), List.of(firstCategory, secondCategory));
    }
}