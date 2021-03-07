package com.teddyhack.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public interface Util {
    Minecraft mc = Minecraft.getMinecraft();
    static void sendPacket(Packet p) { mc.player.connection.sendPacket(p); }
}
