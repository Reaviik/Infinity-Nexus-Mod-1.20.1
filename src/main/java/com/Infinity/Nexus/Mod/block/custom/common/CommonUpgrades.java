package com.Infinity.Nexus.Mod.block.custom.common;

import com.Infinity.Nexus.Core.items.custom.ComponentItem;
import com.Infinity.Nexus.Core.items.custom.UpgradeItem;
import com.Infinity.Nexus.Mod.block.entity.*;
import com.Infinity.Nexus.Mod.item.custom.SolarUpgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkHooks;

import java.util.Objects;

public class CommonUpgrades {

    public static void setUpgrades(Level pLevel, BlockPos pPos, Player pPlayer) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        ItemStack stack = pPlayer.getMainHandItem().copy();
        boolean component = stack.getItem() instanceof ComponentItem;
        boolean upgrade = stack.getItem() instanceof UpgradeItem;
        boolean solarUpgrade = stack.getItem() instanceof SolarUpgrade;
        if(pPlayer instanceof ServerPlayer ){
            if (component) {
                if (Objects.requireNonNull(entity) instanceof AssemblerBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof CrusherBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof FactoryBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof GeneratorBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof MatterCondenserBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof MobCrusherBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof PressBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof RecyclerBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof SmelteryBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof SqueezerBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                } else if (entity instanceof PlacerBlockEntity be) { be.setMachineLevel(stack, pPlayer);
                }
            } else if (upgrade) {
                if (Objects.requireNonNull(entity) instanceof AssemblerBlockEntity be) { be.setUpgradeLevel(stack, pPlayer);
                } else if (entity instanceof CrusherBlockEntity be) { be.setUpgradeLevel(stack, pPlayer);
                } else if (entity instanceof FactoryBlockEntity be) { be.setUpgradeLevel(stack, pPlayer);
                } else if (entity instanceof GeneratorBlockEntity be) { be.setUpgradeLevel(stack, pPlayer);
                } else if (entity instanceof MobCrusherBlockEntity be) { be.setUpgradeLevel(stack, pPlayer);
                } else if (entity instanceof PressBlockEntity be) { be.setUpgradeLevel(stack, pPlayer);
                } else if (entity instanceof RecyclerBlockEntity be) { be.setUpgradeLevel(stack, pPlayer);
                } else if (entity instanceof SmelteryBlockEntity be) { be.setUpgradeLevel(stack, pPlayer);
                } else if (entity instanceof SqueezerBlockEntity be) { be.setUpgradeLevel(stack, pPlayer);
                }
            } else if (solarUpgrade) {
                if (Objects.requireNonNull(entity) instanceof SolarBlockEntity be) { be.setSolarLevel(stack, pPlayer);
                }
            }else{
                try{
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (MenuProvider) entity, pPos);
                }catch (Exception e){
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }
        }
    }
}
