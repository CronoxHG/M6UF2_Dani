package dam.m6.uf2;

import java.util.List;
import java.util.Scanner;

public class MainView {
    private final Scanner scanner = new Scanner(System.in);
    public int mainMenu() {
        System.out.println("\n1. Afegir esport\n2. Afegir atleta\n3. Llistar atletes per esport\n0. Sortir");
        System.out.print("Opció: ");
        return readInt();
    }
    public Sport EsportForm() {
        System.out.print("Nom de l'esport: ");
        String name = scanner.nextLine().trim();
        return name.isEmpty() ? null : new Sport(name);
    }
    public Atleta AtletaForm(List<Sport> sports) {
        if (sports.isEmpty()) { System.out.println("Primer cal afegir un esport."); return null; }
        LlistaEsports(sports);
        System.out.print("ID de l'esport de l'atleta: ");
        int sportId = readInt();
        if (!sports.stream().anyMatch(s -> s.getId() == sportId)) { System.out.println("ID d'esport no vàlid."); return null; }
        System.out.print("Nom de l'atleta: ");
        String name = scanner.nextLine().trim();
        return name.isEmpty() ? null : new Atleta(0, name, sportId);
    }
    public int DemanaEsport(List<Sport> sports) {
        if (sports.isEmpty()) { System.out.println("No hi ha esports."); return -1; }
        LlistaEsports(sports);
        System.out.print("ID de l'esport: ");
        int id = readInt();
        return sports.stream().anyMatch(s -> s.getId() == id) ? id : -1;
    }
    public void LlistaAtletes(List<Atleta> athletes) {
        if (athletes.isEmpty()) { System.out.println("No hi ha atletes per a aquest esport."); return; }
        int number = 1;
        for (Atleta athlete : athletes) System.out.println((number++) + ". " + athlete.getName());
    }
    public void LlistaEsports(List<Sport> sports) {
        for (Sport sport : sports) System.out.println(sport.getId() + ". " + sport.getName());
    }
    private int readInt() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}
