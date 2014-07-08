package org.bukkit.craftbukkit.inventory;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.TradeOffer;
import org.bukkit.support.AbstractTestingBase;

public class TradeOfferTest extends AbstractTestingBase {

    @Test(expected = IllegalArgumentException.class)
    public void testNullFirstItem() {
        TradeOffer.builder()
                .withFirstItem(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBuyItem() {
        TradeOffer.builder()
                .withResultingItem(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeUses() {
        TradeOffer.builder()
                .withSetUses(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMaxUses() {
        TradeOffer.builder()
                .withMaxUses(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAirItem() {
        TradeOffer.builder()
                .withFirstItem(new ItemStack(Material.AIR, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAirSecondItem() {
        TradeOffer.builder()
                .withSecondItem(new ItemStack(Material.AIR, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAirBuyItem() {
        TradeOffer.builder()
                .withResultingItem(new ItemStack(Material.AIR, 1));
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidBuild() {
        TradeOffer.builder()
                .build();
    }

    private static final TradeOffer dummyOffer = TradeOffer.builder()
            .withFirstItem(new ItemStack(Material.EMERALD, 1))
            .withResultingItem(new ItemStack(Material.DIAMOND, 1))
            .build();

    @Test(expected = IllegalArgumentException.class)
    public void testNullSetFirstItem() {
        dummyOffer.builderOf().withFirstItem(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAirSetFirstItem() {
        dummyOffer.builderOf().withFirstItem(new ItemStack(Material.AIR, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSetBuyItem() {
        dummyOffer.builderOf().withResultingItem(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAirSetBuyItem() {
        dummyOffer.builderOf().withResultingItem(new ItemStack(Material.AIR, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAirSetSecondItem() {
        dummyOffer.builderOf().withSecondItem(new ItemStack(Material.AIR, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSetUses() {
        dummyOffer.builderOf().withSetUses(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSetMaxUses() {
        dummyOffer.builderOf().withMaxUses(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroSetMaxUses() {
        dummyOffer.builderOf().withMaxUses(0);
    }

    @Test
    public void testExpiredTrade() {
        TradeOffer offer = dummyOffer.builderOf().withMaxUses(10).withSetUses(0).build();
        assertThat(offer.hasOfferExpired(), is(false));
        offer = dummyOffer.builderOf().withSetUses(10).build();
        assertThat(offer.hasOfferExpired(), is(true));
    }

}
