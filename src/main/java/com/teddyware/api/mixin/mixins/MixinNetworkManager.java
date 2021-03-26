package com.teddyware.api.mixin.mixins;

import com.teddyware.api.event.events.EventPacket;
import com.teddyware.client.Teddyware;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> p_Packet, CallbackInfo callbackInfo) {
        EventPacket event = new EventPacket.Send(p_Packet);
        Teddyware.onEvent(event);

        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
        EventPacket event = new EventPacket.Receive(packet);
        Teddyware.onEvent(event);

        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }

}
