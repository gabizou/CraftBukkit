package org.bukkit.craftbukkit.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.server.EntityVillager;
import net.minecraft.server.MerchantRecipe;
import net.minecraft.server.MerchantRecipeList;
import net.minecraft.server.NBTTagCompound;

import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.inventory.CraftMerchantTrade;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantTrade;

public class CraftVillager extends CraftAgeable implements Villager {
    public CraftVillager(CraftServer server, EntityVillager entity) {
        super(server, entity);
    }

    @Override
    public EntityVillager getHandle() {
        return (EntityVillager) entity;
    }

    @Override
    public String toString() {
        return "CraftVillager";
    }

    public EntityType getType() {
        return EntityType.VILLAGER;
    }

    public Profession getProfession() {
        return Profession.getProfession(getHandle().getProfession());
    }

    public void setProfession(Profession profession) {
        Validate.notNull(profession);
        getHandle().setProfession(profession.getId());
    }

    public void readExtraData(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKey("bukkit")) {
            NBTTagCompound customData = nbttagcompound.getCompound("bukkit");
            EntityVillager handle = getHandle();
            handle.hasTradedBefore = customData.getBoolean("hasTraded");
        }
    }

    public void setExtraData(NBTTagCompound nbttagcompound) {
        if (!nbttagcompound.hasKey("bukkit")) {
            nbttagcompound.set("bukkit", new NBTTagCompound());
        }
        NBTTagCompound data = nbttagcompound.getCompound("bukkit");
        EntityVillager handle = getHandle();
        data.setBoolean("hasTraded", handle.hasTradedBefore);
    }

    @Override
    public boolean hasTradedBefore() {
        return getHandle().hasTradedBefore;
    }

    @Override
    public void setTradingHuman(HumanEntity human) {
        getHandle().a_(((CraftHumanEntity) human).getHandle());
    }

    @Override
    public HumanEntity getTradingHuman() {
        return getHandle().b().getBukkitEntity();
    }

    @Override
    public boolean isTrading() {
        return getHandle().ca();
    }

    @Override
    public List<MerchantTrade> getOffers() {
        List<MerchantTrade> list = new ArrayList<MerchantTrade>(getHandle().getOffers(null).size());
        for (int i = 0; i < getHandle().bu.size() ; i++) {
            MerchantRecipe recipe = (MerchantRecipe) getHandle().getOffers(null).get(i);
            list.add(recipe.getBukkitTrade());
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public void addOffer(MerchantTrade merchantTrade) {
        getHandle().a(CraftMerchantTrade.asNMSCopy(merchantTrade));

    }

    @Override
    public boolean removeOffer(MerchantTrade merchantTrade) {
        if (getHandle() != null && getHandle().bu != null && !getHandle().bu.isEmpty()) {
            MerchantRecipeList list = getHandle().bu;
            for (int i = 0; i < list.size(); i++) {
                MerchantRecipe recipe = (MerchantRecipe) list.get(i);
                MerchantTrade trade = recipe.getBukkitTrade();
                if (merchantTrade.equals(trade)) {
                    list.remove(i);
                    return true;
                }
            }
        }
        return false;
    }
}
