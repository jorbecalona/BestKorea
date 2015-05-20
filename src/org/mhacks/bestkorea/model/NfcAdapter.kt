package org.mhacks.bestkorea.model

import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.exceptions.DBusExecutionException
import org.mhacks.bestkorea.NEARD
import org.mhacks.bestkorea.common.mapToStrings
import org.mhacks.bestkorea.model.NfcTag
import java.util.*
import kotlin.platform.platformStatic
import kotlin.reflect.KClass
import kotlin.reflect.jvm.java

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
class NfcAdapter(connection: DBusConnection,
                 objectPath: String = "/org/neard/nfc0",
                 type: KClass<IAdapter> = IAdapter::class,
                 iface: IAdapter = connection.getRemoteObject(NEARD, objectPath, type.java))
: DBusModel<IAdapter>(type, NfcAdapter.INTERFACE, connection, objectPath, iface = iface), IAdapter by iface {

  companion object {
    platformStatic val INTERFACE = "$NEARD.Adapter"
  }

  enum class PollMode {
    INITIATOR
    TARGET
    DUAL

    val name: String = name().toLowerCase() let {
      it.replaceRange(0..1, it[0].toUpperCase().toString())
    }
  }

  fun startPollLoop(mode: PollMode) = StartPollLoop(mode.name)
  fun stopPollLoop() = StopPollLoop()

  val mode: String get() = get("Mode")
  val polling: Boolean get() = get("Polling")
  var powered: Boolean
    get() = get("Powered")
    set(powered) = set("Powered", powered)
  val protocols: List<String> get() = get("Protocols")

  val tagPaths: List<String> get() = get<List<*>>("Tags").mapToStrings()
  val tags: List<NfcTag> get() = acquire(tagPaths, NfcTag)

}