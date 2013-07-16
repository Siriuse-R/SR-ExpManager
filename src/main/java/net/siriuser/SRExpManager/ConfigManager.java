package net.siriuser.SRExpManager;

import java.io.File;
import java.util.logging.Logger;

import net.syamn.utils.file.FileStructure;
import net.tsuttsu305.util.JarUtil;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final int latestVersion = 1;

    // Logger
    private static final Logger log = SRExpManager.log;
    private static final String logPrefix = SRExpManager.logPrefix;
    private final SRExpManager plugin;

    // private YamlConfiguration conf;
    private FileConfiguration conf;
    private File pluginDir;

    /**
     * Constructor
     */
    public ConfigManager(final SRExpManager plugin) {
        this.plugin = plugin;
        this.pluginDir = this.plugin.getDataFolder();
    }

    public void setStringConfig(String Key, String Value) {
        conf.set(Key, Value);
        this.saveConfig();
    }
    public void setBooleanConfig(String Key, Boolean Value) {
        conf.set(Key, Value);
        this.saveConfig();
    }

    public void saveConfig() {
        plugin.saveConfig();
    }

    public void copyConfig() {
        FileStructure.createDir(pluginDir);
        File file = new File(pluginDir, "config.yml");if (!file.exists()) {
        //FileStructure.extractResource("/config.yml", pluginDir, false, false, plugin);
            JarUtil.copyJarResource(plugin.getJarFile(), new File(pluginDir, "config.yml"), "config.yml");
            log.info(logPrefix + "config.yml is not found! Created default config.yml!");
        }
    }

    public void loadConfig(final boolean initialLoad) throws Exception {
        this.copyConfig();
        this.reloadConfig();
        conf = plugin.getConfig();
        checkver(conf.getInt("ConfigVersion", 1));
    }

    public void reloadConfig() {
        plugin.reloadConfig();
    }

    /**
     * Check configuration file version
     */
    private void checkver(final int ver) {
        // compare configuration file version
        if (ver < latestVersion) {
            // first, rename old configuration
            final String destName = "oldconfig-v" + ver + ".yml";
            String srcPath = new File(pluginDir, "config.yml").getPath();
            String destPath = new File(pluginDir, destName).getPath();
            try {
                FileStructure.copyTransfer(srcPath, destPath);
                log.info("Copied old config.yml to " + destName + "!");
            } catch (Exception ex) {
                log.warning("Failed to copy old config.yml!");
            }

            // force copy config.yml and languages
            FileStructure.extractResource("/config.yml", pluginDir, true, false, plugin);
            // Language.extractLanguageFile(true);

            this.reloadConfig();
            conf = plugin.getConfig();

            log.info("Deleted existing configuration file and generate a new one!");
        }
    }

    /* ***** Begin Configuration Getters *********************** */

    public String getStringConfig(String Key) {
        return conf.getString(Key);
    }
    public Boolean getBooleanConfig(String Key) {
        return conf.getBoolean(Key);
    }
}
