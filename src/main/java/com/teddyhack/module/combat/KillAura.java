package com.teddyhack.module.combat;

import com.teddyhack.event.Event;
import com.teddyhack.event.listeners.EventUpdate;
import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import com.teddyhack.setting.settings.BooleanSetting;
import com.teddyhack.setting.settings.NumberSetting;
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
    }

    public NumberSetting rangeA = new NumberSetting("range", this, 6, 1, 6, 0.5);
    public BooleanSetting passiveMobsA = new BooleanSetting("passives", this, true);
    public BooleanSetting hostileMobsA = new BooleanSetting("hostiles", this, true);
    public BooleanSetting playersA = new BooleanSetting("players", this, true);

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

                targets.forEach(target -> {
                    attack(target);
                });
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
