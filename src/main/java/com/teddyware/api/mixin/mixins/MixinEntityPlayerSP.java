package com.teddyware.api.mixin.mixins;

import com.teddyware.api.event.events.EventMove;
import com.teddyware.client.Teddyware;
import com.teddyware.api.event.EventType;
import com.teddyware.api.event.events.EventUpdate;
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
    public void move(AbstractClientPlayer player, MoverType type, double x, double y, double z) {
        EventMove moveEvent = new EventMove(type, x, y, z);
        Teddyware.EVENT_BUS.post(moveEvent);
        super.move(type, moveEvent.x, moveEvent.y, moveEvent.z);
    }


    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void onUpdateWalkingPlayer(CallbackInfo info) {
        EventUpdate e = new EventUpdate();
        e.setType(EventType.PRE);
        Teddyware.onEvent(e);
    }
}
