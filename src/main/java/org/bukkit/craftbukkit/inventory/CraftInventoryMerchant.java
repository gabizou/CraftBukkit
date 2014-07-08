package org.bukkit.craftbukkit.inventory;

import java.util.List;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.io.netty.buffer.Unpooled;
import org.apache.commons.lang.Validate;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IMerchant;
import net.minecraft.server.InventoryMerchant;
import net.minecraft.server.MerchantRecipe;
import net.minecraft.server.MerchantRecipeList;
import net.minecraft.server.PacketDataSerializer;
import net.minecraft.server.PacketPlayOutCustomPayload;

import org.bukkit.Bukkit;
import org.bukkit.merchant.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.TradeOffer;

public class CraftInventoryMerchant extends CraftInventory implements MerchantInventory {

    private IMerchant merchant;
    private MerchantRecipeList list;

    public CraftInventoryMerchant(InventoryMerchant merchant) {
        super(merchant);
        this.merchant = merchant.merchant;
        this.list = this.merchant.getOffers(merchant.player);
    }

    @Override
    public Merchant getMerchant() {
        return merchant != null ? merchant.getBukkitMerchant() : null;
    }

    @Override
    public List<TradeOffer> getOffers() {
        ImmutableList.Builder<TradeOffer> builder = ImmutableList.builder();
        for (Object obj : list) {
            builder.add(CraftTradeOffer.asBukkitCopy((MerchantRecipe) obj));
        }
        return builder.build();
    }

    @Override
    public void addOffer(TradeOffer offer) {
        Validate.notNull(offer, "Cannot add a null TradeOffer!");
        list.add(CraftTradeOffer.asNMSCopy(offer));
        if (merchant.b() instanceof EntityPlayer) {
            sendUpdatedList((EntityPlayer) merchant.b(), merchant.getOffers(merchant.b()));
        }
    }

    @Override
    public void removeOffer(TradeOffer offer) {
        Validate.notNull(offer, "Cannot remove a null TradeOffer!");
        list.remove(CraftTradeOffer.asNMSCopy(offer));
        if (merchant.b() instanceof EntityPlayer) {
            sendUpdatedList((EntityPlayer) merchant.b(), merchant.getOffers(merchant.b()));
        }
    }

    private void sendUpdatedList(EntityPlayer player, MerchantRecipeList merchantRecipeList) {
        PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.buffer());
        MerchantRecipeList newList = new MerchantRecipeList();
        for (Object tempOffer : merchantRecipeList) {
            newList.add((MerchantRecipe) tempOffer);
        }
        try {
            packetdataserializer.writeInt(player.activeContainer.windowId);
            newList.a(packetdataserializer);
            player.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|TrList", packetdataserializer));
        } catch (Exception ioexception) {
            Bukkit.getLogger().warning("Couldn\'t send trade list");
            ioexception.printStackTrace();
        } finally {
            packetdataserializer.release();
        }
    }
}
