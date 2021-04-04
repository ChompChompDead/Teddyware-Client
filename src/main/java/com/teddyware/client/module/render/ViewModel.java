package com.teddyware.client.module.render;

import com.teddyware.api.event.events.EventTransformSide;
import com.teddyware.client.Teddyware;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.BooleanSetting;
import com.teddyware.client.setting.settings.NumberSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "ViewModel", description = "Change your item FOV to a custom value.", key = Keyboard.KEY_NONE, category = Category.Render)
public class ViewModel extends Module {

    public BooleanSetting items = new BooleanSetting("Items", this, false);
    public NumberSetting itemFOV = new NumberSetting("ItemFOV", this, 110F, 110F,175F, 1F);
    public NumberSetting leftX = new NumberSetting("LeftX", this,  0, -2, 2, 0.1);
    public NumberSetting leftY = new NumberSetting("LeftY", this,  0, -2, 2, 0.1);
    public NumberSetting leftZ = new NumberSetting("LeftZ", this,  0, -2, 2, 0.1);
    public NumberSetting rightX = new NumberSetting("RightX", this,  0, -2, 2, 0.1);
    public NumberSetting rightY = new NumberSetting("RightY", this,  0, -2, 2, 0.1);
    public NumberSetting rightZ = new NumberSetting("RightZ", this,  0, -2, 2, 0.1);


    public ViewModel() {
        this.addSetting(items, itemFOV, leftX, leftY, leftZ, rightX, rightY, rightZ);
    }

    public void onEnable() {
        Teddyware.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        Teddyware.EVENT_BUS.unsubscribe(this);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @EventHandler
    private final Listener<EventTransformSide> eventTransformSideListener = new Listener<>(event -> {
         if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(rightX.getValue(), rightY.getValue(), rightZ.getValue());
         } else if (event.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(leftX.getValue(), leftY.getValue(), leftZ.getValue());
        }
    });

    @SubscribeEvent
    public void onFOV(EntityViewRenderEvent.FOVModifier event) {
        if (items.isEnabled()) {
            event.setFOV((float) itemFOV.getValue());
        }
    }
}
