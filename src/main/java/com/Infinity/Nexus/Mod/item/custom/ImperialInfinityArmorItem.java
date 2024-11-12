package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.client.ImperialInfinityArmorRenderer;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Represents the Imperial Infinity Armor item, a powerful armor set that provides various effects and abilities.
 * This armor is animated using GeckoLib and provides flight capabilities when wearing a full set.
 */
public class ImperialInfinityArmorItem extends ArmorItem implements GeoItem {
    private static final String IMPERIAL_PREFIX = "§6Imperial ";
    private static final int EFFECT_DURATION = 20 * 60;
    private static final int EFFECT_AMPLIFIER = 1;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    /**
     * Constructs a new Imperial Infinity Armor item.
     *
     * @param material The material of the armor
     * @param type The type of armor piece
     * @param settings The item properties
     */
    public ImperialInfinityArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    /**
     * Handles the armor's tick update logic, applying effects and abilities when worn.
     */
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.isClientSide() || !(pEntity instanceof Player player)) {
            return;
        }

        boolean hasFullSet = hasFullSuitOfArmorOn(player);
        updatePlayerAbilities(player, hasFullSet);
        if (hasFullSet) {
            applyArmorEffects(player);
        }

        player.onUpdateAbilities();
    }

    /**
     * Updates the player's flying abilities and name prefix based on armor set completion.
     *
     * @param player The player wearing the armor
     * @param hasFullSet Whether the player has a complete set of Imperial Infinity Armor
     */
    private void updatePlayerAbilities(Player player, boolean hasFullSet) {
        if (hasFullSet && ConfigUtils.imperial_infinity_armor_can_fly) {
            player.getAbilities().mayfly = true;
            player.setCustomName(Component.literal(IMPERIAL_PREFIX + player.getName().getString()));
        } else {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.setCustomName(Component.literal(player.getName().getString()));
        }
        player.onUpdateAbilities();
    }

    /**
     * Applies various beneficial effects to the player wearing the full armor set.
     *
     * @param player The player to receive the effects
     */
    private void applyArmorEffects(Player player) {
        player.getFoodData().setSaturation(20);
        player.getFoodData().setFoodLevel(20);

        MobEffectInstance[] effects = {
                new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EFFECT_DURATION, EFFECT_AMPLIFIER, false, false),
                new MobEffectInstance(MobEffects.DIG_SPEED, EFFECT_DURATION, EFFECT_AMPLIFIER, false, false),
                new MobEffectInstance(MobEffects.DAMAGE_BOOST, EFFECT_DURATION, EFFECT_AMPLIFIER, false, false),
                new MobEffectInstance(MobEffects.LUCK, EFFECT_DURATION, EFFECT_AMPLIFIER, false, false),
                new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, EFFECT_DURATION, EFFECT_AMPLIFIER, false, false)
        };

        for (MobEffectInstance effect : effects) {
            player.addEffect(effect);
        }
    }

    /**
     * Checks if the player is wearing a complete set of Imperial Infinity Armor.
     *
     * @param player The player to check
     * @return true if wearing a complete set, false otherwise
     */
    private boolean hasFullSuitOfArmorOn(Player player) {
        if (player == null) return false;

        return player.getInventory().armor.stream().allMatch(stack -> {
            if (stack.isEmpty()) return false;
            Item item = stack.getItem();
            return item == ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get() ||
                    item == ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get() ||
                    item == ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get() ||
                    item == ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get();
        });
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    /**
     * Initializes the client-side renderer for the armor.
     */
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new ImperialInfinityArmorRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    /**
     * Registers the animation controllers for the armor.
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            state.setAnimation(DefaultAnimations.IDLE);
            state.getController().setAnimation(RawAnimation.begin().then("misc.idle", Animation.LoopType.LOOP));

            Entity entity = state.getData(DataTickets.ENTITY);
            Set<Item> wornArmor = new ObjectOpenHashSet<>();

            for (ItemStack stack : entity.getArmorSlots()) {
                if (stack.isEmpty())
                    return PlayState.STOP;

                wornArmor.add(stack.getItem());
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}