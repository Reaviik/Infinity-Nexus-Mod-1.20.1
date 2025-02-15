package com.Infinity.Nexus.Mod.block;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.custom.*;
import com.Infinity.Nexus.Mod.block.custom.pedestals.*;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocksAdditions {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<Block> INFINIUM_STELLARUM_BLOCK = registerBlock("infinium_stellarum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.DIAMOND)));
    public static final RegistryObject<Block> INFINITY_BLOCK = registerBlock("infinity_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.DIAMOND)));
    public static final RegistryObject<Block> LEAD_BLOCK = registerBlock("lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.LAPIS)));
    public static final RegistryObject<Block> ALUMINUM_BLOCK = registerBlock("aluminum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<Block> NICKEL_BLOCK = registerBlock("nickel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_YELLOW)));
    public static final RegistryObject<Block> ZINC_BLOCK = registerBlock("zinc_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> BRASS_BLOCK = registerBlock("brass_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryObject<Block> BRONZE_BLOCK = registerBlock("bronze_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_BLACK)));
    public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.RAW_IRON)));


    public static final RegistryObject<Block> RAW_INFINITY_BLOCK = registerBlock("raw_infinity_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> RAW_LEAD_BLOCK = registerBlock("raw_lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> RAW_ALUMINUM_BLOCK = registerBlock("raw_aluminum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> RAW_NICKEL_BLOCK = registerBlock("raw_nickel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> RAW_ZINC_BLOCK = registerBlock("raw_zinc_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final RegistryObject<Block> RAW_URANIUM_BLOCK = registerBlock("raw_uranium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));



    public static final RegistryObject<Block> INFINITY_ORE = registerBlock("infinity_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> LEAD_ORE = registerBlock("lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> ALUMINUM_ORE = registerBlock("aluminum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> NICKEL_ORE = registerBlock("nickel_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> ZINC_ORE = registerBlock("zinc_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> DEEPSLATE_INFINITY_ORE = registerBlock("deepslate_infinity_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final RegistryObject<Block> DEEPSLATE_ALUMINUM_ORE = registerBlock("deepslate_aluminum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final RegistryObject<Block> DEEPSLATE_NICKEL_ORE = registerBlock("deepslate_nickel_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final RegistryObject<Block> DEEPSLATE_ZINC_ORE = registerBlock("deepslate_zinc_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final RegistryObject<Block> DEEPSLATE_URANIUM_ORE = registerBlock("deepslate_uranium_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));

    public static final RegistryObject<Block> EXPLORAR_PORTAL_FRAME = registerBlock("explorar_portal_frame", () -> new PortalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.POLISHED_DEEPSLATE).mapColor(MapColor.QUARTZ)));
    public static final RegistryObject<Block> EXPLORAR_PORTAL = registerBlock("explorar_portal", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.SPORE_BLOSSOM)));
    public static final RegistryObject<Block> VOXEL_BLOCK = registerBlock("voxel_cube", () -> new Voxel(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(3.0f, 6.0f).sound(SoundType.FROGLIGHT).mapColor(MapColor.ICE)));
    public static final RegistryObject<Block> ENTITY_CENTRALIZER = registerBlock("entity_centralizer", () -> new EntityCentralizer(BlockBehaviour.Properties.copy(Blocks.WHITE_CARPET).strength(1.0f, 1.0f).sound(SoundType.FROGLIGHT).noCollission().mapColor(MapColor.METAL)));

    public static final RegistryObject<Block> ASPHALT = registerBlock("asphalt", () -> new Asphalt(BlockBehaviour.Properties.copy(Blocks.LADDER).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.POLISHED_DEEPSLATE).mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> MOB_CRUSHER = registerBlock("mob_crusher", () -> new MobCrusher(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> CRUSHER = registerBlock("crusher", () -> new Crusher(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Crusher.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> PRESS = registerBlock("press", () -> new Press(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Press.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> ASSEMBLY = registerBlock("assembler", () -> new Assembler(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Assembler.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> FACTORY = registerBlock("factory", () -> new Factory(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Factory.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> INFUSER = registerBlock("infuser", () -> new Infuser(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Factory.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> SQUEEZER = registerBlock("squeezer", () -> new Squeezer(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Squeezer.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> SMELTERY = registerBlock("smeltery", () -> new Smeltery(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Smeltery.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> GENERATOR = registerBlock("generator", () -> new Generator(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Generator.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> RECYCLER = registerBlock("recycler", () -> new Recycler(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Recycler.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> MATTER_CONDENSER = registerBlock("matter_condenser", () -> new MatterCondenser(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(MatterCondenser.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> COMPACTOR = registerBlock("compactor", () -> new Compactor(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Compactor.LIT) == 1 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> PLACER = registerBlock("placer", () -> new Placer(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> DISPLAY = registerBlock("display", () -> new ItemDisplay(BlockBehaviour.Properties.copy(Blocks.ANDESITE).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> ENTITY_DISPLAY = registerBlock("entity_display", () -> new EntityDisplay(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> 1).noOcclusion().noCollission().mapColor(MapColor.WOOD)));
    public static final RegistryObject<Block> TANK = registerBlock("tank", () -> new Tank(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Tank.LIT) == 1 ? 5 : 0).noLootTable().noOcclusion().mapColor(MapColor.TERRACOTTA_BLACK)));

    public static final RegistryObject<Block> TECH_PEDESTAL = registerBlock("tech_pedestal", () -> new TechPedestal(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> MAGIC_PEDESTAL = registerBlock("magic_pedestal", () -> new MagicPedestal(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> CREATIVITY_PEDESTAL = registerBlock("creativity_pedestal", () -> new CreativityPedestal(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> DECOR_PEDESTAL = registerBlock("decor_pedestal", () -> new DecorPedestal(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> EXPLORATION_PEDESTAL = registerBlock("exploration_pedestal", () -> new ExplorationPedestal(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> RESOURCE_PEDESTAL = registerBlock("resource_pedestal", () -> new ResourcePedestal(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));

    public static final RegistryObject<Block> SOLAR = registerBlock("solar", () -> new Solar(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(1.0f, 6.0f).lightLevel((state) -> state.getValue(Solar.LIT) > 0 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> FERMENTATION_BARREL = registerBlock("fermentation_barrel", () -> new FermentationBarrel(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.WOOD).strength(1.0f, 6.0f).noOcclusion().mapColor(MapColor.WOOD)));

    public static final RegistryObject<LiquidBlock> LUBRICANT = BLOCKS.register("lubricant", () -> new LiquidBlock(ModFluids.LUBRICANT_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<LiquidBlock> ETHANOL = BLOCKS.register("ethanol", () -> new LiquidBlock(ModFluids.ETHANOL_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<LiquidBlock> OIL = BLOCKS.register("oil", () -> new LiquidBlock(ModFluids.OIL_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<LiquidBlock> VINEGAR = BLOCKS.register("vinegar", () -> new LiquidBlock(ModFluids.VINEGAR_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<LiquidBlock> SUGARCANE_JUICE = BLOCKS.register("sugarcane_juice", () -> new LiquidBlock(ModFluids.SUGARCANE_JUICE_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<LiquidBlock> WINE = BLOCKS.register("wine", () -> new LiquidBlock(ModFluids.WINE_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<LiquidBlock> EXPERIENCE = BLOCKS.register("experience", () -> new LiquidBlock(ModFluids.EXPERIENCE_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_GREEN)));
    public static final RegistryObject<LiquidBlock> STARLIQUID = BLOCKS.register("starliquid", () -> new LiquidBlock(ModFluids.STARLIQUID_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<LiquidBlock> POTATO_JUICE = BLOCKS.register("potato_juice", () -> new LiquidBlock(ModFluids.POTATO_JUICE_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_BROWN)));

    public static final RegistryObject<Block> CATWALK = registerBlock("catwalk", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),1));
    public static final RegistryObject<Block> CATWALK_2 = registerBlock("catwalk_2", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),2));
    public static final RegistryObject<Block> CATWALK_3 = registerBlock("catwalk_3", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),3));
    public static final RegistryObject<Block> CATWALK_4 = registerBlock("catwalk_4", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),4));
    public static final RegistryObject<Block> CATWALK_5 = registerBlock("catwalk_5", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),5));
    public static final RegistryObject<Block> CATWALK_6 = registerBlock("catwalk_6", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),6));
    public static final RegistryObject<Block> CATWALK_7 = registerBlock("catwalk_7", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),7));
    public static final RegistryObject<Block> CATWALK_8 = registerBlock("catwalk_8", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),8));
    public static final RegistryObject<Block> CATWALK_9 = registerBlock("catwalk_9", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),9));
    public static final RegistryObject<Block> CATWALK_10 = registerBlock("catwalk_10", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),10));
    public static final RegistryObject<Block> CATWALK_11 = registerBlock("catwalk_11", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),11));
    public static final RegistryObject<Block> CATWALK_12 = registerBlock("catwalk_12", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),12));
    public static final RegistryObject<Block> CATWALK_13 = registerBlock("catwalk_13", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),13));
    public static final RegistryObject<Block> CATWALK_14 = registerBlock("catwalk_14", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),14));
    public static final RegistryObject<Block> CATWALK_15 = registerBlock("catwalk_15", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),15));
    public static final RegistryObject<Block> CATWALK_16 = registerBlock("catwalk_16", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),16));
    public static final RegistryObject<Block> CATWALK_17 = registerBlock("catwalk_17", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),17));
    public static final RegistryObject<Block> CATWALK_18 = registerBlock("catwalk_18", () -> new Catwalk(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),18));

    public static final RegistryObject<Block> DEPOT = registerBlock("depot", () -> new Depot(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.WOOD), "item.infinity_nexus.depot_description"));
    public static final RegistryObject<Block> DEPOT_STONE = registerBlock("depot_stone", () -> new Depot_Stone(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.STONE), "item.infinity_nexus.depot_stone_description"));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItemsAdditions.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
