package pe.edu.utp.blackdog.interfaces;

import pe.edu.utp.blackdog.model.Worker;

import java.sql.SQLException;
import java.util.List;

public interface WorkerCrud {
    void registerWorker(Worker worker) throws SQLException;

    List<Worker> getAllWorkers() throws SQLException;

    void updateWorker(Worker worker, long worker_id) throws SQLException;

    void deleteWorker(long worker_id) throws SQLException;

    Worker getWorkerByEmail(String email) throws SQLException;

    Worker getWorkerById(long worker_id) throws SQLException;
}
