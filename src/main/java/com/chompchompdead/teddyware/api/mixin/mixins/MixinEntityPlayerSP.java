package com.chompchompdead.teddyware.api.mixin.mixins;

import com.chompchompdead.teddyware.api.event.events.EventPlayerUpdate;
import com.chompchompdead.teddyware.api.event.events.EventMove;
import com.chompchompdead.teddyware.api.event.events.EventPlayerMotionUpdate;
import com.chompchompdead.teddyware.client.Teddyware;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {

    public MixinEntityPlayerSP() {
        super(null, null);
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    private void move(AbstractClientPlayer player, MoverType type, double x, double y, double z) {
        EventMove moveEvent = new EventMove(type, x, y, z);
        Teddyware.EVENT_BUS.post(moveEvent);
        super.move(type, moveEvent.x, moveEvent.y, moveEvent.z);
    }


    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    private void onUpdateWalkingPlayer(CallbackInfo info) {
        EventPlayerMotionUpdate event = new EventPlayerMotionUpdate();
        Teddyware.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    private void onUpdate(CallbackInfo info) {
        EventPlayerUpdate event = new EventPlayerUpdate();
        Teddyware.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
