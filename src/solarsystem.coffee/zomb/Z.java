package solarsystem.coffee.zomb;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


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

        for(Player p : Bukkit.getOnlinePlayers()) {
            players[0] = p;
            broadcast("Player [ " + p.getName() + " ] added to lobby");
        }

        //phase 1
        //


        return true;
    }

    //optimize later
    public boolean showLobby(Player p) {
        broadcast("Players in survivor");
        for(Player pl : survivors) {
            broadcast(pl.getDisplayName());
        }
        broadcast("Players in spectator");
        for(Player pl : spectator) {
            broadcast(pl.getDisplayName());
        }
        broadcast("Players in infected ");
        for(Player pl : infected) {
            broadcast(pl.getDisplayName());
        }

    }
    public void broadcast(String s) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(s);
        }
    }

}