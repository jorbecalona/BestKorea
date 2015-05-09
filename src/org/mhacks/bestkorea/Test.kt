package org.mhacks.bestkorea

import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.DBusInterfaceName
import java.util.*

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 08/05/15
 * (C) 2015 Damian Wieczorek
 */

DBusInterfaceName("com.canonical.Unity.Session") trait Session : DBusInterface {
  override fun isRemote(): Boolean = true
  fun RealName(): String
}

fun main(args: Array<String>) {
  val conn = DBusConnection.getConnection(DBusConnection.SESSION)
  val intro = conn.getRemoteObject("com.canonical.Unity.Launcher", "/com/canonical/Unity/Session", javaClass<Session>())
  print(intro.RealName())
}