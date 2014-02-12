package net.siriuser.expmanager.listeners;

import net.siriuser.expmanager.ExpManager;
import net.siriuser.expmanager.Helper;
import net.syamn.utils.LogUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;

import java.util.Locale;

public class BlockListener implements Listener {
    private ExpManager plugin;
    public BlockListener(final ExpManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreakEvent (final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        final int configExp = Helper.getInstance().getConfig().getBlockSection().getInt(block.getType().toString(), -1);

        LogUtil.info(block.getType().toString());

        if (configExp == -1) return;

        event.setExpToDrop(configExp);
    }

    @EventHandler
    public void onFurnaceExtractEvent (final FurnaceExtractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        int getExp = event.getExpToDrop();
    }
}