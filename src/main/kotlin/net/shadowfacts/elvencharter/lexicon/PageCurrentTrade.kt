package net.shadowfacts.elvencharter.lexicon

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.shadowfacts.elvencharter.MODID
import net.shadowfacts.elvencharter.trade.TradeManager
import org.lwjgl.opengl.GL11
import vazkii.botania.api.internal.IGuiLexiconEntry
import vazkii.botania.client.core.handler.MiscellaneousIcons
import vazkii.botania.client.lib.LibResources
import vazkii.botania.common.lexicon.page.PageRecipe
import vazkii.botania.common.lexicon.page.PageText

/**
 * @author shadowfacts
 */
object PageCurrentTrade : PageRecipe("$MODID.lexicon.current.page") {

	private val elvenTradeOverlay = ResourceLocation(LibResources.GUI_ELVEN_TRADE_OVERLAY)

	@SideOnly(Side.CLIENT)
	override fun renderRecipe(gui: IGuiLexiconEntry, mx: Int, my: Int) {

		val trade = TradeManager.getCurrentTrade(Minecraft.getMinecraft().thePlayer.uniqueID)

		if (trade == null) {
			val width = gui.width - 30
			val x = gui.left + 16
			val y = gui.top + 2
			PageText.renderText(x, y, width, gui.height, 10, true, 0, "$MODID.lexicon.current.none")
		} else {
			val render = Minecraft.getMinecraft().renderEngine
			render.bindTexture(elvenTradeOverlay)

			GlStateManager.enableBlend()
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
			GlStateManager.color(1f, 1f, 1f, 1f)
			(gui as GuiScreen).drawTexturedModalRect(gui.left, gui.top, 0, 0, gui.width, gui.height)
			GlStateManager.disableBlend()

			val outputs = trade.outputs
			for (i in 0.until(outputs.size)) {
				renderItemAtOutputPos(gui, i % 2, i / 2, outputs[i])
			}

			val inputs = trade.inputs
			for (i in 0.until(inputs.size)) {
				renderItemAtInputPos(gui, i, inputs[i])
			}

			val portalIcon = MiscellaneousIcons.INSTANCE.alfPortalTex
			render.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
			gui.drawTexturedModalRect(gui.left + 22, gui.top + 36, portalIcon, 48, 48)
		}
	}

	@SideOnly(Side.CLIENT)
	fun renderItemAtInputPos(gui: IGuiLexiconEntry, x: Int, stack: ItemStack?) {
		var stack = stack
		if (stack == null || stack.item == null)
			return
		stack = stack.copy()

		if (stack!!.itemDamage == java.lang.Short.MAX_VALUE.toInt())
			stack.itemDamage = 0

		val xPos = gui.left + x * 20 + 45
		val yPos = gui.top + 14
		val stack1 = stack.copy()
		if (stack1.itemDamage == -1)
			stack1.itemDamage = 0

		renderItem(gui, xPos.toDouble(), yPos.toDouble(), stack1, false)
	}

	@SideOnly(Side.CLIENT)
	fun renderItemAtOutputPos(gui: IGuiLexiconEntry, x: Int, y: Int, stack: ItemStack?) {
		var stack = stack
		if (stack == null || stack.item == null)
			return
		stack = stack.copy()

		if (stack!!.itemDamage == java.lang.Short.MAX_VALUE.toInt())
			stack.itemDamage = 0

		val xPos = gui.left + x * 20 + 94
		val yPos = gui.top + y * 20 + 52

		val stack1 = stack.copy()
		if (stack1.itemDamage == -1) {
			stack1.itemDamage = 0
		}

		renderItem(gui, xPos.toDouble(), yPos.toDouble(), stack1, false)
	}

	override fun getDisplayedRecipes(): MutableList<ItemStack>? {
		return TradeManager.getCurrentTrade().outputs.toMutableList()
	}

}