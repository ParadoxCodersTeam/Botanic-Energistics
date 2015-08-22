package pct.botanic.energistics.items;

import cpw.mods.fml.common.registry.GameRegistry;
import pct.botanic.energistics.references.CoreRefs;

/**
 * Created by magnus97 on 22/08/2015.
 */
public class BotanicEnergisticsItems {

    public static void Register(){
        GameRegistry.registerItem(new RuneAssemblerCraftingPattern(), "RuneAssemblerPattern", CoreRefs.MODID);
    }

}
