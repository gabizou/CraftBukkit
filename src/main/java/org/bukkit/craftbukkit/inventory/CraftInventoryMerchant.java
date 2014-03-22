package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.Entity;
import net.minecraft.server.IMerchant;
import net.minecraft.server.InventoryMerchant;

import org.bukkit.craftbukkit.entity.CraftEntity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Merchant;
import org.bukkit.inventory.MerchantInventory;

public class CraftInventoryMerchant extends CraftInventory implements MerchantInventory {

    private final Merchant merchant;

    public CraftInventoryMerchant(InventoryMerchant inventory) {
        super(inventory);
        this.merchant = (Merchant) ((Entity) inventory.getMerchant()).getBukkitEntity();
    }

    @Override
    public Merchant getMerchant() {
        return merchant;
    }
}
