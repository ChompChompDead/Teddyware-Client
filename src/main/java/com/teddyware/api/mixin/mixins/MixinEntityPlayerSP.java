package com.teddyware.api.mixin.mixins;

import com.teddyware.client.Teddyware;
import com.teddyware.api.event.EventType;
import com.teddyware.api.event.events.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void onUpdateWalkingPlayer(CallbackInfo info) {
        EventUpdate e = new EventUpdate();
        e.setType(EventType.PRE);
        Teddyware.onEvent(e);
    }
}
