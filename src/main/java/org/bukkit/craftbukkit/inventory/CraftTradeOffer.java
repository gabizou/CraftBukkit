package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.MerchantRecipe;
import net.minecraft.server.NBTTagCompound;

import org.bukkit.inventory.TradeOffer;

public class CraftTradeOffer {

    public static MerchantRecipe asNMSCopy(TradeOffer original) {
        if (original == null) {
            return null;
        }

        MerchantRecipe recipe = new MerchantRecipe(CraftItemStack.asNMSCopy(original.getFirstItem()), CraftItemStack.asNMSCopy(original.getSecondItem()), CraftItemStack.asNMSCopy(original.getResultingOffer()));
        recipe.maxUses = original.getMaxUses();
        return recipe;
    }

    public static TradeOffer asBukkitCopy(MerchantRecipe original) {
        if (original == null) {
            return null;
        }
        return TradeOffer.builder()
                .withFirstItem(CraftItemStack.asBukkitCopy(original.getBuyItem1()))
                .withSecondItem(original.getBuyItem2() == null ? null : CraftItemStack.asBukkitCopy(original.getBuyItem2()))
                .withResultingItem(CraftItemStack.asBukkitCopy(original.getBuyItem3()))
                .withMaxUses(original.maxUses)
                .withSetUses(original.uses)
                .build();
    }

}
