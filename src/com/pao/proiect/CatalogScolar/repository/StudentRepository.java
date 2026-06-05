package com.pao.proiect.CatalogScolar.repository;

import com.pao.proiect.CatalogScolar.model.CodInmatriculare;
import com.pao.proiect.CatalogScolar.model.Student;
import com.pao.proiect.CatalogScolar.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements Repository<Student, String> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Student entity) {
        String sql = "INSERT INTO student (cod_inmatriculare, name, surname, email, year) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getCodInmatriculare().getValoare());
            pstmt.setString(2, entity.getName());
            pstmt.setString(3, entity.getSurname());
            pstmt.setString(4, entity.getEmail());
            pstmt.setInt(5, entity.getYear());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Student> findById(String cod) {
        String sql = "SELECT * FROM student WHERE cod_inmatriculare = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cod);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Student(
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            new CodInmatriculare(rs.getString("cod_inmatriculare")),
                            rs.getInt("year")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        List<Student> studenti = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                studenti.add(new Student(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        new CodInmatriculare(rs.getString("cod_inmatriculare")),
                        rs.getInt("year")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studenti;
    }

    @Override
    public void update(Student entity) {
        String sql = "UPDATE student SET name = ?, surname = ?, email = ?, year = ? WHERE cod_inmatriculare = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getSurname());
            pstmt.setString(3, entity.getEmail());
            pstmt.setInt(4, entity.getYear());
            pstmt.setString(5, entity.getCodInmatriculare().getValoare());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String cod) {
        String sql = "DELETE FROM student WHERE cod_inmatriculare = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cod);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}