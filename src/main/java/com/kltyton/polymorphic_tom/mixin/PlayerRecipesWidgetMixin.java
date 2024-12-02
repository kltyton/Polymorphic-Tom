package com.kltyton.polymorphic_tom.mixin;

import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import com.kltyton.polymorphic_tom.client.SharedState;
import com.tom.storagemod.gui.CraftingTerminalMenu;
import com.tom.storagemod.platform.PlatformRecipe;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRecipesWidget.class)
public class PlayerRecipesWidgetMixin {

    @Inject(method = "selectRecipe", at = @At("TAIL"))
    private void onSelectRecipe(ResourceLocation resourceLocation, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;

        if (player != null && player.containerMenu instanceof CraftingTerminalMenu menu) {
            CraftingTerminalBlockEntity craftingBlockEntity = SharedState.currentCraftingTerminal;

            if (craftingBlockEntity != null) {
                ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).setRefillingGrid(false);
                ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).setCurrentRecipe(null);
                ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).invokeOnCraftingMatrixChanged();

                // 然后刷新界面
                menu.onCraftMatrixChanged();
            }
        }
    }
}
