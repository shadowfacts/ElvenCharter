package net.shadowfacts.elvencharter

import net.minecraftforge.common.config.Configuration
import net.shadowfacts.elvencharter.util.TimeUnit
import java.io.File

/**
 * @author shadowfacts
 */
object ECConfig {

	private var file: File? = null

	private val validUnits: Array<String> = arrayOf("week", "day", "six_hours", "hour", "half_hour")

	private var unit: String = "six_hours"

	fun init(configDir: File) {
		file = File(configDir, "shadowfacts/ElvenCharter.cfg");
		load()
	}

	fun load() {
		val config = Configuration(file)

		unit = config.getString("unit", "general", unit, "Every unit the trade will be reset", validUnits)

		if (config.hasChanged()) config.save()
	}

	fun getUnit(): TimeUnit {
		return net.shadowfacts.elvencharter.util.TimeUnit.valueOf(unit.toUpperCase())
	}

}