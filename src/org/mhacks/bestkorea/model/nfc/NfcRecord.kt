package org.mhacks.bestkorea.model.nfc

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

  val encoding: String by readOnlyProperty("Encoding")
  val language: String by readOnlyProperty("Language")
  val representation: String by readOnlyProperty("Representation")
  val uri: String by readOnlyProperty("URI")
  val mimeType: String by readOnlyProperty("MIMEType")
  val size: Int by readOnlyProperty("Size")
  val action: String by readOnlyProperty("Action")
  val androidPackage: String by readOnlyProperty("AndroidPackage")

}