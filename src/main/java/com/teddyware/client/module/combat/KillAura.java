package com.teddyware.client.module.combat;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.BooleanSetting;
import com.teddyware.client.setting.settings.NumberSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

    public KillAura() {
        super("KillAura", "kill thing :)", Keyboard.KEY_NONE, Category.Combat);
        this.addSetting(range, passiveMobs, hostileMobs, players, cooldown);
    }

    public NumberSetting range = new NumberSetting("Range", this, 6, 1, 6, 0.5);
    public BooleanSetting passiveMobs = new BooleanSetting("Passives", this, false);
    public BooleanSetting hostileMobs = new BooleanSetting("Hostiles", this, false);
    public BooleanSetting players = new BooleanSetting("Players", this, true);
    public BooleanSetting cooldown = new BooleanSetting("HitCooldown", this, true);

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.player.isDead) return;
        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= range.getValue())
                .filter(entity -> !entity.isDead)
                .filter(entity -> attackCheck(entity))
                .sorted(Comparator.comparing(s -> mc.player.getDistance(s)))
                .collect(Collectors.toList());

        targets.forEach(this::attack);
    }

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }

    public void attack(Entity e) {
        if (cooldown.isEnabled()) {
            if (mc.player.getCooledAttackStrength(0) >= 1) {
                mc.playerController.attackEntity(mc.player, e);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        } else {
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketAnimation());
        }
    }

    private boolean attackCheck(Entity entity) {
        if (players.isEnabled() && entity instanceof EntityPlayer){
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        }

        if (passiveMobs.isEnabled() && entity instanceof EntityAnimal) {
            if (entity instanceof EntityTameable) {
                return false;
            }
            else {
                return true;
            }
        }
        if (hostileMobs.isEnabled() && entity instanceof EntityMob) {
            return true;
        }
        return false;
    }
}
