package org.mhacks.bestkorea

import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.DBusInterfaceName
import org.freedesktop.dbus.types.DBusListType
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

/**
* Neard interface names
*/
object Interfaces {
  val ADAPTER = "$NEARD.Adapter"
  val TAG = "$NEARD.Tag"
}

/**
* Neard object paths
*/
object Objects {
  val ADAPTER = "/org/neard/nfc0"
  val TAG = "$ADAPTER/tag0"
}

// TODO: Adapter and Tag interfaces

DBusInterfaceName("org.freedesktop.DBus.Properties") class Adapter(val connection: DBusConnection) {
  private val properties: DBus.Properties
  init {
    properties = connection.getRemoteObject(NEARD, Objects.ADAPTER, javaClass<DBus.Properties>())
  }
  fun <T> get(propertyName: String): T = properties.Get(Interfaces.ADAPTER, propertyName)
  val name: String get() = get("Name")
  val mode: String get() = get("Mode")
  val polling: Boolean get() = get("Polling")
  val powered: Boolean get() = get("Powered")
  val protocols: Vector<String> get() = get("Protocols")
}

DBusInterfaceName(Interfaces.TAG) trait Tag : DBusInterface {
  override fun isRemote() = true
}

fun main(args: Array<String>) {
  val conn = DBusConnection.getConnection(DBusConnection.SYSTEM)
  val adapter = Adapter(conn)
  print(adapter.protocols)
}