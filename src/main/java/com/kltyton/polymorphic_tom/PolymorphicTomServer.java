package com.kltyton.polymorphic_tom;

import com.kltyton.polymorphic_tom.network.PacketHandler;
import net.fabricmc.api.DedicatedServerModInitializer;

public class PolymorphicTomServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        PacketHandler.registerC2SPackets();
    }
}