package net.siriuser.SRExpManager;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SRExpManager extends JavaPlugin {

    public final static Logger log = Logger.getLogger("Minecraft");
    public final static String logPrefix = "[SREM] ";

    @Override
    public void onEnable() {

     // イベントリスナー登録
     PluginManager pm = getServer().getPluginManager();

     PluginDescriptionFile pdfFile = this.getDescription();
     log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + "is disabled!");
    }
}
