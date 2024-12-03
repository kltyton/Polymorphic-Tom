package com.kltyton.polymorphic_tom.mixin;

import com.illusivesoulworks.polymorph.common.crafting.RecipeSelection;
import com.kltyton.polymorphic_tom.client.SharedState;
import com.tom.storagemod.gui.CraftingTerminalMenu;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashSet;
import java.util.Optional;

@Mixin(CraftingTerminalBlockEntity.class)
public class CraftingTerminalBlockEntityMixin {
    @Redirect(
            method = "onCraftingMatrixChanged",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private <C extends Container, R extends Recipe<C>> Optional<R> getRecipe(
            RecipeManager manager, RecipeType<R> type, C container, Level level) {
        CraftingTerminalBlockEntity self = (CraftingTerminalBlockEntity) (Object) this;
        SharedState.currentCraftingTerminal = self;
        Player player = null;
        CraftingTerminalMenu menu = null;
        HashSet<CraftingTerminalMenu> craftingListeners = ((CraftingTerminalBlockEntityAccessor) self).getCraftingListeners();
        for (CraftingTerminalMenu m : craftingListeners) {
            if (((StorageTerminalMenuAccessor) m).getTe() == self) {
                Inventory playerInventory = ((StorageTerminalMenuAccessor) m).getPlayerInventory();
                player = playerInventory.player;
                menu = m;
                break;
            }
        }
        return RecipeSelection.getPlayerRecipe(menu, type, container, level, player);
    }
}
