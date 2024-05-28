package solarsystem.coffee.zomb;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;


public class Z extends JavaPlugin {



    public Player[] players = new Player[210];

    public Player[] survivors = new Player[99];
    public Player[] infected = new Player[99];
    public Player[] spectator = new Player[99];

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("zP >Zom< loaded");
    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("zP >Zom< disabled");
    }

    @Override
    public boolean onCommand(CommandSender interpreter, Command cmd, String input, String[] args) {
        if(interpreter instanceof Player) {
            Player player = (Player) interpreter;
            switch(input) {
                case "Z":
                    player.sendMessage("o>");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300,1,true,true));
                case "runZom":
                    runZom();
                    player.sendMessage("new round");
                case "showLobby":
                    showLobby(player);
                default:
                    player.sendMessage("ooO");
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
            if(rnd.nextBoolean()) {
                survivors[surID] = p;
                surID++;
            } else {
                infected[infID] = p;
                infID++;
            }
        }
        for(Player p : Bukkit.getOnlinePlayers()) {
            showLobby(p);
        }
        return true;
    }

    //optimize later
    public boolean showLobby(Player p) {
        broadcast("Players in survivor");
        StringBuilder survivor = new StringBuilder("Players in spectator: ");
        for(Player pl : survivors) {
            survivor.append(" | ").append(pl.getDisplayName());
        }
        broadcast(survivor.toString());
        broadcast("Players in spectator");
        StringBuilder spectators = new StringBuilder("Players in spectator: ");
        for(Player pl : spectator) {
            spectators.append(" | ").append(pl.getDisplayName());
        }
        broadcast(spectators.toString());

        broadcast("Players in infected ");
        StringBuilder infecteds = new StringBuilder("Players in infected: ");
        for(Player pl : infected) {
            infecteds.append(" | ").append(pl.getDisplayName());
        }
        broadcast(infecteds.toString());

        return false;
    }
    public void broadcast(String s) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(s);
        }
    }

}