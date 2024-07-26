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

@WebServlet(name = "admin/updateWorkerRedirect", urlPatterns = {"/admin/updateWorkerRedirect"})
public class UpdateWorkerRedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        WorkerDAO workerDAO;
        try {
            workerDAO = new WorkerDAO();
            Worker worker = workerDAO.getWorkerById(id);
            workerDAO.close();
            req.setAttribute("worker", worker);
            req.getRequestDispatcher("updateWorker.jsp").forward(req, resp);
        } catch (Exception e) {
            String msg = "No se puede registrar el ingrediente";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
