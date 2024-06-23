package solarsystem.coffee.zomb;

import com.google.common.base.Function;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
/*
public class playerDamageByPlayer extends EntityDamageByEntityEvent {

    public playerDamageByPlayer(@NotNull Entity damager, @NotNull Entity damagee, @NotNull EntityDamageEvent.DamageCause cause, @NotNull Map<DamageModifier, Double> modifiers, @NotNull Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(damager, damagee, cause, modifiers, modifierFunctions);
    }

    @Override
    public void setDamage(double damage) {

        String Uuid = this.entity.getUniqueId().toString();
        String attackerUUID = this.getDamager().getUniqueId().toString();
        boolean selfIsZombie = (this.entity.getType() == EntityType.ZOMBIE); //replace with Playerz
        boolean attackerIsZombie = (this.getDamager().getType() == EntityType.ZOMBIE);


        double remaining = damage;
        double oldRemaining = this.getDamage(EntityDamageEvent.DamageModifier.BASE);
        DamageModifier[] var10;
        int var9 = (var10 = MODIFIERS).length;

        for(int var8 = 0; var8 < var9; ++var8) {
            DamageModifier modifier = var10[var8];
            if (this.isApplicable(modifier)) {
                Function<? super Double, Double> modifierFunction = (Function)this.modifierFunctions.get(modifier);
                double newVanilla = (Double)modifierFunction.apply(remaining);
                double oldVanilla = (Double)modifierFunction.apply(oldRemaining);
                double difference = oldVanilla - newVanilla;
                double old = this.getDamage(modifier);
                if (old > 0.0) {
                    this.setDamage(modifier, Math.max(0.0, old - difference));
                } else {
                    this.setDamage(modifier, Math.min(0.0, old - difference));
                }

                remaining += newVanilla;
                oldRemaining += oldVanilla;
            }
        }
        boolean isfriendly = false;




        this.setDamage(EntityDamageEvent.DamageModifier.BASE, damage);
    }

    //define player behaviour for infected and survivors

    //use Logblock to make players only able to break there own property

    @Override
    public default boolean teleportPlayer(Player p, int x, int y, int z, String world, String server) {
        Location loc = p.getLocation();
        loc.setWorld(Bukkit.getWorld(world));
        loc.setX((double) x);
        loc.setY((double) y);
        loc.setZ((double) z);
        p.teleport(loc);
        return true;
    }

    @Override
    public default boolean pl(@NonNull Player p) {

        p.getPlayer().getLastDamage();
        p.getPlayer().getLastDamageCause();
        p.getPlayer().setHealth(40);
        p.setHealthScale(3);
        p.getKiller();

        p.setHealthScaled(true);
        EntityDamageEvent cause = p.getLastDamageCause();
        assert cause != null;
        cause.getCause().toString();
        p.getAttribute();
        p.setStarvationRate(0);



        return true;
    }
}*/
