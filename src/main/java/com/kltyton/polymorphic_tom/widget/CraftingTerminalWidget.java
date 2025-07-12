package com.kltyton.polymorphic_tom.widget;

import com.illusivesoulworks.polymorph.api.client.base.ITickingRecipesWidget;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import com.kltyton.polymorphic_tom.mixin.AbstractStorageTerminalScreenAccessor;
import com.kltyton.polymorphic_tom.mixin.CraftingTerminalBlockEntityAccessor;
import com.kltyton.polymorphic_tom.network.packet.SelectRecipePacket;
import com.tom.storagemod.gui.CraftingTerminalMenu;
import com.tom.storagemod.gui.CraftingTerminalScreen;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class CraftingTerminalWidget extends PlayerRecipesWidget implements ITickingRecipesWidget {
    private final CraftingTerminalScreen screen;
    private boolean lastTallMode;
    private int lastRowCount;

    public CraftingTerminalWidget(CraftingTerminalScreen screen, Slot outputSlot) {
        super(screen, outputSlot);
        this.screen = screen;

        // 初始化最后的已知状态
        AbstractStorageTerminalScreenAccessor accessor = (AbstractStorageTerminalScreenAccessor) screen;
        this.lastTallMode = accessor.isTallMode();
        this.lastRowCount = accessor.getRowCount();
    }

    @Override
    public void tick() {
        AbstractStorageTerminalScreenAccessor accessor = (AbstractStorageTerminalScreenAccessor) screen;
        boolean currentTallMode = accessor.isTallMode();
        int currentRowCount = accessor.getRowCount();

        // 检查更改并相应地更新
        if (currentTallMode != lastTallMode || currentRowCount != lastRowCount) {
            // 更新 Y 位置或执行其他必要的操作
            updateWidgetPosition();

            // 更新最后的已知状态
            lastTallMode = currentTallMode;
            lastRowCount = currentRowCount;
        }
    }

    private void updateWidgetPosition() {
        this.openButton.setOffsets(this.getXPos(), this.getYPos());
        this.selectionWidget.setOffsets(this.getXPos() + this.xOffset, this.getYPos() + 41);
    }

    @Override
    public int getYPos() {
        return super.getYPos();
    }

    @Override
    public void selectRecipe(ResourceLocation id) {
        super.selectRecipe(id);
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.containerMenu instanceof CraftingTerminalMenu) {
                SelectRecipePacket.send();
            }
        }
    }
    @Override
    public void initChildWidgets() {
        super.initChildWidgets();
        updateWidgetPosition();
    }
}
