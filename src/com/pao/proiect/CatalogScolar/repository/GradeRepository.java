package com.pao.proiect.CatalogScolar.repository;

import com.pao.proiect.CatalogScolar.model.CodInmatriculare;
import com.pao.proiect.CatalogScolar.model.Grade;
import com.pao.proiect.CatalogScolar.model.Student;
import com.pao.proiect.CatalogScolar.model.Subject;
import com.pao.proiect.CatalogScolar.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GradeRepository implements Repository<Grade, Integer> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    private final StudentRepository studentRepository = new StudentRepository();
    private final SubjectRepository subjectRepository = new SubjectRepository();

    @Override
    public void save(Grade entity) {
        String sql = "INSERT INTO grade (student_cod, subject_code, value, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getStudent().getCodInmatriculare().getValoare());
            pstmt.setString(2, entity.getSubject().getCode());
            pstmt.setInt(3, entity.getValue());
            pstmt.setDate(4, Date.valueOf(entity.getDate()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Grade> findById(Integer id) {
        String sql = "SELECT * FROM grade WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Student s = studentRepository.findById(rs.getString("student_cod")).orElse(null);
                    Subject sub = subjectRepository.findById(rs.getString("subject_code")).orElse(null);
                    return Optional.of(new Grade(s, sub, rs.getInt("value"), rs.getDate("date").toLocalDate()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Grade> findAll() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grade";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Student s = studentRepository.findById(rs.getString("student_cod")).orElse(null);
                Subject sub = subjectRepository.findById(rs.getString("subject_code")).orElse(null);
                grades.add(new Grade(s, sub, rs.getInt("value"), rs.getDate("date").toLocalDate()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    @Override
    public void update(Grade entity) {
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM grade WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTopStudentsReport() {
        List<String> report = new ArrayList<>();
        String sql = "SELECT s.name, s.surname, AVG(g.value) as medie " +
                "FROM student s " +
                "JOIN grade g ON s.cod_inmatriculare = g.student_cod " +
                "GROUP BY s.cod_inmatriculare " +
                "ORDER BY medie DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                report.add(rs.getString("surname") + " " + rs.getString("name") + " - Medie: " + rs.getBigDecimal("medie"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    public List<String> getSubjectPerformanceReport() {
        List<String> report = new ArrayList<>();
        String sql = "SELECT sub.name as materie, p.surname as prof_nume, COUNT(g.id) as nr_note " +
                "FROM subject sub " +
                "JOIN profesor p ON sub.professor_email = p.email " +
                "LEFT JOIN grade g ON sub.code = g.subject_code " +
                "GROUP BY sub.code";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                report.add("Materie: " + rs.getString("materie") + " | Prof: " + rs.getString("prof_nume") + " | Note puse: " + rs.getInt("nr_note"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    public List<String> getUnmotivatedAbsencesReport() {
        List<String> report = new ArrayList<>();
        String sql = "SELECT s.surname, s.name, sub.name as materie, COUNT(a.id) as nr_absente " +
                "FROM student s " +
                "JOIN absence a ON s.cod_inmatriculare = a.student_cod " +
                "JOIN subject sub ON a.subject_code = sub.code " +
                "WHERE a.motivated = false " +
                "GROUP BY s.cod_inmatriculare, sub.code";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                report.add(rs.getString("surname") + " " + rs.getString("name") + " are " + rs.getInt("nr_absente") + " absențe nemotivate la " + rs.getString("materie"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
}