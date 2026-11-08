package ru.homestead.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import ru.homestead.registry.ModMenus;

public class StorageMenu extends AbstractContainerMenu {
    private final Container container;
    private final int containerRows;

    public StorageMenu(int id, Inventory playerInventory, Container container) {
        super(ModMenus.STORAGE.get(), id);
        this.container = container;
        this.containerRows = container.getContainerSize() / 9;
        container.startOpen(playerInventory.player);

        // Storage slots
        for (int row = 0; row < this.containerRows; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(container, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        int invY = 18 + this.containerRows * 18 + 14;

        // Player inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, invY + row * 18));
            }
        }

        // Hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, invY + 58));
        }
    }

    public StorageMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new net.minecraft.world.SimpleContainer(27));
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            int containerSize = this.container.getContainerSize();
            if (index < containerSize) {
                if (!this.moveItemStackTo(stack, containerSize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, containerSize, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return result;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public int getRowCount() {
        return this.containerRows;
    }
}
