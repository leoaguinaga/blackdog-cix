package pe.edu.utp.blackdog.interfaces;

import java.sql.SQLException;

public interface GetAdmin {
    String getAdministratorNameByEmail(String email) throws SQLException;
}
