package pe.edu.utp.blackdog.interfaces;

import pe.edu.utp.blackdog.model.Report;

import java.sql.SQLException;
import java.util.List;

public interface ReportCrud {
    void registerReport(Report report) throws SQLException;

    List<Report> getAllReports() throws SQLException;

    void updateReport(Report report, long report_id) throws SQLException;

    void deleteReport(long report_id) throws SQLException;

    Report getReportById(long report_id) throws SQLException;
}
