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

DBusInterfaceName(Interfaces.ADAPTER) trait Adapter : DBusInterface {
  override fun isRemote() = true
}

DBusInterfaceName(Interfaces.TAG) trait Tag : DBusInterface {
  override fun isRemote() = true
}

fun main(args: Array<String>) {
  val conn = DBusConnection.getConnection(DBusConnection.SYSTEM)
  val adapter = conn.getRemoteObject(NEARD, Objects.ADAPTER, javaClass<DBus.Properties>())
  print(adapter.GetAll(Interfaces.ADAPTER))
}