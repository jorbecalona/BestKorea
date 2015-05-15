package org.mhacks.bestkorea

import org.freedesktop.dbus.DBusConnection
import java.util.*

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
class NfcTag(connection: DBusConnection, objectPath: String = "/org/neard/nfc0/tag0") : DBusModel("$NEARD.Tag", connection, objectPath) {
  val protocol: String get() = get("Protocol")
  val readOnly: Boolean get() = get("ReadOnly")
  val adapterPath: String get() = get<Any>("Adapter").toString()
  val adapter: NfcAdapter get() = NfcAdapter(connection, adapterPath)
}