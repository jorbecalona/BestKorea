package org.mhacks.bestkorea.model

import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.DBusInterface
import org.mhacks.bestkorea.NEARD
import kotlin.reflect.KClass
import kotlin.reflect.jvm.java

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
abstract class DBusModel <T: DBusInterface> (
    val type: KClass<T>,
    val interfaceName: String,
    val connection: DBusConnection,
    val objectPath: String,
    val properties: DBus.Properties = connection.getRemoteObject(NEARD, objectPath, javaClass<DBus.Properties>()),
    val iface: T = connection.getRemoteObject(NEARD, objectPath, type.java))
: DBus.Properties by properties {

  init {
    System.err.println("Init: $name")
  }

  fun <T> get(propertyName: String): T = Get(interfaceName, propertyName)
  fun <T> set(propertyName: String, propertyValue: T) = Set(interfaceName, propertyName, propertyValue)
  val name: String get() = get("Name")

  override fun toString(): String = objectPath

}
