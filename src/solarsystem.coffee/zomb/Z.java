package solarsystem.coffee.zomb;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Z extends JavaPlugin {


    @Override
    public void OnEnable() {
        Bukkit.getConsoleSender().sendMessage("zP >Zom< loaded");
    }
    @Override
    public void OnDisable() {
        Bukkit.getConsoleSender().sendMessage("zP >Zom< disabled");
    }

    @Override
    public void OnCommand(CommandSender interpreter, Command cmd, String input, String[] args) {
        if(interpreter instanceof Player) {
            Player player = (Player) interpreter;
            switch(input) {
                case "Z":
                    player.sendMessage("o>");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300,1,true,true));
                default:
                    player.sendMessage("ooO");
            }




        } else {
            Bukkit.getConsoleSender().sendMessage("o> Console");
        }
    }



}