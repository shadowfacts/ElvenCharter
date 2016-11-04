package net.shadowfacts.elvencharter.util

/**
 * @author shadowfacts
 */
enum class TimeUnit(val seconds: Int) {
	HALF_HOUR(1800),
	HOUR(HALF_HOUR * 2),
	SIX_HOURS(HOUR * 6),
	DAY(HOUR * 24),
	WEEK(DAY * 7);

	operator fun times(i: Int): Int {
		return seconds * i
	}


}