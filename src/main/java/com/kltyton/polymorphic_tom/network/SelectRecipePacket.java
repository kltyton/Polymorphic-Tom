package com.kltyton.polymorphic_tom.network;

import com.kltyton.polymorphic_tom.mixin.CraftingTerminalBlockEntityAccessor;
import com.kltyton.polymorphic_tom.mixin.StorageTerminalMenuAccessor;
import com.tom.storagemod.gui.CraftingTerminalMenu;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import com.tom.storagemod.platform.PlatformRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class SelectRecipePacket {
    public static final ResourceLocation ID = new ResourceLocation("polymorphic_tom", "select_recipe");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            ResourceLocation recipeId = buf.readResourceLocation();
            server.execute(() -> {
                if (player.containerMenu instanceof CraftingTerminalMenu menu) {
                    CraftingTerminalBlockEntity be = (CraftingTerminalBlockEntity) ((StorageTerminalMenuAccessor) menu).getTe();
                    Recipe<?> recipe = player.level().getRecipeManager().byKey(recipeId).orElse(null);
                    if (recipe != null) {
                        ((CraftingTerminalBlockEntityAccessor) be).setCurrentRecipe(PlatformRecipe.of(recipe));
                        ((CraftingTerminalBlockEntityAccessor) be).setRefillingGrid(false);
                        ((CraftingTerminalBlockEntityAccessor) be).setReading(false);
                        ((CraftingTerminalBlockEntityAccessor) be).invokeOnCraftingMatrixChanged();
                    }
                }
            });
        });
    }
}