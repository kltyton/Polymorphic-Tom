package com.kltyton.polymorphic_tom.client;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.kltyton.polymorphic_tom.widget.CraftingTerminalWidget;
import com.tom.storagemod.gui.CraftingTerminalScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.inventory.ResultSlot;

public class Polymorphic_tomClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PolymorphApi.client().registerWidget(screen -> {
            if (screen instanceof CraftingTerminalScreen craftingTerminal) {
                for (var slot : craftingTerminal.getMenu().slots) {
                    if (slot instanceof ResultSlot) {
                        return new CraftingTerminalWidget(craftingTerminal, slot);
                    }
                }
            }
            return null;
        });
    }
}
