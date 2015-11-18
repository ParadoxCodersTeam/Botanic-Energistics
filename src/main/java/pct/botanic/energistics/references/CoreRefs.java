package pct.botanic.energistics.references;

/**
 * Created by magnus97 on 22/08/2015.
 */
public class CoreRefs {

    public static final String NAME = "Botania-Energistics";
    public static final String MODID = "botaniaenergistics";
    public static final String VERSIONS = "0.2.3";
    public static final String DEPENDENCIES = "required-after:Forge@[" // require forge.
            + net.minecraftforge.common.ForgeVersion.majorVersion + '.' // majorVersion
            + net.minecraftforge.common.ForgeVersion.minorVersion + '.' // minorVersion
            + net.minecraftforge.common.ForgeVersion.revisionVersion + '.' // revisionVersion
            + net.minecraftforge.common.ForgeVersion.buildVersion + ",)" // buildVersion
            + ";required-after:Botania"
            + ";required-after:appliedenergistics2";
    public static final String CTAB = "bot.ae";

}
