package com.kltyton.polymorphic_tom.mixin;

import com.tom.storagemod.tile.StorageTerminalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import com.tom.storagemod.gui.StorageTerminalMenu;
import net.minecraft.world.entity.player.Inventory;

@Mixin(StorageTerminalMenu.class)
public interface StorageTerminalMenuAccessor {
    @Accessor("pinv")
    Inventory getPlayerInventory();
    @Accessor("te")
    StorageTerminalBlockEntity getTe();
}
