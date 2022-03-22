package todo.servlets;

import todo.models.User;
import todo.store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (HbmStore.instOf().findUserByEmail(email) == null) {
            User user = User.of(name, HbmStore.instOf().findRole(2));
            user.setEmail(email);
            user.setPassword(password);
            HbmStore.instOf().addUser(user);
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("error", "User with this email already exists");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
