package com.pao.proiect.CatalogScolar.model;

public class Diriginte extends Profesor {
    private Group group;

    public Diriginte(String name, String surname, String email, String department) {
        super(name, surname, email, department);
    }

    @Override
    public String getRole() {
        return "Diriginte";
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Diriginte:\n" +
                "  Nume: " + getFullName() + "\n" +
                "  Email: " + email + "\n" +
                "  Departament: " + getDepartment() + "\n" +
                "  Grupa: " + (group != null ? group.getName() : "neatribuită");
    }
}
