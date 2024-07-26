package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.ClientCrud;
import pe.edu.utp.blackdog.model.Client;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements AutoCloseable, ClientCrud {
    private final Connection cnn;

    public ClientDAO() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    @Override
    public Client getClientByEmail(String email) throws SQLException {
        String query = "{CALL sp_get_client_by_email(?)}";
        Client client = null;
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setString(1, email);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    client = Client.createClient(
                            rs.getLong("client_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("phone_number"),
                            rs.getString("email"),
                            rs.getString("pwd")
                    );
                } else {
                    throw new SQLException("No se pudo encontrar el cliente en la base de datos.");
                }
            }
        }
        return client;
    }

    @Override
    public void registerClient(Client client) throws SQLException {
        String query = "{CALL sp_register_client(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setString(1, client.getFirst_name());
            cs.setString(2, client.getLast_name());
            cs.setString(3, client.getPhone_number());
            cs.setString(4, client.getEmail());
            cs.setString(5, client.getPwd());
            cs.execute();
        }
    }

    @Override
    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "{CALL sp_get_all_clients()}";
        try (CallableStatement cs = cnn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                clients.add(Client.createClient(
                        rs.getLong("client_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("pwd")
                ));
            }
            if (clients.isEmpty()) {
                throw new SQLException("No se encontraron clientes en la base de datos.");
            }
        }
        return clients;
    }

    @Override
    public Client getClientById(long client_id) throws SQLException {
        String query = "{CALL sp_get_client_by_id(?)}";
        Client client = null;
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, client_id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    client = Client.createClient(
                            rs.getLong("client_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("phone_number"),
                            rs.getString("email"),
                            rs.getString("pwd")
                    );
                } else {
                    throw new SQLException(String.format("No se encontró un cliente con el ID %d en la base de datos.", client_id));
                }
            }
        }
        return client;
    }

    @Override
    public void updateClient(Client client, String email) throws SQLException {
        String query = "{CALL sp_update_client(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setString(1, client.getFirst_name());
            cs.setString(2, client.getLast_name());
            cs.setString(3, client.getPhone_number());
            cs.setString(4, email); // Current email
            cs.setString(5, client.getEmail()); // New email
            cs.execute();
        }
    }

    @Override
    public void deleteClient(long client_id) throws SQLException {
        String query = "{CALL sp_delete_client(?)}";
        try (CallableStatement cs = cnn.prepareCall(query)) {
            cs.setLong(1, client_id);
            cs.execute();
        }
    }
}
