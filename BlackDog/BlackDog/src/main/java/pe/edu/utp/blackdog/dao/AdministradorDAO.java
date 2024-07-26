package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.GetAdmin;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorDAO implements GetAdmin, AutoCloseable {
    private final Connection cnn;

    public AdministradorDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    @Override
    public String getAdministratorNameByEmail(String email) throws SQLException {
        String query = "SELECT * FROM administrator WHERE email = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("full_Name");
                }
            }
        }
        return null;
    }
}
