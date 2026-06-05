package com.pao.proiect.CatalogScolar.repository;

import com.pao.proiect.CatalogScolar.model.Profesor;
import com.pao.proiect.CatalogScolar.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfesorRepository implements Repository<Profesor, String> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Profesor entity) {
        String sql = "INSERT INTO profesor (email, name, surname, department) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getEmail());
            pstmt.setString(2, entity.getName());
            pstmt.setString(3, entity.getSurname());
            pstmt.setString(4, entity.getDepartment());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Profesor> findById(String email) {
        String sql = "SELECT * FROM profesor WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Profesor(
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("department")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Profesor> findAll() {
        List<Profesor> profesori = new ArrayList<>();
        String sql = "SELECT * FROM profesor";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                profesori.add(new Profesor(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("department")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profesori;
    }

    @Override
    public void update(Profesor entity) {
        String sql = "UPDATE profesor SET name = ?, surname = ?, department = ? WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getSurname());
            pstmt.setString(3, entity.getDepartment());
            pstmt.setString(4, entity.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String email) {
        String sql = "DELETE FROM profesor WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}