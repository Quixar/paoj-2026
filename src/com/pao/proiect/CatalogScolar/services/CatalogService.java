package com.pao.proiect.CatalogScolar.services;

import com.pao.proiect.CatalogScolar.model.Group;
import com.pao.proiect.CatalogScolar.model.Student;
import com.pao.proiect.CatalogScolar.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CatalogService {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    private final AuditService auditService = AuditService.getInstance();

    public void assignDiriginteToGroupTransaction(Group group) throws SQLException {
        auditService.logAction("asigneaza_diriginte_tranzactie");

        String updateProfesorSql = "UPDATE profesor SET is_diriginte = true, group_name = ? WHERE email = ?";
        String updateStudentsSql = "UPDATE student SET group_name = ? WHERE cod_inmatriculare = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement pstmtProf = connection.prepareStatement(updateProfesorSql)) {
                pstmtProf.setString(1, group.getName());
                pstmtProf.setString(2, group.getDiriginte().getEmail());
                pstmtProf.executeUpdate();
            }

            try (PreparedStatement pstmtStud = connection.prepareStatement(updateStudentsSql)) {
                for (Student student : group.getStudents()) {
                    pstmtStud.setString(1, group.getName());
                    pstmtStud.setString(2, student.getCodInmatriculare().getValoare());
                    pstmtStud.addBatch();
                }
                pstmtStud.executeBatch();
            }

            connection.commit();
            System.out.println("Tranzacție finalizată cu succes!");

        } catch (SQLException e) {
            connection.rollback();
            System.err.println("Eroare în tranzacție. S-a efectuat rollback.");
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}