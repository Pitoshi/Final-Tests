package DBConnection;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "company", schema = "public", catalog = "xx_clients_db_75hr")

public class CompanyEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;
    @Column
    private String name;
    @lombok.Getter
    @Column
    private final String description;
    @Column(name = "is_active")
    private boolean isActive;

    public CompanyEntity(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyEntity company)) return false;
        return getId() == company.getId() && isActive() == company.isActive() && Objects.equals(getName(), company.getName()) && Objects.equals(getDescription(), company.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), isActive());
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}