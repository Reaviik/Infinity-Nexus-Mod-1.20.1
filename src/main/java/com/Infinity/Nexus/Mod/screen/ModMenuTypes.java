package com.Infinity.Nexus.Mod.screen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.screen.assembler.AssemblerMenu;
import com.Infinity.Nexus.Mod.screen.condenser.CondenserMenu;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherMenu;
import com.Infinity.Nexus.Mod.screen.factory.FactoryMenu;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelMenu;
import com.Infinity.Nexus.Mod.screen.generator.GeneratorMenu;
import com.Infinity.Nexus.Mod.screen.mobcrusher.MobCrusherMenu;
import com.Infinity.Nexus.Mod.screen.placer.PlacerMenu;
import com.Infinity.Nexus.Mod.screen.press.PressMenu;
import com.Infinity.Nexus.Mod.screen.recycler.RecyclerMenu;
import com.Infinity.Nexus.Mod.screen.smeltery.SmelteryMenu;
import com.Infinity.Nexus.Mod.screen.solar.SolarMenu;
import com.Infinity.Nexus.Mod.screen.squeezer.SqueezerMenu;
import com.Infinity.Nexus.Mod.screen.tank.TankMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, InfinityNexusMod.MOD_ID);

    public static final RegistryObject<MenuType<MobCrusherMenu>> MOB_CRUSHER_MENU =
            registerMenuType("mob_crusher_menu", MobCrusherMenu::new);
    public static final RegistryObject<MenuType<CrusherMenu>> CRUSHER_MENU =
            registerMenuType("crusher_menu", CrusherMenu::new);

    public static final RegistryObject<MenuType<PressMenu>> PRESS_MENU =
            registerMenuType("press_menu", PressMenu::new);

    public static final RegistryObject<MenuType<AssemblerMenu>> ASSEMBLY_MENU =
            registerMenuType("assembler_menu", AssemblerMenu::new);

    public static final RegistryObject<MenuType<FactoryMenu>> FACTORY_MENU =
            registerMenuType("factory_menu", FactoryMenu::new);

    public static final RegistryObject<MenuType<SqueezerMenu>> SQUEEZER_MENU =
            registerMenuType("squeezer_menu", SqueezerMenu::new);

    public static final RegistryObject<MenuType<SmelteryMenu>> SMELTERY_MENU =
            registerMenuType("smeltery_menu", SmelteryMenu::new);

    public static final RegistryObject<MenuType<GeneratorMenu>> GENERATOR_MENU =
            registerMenuType("generator_menu", GeneratorMenu::new);

    public static final RegistryObject<MenuType<FermentationBarrelMenu>> FERMENTATION_BARREL_MENU =
            registerMenuType("fermentation_barre_menu", FermentationBarrelMenu::new);
    public static final RegistryObject<MenuType<RecyclerMenu>> RECYCLER_MENU =
            registerMenuType("recycler_menu", RecyclerMenu::new);
    public static final RegistryObject<MenuType<CondenserMenu>> MATTER_CONDENSER_MENU =
            registerMenuType("condenser_menu", CondenserMenu::new);
    public static final RegistryObject<MenuType<PlacerMenu>> PLACER_MENU =
            registerMenuType("placer_menu", PlacerMenu::new);
    public static final RegistryObject<MenuType<TankMenu>> TANK_MENU =
            registerMenuType("tank_menu", TankMenu::new);


    //Solar
    public static final RegistryObject<MenuType<SolarMenu>> SOLAR_MENU = registerMenuType("solar_menu", SolarMenu::new);
    //public static final RegistryObject<MenuType<SolarAdvancedMenu>> SOLAR_ADVANCED_MENU = registerMenuType("solar_advanced_menu", SolarAdvancedMenu::new);
    //public static final RegistryObject<MenuType<SolarUltimateMenu>> SOLAR_ULTIMATE_MENU = registerMenuType("solar_ultimate_menu", SolarUltimateMenu::new);
    //public static final RegistryObject<MenuType<SolarQuantumMenu>> SOLAR_QUANTUM_MENU = registerMenuType("solar_quantum_menu", SolarQuantumMenu::new);


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
