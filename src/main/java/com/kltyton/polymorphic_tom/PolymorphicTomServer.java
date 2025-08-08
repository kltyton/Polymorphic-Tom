package com.kltyton.polymorphic_tom;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.kltyton.polymorphic_tom.data.CraftingTerminalRecipeData;
import com.kltyton.polymorphic_tom.mixin.StorageTerminalMenuAccessor;
import com.kltyton.polymorphic_tom.network.SelectRecipePacket;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import net.fabricmc.api.ModInitializer;
import com.tom.storagemod.gui.CraftingTerminalMenu;

public class PolymorphicTomServer implements ModInitializer {
    @Override
    public void onInitialize() {
        PolymorphApi.common().registerBlockEntity2RecipeData(
                blockEntity -> blockEntity instanceof CraftingTerminalBlockEntity
                        ? new CraftingTerminalRecipeData((CraftingTerminalBlockEntity) blockEntity)
                        : null
        );

        PolymorphApi.common().registerContainer2BlockEntity(
                menu -> menu instanceof CraftingTerminalMenu
                        ? ((StorageTerminalMenuAccessor) menu).getTe()
                        : null
        );
        SelectRecipePacket.register();
    }
}
