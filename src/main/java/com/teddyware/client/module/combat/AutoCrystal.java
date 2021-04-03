package com.teddyware.client.module.combat;

import com.teddyware.api.event.events.EventPlayerUpdate;
import com.teddyware.api.event.events.EventRender;
import com.teddyware.api.util.TWTessellator;
import com.teddyware.api.util.TimerUtil;
import com.teddyware.api.util.color.JColor;
import com.teddyware.client.setting.settings.BooleanSetting;
import com.teddyware.client.setting.settings.ColorSetting;
import com.teddyware.client.setting.settings.ModeSetting;
import com.teddyware.client.setting.settings.NumberSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;
import com.teddyware.api.util.EntityUtil;

// SKID FROM EVERY CLIENT U KNOW INCLUDING TATER!!!!!!!!!!!!

@Module.Data(name = "AutoCrystal", description = "Automatically places crystals and breaks it for you.", key = Keyboard.KEY_NONE, category = Category.Combat)
public class AutoCrystal extends Module {

    public BooleanSetting switchToCrystal = new BooleanSetting("SwitchToCrystal", this, false);
    public BooleanSetting players = new BooleanSetting("Players", this, false);
    public BooleanSetting mobs = new BooleanSetting("Hostiles", this, false);
    public BooleanSetting passives = new BooleanSetting("Passives", this, false);
    public BooleanSetting place = new BooleanSetting("Place", this, false);
    public BooleanSetting explode = new BooleanSetting("Break", this, false);
    public NumberSetting range = new NumberSetting("Range", this, 5, 0, 6, 1);
    public NumberSetting minDamage = new NumberSetting("MinimumDmg", this, 4, 0, 20, 1);
    public NumberSetting selfDamage = new NumberSetting("SelfDamage", this, 10, 0, 20, 1);
    public BooleanSetting antiWeakness = new BooleanSetting("AntiWeakness", this, false);
    public BooleanSetting singlePlace = new BooleanSetting("SinglePlace", this, false);
    public BooleanSetting rotate = new BooleanSetting("Rotate", this, false);
    public BooleanSetting rayTrace = new BooleanSetting("Raytrace", this, false);
    public ColorSetting color = new ColorSetting("Color", this, new JColor(255, 255, 255, 255));
    public ModeSetting breakHand = new ModeSetting("BrkHand", this, "Main", "Main", "Offhand");
    public NumberSetting breakSpeed = new NumberSetting("BrkSpeed", this, 20, 0, 20, 1);
    public NumberSetting placeSpeed = new NumberSetting("PlaceSpeed", this, 20, 0, 20, 1);


    public AutoCrystal() {
        this.addSetting(players, mobs, passives, breakHand, breakSpeed, placeSpeed, minDamage, selfDamage, range, place, explode, switchToCrystal, antiWeakness, singlePlace, rotate, rayTrace, color);
    }

    private BlockPos render;
    private Entity renderEnt;
    private long systemTime = -1;
    private static boolean togglePitch = false;
    private boolean switchCooldown = false;
    private boolean isAttacking = false;
    private int oldSlot = -1;
    private int newSlot;
    private int breaks;

    private TimerUtil timer = new TimerUtil();

    @Override
    public void onUpdate() {
        EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityEnderCrystal)
                    .map(entity -> (EntityEnderCrystal) entity)
                    .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                    .orElse(null);
        if (explode.isEnabled() && crystal != null && mc.player.getDistance(crystal) <= range.getValue() && mc.player.getHealth() >= selfDamage.getValue()) {
            if (antiWeakness.isEnabled() && mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                if (!isAttacking) {
                    oldSlot = mc.player.inventory.currentItem;
                    isAttacking = true;
                }
                newSlot = -1;
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = mc.player.inventory.getStackInSlot(i);
                    if (stack == ItemStack.EMPTY) {
                        continue;
                    }
                    if ((stack.getItem() instanceof ItemSword)) {
                        newSlot = i;
                        break;
                    }
                    if ((stack.getItem() instanceof ItemTool)) {
                        newSlot = i;
                        break;
                    }
                }

                if (newSlot != -1) {
                    mc.player.inventory.currentItem = newSlot;
                    switchCooldown = true;
                }
            }
            lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
            mc.playerController.attackEntity(mc.player, crystal);
            if (timer.getTimePassed() / 50 >= 20 - breakSpeed.getValue()) {
                timer.reset();
                mc.player.swingArm(getHandToBreak());
            }
            breaks++;
            if (breaks == 2 && !singlePlace.isEnabled()) {
                if (rotate.isEnabled()) {
                    resetRotation();
                }
                breaks = 0;
                return;
            } else if (singlePlace.isEnabled() && breaks == 1) {
                if (singlePlace.isEnabled()) {
                    resetRotation();
                }
                breaks = 0;
                return;
            }
        } else {
            if (rotate.isEnabled()) {
                resetRotation();
            }
            if (oldSlot != -1) {
                mc.player.inventory.currentItem = oldSlot;
                oldSlot = -1;
            }
            isAttacking = false;
        }

        int crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? mc.player.inventory.currentItem : -1;
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
        }

        boolean offhand = false;
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        } else if (crystalSlot == -1) {
            return;
        }

        List<BlockPos> blocks = findCrystalBlocks();
        List<Entity> entities = new ArrayList<>();
        if (players.isEnabled()) {
            entities.addAll(mc.world.playerEntities);
        }
        entities.addAll(mc.world.loadedEntityList.stream().filter(entity -> EntityUtil.isLiving(entity) && (EntityUtil.isPassive(entity) ? passives.isEnabled() : mobs.isEnabled())).collect(Collectors.toList()));

        BlockPos q = null;
        double damage = .5;
        for (Entity entity : entities) {
            if (entity == mc.player || ((EntityLivingBase) entity).getHealth() <= 0) {
                continue;
            }
            for (BlockPos blockPos : blocks) {
                double b = entity.getDistanceSq(blockPos);
                if (b >= 169) {
                    continue;
                }
                double d = calculateDamage(blockPos.getX() + .5, blockPos.getY() + 1, blockPos.getZ() + .5, entity);
                if (d < minDamage.getValue()) {
                    continue;
                }
                if (d > damage) {
                    double self = calculateDamage(blockPos.getX() + .5, blockPos.getY() + 1, blockPos.getZ() + .5, mc.player);
                    if ((self > d && !(d < ((EntityLivingBase) entity).getHealth())) || self - .5 > mc.player.getHealth()) {
                        continue;
                    }
                    damage = d;
                    q = blockPos;
                    renderEnt = entity;
                }
            }
        }
        if (damage == .5) {
            render = null;
            renderEnt = null;
            if (rotate.isEnabled()) {
                resetRotation();
            }
            return;
        }
        render = q;

        if (place.isEnabled()) {
            if (!offhand && mc.player.inventory.currentItem != crystalSlot) {
                if (switchToCrystal.isEnabled()) {
                    mc.player.inventory.currentItem = crystalSlot;
                    if (rotate.isEnabled()) {
                        resetRotation();
                    }
                    switchCooldown = true;
                }
                return;
            }
            EnumFacing f;
            lookAtPacket(q.getX() + .5, q.getY() - .5, q.getZ() + .5, mc.player);
            if (rayTrace.isEnabled()) {
                RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(q.getX() + .5, q.getY() - .5d, q.getZ() + .5));
                if (result == null || result.sideHit == null) {
                    f = EnumFacing.UP;
                } else {
                    f = result.sideHit;
                }
                if (switchCooldown) {
                    switchCooldown = false;
                    return;
                }
            } else {
                f = EnumFacing.UP;
            }
            if (timer.getTimePassed() / 50 >= 20 - placeSpeed.getValue()) {
                timer.reset();
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(q, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
            }
        }

        if (isSpoofingAngles) {
            if (togglePitch) {
                mc.player.rotationPitch += 0.0004;
                togglePitch = false;
            } else {
                mc.player.rotationPitch -= 0.0004;
                togglePitch = true;
            }
        }
    }

    @Override
    public void onWorldRender(EventRender e) {
        if (render != null) {
            double d = calculateDamage(render.getX() + .5, render.getY() + 1,  render.getZ() + .5, renderEnt);
            String[] dmgText = new String[1];
            dmgText[0] = (Math.floor (d) == d ? (int) d : String.format("%.1f", d)) + "";

            TWTessellator.drawBox(render,1, new JColor(color.getValue()), 255);
            TWTessellator.drawNametag(render.getX() + .5, render.getY() + 0.5, render.getZ() + 0.5, dmgText, new JColor(255, 255, 255), 1);
            if (renderEnt != null) {
                Vec3d p = EntityUtil.getInterpolatedRenderPos(renderEnt, mc.getRenderPartialTicks());
            }
        }
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private boolean canPlaceCrystal(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        if ((mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK
                && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN)
                || mc.world.getBlockState(boost).getBlock() != Blocks.AIR
                || mc.world.getBlockState(boost2).getBlock() != Blocks.AIR
                || !mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty()) {
            return false;
        }
        return true;
    }

    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(getSphere(getPlayerPos(), (float) range.getValue(), (int) range.getValue(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 6.0F * 2.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = (double) entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 9.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
        }
        return (float) finald;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp(k, 0.0F, 20.0F);
            damage = damage * (1.0F - f / 25.0F);

            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage = damage - (damage / 4);
            }

            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    private float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }

    public float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }

    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;

    private void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private void resetRotation() {
        if (isSpoofingAngles) {
            yaw = mc.player.rotationYaw;
            pitch = mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }

    private EnumHand getHandToBreak() {
        if (breakHand.is("Offhand")) {
            return EnumHand.OFF_HAND;
        }
        return EnumHand.MAIN_HAND;
    }

}
