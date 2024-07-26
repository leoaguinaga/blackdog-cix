package pe.edu.utp.blackdog.dao;

import pe.edu.utp.blackdog.interfaces.ClientCrud;
import pe.edu.utp.blackdog.model.Client;
import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "SELECT * FROM client WHERE email = ?";
        Client client = null;
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
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
        String query = "INSERT INTO client (first_name, last_name, phone_number, email, pwd) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, client.getFirst_name());
            ps.setString(2, client.getLast_name());
            ps.setString(3, client.getPhone_number());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getPwd());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el cliente en la base de datos.");
            }
        }
    }

    @Override
    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client";
        try (PreparedStatement ps = cnn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clients.add(Client.createClient(
                        rs.getLong("client_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("pwd")
                ));
            } if (clients.size() == 0) {
                throw new SQLException("No se encontraron clientes en la base de datos.");
            }
        }
        return clients;
    }

    @Override
    public Client getClientById(long client_id) throws SQLException {
        String query = "SELECT * FROM client WHERE client_id = ?";
        Client client = null;
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, client_id);
            try (ResultSet rs = ps.executeQuery()) {
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
        String query = "UPDATE client SET first_name = ?, last_name = ?, phone_number = ?, email = ? WHERE email = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, client.getFirst_name());
            ps.setString(2, client.getLast_name());
            ps.setString(3, client.getPhone_number());
            ps.setString(4, client.getEmail());
            ps.setString(5, email);
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteClient(long client_id) throws SQLException {
        String query = "DELETE FROM client WHERE client_id = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setLong(1, client_id);
            ps.executeUpdate();
        }
    }
}
