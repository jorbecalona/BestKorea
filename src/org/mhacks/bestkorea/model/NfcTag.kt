package org.mhacks.bestkorea.model

import org.freedesktop.dbus.DBusConnection
import org.mhacks.bestkorea.NEARD
import kotlin.platform.platformStatic
import kotlin.reflect.KClass
import kotlin.reflect.jvm.java

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
class NfcTag(connection: DBusConnection,
             objectPath: String = "/org/neard/nfc0/tag0",
             type: KClass<ITag> = ITag::class,
             iface: ITag = connection.getRemoteObject(NEARD, objectPath, type.java))
: DBusModel<ITag>(type, NfcTag.INTERFACE, connection, objectPath, iface = iface), ITag by iface {

  companion object {
    platformStatic val INTERFACE = "$NEARD.Tag"
  }

  val protocol: String get() = get("Protocol")
  val readOnly: Boolean get() = get("ReadOnly")
  val adapterPath: String get() = get<Any>("Adapter").toString()
  val adapter: NfcAdapter get() = NfcAdapter(connection, adapterPath)

}