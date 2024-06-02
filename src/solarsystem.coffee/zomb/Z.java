package solarsystem.coffee.zomb;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;


public class Z extends JavaPlugin {

    lobby l = new lobby();
    public Player[] survivors = new Player[990];
    public Player[] infected = new Player[990];
    public Player[] spectator = new Player[990];

    public String Lobbyworld = "Lobby";



    public static void main (String[] args){

    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("zP >Zom< loaded");
    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("zP >Zom< disabled");
    }

   // @Override
    //public boolean onTick(){

    //}

    public int surID = 0;
    public int infID = 0;
    public int spID = 0;
    @Override
    public boolean onCommand(CommandSender interpreter, Command cmd, String input, String[] args) {
        if(interpreter instanceof Player) {
            Player player = (Player) interpreter;
            switch(input) {
                case "zP":
                    player.sendMessage("o>");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300,1,true,true));
                case "zom":
                    player.sendMessage("baaa");
                    if(args.length > 0) {
                        if(args[0].equalsIgnoreCase("runZom")) {
                            runZom();
                        }
                        if(args[0].equalsIgnoreCase("Lobby")) {
                            l.showLobbyforplayer(player);
                        }
                        if(args[0].equalsIgnoreCase("P")) {
                            player.sendMessage("Sh33pio");
                            player.setGravity(false);

                        }
                        if(args[0].equalsIgnoreCase("showLobby")){
                            l.showLobbyforplayer(player);
                        }
                        if(args[0].equalsIgnoreCase("baa")){

                        }
                        if(args[0].equalsIgnoreCase("set")) {
                            Player p = null;
                            p = getPlayerbyString(args[2]);
                            if (p != null) {
                                switch (args[1]) {
                                    case "Inf":
                                            set(p, args);
                                            player.sendMessage(p.getDisplayName() + "set to infected");
                                        break;
                                    case "Su":
                                            set(p, args);
                                            player.sendMessage(p.getDisplayName() +"set to su");
                                        break;
                                    case "sp":
                                            set(p, args);
                                            player.sendMessage(p.getDisplayName() + "set to spectator");
                                        break;
                                    default:
                                        player.sendMessage("Invalid arguments");
                                        break;
                                }
                            } else {
                                player.sendMessage("player not found");
                            }
                        }

                    }
            }

        } else {
            Bukkit.getConsoleSender().sendMessage("o> Console");
        }
        return false;
    }

    public boolean runZom() {
        l.InLobby();
        l.shuffle();
        l.showLobby();




        return true;
    }
    public void runround() {

        //init timer


        for(Player p : infected) {

            p.setFoodLevel(9);
            p.setSaturation((float) 9);

            //transport to infected spawnarea
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1,1,true,false));
            p.sendMessage("You are infected with the Virus - Your desire for blood has awakened - Infect Survivors!");
        }
        for(Player p : spectator) {
            p.setFlying(true);
            p.setAllowFlight(true);
            //transport spectators
            p.sendMessage("spectator - round initialized");
        }
        for(Player p : survivors) {
            //transport survivors
            p.sendMessage("You are a survivor defend yourself against the Virus - the undead have awakened");
        }

        broadcast("round begins");


    }
    public Player getPlayerbyString(String s) {
        for(Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.getName().toString().equalsIgnoreCase(s)) {
                return pl;
            }
        }
        return null;
    }
    public boolean set(Player p, String[] args) {
        int ID = 990;
        String UuID = p.getUniqueId().toString();
        if (args[3].equalsIgnoreCase("Inf")) {
            for (Player pl : infected) {
                if (pl != null) {
                    if (Objects.equals(pl.getUniqueId().toString(), UuID)) {
                        return false;
                    }
                }
            }
            infected[infID] = p;
            infID++;
            removePlayer(UuID, survivors);
            removePlayer(UuID, spectator);
            return true;
        } else if (args[3].equalsIgnoreCase("Su")) {
            for (Player pl : survivors) {
                if (pl != null) {
                    if (Objects.equals(pl.getUniqueId().toString(), UuID)) {
                        return false;
                    }
                }
            }
            survivors[surID] = p;
            surID++;
            removePlayer(UuID, infected);
            removePlayer(UuID, spectator);
            return true;
        } else if (args[3].equalsIgnoreCase("sp")) {
            for(Player pl : spectator) {
                if (pl != null) {
                    if(Objects.equals(pl.getUniqueId().toString(), UuID)) {
                        return false;
                    }
                }
            }
            spectator[spID] = p;
            spID++;
            removePlayer(UuID, survivors);
            removePlayer(UuID, infected);
            return true;
        }
        return false;
    }
    public boolean removePlayer(String UuID, Player[] playercontainer) {
        int suID = 0;
        for(Player p : playercontainer) {
            if (p != null) {
                if (Objects.equals(p.getUniqueId().toString(), UuID)) {
                    playercontainer[suID] = null;
                }
            }
            suID++;
        }
        return true;
    }
///////
    public void onPlayerDeath() {

    }
    public boolean teleportPlayer(Player p, int x, int y, int z, String world, String server) {
        Location loc = p.getLocation();
        loc.setWorld(Bukkit.getWorld(world));
        loc.setX((double) x);
        loc.setY((double) y);
        loc.setZ((double) z);
        p.teleport(loc);
        return true;
    }
    public void broadcast(String s) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(s);
        }
    }
    public void echo(Player p, String s) {
        p.sendMessage(s);
    }
    public void resetround() {

    }

}