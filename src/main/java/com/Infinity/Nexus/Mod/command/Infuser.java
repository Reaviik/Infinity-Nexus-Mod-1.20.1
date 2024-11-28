package com.Infinity.Nexus.Mod.command;

import com.Infinity.Nexus.Core.fakePlayer.IFFakePlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;

public class Infuser {
    public Infuser(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("inm").then(Commands.literal("pedestals")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        player.sendSystemMessage(Component.literal("§31 §f= " + Component.translatable("block.infinity_nexus_mod.tech_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§e2 §f= " + Component.translatable("block.infinity_nexus_mod.resource_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§53 §f= " + Component.translatable("block.infinity_nexus_mod.magic_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§74 §f= " + Component.translatable("block.infinity_nexus_mod.decor_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§65 §f= " + Component.translatable("block.infinity_nexus_mod.creativity_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§26 §f= " + Component.translatable("block.infinity_nexus_mod.exploration_pedestal").getString()));

        return 1;
    }

}