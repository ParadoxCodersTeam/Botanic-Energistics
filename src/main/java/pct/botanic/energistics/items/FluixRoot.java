package pct.botanic.energistics.items;

import pct.botanic.energistics.BotanicEnergistics;
import pct.botanic.energistics.references.CoreRefs;
import net.minecraft.item.Item;

public class FluixRoot extends Item{
	public FluixRoot() {
		super();
		setUnlocalizedName("fluixRoot");
		setHasSubtypes(false);
		setTextureName(CoreRefs.MODID+":fluixRoot");
		setCreativeTab(BotanicEnergistics.botanicEnergisticsTab);
        setMaxStackSize(64);
	}

}
