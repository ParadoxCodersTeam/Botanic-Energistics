package pct.botanic.energistics.references;

/**
 * Created by magnus97 on 22/08/2015.
 */
public class CoreRefs {

    public static final String NAME = "Botanic-Energistics";
    public static final String MODID = "botanicenergistics";
    public static final String VERSIONS = "@version@";
    public static final String DEPENDENCIES = "required-after:Forge@[" // require forge.
            + net.minecraftforge.common.ForgeVersion.majorVersion + '.' // majorVersion
            + net.minecraftforge.common.ForgeVersion.minorVersion + '.' // minorVersion
            + net.minecraftforge.common.ForgeVersion.revisionVersion + '.' // revisionVersion
            + net.minecraftforge.common.ForgeVersion.buildVersion + ",);required-after:Botania;required-after:appliedenergistics2"; // buildVersion
    public static final String CTAB = "bot.ae";

}
