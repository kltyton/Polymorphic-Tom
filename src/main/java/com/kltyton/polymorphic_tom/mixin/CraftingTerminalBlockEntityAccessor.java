package com.kltyton.polymorphic_tom.mixin;

import com.tom.storagemod.gui.CraftingTerminalMenu;
import com.tom.storagemod.platform.PlatformRecipe;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.HashSet;

@Mixin(CraftingTerminalBlockEntity.class)
public interface CraftingTerminalBlockEntityAccessor {
    @Accessor("craftingListeners")
    HashSet<CraftingTerminalMenu> getCraftingListeners();
    @Invoker("onCraftingMatrixChanged")
    void invokeOnCraftingMatrixChanged();
    @Accessor("currentRecipe")
    void setCurrentRecipe(PlatformRecipe recipe);
    @Accessor("refillingGrid")
    void setRefillingGrid(boolean value);
    @Accessor("reading")
    void setReading(boolean value);
}