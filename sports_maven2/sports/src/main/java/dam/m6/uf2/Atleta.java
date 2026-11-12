package dam.m6.uf2;

public class Atleta {
    private final int id;
    private final String name;
    private final int sportId;


    public Atleta(int id, String name, int sportId) {
        this.id = id;
        this.name = name;
        this.sportId = sportId;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSportId() {
        return sportId;
    }
}
