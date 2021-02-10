package com.teddyhack.mixin.mixins;

import com.teddyhack.Client;
import com.teddyhack.event.EventType;
import com.teddyhack.event.listeners.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void onUpdateWalkingPlayer(CallbackInfo ci) {
        EventUpdate e = new EventUpdate();
        e.setType(EventType.PRE);
        Client.onEvent(e);
    }
}
