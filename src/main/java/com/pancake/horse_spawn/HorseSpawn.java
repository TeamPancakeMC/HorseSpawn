package com.pancake.horse_spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.http.util.EntityUtils;

@Mod(HorseSpawn.MOD_ID)
public class HorseSpawn {
    public static final String MOD_ID = "horse_spawn";
    public HorseSpawn() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);
    }


    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (player.getPersistentData().contains("firstJoin")) return;

        player.getPersistentData().putBoolean("firstJoin", true);

        Horse horse = EntityType.HORSE.create(level);
        int radius = 5;
        int x = level.random.nextInt(radius);
        int z = level.random.nextInt(radius);

        if (horse != null){
            horse.moveTo(player.getX() + x, player.getY(), player.getZ() + z);
            horse.tameWithName(player);
            horse.equipSaddle(null);
            level.addFreshEntity(horse);
        }
    }


}
