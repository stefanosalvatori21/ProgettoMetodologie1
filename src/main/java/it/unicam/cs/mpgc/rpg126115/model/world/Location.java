package it.unicam.cs.mpgc.rpg126115.model.world;

public class Location {
    private final String name;
    private final Area area;
    private final String description;

    public Location(String name, Area area, String description) {
        this.name = name;
        this.area = area;
        this.description = description;
    }

    public String getName() { return name; }
    public Area getArea() { return area; }
    public String getDescription() { return description; }
}
