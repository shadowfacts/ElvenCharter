package net.shadowfacts.elvencharter

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.FakePlayer
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.shadowfacts.elvencharter.lexicon.CategoryElvenCharter
import net.shadowfacts.elvencharter.lexicon.EntryCurrentTrade
import net.shadowfacts.elvencharter.lexicon.EntryInfo
import net.shadowfacts.elvencharter.trade.TradeManager
import net.shadowfacts.elvencharter.util.hasOwner
import net.shadowfacts.elvencharter.util.setOwner
import net.shadowfacts.shadowlib.util.IOUtils
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.common.item.ModItems
import vazkii.botania.common.lexicon.LexiconData
import java.io.File
import java.io.FileOutputStream

/**
 * @author shadowfacts
 */
@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = "required-after:Botania;", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object ElvenCharter {

	private var configDir: File? = null

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
		ECConfig.init(event.modConfigurationDirectory)

		configDir = event.modConfigurationDirectory
		initTrades()

		MinecraftForge.EVENT_BUS.register(this)
	}

	@Mod.EventHandler
	fun init(event: FMLInitializationEvent) {
		BotaniaAPI.elvenTradeRecipes.add(RecipeElvenCharter)
		BotaniaAPI.addCategory(CategoryElvenCharter.init())
		BotaniaAPI.addEntry(LexiconData.alfhomancyIntro, CategoryElvenCharter)
		BotaniaAPI.addEntry(EntryInfo.init(), CategoryElvenCharter)
		BotaniaAPI.addEntry(EntryCurrentTrade.init(), CategoryElvenCharter)
	}

	@Mod.EventHandler
	fun serverStarting(event: FMLServerStartingEvent) {
		event.registerServerCommand(object : CommandBase() {
			override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<out String>) {
				initTrades()
				sender.addChatMessage(TextComponentString("Elven Charter trades reloaded"))
			}

			override fun getCommandName(): String = "ecreload"

			override fun getCommandUsage(sender: ICommandSender): String = "Reloads Elven Charter trades"
		})
	}

	@SubscribeEvent
	fun onRightClickItem(event: PlayerInteractEvent.RightClickItem) {
		if (event.itemStack == null) return
		if (event.entityPlayer is FakePlayer) return
		if (event.itemStack!!.item == ModItems.lexicon && !event.itemStack!!.hasOwner()) {
			event.itemStack!!.setOwner(event.entityPlayer.uniqueID)
		}
	}

	private fun initTrades() {
		val f = File(configDir!!, "shadowfacts/ElvenTrades.xml")
		if (!f.exists()) {
			f.parentFile.mkdirs()
			f.createNewFile()
			IOUtils.copy(javaClass.getResourceAsStream("/assets/$MODID/trades.xml"), FileOutputStream(f))
		}
		TradeManager.load(f)
	}

}