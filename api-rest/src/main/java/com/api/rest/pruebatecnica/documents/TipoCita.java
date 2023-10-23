package com.api.rest.pruebatecnica.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection =  "TipoCita")
public class TipoCita {

    @Id
    private String id;
    private String name;
    private String description;
    private String durationMinutes;
    private String colorHexDecimal;

    public TipoCita() {
    }

    public TipoCita(String name, String description, String durationMinutes, String colorHexDecimal) {
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.colorHexDecimal = colorHexDecimal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(String durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getColorHexDecimal() {
        return colorHexDecimal;
    }

    public void setColorHexDecimal(String colorHexDecimal) {
        this.colorHexDecimal = colorHexDecimal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoCita tipoCita = (TipoCita) o;
        return Objects.equals(id, tipoCita.id) && Objects.equals(name, tipoCita.name) && Objects.equals(description, tipoCita.description) && Objects.equals(durationMinutes, tipoCita.durationMinutes) && Objects.equals(colorHexDecimal, tipoCita.colorHexDecimal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, durationMinutes, colorHexDecimal);
    }

    @Override
    public String toString() {
        return "TipoCita{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", durationMinutes='" + durationMinutes + '\'' +
                ", colorHexDecimal='" + colorHexDecimal + '\'' +
                '}';
    }
}
