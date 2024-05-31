package solarsystem.coffee.zomb;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Random;


public class Z extends JavaPlugin {



    public Player[] players = new Player[210];

    public Player[] survivors = new Player[990];
    public Player[] infected = new Player[990];
    public Player[] spectator = new Player[990];

    public String Lobbyworld = "Lobby";
    public String Playworld = "Playworld";



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
                            player.sendMessage("new round");
                        }
                        if(args[0].equalsIgnoreCase("Lobby")) {
                            showLobbyforplayer(player);
                        }
                        if(args[0].equalsIgnoreCase("P")) {
                            player.sendMessage("Sh33pio");

                        }
                        if(args[0].equalsIgnoreCase("showLobby")){
                            showLobbyforplayer(player);
                        }
                        if(args[0].equalsIgnoreCase("baa")){

                        }
                        if(args[0].equalsIgnoreCase("set")) {
                            Player p = null;
                            switch (args[1].toString()) {
                                case "Inf":
                                    p = getPlayerbyString(args[2]);
                                    if (p != null) {
                                        player.sendMessage("found " + p.getName());
                                        setInf(p, true);
                                        player.sendMessage("set to infected");
                                    } else {
                                        player.sendMessage("player not found");
                                    }
                                    break;
                                case "Su":
                                    p = getPlayerbyString(args[2]);
                                    if (p != null) {
                                        setInf(p, false);
                                        player.sendMessage("set to su");
                                    } else {
                                        player.sendMessage("player not found");
                                    }
                                    break;
                                case "sp":
                                    p = getPlayerbyString(args[2]);
                                    if (p != null) {
                                        setInf(p, false);
                                        player.sendMessage("set to spectator");
                                    } else {
                                        player.sendMessage("player not found");
                                    }
                                    break;
                                default:
                                    p.sendMessage("Invalid arguments");
                                    break;
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

        int ID = 0;
        for(Player p : Bukkit.getOnlinePlayers()) {
            players[ID] = p;
            broadcast("Player [ " + p.getName() + " ] added to lobby");
            ID++;
        }

        //phase 1
        //
        Random rnd = new Random();
        surID = 0;
        infID = 0;
        for(Player p : players) {
            if(p != null) {
                if (rnd.nextBoolean()) {
                    survivors[surID] = p;
                    surID++;
                } else {
                    infected[infID] = p;
                    infID++;
                }
            }
        }
           showLobby();
        //
        return true;
    }
    public void runround() {

        //init timer


        for(Player p : infected) {
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
    public boolean setInf(Player p, boolean booly) {
        int ID = 990;
        String UuID = p.getUniqueId().toString();
        if (booly) {
            for (Player pl : infected) {
                if (pl != null) {
                    if (Objects.equals(pl.getUniqueId().toString(), UuID)) {
                        return false;
                    }
                }
            }
                infected[infID] = p;
                infID++;
                int suID = 0;
                for(Player pl : survivors) {
                    if (pl != null) {
                        if (Objects.equals(pl.getUniqueId().toString(), UuID)) {
                            survivors[suID] = null;
                        }
                    }
                    suID++;
                }
                int specID = 0;
                for(Player pl : spectator) {
                    if(pl != null) {
                        if (Objects.equals(pl.getUniqueId().toString(), UuID)) {
                            spectator[specID] = null;
                        }
                    }
                    specID++;
                }
                return true;
        } else {
            for (Player pl : survivors) {
                if(pl != null) {
                    if (Objects.equals(pl.getUniqueId().toString(), UuID)) {
                        return false;
                    }
                }
            }
                survivors[surID] = p;
                surID++;
                int inID = 0;
                for(Player pl : infected) {
                    if(pl != null) {
                        if (Objects.equals(pl.getUniqueId().toString(), UuID)) {
                            infected[inID] = null;
                        }
                    }
                    inID++;
                }
                int specID = 0;
                for(Player pl : spectator) {
                    if(pl != null) {
                        if (Objects.equals(pl.getUniqueId().toString(), UuID)) {
                            spectator[specID] = null;
                        }
                    }
                    specID++;
                }
                return true;
        }
    }

    //optimize later
    public void showLobby() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            showLobbyforplayer(p);
        }
    }
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
    public void showLobbyforplayer(Player p) {
        StringBuilder survivor = new StringBuilder("Players in survivors: ");
        for(Player pl : survivors) {
            if (pl != null) {
                survivor.append(" | ").append(pl.getDisplayName());
            }
        }
        echo(p,survivor.toString());
        StringBuilder spectators = new StringBuilder("Players in spectator: ");
        for(Player pl : spectator) {
            if (pl != null) {
            spectators.append(" | ").append(pl.getDisplayName());
        }
        }
        echo(p,spectators.toString());

        StringBuilder infecteds = new StringBuilder("Players in infected: ");
        for(Player pl : infected) {
            if (pl != null) {
                infecteds.append(" | ").append(pl.getDisplayName());
            }
        }
        echo(p,infecteds.toString());

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