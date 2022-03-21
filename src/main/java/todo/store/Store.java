package todo.store;

import todo.models.Category;
import todo.models.Role;
import todo.models.Task;
import todo.models.User;

import java.util.List;

public interface Store extends AutoCloseable {

    Task addTask(Task task);
    User addUser(User user);
    Role addRole(Role role);
    Category addCategory(Category category);
    void invertDone(int id);
    List<Task> findAll();
    List<Category> findAllCategories();
    Task findById(int id);
    User findUserByEmail(String email);
    Role findRole(int id);
}
