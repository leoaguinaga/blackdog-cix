package pe.edu.utp.blackdog.servlet.admin.worker;

import pe.edu.utp.blackdog.dao.IngredientDAO;
import pe.edu.utp.blackdog.dao.WorkerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name ="admin/deleteWorker", urlPatterns = {"/admin/deleteWorker"})
public class DeleteWorkerServlet extends HttpServlet {
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
            workerDAO.deleteWorker(id);
            workerDAO.close();
            resp.sendRedirect("workers");
        } catch (Exception e) {
            String msg = "Error al eliminar el trabajador";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
