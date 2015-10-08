package pct.botanic.energistics.utilities;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.DummyPage;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.lexicon.page.PageText;

/**
 * Created by beepbeat on 20.09.2015.
 */
public class LexionEntryHelper{

    public static class basicKnowledgeEntry extends LexiconEntry {
        public basicKnowledgeEntry(String unlocalizedName, LexiconCategory category, ItemStack icon) {
            super(unlocalizedName, category);
            setKnowledgeType(BotaniaAPI.basicKnowledge);
        }
    }

    public static class elvenKnowledgeEntry extends LexiconEntry {
        public elvenKnowledgeEntry(String unlocalizedName, LexiconCategory category, ItemStack icon) {
            super(unlocalizedName, category);
            setKnowledgeType(BotaniaAPI.elvenKnowledge);
        }
    }

    public static class runicKnowledgeEntry extends LexiconEntry {
        public runicKnowledgeEntry(String unlocalizedName, LexiconCategory category, ItemStack icon) {
            super(unlocalizedName, category);
            setKnowledgeType(BotaniaAPI.relicKnowledge);
            setIcon(icon);
        }
    }

    public static class LexiconPageCreator extends PageText {
        String text = "";
        public LexiconPageCreator(String unlocalizedName) {
            super(unlocalizedName);
        }

        public static LexiconPage createTextPage(String unlocalizedName, int x, int y, int width, int height, String text) {
            LexiconPageCreator page = new LexiconPageCreator(unlocalizedName);
            text = x + ":" + y + ":" + text.length() + 2 + ":" + height + ":" + text;

            return page;
        }

        @Override
        public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
            super.renderScreen(gui, mx, my);
            String[] data = text.split(":", 5);
           try {
               renderText(Integer.valueOf(gui.getLeft() + data[0] + 16), gui.getTop() + Integer.valueOf(data[1] + 2), Integer.valueOf(data[2]), Integer.valueOf(data[3]), data[4]);
           }catch (Exception e){
               renderText(2, 2, 4, 10, "something went wrong");
           }
        }
    }
}
