package solarsystem.coffee.zomb;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class lobby extends Z {

    public ArrayList<Player> Players = new ArrayList<>();

    public ArrayList<Player> Survivors = new ArrayList<>();
    public ArrayList<Player> Infected = new ArrayList<>();
    public HashMap<String, InfPlayer> InfPlayerz = new HashMap<String,InfPlayer>();


    public boolean runZom = false;

    public HashMap<String, Integer> PlayerInfo = new HashMap<String, Integer>();
    public String Playworld = "Playworld";
    public config conf = null;

    //optimize later
    public void showLobby() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            showLobbyforplayer(p);
        }
    }

    public lobby InLobby(){

        for(Player p : Bukkit.getOnlinePlayers()) {
            Players.add(p);
            broadcast("Player [ " + p.getName() + " ] added to lobby");
        }
        
        return this;
    }
    public void shuffle(){

        Random rnd = new Random();
        surID = 0;
        infID = 0;
        for(Player p : Players) {
            if(p != null) {
                if (rnd.nextInt() > 0.33) {
                    Survivors.add(p);
                    PlayerInfo.put(p.getUniqueId().toString(),0);
                    surID++;
                } else {
                    infID++;
                    Infected.add(p);
                    PlayerInfo.put(p.getUniqueId().toString(),1);
                }
            }
        }
    }
    public Player getNearestSurvivor(Location loc) {

        Player nearest = null;
        double dis = 0;
        for(Player p :Survivors) {
            if(nearest == null || p.getLocation().distance(loc)<dis) {
                nearest = p;
                dis = p.getLocation().distance(loc);
            }
        }
        return nearest;
    }
    public void lobbyrun() {

        runZom = true;
        conf.clearBlocks();
        for(Player p : infected) {

            p.setFoodLevel(9);
            p.setSaturation((float) 9);

            //transport to infected spawnarea
            spawn(p);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1,1,true,false));
            p.sendMessage("You are infected with the Virus - Your desire for blood has awakened - Infect Survivors!");
        }
        for(Player p : spectator) {
            p.setFlying(true);
            p.setAllowFlight(true);
            //transport spectators
            spawn(p);
            p.sendMessage("spectator - round initialized");
        }
        for(Player p : survivors) {
            //transport survivors
            spawn(p);
            p.sendMessage("You are a survivor defend yourself against the Virus - the undead have awakened");
        }

        broadcast("round begins");
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
    public void setConf(config conf) {
        this.conf = conf;
    }

    public boolean getrunning() {
        return runZom;
    }
    public boolean getisPlayerIn(String UUID) {
        for(Player p : Players) {
            if(p.getUniqueId().toString() == UUID) {
                return true;
            }
        }
        return false;
    }
    public boolean setPlayerAlive(Player p, boolean alive) {
        String UUID = p.getUniqueId().toString();
        for(InfPlayer pl : InfPlayerz.values()) {
            pl.alive = alive;

        }
        InfPlayerz.get(UUID).alive = alive;
        return true;
    }
    public boolean setPlayerOnline(String UUID, boolean online) {
        InfPlayerz.get(UUID).online = online;
        return true;
    }
    public boolean spawn(Player p) {
        if(runZom == true) {
            switch (PlayerInfo.get(p.getUniqueId().toString())) {
                case 0:
                    l.tp(p,conf.getInfSpawns());
                case 1:
                    l.tp(p,conf.getSuSpawns());
                default:
                    l.tp(p,conf.getSpSpawn());
            }
        } else {
            l.tp(p,conf.getLobbyLoc());
        }


        return true;
    }
    public boolean tp(Player p, ArrayList<Location> loc){
        int length = loc.size();
        Random rand = new Random();
        if(length > 0) {
            int locID = rand.nextInt(0,length);
            p.teleport(loc.get(locID));
            return true;
        }
        return false;
    }
}
