package com.teddyhack.api.mixin.mixins;

import com.teddyhack.client.Client;
import com.teddyhack.api.event.EventType;
import com.teddyhack.api.event.listeners.EventUpdate;
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
        Client.onEvent(e);
    }
}
