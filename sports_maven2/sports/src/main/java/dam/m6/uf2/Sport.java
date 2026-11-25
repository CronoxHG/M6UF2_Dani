package dam.m6.uf2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deportes", schema = "public")
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod")
    private int id;

    @Column(name = "nombre", nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "sport")
    private List<Atleta> athletes = new ArrayList<>();

    public Sport() { }

    public Sport(String name) {
        this.name = name;
    }

    public Sport(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Atleta> getAthletes() {
        return athletes;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
