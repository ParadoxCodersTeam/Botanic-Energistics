package pct.botanic.energistics.utilities;

import net.minecraftforge.common.config.Configuration;

import java.io.File;


public class Config {
    static boolean runeAssembler, aedaisy;
    static boolean passiveMode;
    static boolean elvenPortal;


    public static void init(File configFile){
        Configuration config = new Configuration(configFile);
        config.load();

        runeAssembler = config.get(Configuration.CATEGORY_GENERAL, "runeAssembler", true, "Whether or not the RuneAssembler should be loaded during startup. If you set this to false, the block will be removed from worlds when loading them").getBoolean();
        aedaisy = config.get(Configuration.CATEGORY_GENERAL, "aedaisy", true, "Whether or not the aedaisy should be loaded during startup. If you set this to false, the block will be removed from worlds when loading them").getBoolean();
        elvenPortal = config.get(Configuration.CATEGORY_GENERAL, "elvenPortal", true, "Whether or not the elven Portal should be loaded during startup. If you set this to false, the block will be removed from worlds when loading them").getBoolean();
        passiveMode = config.get(Configuration.CATEGORY_GENERAL, "passiveMode", true, "When set to true, all blocks won't do anything on their own and will require a valid (Multi-)Block next to them to work").getBoolean();

        config.save();
    }

    public static boolean isRuneAssembler() {
        return runeAssembler;
    }

    public static boolean isAedaisy() {
        return aedaisy;
    }

    public static boolean isElvenPortal() {
        return elvenPortal;
    }

    public static boolean isPassiveMode() {
        return passiveMode;
    }
}
