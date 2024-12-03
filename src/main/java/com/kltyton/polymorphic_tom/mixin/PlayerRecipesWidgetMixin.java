package com.kltyton.polymorphic_tom.mixin;

import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import com.kltyton.polymorphic_tom.client.SharedState;
import com.tom.storagemod.gui.CraftingTerminalMenu;
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
        if (player != null) {
            if (player.containerMenu instanceof CraftingTerminalMenu) {
                CraftingTerminalBlockEntity craftingBlockEntity = SharedState.currentCraftingTerminal;
                if (craftingBlockEntity != null) {
                    ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).setRefillingGrid(false);
                    ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).setCurrentRecipe(null);
                    ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).setReading(false);
                    Minecraft.getInstance().execute(() -> {
                        try {
                            Thread.sleep(5);
                            Minecraft.getInstance().execute(() -> {
                                // 这里调用更新方法
                                ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).invokeOnCraftingMatrixChanged();
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }
}
