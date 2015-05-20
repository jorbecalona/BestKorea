package org.mhacks.bestkorea.model

import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.DBusInterfaceName
import org.freedesktop.dbus.DBusSignal

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
DBusInterfaceName(NfcTag.INTERFACE) public trait ITag : DBusInterface {
  DBusInterfaceName("Write") public fun write(values: Array<*>)
  DBusInterfaceName("GetRawNDEF") public fun raw(): Array<Byte>
}
