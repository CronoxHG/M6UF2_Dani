package dam.m6.uf2;

public class Sport {
    private final int id;
    private final String name;

    public Sport(String name) {
        this(0, name);
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

    @Override public String toString() {
        return id + " - " + name;
    }
}
