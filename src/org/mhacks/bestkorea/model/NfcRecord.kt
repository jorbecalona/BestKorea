package org.mhacks.bestkorea.model

import com.google.gson.JsonObject
import org.freedesktop.dbus.DBusConnection
import org.mhacks.bestkorea.NEARD
import kotlin.platform.platformStatic
import kotlin.reflect.KClass
import kotlin.reflect.jvm.java

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 19/05/15
 * (C) 2015 Damian Wieczorek
 */
class NfcRecord(connection: DBusConnection,
                objectPath: String = "/org/neard/nfc0/tag0/record0",
                type: KClass<IRecord> = IRecord::class,
                iface: IRecord = connection.getRemoteObject(NEARD, objectPath, type.java))
: DBusModel<IRecord>(type, NfcRecord.INTERFACE, connection, objectPath, iface = iface), IRecord by iface {

  companion object: DBusModel.Factory<NfcRecord> {
    platformStatic val INTERFACE = "$NEARD.Record"
    override fun create(connection: DBusConnection, path: String) = NfcRecord(connection, path)
  }

  val encoding: String get() = get("Encoding")
  val language: String get() = get("Language")
  val representation: String get() = get("Representation")
  val uri: String get() = get("URI")
  val mimeType: String get() = get("MIMEType")
  val size: Int get() = get("Size")
  val action: String get() = get("Action")
  val androidPackage: String get() = get("AndroidPackage")

}