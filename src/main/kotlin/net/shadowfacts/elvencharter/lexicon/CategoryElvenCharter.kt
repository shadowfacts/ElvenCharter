package net.shadowfacts.elvencharter.lexicon

import net.minecraft.util.ResourceLocation
import net.shadowfacts.elvencharter.MODID
import vazkii.botania.api.lexicon.LexiconCategory

/**
 * @author shadowfacts
 */
object CategoryElvenCharter : LexiconCategory("$MODID.lexicon.category") {

	fun init(): CategoryElvenCharter {
		icon = ResourceLocation(MODID, "textures/gui/categories/$MODID.png")
		setPriority(4)
		return this
	}

}