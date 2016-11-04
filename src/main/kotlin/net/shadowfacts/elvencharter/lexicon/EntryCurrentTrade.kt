package net.shadowfacts.elvencharter.lexicon

import net.shadowfacts.elvencharter.MODID
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.LexiconEntry

/**
 * @author shadowfacts
 */
object EntryCurrentTrade : LexiconEntry("$MODID.lexicon.current", CategoryElvenCharter) {

	fun init(): EntryCurrentTrade {
		knowledgeType = BotaniaAPI.elvenKnowledge

		setLexiconPages(PageCurrentTrade)

		return this
	}

}