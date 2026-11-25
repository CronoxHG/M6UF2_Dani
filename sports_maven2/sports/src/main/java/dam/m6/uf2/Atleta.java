package dam.m6.uf2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "deportistas", schema = "public")
public class Atleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod")
    private int id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_deporte")
    private Sport sport;

    public Atleta() { }

    public Atleta(String name, Sport sport) {
        this.name = name;
        this.sport = sport;
    }

    public Atleta(int id, String name, int sportId) {
        this.id = id;
        this.name = name;
        this.sport = sportId == 0 ? null : new Sport(sportId, null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Sport getSport() {
        return sport;
    }
    public int getSportId() {
        return sport == null ? 0 : sport.getId();
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSport(Sport sport) {
        this.sport = sport;
    }
}
