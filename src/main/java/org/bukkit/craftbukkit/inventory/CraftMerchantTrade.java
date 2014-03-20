package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.MerchantRecipe;
import net.minecraft.server.NBTTagCompound;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantTrade;

/**
 * Author: gabizou
 */
public class CraftMerchantTrade extends MerchantTrade {

    public static MerchantRecipe asNMSCopy(MerchantTrade original) {
        if (original instanceof CraftMerchantTrade) {
            CraftMerchantTrade trade = (CraftMerchantTrade) original;
            return trade.handle == null ? null : trade.handle.cloneTrade();
        }
        if (original == null || original.getResult() == null) {
            return null;
        }

        MerchantRecipe recipe = new MerchantRecipe(CraftItemStack.asNMSCopy(original.getFirstBuyingItem()), CraftItemStack.asNMSCopy(original.getSeconBuyingItem()), CraftItemStack.asNMSCopy(original.getResult()));
        recipe.type = TradeType.PLUGIN_ADDED;
        recipe.maxUses = original.getMaxUses();
        return recipe;
    }

    public static MerchantRecipe copyNMSTrade(MerchantRecipe original) {
        return original.cloneTrade();
    }

    public static MerchantTrade asBukkitCopy(MerchantRecipe original) {
        if (original == null) {
            return null;
        }
        return  new MerchantTrade(CraftItemStack.asBukkitCopy(original.getBuyItem1()), CraftItemStack.asBukkitCopy(original.getBuyItem2()), CraftItemStack.asBukkitCopy(original.getBuyItem3()));
    }

    public static CraftMerchantTrade asCraftMirror(MerchantRecipe original) {
        return new CraftMerchantTrade(original);
    }

    public static CraftMerchantTrade asCraftCopy(MerchantTrade original) {
        if (original instanceof CraftMerchantTrade) {
            CraftMerchantTrade trade = (CraftMerchantTrade) original;
            return new CraftMerchantTrade(trade.handle == null ? null : trade.handle.cloneTrade());
        }
        return new CraftMerchantTrade(original);
    }

    public static CraftMerchantTrade asNewCraftTrade(MerchantTrade trade) {
        return new CraftMerchantTrade(trade);
    }

    private MerchantRecipe handle;

    private CraftMerchantTrade(MerchantTrade trade) {
        setFirstBuyingItem(trade.getFirstBuyingItem());
        setSecondBuyingItem(trade.getSeconBuyingItem());
        setResult(trade.getResult());
        setMaxUses(trade.getMaxUses());
    }

    private CraftMerchantTrade(final MerchantRecipe recipe) {
        this.handle = recipe;
    }

    private CraftMerchantTrade(final MerchantRecipe recipe, final NBTTagCompound compound) {
        this.handle = recipe;
        recipe.a(compound);
    }

    @Override
    public ItemStack getFirstBuyingItem() {
        return handle != null ? CraftItemStack.asBukkitCopy(handle.getBuyItem1()) : null;
    }

    @Override
    public ItemStack getSeconBuyingItem() {
        return handle != null ? CraftItemStack.asBukkitCopy(handle.getBuyItem2()) : null;
    }

    @Override
    public ItemStack getResult() {
        return handle != null ? CraftItemStack.asBukkitCopy(handle.getBuyItem3()) : null;
    }

    @Override
    public boolean hasSecondBuyingItem() {
        return handle != null && handle.hasSecondItem();
    }

    @Override
    public int useTradeRecipe() {
        return handle != null ? ++handle.uses : 0;
    }

    @Override
    public int getUses() {
        return handle != null ? handle.uses : 0;
    }

    @Override
    public int increaseMaxUses(int i) {
        return handle != null ? handle.maxUses += i : 0;
    }

    @Override
    public int getMaxUses() {
        return handle != null ? handle.maxUses : 0;
    }

    @Override
    public int setMaxUses(int maxUses) {
        if (handle != null && maxUses > 0) {
            handle.maxUses = maxUses;
            return handle.maxUses;
        }
        return 0;
    }

    @Override
    public boolean isTradeExpired() {
        return handle != null && handle.g();
    }

    @Override
    public TradeType getType() {
        return handle != null ? handle.type : TradeType.PLUGIN_ADDED;
    }

    @Override
    public CraftMerchantTrade clone() {
        CraftMerchantTrade trade = (CraftMerchantTrade) super.clone();
        if (this.handle != null) {
            trade.handle = this.handle.cloneTrade();
        }
        return trade;
    }

    public MerchantRecipe getHandle() {
        return this.handle;
    }

    public void readExtraData(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKey("bukkit")) {
            NBTTagCompound data = nbttagcompound.getCompound("bukkit");
            MerchantRecipe handle = getHandle();
            handle.type = TradeType.valueOf(data.getString("type"));
        }
    }

    public void setExtraData(NBTTagCompound nbttagcompound) {
        if (!nbttagcompound.hasKey("bukkit")) {
            nbttagcompound.set("bukkit", new NBTTagCompound());
        }
        NBTTagCompound data = nbttagcompound.getCompound("bukkit");
        MerchantRecipe handle = getHandle();
        data.setString("type", handle.type.toString());
    }
}
