package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.model.Report_log;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Report_logDAO implements AutoCloseable {
    private final Connection cnn;

    public Report_logDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    public void registerReportLog(Report_log reportLog) throws SQLException {
        String query = "{CALL sp_register_report_log(?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, reportLog.getWorker_id());
            cs.setLong(2, reportLog.getReport_id());
            cs.setTimestamp(3, Timestamp.valueOf(reportLog.getDate()));
            cs.setString(4, reportLog.getState());
            cs.execute();
        }
    }

    public Report_log getReportLogByWorkerId(long worker_id) throws SQLException {
        String query = "{CALL sp_get_report_log_by_worker_id(?)}";
        Report_log reportLog = null;
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, worker_id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    reportLog = Report_log.createReport_log(
                            rs.getLong("worker_id"),
                            rs.getLong("report_id"),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getString("state")
                    );
                } else {
                    throw new SQLException("No se pudo encontrar el reporte de log en la base de datos.");
                }
            }
        }
        return reportLog;
    }

    public List<Report_log> getAllReportLogs() throws SQLException {
        List<Report_log> reportLogs = new ArrayList<>();
        String query = "{CALL sp_get_all_report_logs()}";
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                reportLogs.add(Report_log.createReport_log(
                        rs.getLong("worker_id"),
                        rs.getLong("report_id"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getString("state")
                ));
            }
            if (reportLogs.size() == 0) {
                throw new SQLException("No se encontraron logs de reportes en la base de datos.");
            }
        }
        return reportLogs;
    }

    public void updateReportLog(Report_log reportLog, long worker_id, long report_id) throws SQLException {
        String query = "{CALL sp_update_report_log(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, reportLog.getWorker_id());
            cs.setLong(2, reportLog.getReport_id());
            cs.setTimestamp(3, Timestamp.valueOf(reportLog.getDate()));
            cs.setString(4, reportLog.getState());
            cs.setLong(5, worker_id);
            cs.setLong(6, report_id);
            cs.execute();
        }
    }

    public void deleteReportLog(long worker_id, long report_id) throws SQLException {
        String query = "{CALL sp_delete_report_log(?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, worker_id);
            cs.setLong(2, report_id);
            cs.execute();
        }
    }
}
