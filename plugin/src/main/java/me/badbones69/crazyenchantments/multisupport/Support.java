package me.badbones69.crazyenchantments.multisupport;

import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import me.badbones69.crazyenchantments.api.managers.WingsManager;
import me.badbones69.crazyenchantments.multisupport.plotsquared.PlotSquaredVersion;
import me.badbones69.crazyenchantments.multisupport.worldguard.WorldGuardVersion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Support {
    
    private static CrazyEnchantments ce = CrazyEnchantments.getInstance();
    private static WingsManager manager = ce.getWingsManager();
    private static WorldGuardVersion worldGuardVersion = ce.getWorldGuardSupport();
    private static PlotSquaredVersion plotSquaredVersion = ce.getPlotSquaredSupport();
    
    public static boolean inTerritory(Player player) {
        if (SupportedPlugins.ASKYBLOCK.isPluginLoaded() && ASkyBlockSupport.inTerritory(player)) {
            return true;
        }
        if (SupportedPlugins.ACID_ISLAND.isPluginLoaded() && AcidIslandSupport.inTerritory(player)) {
            return true;
        }
        if (SupportedPlugins.GRIEF_PREVENTION.isPluginLoaded() && GriefPreventionSupport.inTerritory(player)) {
            return true;
        }
        if (SupportedPlugins.PLOT_SQUARED.isPluginLoaded() && plotSquaredVersion.inTerritory(player)) {
            return true;
        }
        return false;
    }
    
    public static boolean isFriendly(Entity p, Entity o) {
        if (p instanceof Player && o instanceof Player) {
            Player player = (Player) p;
            Player other = (Player) o;
            if (SupportedPlugins.ASKYBLOCK.isPluginLoaded() && ASkyBlockSupport.isFriendly(player, other)) {
                return true;
            }
            if (SupportedPlugins.ACID_ISLAND.isPluginLoaded() && AcidIslandSupport.isFriendly(player, other)) {
                return true;
            }
            if (SupportedPlugins.MCMMO.isPluginLoaded() && MCMMOParty.isFriendly(player, other)) {
                return true;
            }
            if (SupportedPlugins.GRIEF_PREVENTION.isPluginLoaded()) {
                return GriefPreventionSupport.isFriendly(player, other);
            }
        }
        return false;
    }
    
    public static boolean isVanished(Entity p) {
        for (MetadataValue meta : p.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
    
    public static boolean canBreakBlock(Player player, Block block) {
        if (player != null) {
            if (SupportedPlugins.GRIEF_PREVENTION.isPluginLoaded() && !GriefPreventionSupport.canBreakBlock(player, block)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean allowsPVP(Location loc) {
        return !SupportedPlugins.WORLD_EDIT.isPluginLoaded() || !SupportedPlugins.WORLD_GUARD.isPluginLoaded() || worldGuardVersion.allowsPVP(loc);
    }
    
    public static boolean allowsBreak(Location loc) {
        return !SupportedPlugins.WORLD_EDIT.isPluginLoaded() || !SupportedPlugins.WORLD_GUARD.isPluginLoaded() || worldGuardVersion.allowsBreak(loc);
    }
    
    public static boolean allowsExplotions(Location loc) {
        return !SupportedPlugins.WORLD_EDIT.isPluginLoaded() || !SupportedPlugins.WORLD_GUARD.isPluginLoaded() || worldGuardVersion.allowsExplosions(loc);
    }
    
    public static boolean inWingsRegion(Player player) {
        if (SupportedPlugins.WORLD_EDIT.isPluginLoaded() && SupportedPlugins.WORLD_GUARD.isPluginLoaded()) {
            for (String region : manager.getRegions()) {
                if (worldGuardVersion.inRegion(region, player.getLocation())) {
                    return true;
                } else {
                    if (manager.canOwnersFly() && worldGuardVersion.isOwner(player)) {
                        return true;
                    }
                    if (manager.canMembersFly() && worldGuardVersion.isMember(player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static void noStack(Entity en) {
        if (SupportedPlugins.MOB_STACKER.isPluginLoaded()) {
            MobStacker.noStack(en);
        }
        if (SupportedPlugins.MOB_STACKER_2.isPluginLoaded()) {
            MobStacker2.noStack(en);
        }
    }
    
    public enum SupportedPlugins {
        
        MCMMO("mcMMO"),
        GRIEF_PREVENTION("GriefPrevention"),
        LEGACY_FACTIONS("LegacyFactions"),
        TOWNY("Towny"),
        EPIC_SPAWNERS("EpicSpawners"),
        AAC("AAC"),
        DAKATA("DakataAntiCheat"),
        NO_CHEAT_PLUS("NoCheatPlus"),
        VAULT("Vault"),
        WORLD_EDIT("WorldEdit"),
        WORLD_GUARD("WorldGuard"),
        FACTIONS_MASSIVE_CRAFT("Factions"),
        FACTIONS3("Factions"),
        FACTIONS_UUID("Factions"),
        FEUDAL("Feudal"),
        ACID_ISLAND("AcidIsland"),
        ASKYBLOCK("ASkyBlock"),
        KINGDOMS("Kingdoms"),
        SILK_SPAWNERS("SilkSpawners"),
        SILK_SPAWNERS_CANDC("SilkSpawners"),
        SPARTAN("Spartan"),
        MOB_STACKER("MobStacker"),
        MOB_STACKER_2("MobStacker2"),
        STACK_MOB("StackMob"),
        WILDSTACKER("WildStacker"),
        MEGA_SKILLS("MegaSkills"),
        PRECIOUS_STONES("PreciousStones"),
        PLOT_SQUARED("PlotSquared");
        
        private String name;
        
        private SupportedPlugins(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public boolean isPluginLoaded() {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(name);
            if (plugin != null) {
                if (this == SupportedPlugins.SILK_SPAWNERS) {
                    if (plugin.getDescription().getAuthors() != null) {
                        return plugin.getDescription().getAuthors().contains("xGhOsTkiLLeRx");
                    }
                    return false;
                } else if (this == SupportedPlugins.SILK_SPAWNERS_CANDC) {
                    if (plugin.getDescription().getAuthors() != null) {
                        return plugin.getDescription().getAuthors().contains("CandC_9_12");
                    }
                    return false;
                } else if (this == SupportedPlugins.FACTIONS_MASSIVE_CRAFT) {
                    if (plugin.getDescription() != null) {
                        if (plugin.getDescription().getWebsite() != null) {
                            return plugin.getDescription().getWebsite().equalsIgnoreCase("https://www.massivecraft.com/factions");
                        }
                    }
                    return false;
                } else if (this == SupportedPlugins.FACTIONS_UUID) {
                    if (plugin.getDescription().getAuthors() != null) {
                        return plugin.getDescription().getAuthors().contains("drtshock");
                    }
                    return false;
                } else if (this == SupportedPlugins.FACTIONS3) {
                    if (plugin.getDescription().getAuthors() != null) {
                        return plugin.getDescription().getAuthors().contains("Madus");
                    }
                    return false;
                }
            }
            return plugin != null;
        }
        
        public Plugin getPlugin() {
            return Bukkit.getServer().getPluginManager().getPlugin(name);
        }
        
    }
    
}
