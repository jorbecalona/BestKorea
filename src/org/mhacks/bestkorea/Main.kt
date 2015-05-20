package org.mhacks.bestkorea

import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.DBusInterfaceName
import org.freedesktop.dbus.exceptions.DBusExecutionException
import org.freedesktop.dbus.types.DBusListType
import org.mhacks.bestkorea.model.IAdapter
import org.mhacks.bestkorea.model.NfcAdapter
import java.util.*

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 08/05/15
 * (C) 2015 Damian Wieczorek
 */

/**
* Neard package namespace
*/
val NEARD = "org.neard"

fun main(args: Array<String>) {
  val conn = DBusConnection.getConnection(DBusConnection.SYSTEM)
  val adapter = conn.adapter
  println("Powering...")
  adapter.powered = true
  println("Ready!")
  while (true) {
    if (!adapter.polling) adapter.startPollLoop(NfcAdapter.PollMode.DUAL)
    val foo = adapter.tags
    if (foo.isNotEmpty()) try {
      println(foo)
    } catch (error: DBusExecutionException) {}
  }
}