package com.teddyware.api.mixin.mixins;

import com.teddyware.api.event.events.EventTravel;
import com.teddyware.client.Teddyware;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, priority = Integer.MAX_VALUE)
public class MixinEntityPlayer extends MixinEntity {

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void travel(float strafe, float vertical, float forward, CallbackInfo callbackInfo) {
        EventTravel event = new EventTravel(strafe, vertical, forward);
        Teddyware.EVENT_BUS.post(event);
        if(event.isCancelled()) {
            move(MoverType.SELF, motionX, motionY, motionZ);
            callbackInfo.cancel();
        }
    }

}
