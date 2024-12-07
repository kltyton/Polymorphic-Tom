package com.kltyton.polymorphic_tom.network;

import com.kltyton.polymorphic_tom.PolymorphicTom;
import com.kltyton.polymorphic_tom.network.packet.SelectRecipePacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class PacketHandler {

    public static final ResourceLocation SELECT_RECIPE_ID = new ResourceLocation(PolymorphicTom.MOD_ID, "select_recipe");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SELECT_RECIPE_ID, SelectRecipePacket::receive);
    }

    public static void registerS2CPackets() {
        // No S2C packets to register currently
    }
}