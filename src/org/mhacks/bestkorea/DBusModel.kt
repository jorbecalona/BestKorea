package org.mhacks.bestkorea

import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
abstract class DBusModel(val interfaceName: String, val connection: DBusConnection, val objectPath: String) {
  val properties: DBus.Properties
  init {
    properties = connection.getRemoteObject(NEARD, objectPath, javaClass<DBus.Properties>())
  }
  fun <T> get(propertyName: String): T = properties.Get(interfaceName, propertyName)
  val name: String get() = get("Name")
  override fun toString(): String = name
}