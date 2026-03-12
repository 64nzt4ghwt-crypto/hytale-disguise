package com.howlstudio.disguise;
import com.hypixel.hytale.component.Ref; import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.*; import java.util.concurrent.ConcurrentHashMap;
public class DisguiseManager {
    private final Map<UUID,String> disguises=new ConcurrentHashMap<>();
    private final Map<UUID,String> realNames=new ConcurrentHashMap<>();
    private static final List<String> MOB_NAMES=List.of("Zombie","Skeleton","Creeper","Spider","Witch","Enderman","Zombie_Piglin","Blaze","Ghast","Warden","Phantom","Pillager","Evoker","Ravager","Elder_Guardian");
    public AbstractPlayerCommand getDisguiseCommand(){
        return new AbstractPlayerCommand("disguise","Disguise yourself. /disguise <mob|name>"){
            @Override protected void execute(CommandContext ctx,Store<EntityStore> store,Ref<EntityStore> ref,PlayerRef playerRef,World world){
                String name=ctx.getInputString().trim();if(name.isEmpty()||name.equalsIgnoreCase("help")){playerRef.sendMessage(Message.raw("[Disguise] Usage: /disguise <name> | /undisguise | Mobs: "+String.join(", ",MOB_NAMES.subList(0,5))+"..."));return;}
                if(name.length()>32){playerRef.sendMessage(Message.raw("[Disguise] Name too long (max 32 chars)."));return;}
                realNames.put(playerRef.getUuid(),playerRef.getUsername());
                disguises.put(playerRef.getUuid(),name);
                playerRef.sendMessage(Message.raw("[Disguise] Disguised as §6"+name+"§r. Other players see this name. /undisguise to reveal."));
                System.out.println("[Disguise] "+playerRef.getUsername()+" disguised as: "+name);
            }
        };
    }
    public AbstractPlayerCommand getUndisguiseCommand(){
        return new AbstractPlayerCommand("undisguise","Remove your disguise. /undisguise"){
            @Override protected void execute(CommandContext ctx,Store<EntityStore> store,Ref<EntityStore> ref,PlayerRef playerRef,World world){
                if(!disguises.containsKey(playerRef.getUuid())){playerRef.sendMessage(Message.raw("[Disguise] You're not disguised."));return;}
                disguises.remove(playerRef.getUuid());realNames.remove(playerRef.getUuid());
                playerRef.sendMessage(Message.raw("[Disguise] Disguise removed. You're back to §6"+playerRef.getUsername()+"§r."));
            }
        };
    }
    public AbstractPlayerCommand getDisguiseListCommand(){
        return new AbstractPlayerCommand("disguiselist","[Staff] List all disguised players. /disguiselist"){
            @Override protected void execute(CommandContext ctx,Store<EntityStore> store,Ref<EntityStore> ref,PlayerRef playerRef,World world){
                if(disguises.isEmpty()){playerRef.sendMessage(Message.raw("[Disguise] No disguised players."));return;}
                playerRef.sendMessage(Message.raw("[Disguise] Disguised players:"));
                for(var e:disguises.entrySet()){String real=realNames.getOrDefault(e.getKey(),"?");playerRef.sendMessage(Message.raw("  §6"+real+"§r → §e"+e.getValue()));}
            }
        };
    }
}
