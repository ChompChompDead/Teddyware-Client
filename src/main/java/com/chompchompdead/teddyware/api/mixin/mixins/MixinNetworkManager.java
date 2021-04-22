package com.chompchompdead.teddyware.api.mixin.mixins;

import com.chompchompdead.teddyware.api.event.events.EventPacket;
import com.chompchompdead.teddyware.client.Teddyware;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> p_Packet, CallbackInfo callbackInfo) {
        EventPacket event = new EventPacket.Send(p_Packet);
        Teddyware.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void recieve(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInf) {
        EventPacket event1 = new EventPacket.Receive(packet);
        Teddyware.EVENT_BUS.post(event1);

        if (event1.isCancelled()) {
           callbackInf.cancel();
        }
    }
}
