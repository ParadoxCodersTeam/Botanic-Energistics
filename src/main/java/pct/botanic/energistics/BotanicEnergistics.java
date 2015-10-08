package pct.botanic.energistics;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import pct.botanic.energistics.blocks.AERuneAssembler;
import pct.botanic.energistics.blocks.BotanicEnergisticsBlocks;
import pct.botanic.energistics.items.BotanicEnergisticsItems;
import pct.botanic.energistics.recipes.BotanicEnergisticsRecipes;
import pct.botanic.energistics.references.CoreRefs;
import vazkii.botania.api.BotaniaAPI;

/**
 * Created by magnus97 on 22/08/2015.
 */
@Mod(modid = CoreRefs.MODID, name = CoreRefs.NAME,version = CoreRefs.VERSIONS,dependencies = CoreRefs.DEPENDENCIES,acceptedMinecraftVersions = "1.7.10")
public class BotanicEnergistics {

    @Mod.Instance()
    public static BotanicEnergistics instance;

    public static final CreativeTabs botanicEnergisticsTab = new CreativeTabs(CoreRefs.CTAB) {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Items.item_frame;
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        BotanicEnergisticsBlocks.Register();

        BotanicEnergisticsItems.Register();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new pct.botanic.energistics.gui.GUIHandler());
    }
    @Mod.EventHandler
    public static void init(FMLInitializationEvent event){
    	BotanicEnergisticsRecipes.Register();
    	
    	BotaniaAPI.addEntry(new AERuneAssembler().getEntry(), BotaniaAPI.categoryDevices);
    }
    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event){

    }

}
