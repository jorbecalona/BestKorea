package org.mhacks.bestkorea.model.nfc

import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.exceptions.DBusExecutionException
import org.mhacks.bestkorea.NEARD
import org.mhacks.bestkorea.property.ReadOnlyDbusProperty
import org.mhacks.bestkorea.property.ReadWriteDbusProperty
import kotlin.reflect.KClass
import kotlin.reflect.jvm.java
import kotlin.reflect.jvm.kotlin

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

  trait Factory<T: DBusModel<*>> {
    fun create(connection: DBusConnection, path: String): T
  }

  init {
    System.err.println("Init: $name")
  }

  fun <T> get(propertyName: String): T = Get(interfaceName, propertyName)
  fun <T> set(propertyName: String, propertyValue: T) = Set(interfaceName, propertyName, propertyValue)

  fun <T: DBusModel<*>> acquire(paths: List<String>, factory: Factory<T>) = paths.flatMap {
    try {
      listOf(factory.create(connection, it))
    } catch (error: DBusExecutionException) {
      emptyList<T>()
    }
  }

  fun <T> writableProperty(name: String) = ReadWriteDbusProperty<T>(name)
  fun <T> readOnlyProperty(name: String) = ReadOnlyDbusProperty<T>(name)

  val name: String get() = get("Name")

  override fun toString(): String = javaClass.kotlin let {
    try {
      "${it.simpleName} (${it.properties.map {
        "${it.name}: ${it.get(this)}"
      }.join(", ")})"
    } catch(error: Throwable) {
      "(invalid object)"
    }
  }

}