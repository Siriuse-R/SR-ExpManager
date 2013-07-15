package net.siriuser.SRExpManager;

import java.io.IOException;
import java.util.logging.Logger;

import net.syamn.utils.Metrics;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SRExpManager extends JavaPlugin {

    public final static Logger log = Logger.getLogger("Minecraft");
    public final static String logPrefix = "[SREM] ";

    private ConfigManager config;

    @Override
    public void onEnable() {

        config = new ConfigManager(this);
        try {
            config.loadConfig(true);
        } catch (Exception ex) {
            log.warning(logPrefix + "an error occured while trying to load the config file.");
            ex.printStackTrace();
        }

        // イベントリスナー登録
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ExpListener(this), this);

        // SR-CoreLibが無ければプラグインを停止
        if (!getServer().getPluginManager().isPluginEnabled("SR-CoreLib")) {
            log.warning(logPrefix + "SR-CoreLib is not enabled!");
            pm.disablePlugin(this);
            return;
        }

        setupMetrics();

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + "is disabled!");
    }

    private void setupMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            log.warning(logPrefix);
        }
    }

    public ConfigManager getConfigs() {
        return config;
    }
}
