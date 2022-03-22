package todo.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import todo.models.Task;
import todo.models.User;
import todo.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AddTaskServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String description = req.getParameter("description");
        String[] categories = req.getParameterValues("categories[]");
        Task task = new Task(description);
        HttpSession sc = req.getSession();
        User user = (User) sc.getAttribute("user");
        task.setUser(user);
        HbmStore.instOf().addTask(task, categories);
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(task);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(HbmStore.instOf().findAll());
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}