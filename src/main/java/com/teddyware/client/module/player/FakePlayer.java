package com.teddyware.client.module.player;

import com.mojang.authlib.GameProfile;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;
import java.util.UUID;

public class FakePlayer extends Module {

    public FakePlayer() {
        super("FakePlayer", "Spawns a dummy to test things on.", Keyboard.KEY_NONE, Category.Player);
    }

    EntityOtherPlayerMP fakePlayer;

    @Override
    public void onEnable() {
        fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("a07208c2-01e5-4eac-a3cf-a5f5ef2a4700"), "Fit"));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        fakePlayer.inventory.copyInventory(mc.player.inventory);
        mc.world.addEntityToWorld(-100, fakePlayer);
    }

    @Override
    public void onDisable() {
        try {
            mc.world.removeEntity(fakePlayer);
        } catch (Exception ignored) {}
    }
}
