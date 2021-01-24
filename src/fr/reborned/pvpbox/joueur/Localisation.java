package fr.reborned.pvpbox.joueur;

import org.bukkit.Location;
import org.bukkit.World;

public class Localisation extends Location {
    private Location Loc1;
    private Location Loc2;

    public Localisation(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public Localisation(World world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z, yaw, pitch);
    }

    public Location getLoc1() {
        return Loc1;
    }

    public Location getLoc2() {
        return Loc2;
    }
}
