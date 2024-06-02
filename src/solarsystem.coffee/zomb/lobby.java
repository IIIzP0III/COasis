package solarsystem.coffee.zomb;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

public class lobby extends Z {

    public Player[] players = new Player[210];

    public Player[] survivors = new Player[990];
    public Player[] infected = new Player[990];
    public Player[] spectator = new Player[990];

    public String Lobbyworld = "Lobby";
    public String Playworld = "Playworld";

    //optimize later
    public void showLobby() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            showLobbyforplayer(p);
        }
    }

    public lobby InLobby(){
        int ID = 0;
        for(Player p : Bukkit.getOnlinePlayers()) {
            players[ID] = p;
            broadcast("Player [ " + p.getName() + " ] added to lobby");
            ID++;
        }
        return this;
    }
    public void shuffle(){

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
}
