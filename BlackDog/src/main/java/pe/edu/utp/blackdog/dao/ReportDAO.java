package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.ReportCrud;
import pe.edu.utp.blackdog.model.Report;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO implements AutoCloseable, ReportCrud {
    private final Connection cnn;

    public ReportDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    @Override
    public void registerReport(Report report) throws SQLException {
        String query = "{CALL sp_register_report(?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, report.getCustomer_order_id());
            cs.setTimestamp(2, Timestamp.valueOf(report.getLastUpdate()));
            cs.setString(3, report.getLastState());
            cs.execute();
        }
    }

    @Override
    public List<Report> getAllReports() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String query = "{CALL sp_get_all_reports()}";
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                reports.add(Report.createReport(
                        rs.getLong("report_id"),
                        rs.getLong("customer_order_id"),
                        rs.getTimestamp("lastUpdate").toLocalDateTime(),
                        rs.getString("lastState")
                ));
            }
        }
        return reports;
    }

    @Override
    public Report getReportById(long report_id) throws SQLException {
        Report report = null;
        String query = "{CALL sp_get_report_by_id(?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, report_id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    report = Report.createReport(
                            rs.getLong("report_id"),
                            rs.getLong("customer_order_id"),
                            rs.getTimestamp("lastUpdate").toLocalDateTime(),
                            rs.getString("lastState")
                    );
                }
            }
        }
        return report;
    }

    @Override
    public void updateReport(Report report, long report_id) throws SQLException {
        String query = "{CALL sp_update_report(?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, report_id);
            cs.setLong(2, report.getCustomer_order_id());
            cs.setTimestamp(3, Timestamp.valueOf(report.getLastUpdate()));
            cs.setString(4, report.getLastState());
            cs.execute();
        }
    }

    @Override
    public void deleteReport(long report_id) throws SQLException {
        String query = "{CALL sp_delete_report(?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, report_id);
            cs.execute();
        }
    }
}
