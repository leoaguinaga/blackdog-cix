package pe.edu.utp.blackdog.interfaces;

import pe.edu.utp.blackdog.model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ClientCrud {
    void registerClient(Client client) throws SQLException;

    List<Client> getAllClients() throws SQLException;

    void updateClient(Client client, String email) throws SQLException;

    void deleteClient(long client_id) throws SQLException;

    Client getClientByEmail(String email) throws SQLException;
    
    Client getClientById(long client_id) throws SQLException;
}
