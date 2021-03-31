package com.teddyware.client;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class Discord {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static String details;
    public static String state;
    public static int players;
    public static int maxPlayers;
    public static int players2;
    public static int maxPlayers2;
    public static ServerData server;

    public static void start() {

        String discordAppID = "809223921575395370";

        DiscordRichPresence presence = new DiscordRichPresence();
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRPC.discordInitialize(discordAppID, handlers, true);
        DiscordRPC.discordUpdatePresence(presence);

        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.details = "teddyware coooool.";
        presence.state = "Teddyware";
        presence.largeImageKey = "teddyhacklogo";

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    details = "";
                    state = "";
                    players = 0;
                    maxPlayers = 0;

                    if (mc.isIntegratedServerRunning()) {
                        details = "In a singleplayer world";
                    } else if (mc.getCurrentServerData() != null) {
                        server = mc.getCurrentServerData();

                        if (!server.serverIP.equals("")) {
                            details = "On Server:";
                            state = server.serverIP;
                        }
                    } else {
                        details = "teddyware coooool.";
                    }

                    if (!details.equals(presence.details) || !state.equals(presence.state)) {
                        presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    presence.details = details;
                    presence.state = state;
                    DiscordRPC.discordUpdatePresence(presence);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();

    }

    /*
    private static final String discordID = "809223921575395370";
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;

    public static void startRPC() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);
        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.details = "Version " + Teddyware.VERSION;
        discordRichPresence.largeImageKey = "teddyhacklogo";
        discordRichPresence.largeImageText = ":)";
        discordRichPresence.state = null;
        discordRPC.Discord_UpdatePresence(discordRichPresence);
    }

    public static void stopRPC() {
        discordRPC.Discord_Shutdown();
        discordRPC.Discord_ClearPresence();
    }
     */

}