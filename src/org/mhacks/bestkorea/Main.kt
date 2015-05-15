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

// TODO: Adapter and Tag method interfaces

class Adapter(val connection: DBusConnection, val name: String = Objects.ADAPTER) {
  private val properties: DBus.Properties
  init {
    properties = connection.getRemoteObject(NEARD, name, javaClass<DBus.Properties>())
  }
  fun <T> get(propertyName: String): T = properties.Get(Interfaces.ADAPTER, propertyName)
  val mode: String get() = get("Mode")
  val polling: Boolean get() = get("Polling")
  val powered: Boolean get() = get("Powered")
  val protocols: Vector<String> get() = get("Protocols")
  val tagPaths: List<String> get() = get<Vector<*>>("Tags").mapNotNull { it.toString() }
  val tags: List<Tag> get() = tagPaths.mapNotNull { Tag(connection, it) }
}

class Tag(val connection: DBusConnection, val name: String) {
  private val properties: DBus.Properties
  init {
    properties = connection.getRemoteObject(NEARD, name, javaClass<DBus.Properties>())
  }
  fun <T> get(propertyName: String): T = properties.Get(Interfaces.TAG, propertyName)
  val protocol: String get() = get("Protocol")
  val readOnly: Boolean get() = get("ReadOnly")
  val adapterPath: String get() = get<Any>("Adapter").toString()
  val adapter: Adapter get() = Adapter(connection, adapterPath)
}

fun main(args: Array<String>) {
  val conn = DBusConnection.getConnection(DBusConnection.SYSTEM)
  val adapter = Adapter(conn)
  print(adapter.tags.firstOrNull()?.name ?: "No tag")
}