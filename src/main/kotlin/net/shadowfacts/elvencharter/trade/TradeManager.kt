package net.shadowfacts.elvencharter.trade

import net.shadowfacts.elvencharter.ECConfig
import net.shadowfacts.elvencharter.util.forEach
import org.w3c.dom.Element
import java.io.File
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

/**
 * @author shadowfacts
 */
object TradeManager {

	val trades: MutableList<Trade> = mutableListOf()
	val lastTradeTimes: MutableMap<UUID, Long> = hashMapOf()
	val random = Random()

	fun load(file: File) {
		trades.clear()

		val factory = DocumentBuilderFactory.newInstance()
		val builder = factory.newDocumentBuilder()
		val document = builder.parse(file)
		document.documentElement.normalize()

		document.getElementsByTagName("trade").forEach {
			if (it is Element) {
				register(Trade(it))
			}
		}
	}

	fun register(trade: Trade) {
		trades.add(trade)
	}

	fun getCurrentTrade(): Trade {
		val seed = System.currentTimeMillis() / 1000 / ECConfig.getUnit().seconds
		random.setSeed(seed)
		return trades[random.nextInt(trades.size)]
	}

	fun getCurrentTrade(owner: UUID): Trade? {
		if (lastTradeTimes.containsKey(owner)) {
			val now = System.currentTimeMillis() / 1000
			val last = lastTradeTimes[owner]!!
			if (now < last + ECConfig.getUnit().seconds) {
				return null
			}
		}
		return getCurrentTrade()
	}

	fun useCurrentTrade(owner: UUID) {
		val currentSeconds = System.currentTimeMillis() / 1000
		lastTradeTimes[owner] = currentSeconds - (currentSeconds % ECConfig.getUnit().seconds)
	}

}