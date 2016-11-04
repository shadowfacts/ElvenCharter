package net.shadowfacts.elvencharter

import com.google.common.collect.ImmutableList
import net.minecraft.item.ItemStack
import net.shadowfacts.elvencharter.trade.Trade
import net.shadowfacts.elvencharter.trade.TradeManager
import net.shadowfacts.elvencharter.util.getOwner
import net.shadowfacts.elvencharter.util.hasOwner
import vazkii.botania.api.recipe.RecipeElvenTrade
import vazkii.botania.common.item.ModItems
import java.util.*

/**
 * @author shadowfacts
 */
object RecipeElvenCharter : RecipeElvenTrade(arrayOf()) {

	var lexica: ItemStack? = null

	override fun getInputs(): MutableList<Any>? {
		return ImmutableList.of()
	}

	override fun getOutputs(): MutableList<ItemStack> {
		if (lexica != null) {
			val owner = lexica!!.getOwner()
			val trade: Trade? = TradeManager.getCurrentTrade(owner)
			if (trade != null) {
				TradeManager.useCurrentTrade(owner)
				val outputs = trade.outputs.toMutableList()
				outputs.add(lexica!!)
				lexica = null
				return outputs
			}
		}
		return mutableListOf()
	}

	override fun matches(inputs: MutableList<ItemStack>, remove: Boolean): Boolean {
		var lexica: ItemStack? = null
		for (stack in inputs) {
			if (stack.item == ModItems.lexicon) {
				lexica = stack
				break
			}
		}
		if (lexica != null && lexica.hasOwner()) {
			val owner = lexica.getOwner()
			val trade: Trade? = TradeManager.getCurrentTrade(owner)
			if (trade != null) {
				val result = trade.matches(inputs)

				if (remove) {
					trade.remove(inputs)
					inputs.remove(lexica)
					this.lexica = lexica
				}

				return result
			}
		}
		return false
	}

}