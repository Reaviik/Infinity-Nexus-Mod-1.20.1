package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModPaxelItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class PaxelItem extends ModPaxelItem {
    private final boolean drop;
    private final Component translation;
    private final MobEffectInstance[] effects =new MobEffectInstance[]{new MobEffectInstance(MobEffects.LUCK, 1, 1, false, false)};

    public PaxelItem(Tier tier, float damage, float attackSpeed, Function<Item.Properties, Item.Properties> properties, Component translation, boolean drop) {
        super(tier,damage,attackSpeed, properties);
        this.translation = translation;
        this.drop = drop;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        if(drop){
            Level level = player.level();
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ModItemsAdditions.INFINITY_NUGGET.get().getDefaultInstance());
            Random random = new Random();
            int numeroAleatorio = random.nextInt(10000) + 1;
            if(numeroAleatorio <= 1){
                level.addFreshEntity(itemEntity);
            }
        }
        return super.onBlockStartBreak(itemstack, pos, player);

    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        for (MobEffectInstance effect : effects) {
            pTarget.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration()), pAttacker);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(translation);
        } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }

        super.appendHoverText(stack, level, components, flag);
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

}