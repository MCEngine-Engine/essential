package io.github.mcengine.spigotmc.essential.engine;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.Metrics;
import io.github.mcengine.common.essential.MCEngineEssentialCommon;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main PaperMC plugin class for MCEngineEssential.
 */
public class MCEngineEssentialSpigotMC extends JavaPlugin {

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        new Metrics(this, 26841);
        saveDefaultConfig(); // Save config.yml if it doesn't exist

        boolean enabled = getConfig().getBoolean("enable", false);
        if (!enabled) {
            getLogger().warning("Plugin is disabled in config.yml (enable: false). Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        String license = getConfig().getString("licenses.license", "free"); 
        if (!license.equalsIgnoreCase("free")) { 
            getLogger().warning("Plugin is disabled in config.yml.");
            getLogger().warning("Invalid license.");
            getLogger().warning("Check license or use \"free\".");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        MCEngineEssentialCommon api = new MCEngineEssentialCommon(this);

        // Load extensions
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.essential.extension.library.IMCEngineEssentialLibrary",
            "libraries",
            "Library"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.essential.extension.api.IMCEngineEssentialAPI",
            "apis",
            "API"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.essential.extension.agent.IMCEngineEssentialAgent",
            "agents",
            "Agent"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.essential.extension.addon.IMCEngineEssentialAddOn",
            "addons",
            "AddOn"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.essential.extension.dlc.IMCEngineEssentialDLC",
            "dlcs",
            "DLC"
        );

        // Check for plugin updates
        MCEngineCoreApi.checkUpdate(
            this,
            getLogger(),
            "github",
            "MCEngine-Engine",
            "essential",
            getConfig().getString("github.token", "null")
        );

        // Log project information
        getLogger().info("Project Platform: " + getConfig().getString("project.platform", "github"));
        getLogger().info("Project Organization Name: " + getConfig().getString("project.org.name", "MCEngine-Engine"));
        getLogger().info("Project Organization URL: " + getConfig().getString("project.org.url", "https://github.com/MCEngine-Engine"));
        getLogger().info("Project Repository Name: " + getConfig().getString("project.repo.name", "essential"));
        getLogger().info("Project Repository URL: " + getConfig().getString("project.repo.url", "https://github.com/MCEngine-Engine/essential"));
        getLogger().info("Project Repository Issues URL: " + getConfig().getString("project.repo.issue.url", "https://github.com/MCEngine-Engine/essential/issues"));
        getLogger().info("Project Website URL: " + getConfig().getString("project.website.url", "https://mcengine-website.github.io/essential"));
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic (if any) can go here
    }
}
