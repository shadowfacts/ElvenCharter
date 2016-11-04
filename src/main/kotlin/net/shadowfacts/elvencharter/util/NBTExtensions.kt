package net.shadowfacts.elvencharter.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.shadowfacts.elvencharter.MODID
import java.util.*

/**
 * @author shadowfacts
 */
const val OWNER = "$MODID-owner"

fun ItemStack.hasOwner(): Boolean {
	if (hasTagCompound()) {
		return tagCompound!!.hasUniqueId(OWNER)
	}
	return false
}

fun ItemStack.getOwner(): UUID {
	return tagCompound!!.getUniqueId(OWNER)!!
}

fun ItemStack.setOwner(owner: UUID) {
	if (!hasTagCompound()) tagCompound = NBTTagCompound()
	tagCompound!!.setUniqueId(OWNER, owner)
}