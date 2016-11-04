package net.shadowfacts.elvencharter.util

import org.w3c.dom.Node
import org.w3c.dom.NodeList

/**
 * @author shadowfacts
 */
fun NodeList.forEach(action: (Node) -> Unit) {
	for (i in 0..length - 1) {
		action(item(i))
	}
}