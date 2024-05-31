package solarsystem.coffee.zomb;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class player extends JavaPlugin {

    //define player behaviour for infected and survivors

    //use Logblock to make players only able to break there own property

    public boolean teleportPlayer(Player p, int x, int y, int z, String world, String server) {
        Location loc = p.getLocation();
        loc.setWorld(Bukkit.getWorld(world));
        loc.setX((double) x);
        loc.setY((double) y);
        loc.setZ((double) z);
        p.teleport(loc);
        return true;
    }
}
