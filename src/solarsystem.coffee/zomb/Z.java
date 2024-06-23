package solarsystem.coffee.zomb;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static jogamp.common.os.elf.SectionArmAttributes.Tag.File;


public class Z extends JavaPlugin {

    lobby l = new lobby();
    config conf = new config();
    public Player[] survivors = new Player[990];
    public Player[] infected = new Player[990];
    public Player[] spectator = new Player[990];

    public String Lobbyworld = "Lobby";

    public String ver = "0.1.3";





    public static void main (String[] args){

    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("zP >Zom< loaded");
        Bukkit.getConsoleSender().sendMessage("Version: " + ver);
        getServer().getPluginManager().registerEvents(new o(), this);

        try {
            conf.loadConfig();
            l.setConf(conf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("zP >Zom< disabled");
        HandlerList.unregisterAll(this);
    }

   // @Override
    //public boolean onTick(){

    //}

    public int surID = 0;
    public int infID = 0;
    public int spID = 0;


    public boolean overwrite = false;
    public boolean overwritepvp = false;


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
                        if(args[0].equalsIgnoreCase("debug")){
                            if(args.length > 1) {
                                if(args[1].equalsIgnoreCase("setoverwrite")) {
                                if ("true".equalsIgnoreCase(args[2].toString())) {
                                   overwrite = true;
                                } else if ("false".equalsIgnoreCase(args[2].toString())) {
                                    overwrite = false;
                                } else {
                                    player.sendMessage("incorrect usage");
                                }
                                if(overwrite) { player.sendMessage("Overwrite [true]"); }
                                else {
                                    player.sendMessage("Overwrite [false]");
                                }
                                } else if(args[1].equalsIgnoreCase("overwritepvp")) {
                                    if ("true".equalsIgnoreCase(args[2].toString())) {
                                        overwritepvp = true;
                                    } else if ("false".equalsIgnoreCase(args[2].toString())) {
                                        overwritepvp = false;
                                    } else {
                                        player.sendMessage("incorrect usage");
                                    }
                                    if(overwritepvp) { player.sendMessage("Overwrite PVP [true]"); }
                                    else {
                                        player.sendMessage("Overwrite PVP [false]");
                                    }
                                }
                            } else {
                                player.sendMessage("Debug Command /zom debug [cmds]");
                                player.sendMessage("Debug cmds [setoverwrite:overwritepvp] [true|false]");
                                player.sendMessage("both overwrites have to be active to be affective");
                            }
                        }
                        if(args[0].equalsIgnoreCase("baa")){

                        }
                        if(args[0].equalsIgnoreCase("set")) {
                            Player p = null;
                            p = getPlayerbyString(args[2]);
                            if (p != null) {
                                try {
                                    switch (args[1]) {
                                        case "spawnInf":
                                            conf.setNewInfSpawn(p);
                                        case "spawnSu":
                                            conf.setNewSuSpawn(p);
                                        case "spawnsp":
                                                conf.setNewSpSpawn(p);
                                        case "listSpawns":
                                            conf.listSpawns(p);
                                        case "Inf":
                                            set(p, args);
                                            player.sendMessage(p.getDisplayName() + "set to infected");
                                            return true;
                                        case "Su":
                                            set(p, args);
                                            player.sendMessage(p.getDisplayName() + "set to su");
                                            return true;
                                        case "sp":
                                            set(p, args);
                                            player.sendMessage(p.getDisplayName() + "set to spectator");
                                            return true;
                                        default:
                                            player.sendMessage("Invalid arguments");
                                            break;
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                player.sendMessage("player not found");
                                return false;
                            }
                        }
                        if(args[0].equalsIgnoreCase("help")) {
                            String[] s = new String[13];
                            s[0] = "<<< Zom | Commands >>> ";
                            s[1] = "/runZom | /zom ";
                            s[2] = "/zom Lobby ";
                            s[3] = "/set (Inf|Su|sp) Player";
                            s[4] = "/baa";
                            player.sendMessage(s);
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
        l.lobbyrun();
        return true;
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
    public class o implements Listener {

        @EventHandler
        public void onEntityDeathEvent(EntityDeathEvent event) {

            if(event != null) {

                Entity pl = event.getEntity();
                if (pl.getType() == EntityType.PLAYER) {

                    Player p = (Player) pl;
                    broadcast(
                            "nerd died [ " +
                                    p.getDisplayName() +
                                    " ] "
                    );

                } else {

                    if (overwrite) {
                        Bukkit.getConsoleSender().sendMessage("Entity Died [" + pl.getName());
                    }

                }
            }
        }
        @EventHandler
        public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
            String UUID = event.getDamager().getUniqueId().toString();
            String Uuid = event.getEntity().getUniqueId().toString();

            boolean canceled = true;

            //check infection stat
            boolean selfIsZombie = false;
            if(event.getEntity().getType() == EntityType.ZOMBIE) { selfIsZombie = true; }
            if(!selfIsZombie && event.getEntity().getType() == EntityType.PLAYER) { for(Player p : l.infected) { if (p.getUniqueId().toString() == UUID) { selfIsZombie = true;}}}//replace with Playerz

            boolean attackerIsZombie = false;
            if (event.getDamager().getType() == EntityType.ZOMBIE) { attackerIsZombie = true; }
            if(!attackerIsZombie && event.getDamager().getType() == EntityType.PLAYER) { for(Player p : l.infected) { if (p.getUniqueId().toString() == UUID) { attackerIsZombie = true;}}}

            //cancel if same
            if((selfIsZombie && attackerIsZombie) || (!selfIsZombie && !attackerIsZombie)) {
                canceled = true;
            }

            //cancel
            if(!overwrite) {
                if (canceled) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(overwritepvp);
            }
        }
        @EventHandler
        public void onBlockBreakEvent(BlockBreakEvent event) {
            Player p = event.getPlayer();
            Block block = event.getBlock();
            if(event.isCancelled() == false) {
                if (conf.isPlayerBlocks(p.getUniqueId().toString(), block.getLocation())) {
                } else { event.setCancelled(true); };
            }
            //only allow own placed blockz to break
        }
        @EventHandler
        public void onBlockPlaceEvent(BlockPlaceEvent event) {
            Player p = event.getPlayer();
            Block block = event.getBlock();
            if(event.isCancelled() == false ) {
                conf.setPlayerBlocks(p.getUniqueId().toString(), block.getLocation());
            }
            //add block to own placed blockz
        }
    }

}
class config {
    public Location LobbyLoc = null;
    public Location FinalLoc = null;

    public FileConfiguration conf = new YamlConfiguration();
    public ArrayList<Location> InfLoc = new ArrayList<>();
    public ArrayList<Location> SuLoc = new ArrayList<>();
    public Location SpLoc = null;
    HashMap<String , Location[]> playerBlocks;

    public config() {
        conf = Bukkit.getPluginManager().getPlugin("zPZom").getConfig();
    }
    public boolean saveConfig() throws IOException {
        conf.set("LobbyLoc", LobbyLoc);
        conf.set("FinalLoc", FinalLoc);
        int ID = 0;
        for(Location l : InfLoc) {
            conf.set("InfLoc-" + Integer.toString(ID), l);
            ID++;
        }
        for(Location l : SuLoc) {
            conf.set("SuLoc-" + Integer.toString(ID), l);
        }
        conf.set("SpLoc", SpLoc);
        conf.save("Locationz.yml");
        return true;
    }
    public boolean loadConfig() throws IOException, InvalidConfigurationException {
        conf.load("Locationz.yml");
        int ID = 0;
        boolean load = true;
        while(load) {
            Location l = conf.getLocation("InfLoc-" + Integer.toString(ID));
            if(l != null) {
                InfLoc.add(l);
            } else {
                load = false;
            }
        }

        ID = 0;
        load = true;
        while(load) {
            Location l = conf.getLocation("SuLoc-" + Integer.toString(ID));
            if(l != null) {
            SuLoc.add(l); } else {
                load = false;
            }
        }

        SpLoc = conf.getLocation("SpLoc");
        LobbyLoc = conf.getLocation("LobbyLoc");
        FinalLoc = conf.getLocation("FinalLoc");
        //load locations
        return true;
    }
    public Location getLobbyLoc() {
        return LobbyLoc;
    }
    public Location getFinalLoc() {
        return FinalLoc;
    }
    public boolean setLobbyLoc(Player p) {
        LobbyLoc = p.getLocation();
        try {
            saveConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public boolean setFinalLoc(Player p) {
        FinalLoc = p.getLocation();
        try {
            saveConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public boolean setPlayerBlocks(String UUID, Location l) {
        Location[] loc = playerBlocks.get(UUID);
        loc[loc.length+1] = l;
        playerBlocks.put(UUID,loc);
        return true;
    }
    public boolean isPlayerBlocks(String UUID, Location pl) {
        Location[] loc = playerBlocks.get(UUID);
        boolean isFromPlayer = false;
        for(Location l : loc) {
            if(pl == l) { isFromPlayer = true; }
            break;
        }
        return isFromPlayer;
    }
    public boolean clearBlocks() {
        playerBlocks.clear();
        return true;
    }
    public boolean listSpawns(Player p) {

        StringBuilder s = new StringBuilder();

        s.append("Infected Spawns");
        for(Location l : InfLoc) {
            appendLoc(s,l);
        }
        s.append("Survivor Spawns");
        for(Location l : SuLoc) {
            appendLoc(s,l);
        }
        s.append("Spectator Spawn");
        appendLoc(s,SpLoc);

        p.sendMessage(s.toString());

        return true;
    }
    public StringBuilder appendLoc(StringBuilder s, Location l) {
        s.append("World: " + l.getWorld().toString() + " x: " + l.getX() + " y: " + l.getY() + " z: " + l.getZ());
        return s;
    }

    public ArrayList<Location> getInfSpawns() {
        return InfLoc;
    }
    public ArrayList<Location> getSuSpawns() {
        return SuLoc;
    }
    public Location getSpSpawn() {
        return SpLoc;
    }
    public boolean setNewInfSpawn(Player p) {
        InfLoc.add(p.getLocation());
        return true;
    }
    public boolean setNewSuSpawn(Player p) {
        SuLoc.add(p.getLocation());
        return true;
    }
    public boolean setNewSpSpawn(Player p) throws IOException{
        SpLoc = p.getLocation();
        saveConfig();
        return true;
    }


}