package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.WorkerCrud;
import pe.edu.utp.blackdog.model.enums.Role;
import pe.edu.utp.blackdog.model.Worker;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAO implements AutoCloseable, WorkerCrud {
    private final Connection cnn;

    public WorkerDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    @Override
    public void registerWorker(Worker worker) throws SQLException {
        String query = "{ CALL registerWorker(?, ?, ?, ?) }";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, worker.getFull_name());
            ps.setString(2, worker.getEmail());
            ps.setString(3, worker.getPwd());
            ps.setString(4, worker.getRole().toString());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Worker> getAllWorkers() throws SQLException {
        List<Worker> workers = new ArrayList<>();
        String query = "{ CALL getAllWorkers() }";
        try (PreparedStatement ps = cnn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                workers.add(Worker.createWorker(
                        rs.getLong("worker_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("pwd"),
                        Role.valueOf(rs.getString("role"))
                ));
            }
        }
        return workers;
    }

    @Override
    public void updateWorker(Worker worker, long worker_id) throws SQLException {
        String query = "{ CALL updateWorker(?, ?, ?, ?, ?, ?) }";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, worker_id);
            ps.setString(2, worker.getFull_name());
            ps.setString(3, worker.getEmail());
            ps.setString(5, worker.getPwd());
            ps.setString(6, worker.getRole().toString());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteWorker(long worker_id) throws SQLException {
        String query = "{ CALL deleteWorker(?) }";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, worker_id);
            ps.executeUpdate();
        }
    }

    @Override
    public Worker getWorkerByEmail(String email) throws SQLException {
        String query = "{ CALL getWorkerByEmail(?) }";
        Worker worker = null;
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    worker = Worker.createWorker(
                            rs.getLong("worker_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("pwd"),
                            Role.valueOf(rs.getString("role"))
                    );
                } else {
                    throw new SQLException("No se pudo encontrar el trabajador en la base de datos.");
                }
            }
        }
        return worker;
    }

    @Override
    public Worker getWorkerById(long worker_id) throws SQLException {
        String query = "{ CALL getWorkerById(?) }";
        Worker worker = null;
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, worker_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    worker = Worker.createWorker(
                            rs.getLong("worker_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("pwd"),
                            Role.valueOf(rs.getString("role"))
                    );
                } else {
                    throw new SQLException(String.format("No se encontró un trabajador con el ID %d en la base de datos.", worker_id));
                }
            }
        }
        return worker;
    }
}