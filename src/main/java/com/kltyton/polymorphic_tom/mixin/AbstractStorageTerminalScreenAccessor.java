package com.kltyton.polymorphic_tom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import com.tom.storagemod.gui.AbstractStorageTerminalScreen;

@Mixin(AbstractStorageTerminalScreen.class)
public interface AbstractStorageTerminalScreenAccessor {
    @Accessor("rowCount")
    int getRowCount();

    @Accessor("tallMode")
    boolean isTallMode();
}
