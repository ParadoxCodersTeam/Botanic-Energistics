package pct.botanic.energistics.utilities;

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
        public basicKnowledgeEntry(String unlocalizedName, LexiconCategory category) {
            super(unlocalizedName, category);
            setKnowledgeType(BotaniaAPI.basicKnowledge);
        }
    }

    public static class elvenKnowledgeEntry extends LexiconEntry {
        public elvenKnowledgeEntry(String unlocalizedName, LexiconCategory category) {
            super(unlocalizedName, category);
            setKnowledgeType(BotaniaAPI.elvenKnowledge);
        }
    }

    public static class runicKnowledgeEntry extends LexiconEntry {
        public runicKnowledgeEntry(String unlocalizedName, LexiconCategory category) {
            super(unlocalizedName, category);
            setKnowledgeType(BotaniaAPI.relicKnowledge);
        }
    }

    public static class LexiconPageCreator extends PageText {
        String text = "";
        public LexiconPageCreator(String unlocalizedName) {
            super(unlocalizedName);
        }

        public static LexiconPage createTextPage(String unlocalizedName,int x, int y, int width, int height,  String text) {
            LexiconPageCreator page = new LexiconPageCreator(unlocalizedName);
            text = x + ":" + y + ":" + width + ":" + height + ":" + text;
            return page;
        }

        @Override
        public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
            super.renderScreen(gui, mx, my);
            String[] data = text.split(":", 5);
            renderText(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]), Integer.valueOf(data[3]), data[4]);
        }
    }
}
