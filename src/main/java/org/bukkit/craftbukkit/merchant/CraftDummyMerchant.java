package org.bukkit.craftbukkit.merchant;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.IMerchant;
import net.minecraft.server.InventoryMerchant;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MerchantRecipe;
import net.minecraft.server.MerchantRecipeList;

import org.bukkit.craftbukkit.inventory.CraftInventoryMerchant;
import org.bukkit.Sound;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.merchant.Merchant;

public class CraftDummyMerchant implements Merchant, IMerchant {

    public MerchantRecipeList recipeList;
    public EntityHuman tradingHuman;

    public CraftDummyMerchant(MerchantRecipeList recipeList, EntityHuman tradingHuman) {
        this.recipeList = recipeList;
        this.tradingHuman = tradingHuman;
    }

    @Override
    public void a_(EntityHuman entityhuman) {
        if (tradingHuman == null) {
            this.tradingHuman = entityhuman;
        }
    }

    @Override
    public EntityHuman b() {
        return tradingHuman;
    }

    @Override
    public MerchantRecipeList getOffers(EntityHuman entityhuman) {
        if (recipeList == null) {
            recipeList = new MerchantRecipeList();
        }
        return recipeList;
    }

    @Override
    public void a(MerchantRecipe merchantrecipe) {
        merchantrecipe.f();
        for (int i = 0; i < recipeList.size(); i++) {
            MerchantRecipe queried = (MerchantRecipe) recipeList.get(i);
            if (merchantrecipe.a(queried)) {
                recipeList.remove(i);
                recipeList.add(i, merchantrecipe);
            }
        }
    }

    @Override
    public void a_(ItemStack itemstack) {
    }

    @Override
    public Merchant getBukkitMerchant() {
        return this;
    }

    @Override
    public MerchantInventory getInventory() {
        return new CraftInventoryMerchant(new InventoryMerchant(tradingHuman, this));
    }
}
