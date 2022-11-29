package ru.hogwarts.school.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Faculty {
    @Id
    @GeneratedValue
    private long idFac;
    private String name;
    private String color;

    public Faculty(long idFac, String name, String color) {
        this.idFac = idFac;
        this.name = name;
        this.color = color;
    }

    public Faculty() {

    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + idFac +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public long getIdFac() {
        return idFac;
    }

    public void setIdFac(long idFac) {
        this.idFac = idFac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
