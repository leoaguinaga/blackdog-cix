package pe.edu.utp.blackdog.service;

import pe.edu.utp.blackdog.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth implements AutoCloseable {

    private final Connection cnn;

    public Auth() throws SQLException, NamingException {
        this.cnn = DataAccessMariaDB.getConnection(DataAccessMariaDB.TipoDA.DATASOURCE, "java:/MariaDB");
    }

    @Override
    public void close() throws SQLException {
        if (this.cnn != null) DataAccessMariaDB.closeConnection(this.cnn);
    }

    public static String md5(String data) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            return byteArrayToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        }
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public boolean isValidAdmin(String email, String password) throws SQLException, IOException {
        String query = "SELECT * FROM administrator WHERE email = ? AND pwd = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, md5(password));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean isValidClient(String email, String password) throws SQLException, IOException {
        String query = "SELECT * FROM client WHERE email = ? AND pwd = ?";
        try (PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, md5(password));
            System.out.println("Email: " + email);
            System.out.println("Password: " + md5(password));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public String getTipoUsuario(String email, String password) throws SQLException, IOException {
        if (isValidAdmin(email, password)) {
            return "admin";
        }
        if (isValidClient(email, password)) {
            return "client";
        }
        return null;
    }
}
