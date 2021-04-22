package com.chompchompdead.teddyware.api.mixin.mixins;

import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.api.event.events.EventTransformSide;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
    public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo callbackInfo) {
        EventTransformSide event = new EventTransformSide(hand);
        Teddyware.EVENT_BUS.post(event);
    }

    @Inject(method = "transformFirstPerson", at = @At("HEAD"))
    public void transformFirstPerson(EnumHandSide hand, float p_187453_2_, CallbackInfo callbackInfo) {
        EventTransformSide event = new EventTransformSide(hand);
        Teddyware.EVENT_BUS.post(event);
    }

}
