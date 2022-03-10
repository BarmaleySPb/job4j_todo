package todo.servlets;

import todo.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetDoneStatus extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String readLine = req.getReader().readLine().replaceAll("[^0-9]", "");
        int i = Integer.parseInt(readLine);
        HbmStore.instOf().invertDone(i);
    }
}