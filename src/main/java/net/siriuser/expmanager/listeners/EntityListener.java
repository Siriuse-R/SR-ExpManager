package net.siriuser.expmanager.listeners;

import net.siriuser.expmanager.ExpManager;
import net.siriuser.expmanager.Helper;
import net.syamn.utils.LogUtil;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener {
    private ExpManager plugin;
    public EntityListener(final ExpManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void EntityDeathEvent (final EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();

        if (entity.getLastDamageCause() == (null)) return;
        final EntityDamageEvent cause = entity.getLastDamageCause();
        if (cause instanceof EntityDamageByEntityEvent) {
            Entity killer = ((EntityDamageByEntityEvent) cause).getDamager();

            Player killerP;
            if (killer instanceof Player) {
                killerP = (Player)killer;
            } else if (killer instanceof Wolf && ((Wolf) killer).isTamed()) {
                Wolf killerW = (Wolf)killer;
                killerP = (Player)killerW.getOwner();
            } else if (killer instanceof Arrow && ((Arrow) killer).getShooter() instanceof Player) {
                killerP = (Player) ((Arrow) killer).getShooter();
            } else {
                return;
            }

            final int configExp = Helper.getInstance().getConfig().getEntitySection().getInt(entity.getType().toString(), -1);
            LogUtil.info(entity.getType().toString());

            if (configExp == -1) return;

            event.setDroppedExp(configExp);
        }
    }
}
