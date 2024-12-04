package com.kltyton.polymorphic_tom.widget;

import com.illusivesoulworks.polymorph.api.client.base.ITickingRecipesWidget;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import com.kltyton.polymorphic_tom.client.SharedState;
import com.kltyton.polymorphic_tom.mixin.CraftingTerminalBlockEntityAccessor;
import com.tom.storagemod.gui.CraftingTerminalMenu;
import com.tom.storagemod.gui.CraftingTerminalScreen;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class CraftingTerminalWidget extends PlayerRecipesWidget implements ITickingRecipesWidget {
    public CraftingTerminalWidget(CraftingTerminalScreen screen, Slot outputSlot) {
        super(screen, outputSlot);
    }

    @Override
    public void tick() {
    }

    @Override
    public int getYPos() {
        return super.getYPos() + 68;
    }

    @Override
    public void selectRecipe(ResourceLocation id) {
        super.selectRecipe(id);
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
                            Minecraft.getInstance().execute(() -> ((CraftingTerminalBlockEntityAccessor) craftingBlockEntity).invokeOnCraftingMatrixChanged());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }
    @Override
    public void initChildWidgets() {
        super.initChildWidgets();
        int openButtonYOffset = -68;
        this.openButton.setOffsets(this.getXPos(), this.getYPos() + openButtonYOffset);
    }
}
