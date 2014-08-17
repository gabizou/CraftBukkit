package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityVillager;
import net.minecraft.server.InventoryMerchant;

import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventoryMerchant;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantInventory;

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

    @Override
    public MerchantInventory getInventory() {
        return new CraftInventoryMerchant(new InventoryMerchant(getHandle().b(), getHandle()));
    }
}
