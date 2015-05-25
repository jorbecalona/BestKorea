package org.mhacks.bestkorea.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
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

  Expose val encoding: String by readOnlyProperty("Encoding")
  Expose val language: String by readOnlyProperty("Language")
  Expose val representation: String by readOnlyProperty("Representation")
  Expose val uri: String by readOnlyProperty("URI")
  Expose val mimeType: String by readOnlyProperty("MIMEType")
  Expose val size: Int by readOnlyProperty("Size")
  Expose val action: String by readOnlyProperty("Action")
  Expose val androidPackage: String by readOnlyProperty("AndroidPackage")

}