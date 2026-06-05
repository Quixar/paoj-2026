package com.pao.proiect.CatalogScolar.repository;

import com.pao.proiect.CatalogScolar.model.Profesor;
import com.pao.proiect.CatalogScolar.model.Subject;
import com.pao.proiect.CatalogScolar.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectRepository implements Repository<Subject, String> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    private final ProfesorRepository profesorRepository = new ProfesorRepository();

    @Override
    public void save(Subject entity) {
        String sql = "INSERT INTO subject (code, name, professor_email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getCode());
            pstmt.setString(2, entity.getName());
            pstmt.setString(3, entity.getProfessor() != null ? entity.getProfessor().getEmail() : null);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Subject> findById(String code) {
        String sql = "SELECT * FROM subject WHERE code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Profesor prof = profesorRepository.findById(rs.getString("professor_email")).orElse(null);
                    return Optional.of(new Subject(
                            rs.getString("code"),
                            rs.getString("name"),
                            prof
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Subject> findAll() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subject";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Profesor prof = profesorRepository.findById(rs.getString("professor_email")).orElse(null);
                subjects.add(new Subject(
                        rs.getString("code"),
                        rs.getString("name"),
                        prof
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    @Override
    public void update(Subject entity) {
        String sql = "UPDATE subject SET name = ?, professor_email = ? WHERE code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getProfessor() != null ? entity.getProfessor().getEmail() : null);
            pstmt.setString(3, entity.getCode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String code) {
        String sql = "DELETE FROM subject WHERE code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}