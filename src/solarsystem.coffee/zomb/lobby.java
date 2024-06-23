package solarsystem.coffee.zomb;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class lobby extends Z {

    public ArrayList<Player> Players = new ArrayList<>();

    public ArrayList<Player> Survivors = new ArrayList<>();
    public Player[] infected = new Player[990];
    public ArrayList<Player> Infected = new ArrayList<>();
    public Player[] spectator = new Player[990];

    public String Lobbyworld = "Lobby";
    public String Playworld = "Playworld";
    public config conf = null;

    //optimize later
    public void showLobby() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            showLobbyforplayer(p);
        }
    }

    public lobby InLobby(){

        survivors = new Player[990];
        infected = new Player[990];
        spectator = new Player[990];
        
        int ID = 0;
        for(Player p : Bukkit.getOnlinePlayers()) {
            Players.add(p);
            broadcast("Player [ " + p.getName() + " ] added to lobby");
            ID++;
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
                    survivors[surID] = p;
                    Survivors.add(p);
                    surID++;
                } else {
                    infected[infID] = p;
                    infID++;
                    Infected.add(p);
                }
            }
        }
    }

    public void lobbyrun() {
        conf.clearBlocks();
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
}
