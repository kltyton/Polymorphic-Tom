package com.kltyton.polymorphic_tom.network.packet;

import com.kltyton.polymorphic_tom.mixin.CraftingTerminalBlockEntityAccessor;
import com.kltyton.polymorphic_tom.mixin.StorageTerminalMenuAccessor;
import com.kltyton.polymorphic_tom.network.PacketHandler;
import com.tom.storagemod.gui.CraftingTerminalMenu;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class SelectRecipePacket {

    public static void send() {
        ClientPlayNetworking.send(PacketHandler.SELECT_RECIPE_ID, PacketByteBufs.create());
    }

    public static void receive(net.minecraft.server.MinecraftServer server, ServerPlayer player,
            net.minecraft.server.network.ServerGamePacketListenerImpl handler, FriendlyByteBuf buf,
            net.fabricmc.fabric.api.networking.v1.PacketSender responseSender) {
        server.execute(() -> {
            if (player.containerMenu instanceof CraftingTerminalMenu menu) {
                if (((StorageTerminalMenuAccessor) menu).getTe() instanceof CraftingTerminalBlockEntity craftingBlockEntity) {
                    if (craftingBlockEntity != null) {
                        ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).setRefillingGrid(false);
                        ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).setCurrentRecipe(null);
                        ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).setReading(false);
                        ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).invokeOnCraftingMatrixChanged();
                    }
                }
            }
        });
    }
}