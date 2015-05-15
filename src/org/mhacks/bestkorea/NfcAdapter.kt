package org.mhacks.bestkorea

import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection
import java.util.*

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
class NfcAdapter(connection: DBusConnection, objectPath: String = "/org/neard/nfc0") : DBusModel("$NEARD.Adapter", connection, objectPath) {
  val mode: String get() = get("Mode")
  val polling: Boolean get() = get("Polling")
  val powered: Boolean get() = get("Powered")
  val protocols: List<String> get() = get("Protocols")
  val tagPaths: List<String> get() = get<Vector<*>>("Tags").mapNotNull { it.toString() }
  val tags: List<NfcTag> get() = tagPaths.mapNotNull { NfcTag(connection, it) }
}