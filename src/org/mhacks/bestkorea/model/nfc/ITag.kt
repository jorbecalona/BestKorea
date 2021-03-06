package org.mhacks.bestkorea.model.nfc

import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.DBusInterfaceName
import org.freedesktop.dbus.Variant
import org.mhacks.bestkorea.model

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
DBusInterfaceName(NfcTag.INTERFACE) public interface ITag : DBusInterface {
  public fun Write(values: Map<String, Variant<String>>)
  public fun GetRawNDEF(): ByteArray
}