package net.shadowfacts.elvencharter.trade

import net.minecraft.item.ItemStack
import net.shadowfacts.elvencharter.util.forEach
import net.shadowfacts.forgelin.extensions.*
import net.shadowfacts.shadowmc.recipe.Recipe
import org.w3c.dom.Element

/**
 * @author shadowfacts
 */
class Trade(var outputs: List<ItemStack>, var inputs: List<ItemStack>) {

	fun matches(inputs: List<ItemStack>): Boolean {
		for (input in this.inputs) {
			if (!inputs.containsStack(input)) {
				return false
			}
		}
		return true
	}

	fun remove(inputs: MutableList<ItemStack>) {
		for (input in this.inputs) {
			if (inputs.contains(input)) {
				inputs.remove(input)
			}
		}
	}

}

fun Trade(e: Element): Trade {
	val inputs: MutableList<ItemStack> = mutableListOf()
	e.getElementsByTagName("input").forEach {
		if (it is Element) {
			val stack = Recipe.getStack(it) ?: throw NullPointerException("Stack for ${e.getAttribute("modid")}:${e.getAttribute("name")} was null")
			inputs.add(stack)
		}
	}

	val outputs: MutableList<ItemStack> = mutableListOf()
	e.getElementsByTagName("output").forEach {
		if (it is Element) {
			val stack = Recipe.getStack(it) ?: throw NullPointerException("Stack for ${e.getAttribute("modid")}:${e.getAttribute("name")} was null")
			outputs.add(stack)
		}
	}

	return Trade(outputs, inputs)
}
