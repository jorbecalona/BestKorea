package org.mhacks.bestkorea.model

import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.DBusInterfaceName
import org.freedesktop.dbus.DBusSignal
import org.freedesktop.dbus.Variant

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
DBusInterfaceName(NfcTag.INTERFACE) public trait ITag : DBusInterface {
  public fun Write(values: Map<String, Variant<String>>)
  public fun GetRawNDEF(): ByteArray
}
