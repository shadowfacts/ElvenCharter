package net.shadowfacts.elvencharter.lexicon

import net.shadowfacts.elvencharter.MODID
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.common.lexicon.page.PageText

/**
 * @author shadowfacts
 */
object EntryInfo : LexiconEntry("$MODID.lexicon.info", CategoryElvenCharter) {

	fun init(): EntryInfo {
		setPriority()

		knowledgeType = BotaniaAPI.elvenKnowledge

		setLexiconPages(PageText("$MODID.lexicon.info.text"))

		return this
	}

	override fun compareTo(other: LexiconEntry): Int {
		return -1
	}

}