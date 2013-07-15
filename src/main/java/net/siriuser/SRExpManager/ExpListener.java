package net.siriuser.SRExpManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import net.syamn.utils.Util;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;

public class ExpListener implements Listener {

    private final static Logger log = SRExpManager.log;
    private final static String logPrefix = SRExpManager.logPrefix;
    private final SRExpManager plugin;
    private HashMap<UUID, Map<String, Long>> dragons;

    public ExpListener (final SRExpManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void EntityGiveExp (final EntityDeathEvent event) {
        final LivingEntity ent = event.getEntity();
        final int dropExp = event.getDroppedExp();

        if (ent.getLastDamageCause() == (null)) return;

        final EntityDamageEvent cause = ent.getLastDamageCause();
        if (cause instanceof EntityDamageByEntityEvent) {
            Entity killer = ((EntityDamageByEntityEvent) cause).getDamager();

            if (ent.getType().equals(EntityType.ENDER_DRAGON)) {
                return;

            } else {

                if (killer instanceof Player) {
                    Player killerP = (Player)killer;

                    if (killerP.getGameMode().getValue() == 1) {
                        return;
                    }

                    killerP.giveExp(dropExp);
                    killerP.sendMessage(Util.coloring("&a[Exp] &2" + ent.getType().getName() + "&eを倒し" + dropExp + "経験値を獲得。"));
                    event.setDroppedExp(0);

                } else if (killer instanceof Wolf && ((Wolf) killer).isTamed()) {
                    Wolf killerW = (Wolf)killer;
                    Player player = (Player) killerW.getOwner();

                    if (player.getGameMode().getValue() == 1) {
                        return;
                    }

                    player.giveExp(dropExp + 5);
                    player.sendMessage(Util.coloring("&a[Exp] &2" + ent.getType().getName() + "&eを狼が倒し" + dropExp + "経験値を獲得。&b (狼ボーナス: +5)"));
                    event.setDroppedExp(0);

                } else if (killer instanceof Arrow) {
                    Arrow arrow = (Arrow)killer;
                    Player killerP = (Player)arrow.getShooter();

                    if (killerP.getGameMode().getValue() == 1) {
                        return;
                    }

                    killerP.giveExp(dropExp);
                    killerP.sendMessage(Util.coloring("&a[Exp] &2" + ent.getType().getName() + "&eを倒し" + dropExp + "経験値を獲得。"));
                    event.setDroppedExp(0);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void BlockBreakGiveExp (final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final int dropExp = event.getExpToDrop();

        if (player.getGameMode().getValue() == 1) {
            return;
        }

        if (dropExp != 0) {
            player.giveExp(dropExp);
            player.sendMessage(Util.coloring("&a[Exp] &2" + event.getBlock().getType().toString() + "&eを破壊し" + dropExp + "経験値を獲得。"));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFurnaceExtract(final FurnaceExtractEvent event) {
        final Player player = event.getPlayer();
        final int dropExp = event.getExpToDrop();

        if (player.getGameMode().getValue() == 1) {
            return;
        }

        if (dropExp != 0) {
            player.giveExp(dropExp);
            player.sendMessage(Util.coloring("&a[Exp] &2" + "&eかまどから" + dropExp + "経験値を獲得。"));
        }
    }
}
