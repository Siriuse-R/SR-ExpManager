package net.siriuser.SRExpManager;

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
    private final ConfigManager config;

    public ExpListener (final SRExpManager plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigs();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void EntityGiveExp (final EntityDeathEvent event) {

        if (!config.getBooleanConfig("DirectGiveExp.Entity")) return;

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

                    if (config.getBooleanConfig("CreativeModeExp") && !(killerP.getGameMode().getValue() == 1)) {
                        killerP.giveExp(dropExp);
                        if (config.getBooleanConfig("UseExpMessage")) {
                            killerP.sendMessage(Util.coloring(config.getStringConfig("Message.Entity")
                                    .replace("%Entity%", ent.getType().getName())
                                    .replace("%Exp%", String.valueOf(dropExp))));
                        }

                    }

                    event.setDroppedExp(0);

                } else if (killer instanceof Wolf && ((Wolf) killer).isTamed()) {
                    Wolf killerW = (Wolf)killer;
                    Player player = (Player) killerW.getOwner();

                    if (config.getBooleanConfig("CreativeModeExp") && !(player.getGameMode().getValue() == 1)) {
                        player.giveExp(dropExp + 5);
                        if (config.getBooleanConfig("UseExpMessage")) {
                            player.sendMessage(Util.coloring(config.getStringConfig("Message.Entity") + config.getStringConfig("Message.EntityWolf")
                                    .replace("%Entity%", ent.getType().getName())
                                    .replace("%Exp%", String.valueOf(dropExp))
                                    ));
                        }
                    }

                    event.setDroppedExp(0);

                } else if (killer instanceof Arrow) {
                    Arrow arrow = (Arrow)killer;
                    Player killerP = (Player)arrow.getShooter();

                    if (config.getBooleanConfig("CreativeModeExp") && !(killerP.getGameMode().getValue() == 1)) {
                        killerP.giveExp(dropExp);
                        if (config.getBooleanConfig("UseExpMessage")) {
                            killerP.sendMessage(Util.coloring(config.getStringConfig("Message.Entity")
                                    .replace("%Entity%", ent.getType().getName())
                                    .replace("%Exp%", String.valueOf(dropExp))));
                        }
                    }

                    event.setDroppedExp(0);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void BlockBreakGiveExp (final BlockBreakEvent event) {

        if (!config.getBooleanConfig("DirectGiveExp.Block")) return;

        final Player player = event.getPlayer();
        final int dropExp = event.getExpToDrop();

        if (config.getBooleanConfig("CreativeModeExp") && !(player.getGameMode().getValue() == 1) && dropExp != 0) {
            player.giveExp(dropExp);
            if (config.getBooleanConfig("UseExpMessage")) {
                player.sendMessage(Util.coloring(config.getStringConfig("Message.Block")
                        .replace("%Block%", event.getBlock().getType().toString())
                        .replace("%Exp%", String.valueOf(dropExp))));
            }
        }

        event.setExpToDrop(0);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFurnaceExtract(final FurnaceExtractEvent event) {

        if (!config.getBooleanConfig("DirectGiveExp.Furnace")) return;

        final Player player = event.getPlayer();
        final int dropExp = event.getExpToDrop();

        if (config.getBooleanConfig("CreativeModeExp") && !(player.getGameMode().getValue() == 1) && dropExp != 0) {
            player.giveExp(dropExp);
            if (config.getBooleanConfig("UseExpMessage")) {
                player.sendMessage(Util.coloring(config.getStringConfig("Message.Furnace")
                        .replace("%Exp%", String.valueOf(dropExp))));
            }
        }

        event.setExpToDrop(0);
    }
}
