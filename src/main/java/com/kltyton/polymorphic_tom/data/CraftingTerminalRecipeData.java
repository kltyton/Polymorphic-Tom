package com.kltyton.polymorphic_tom.data;

import com.illusivesoulworks.polymorph.api.common.base.IRecipePair;
import com.illusivesoulworks.polymorph.api.common.capability.IBlockEntityRecipeData;
import com.kltyton.polymorphic_tom.mixin.CraftingTerminalBlockEntityAccessor;
import com.mojang.datafixers.util.Pair;
import com.tom.storagemod.platform.PlatformRecipe;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.*;

public class CraftingTerminalRecipeData implements IBlockEntityRecipeData {
    private final CraftingTerminalBlockEntity blockEntity;

    public CraftingTerminalRecipeData(CraftingTerminalBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public void selectRecipe(Recipe<?> recipe) {
        ((CraftingTerminalBlockEntityAccessor)blockEntity).setCurrentRecipe(PlatformRecipe.of(recipe));
        ((CraftingTerminalBlockEntityAccessor)blockEntity).invokeOnCraftingMatrixChanged();
    }

    @Override
    public Optional<? extends Recipe<?>> getSelectedRecipe() {
        return ((CraftingTerminalBlockEntityAccessor)blockEntity).getCurrentRecipe() == null ? Optional.empty()
                : Optional.of(((CraftingTerminalBlockEntityAccessor)blockEntity).getCurrentRecipe().recipe());
    }

    @Override
    public <T extends Recipe<C>, C extends Container> Optional<T> getRecipe(RecipeType<T> type, C container, Level level, List<T> list) {
        return level.getRecipeManager().getRecipeFor(type, container, level);
    }

    // === 以下方法可留空或返回默认值 ===

    @Override public void tick() {}
    @Override public void addListener(ServerPlayer p) {}
    @Override public void removeListener(ServerPlayer p) {}
    @Override public void setSelectedRecipe(Recipe<?> recipe) {}

    @Override
    public SortedSet<IRecipePair> getRecipesList() {return null;}

    @Override
    public void setRecipesList(SortedSet<IRecipePair> sortedSet) {}

    @Override public boolean isEmpty(Container c) { return false; }
    @Override public Set<ServerPlayer> getListeners() { return Set.of(); }
    @Override public void sendRecipesListToListeners(boolean b) {}

    @Override
    public Pair<SortedSet<IRecipePair>, ResourceLocation> getPacketData() {return null;}

    @Override public CraftingTerminalBlockEntity getOwner() { return blockEntity; }
    @Override public boolean isFailing() { return false; }
    @Override public void setFailing(boolean b) {}

    @Override
    public CompoundTag writeNBT() {return null;}

    @Override
    public void readNBT(CompoundTag compoundTag) {}
}