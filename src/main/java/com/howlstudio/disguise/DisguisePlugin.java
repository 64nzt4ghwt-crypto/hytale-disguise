package com.howlstudio.disguise;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/** DisguiseSystem — Cosmetic disguises. Change your display name to a mob or custom name. Staff tool. */
public final class DisguisePlugin extends JavaPlugin {
    public DisguisePlugin(JavaPluginInit init){super(init);}
    @Override protected void setup(){
        System.out.println("[Disguise] Loading...");
        DisguiseManager mgr=new DisguiseManager();
        CommandManager.get().register(mgr.getDisguiseCommand());
        CommandManager.get().register(mgr.getUndisguiseCommand());
        CommandManager.get().register(mgr.getDisguiseListCommand());
        System.out.println("[Disguise] Ready.");
    }
    @Override protected void shutdown(){System.out.println("[Disguise] Stopped.");}
}
