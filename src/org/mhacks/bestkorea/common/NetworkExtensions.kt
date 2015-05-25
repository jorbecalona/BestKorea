package org.mhacks.bestkorea.common

import java.net.InetAddress
import java.net.NetworkInterface

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 19/05/15
 * (C) 2015 Damian Wieczorek
 */
val InetAddress.networkInterface: NetworkInterface get() = NetworkInterface.getByInetAddress(this)
val NetworkInterface.hardwareAddress: ByteArray get() = getHardwareAddress()
fun ByteArray.asAddress() = map { "%02X".format(it) }.join("-")
