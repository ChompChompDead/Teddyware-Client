package com.teddyhack.client.module.combat;

import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.events.EventUpdate;
import com.teddyhack.client.Teddyhack;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.setting.Setting;
import com.teddyhack.client.setting.settings.BooleanSetting;
import com.teddyhack.client.setting.settings.NumberSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

    public KillAura() {
        super("KillAura", "kill thing :)", Keyboard.KEY_NONE, Category.Combat);
        this.addSetting(rangeA, passiveMobsA, hostileMobsA, playersA);
    }

    public NumberSetting rangeA = new NumberSetting("Range", this, 6, 1, 6, 0.5);
    public BooleanSetting passiveMobsA = new BooleanSetting("Passives", this, true);
    public BooleanSetting hostileMobsA = new BooleanSetting("Hostiles", this, true);
    public BooleanSetting playersA = new BooleanSetting("Players", this, true);

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                if (mc.player == null || mc.player.isDead) return;
                List<Entity> targets = mc.world.loadedEntityList.stream()
                        .filter(entity -> entity != mc.player)
                        .filter(entity -> mc.player.getDistance(entity) <= rangeA.getValue())
                        .filter(entity -> !entity.isDead)
                        .filter(entity -> attackCheck(entity))
                        .sorted(Comparator.comparing(s -> mc.player.getDistance(s)))
                        .collect(Collectors.toList());

                targets.forEach(this::attack);
            }
        }
    }

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }

    public void attack(Entity e) {
        if (mc.player.getCooledAttackStrength(0) >= 1){
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    private boolean attackCheck(Entity entity) {

        if (playersA.isEnabled() && entity instanceof EntityPlayer){
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        }

        if (passiveMobsA.isEnabled() && entity instanceof EntityAnimal) {
            if (entity instanceof EntityTameable) {
                return false;
            }
            else {
                return true;
            }
        }
        if (hostileMobsA.isEnabled() && entity instanceof EntityMob) {
            return true;
        }
        return false;
    }
}
