package solarsystem.coffee.zomb;
import jdk.jfr.Timestamp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
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
        int surID = 0;
        int infID = 0;
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

        //broadcast("round begins");


    }

    //optimize later
    public boolean showLobby() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            showLobbyforplayer(p);
        }
        return true;
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
    public boolean showLobbyforplayer(Player p) {
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