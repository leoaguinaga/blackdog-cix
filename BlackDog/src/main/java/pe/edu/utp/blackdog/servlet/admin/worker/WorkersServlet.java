package pe.edu.utp.blackdog.servlet.admin.worker;

import pe.edu.utp.blackdog.dao.IngredientDAO;
import pe.edu.utp.blackdog.dao.WorkerDAO;
import pe.edu.utp.blackdog.model.Ingredient;
import pe.edu.utp.blackdog.model.Worker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name ="admin/workers", urlPatterns = {"/admin/workers"})
public class WorkersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Worker> workers;
        try {
            WorkerDAO workerDAO = new WorkerDAO();
            workers = workerDAO.getAllWorkers();
            workerDAO.close();

            req.setAttribute("workers", workers);
            req.getRequestDispatcher("workers.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
