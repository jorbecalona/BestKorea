package org.mhacks.bestkorea

import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.DBusSigHandler
import org.freedesktop.dbus.DBusSignal
import org.mhacks.bestkorea.model.nfc.IAdapter
import org.mhacks.bestkorea.model.nfc.ITag
import org.mhacks.bestkorea.model.nfc.NfcAdapter
import org.mhacks.bestkorea.model.nfc.NfcTag
import kotlin.reflect.KClass
import kotlin.reflect.jvm.java

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
val DBusConnection.adapter: NfcAdapter get() = NfcAdapter(this, "/org/neard/nfc0")

fun <T : DBusSignal> toSigHandler(body: (T) -> Unit) = object : DBusSigHandler<T> {
  override fun handle(p0: T) {
    body(p0)
  }
}

fun <T : DBusSignal> DBusConnection.addSigHandler(type: KClass<T>, body: (T) -> Unit) = addSigHandler(type.java, toSigHandler(body))
fun DBusConnection.onTagFound(body: (NfcTag) -> Unit) = addSigHandler(IAdapter.TagFound::class) { signal ->
  body(NfcTag(this))
}

fun DBusConnection.onTagLost(body: (String) -> Unit) = addSigHandler(IAdapter.TagLost::class) { signal ->
  body(signal.address)
}