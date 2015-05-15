package org.mhacks.bestkorea

import org.freedesktop.dbus.DBusConnection

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
val DBusConnection.adapter: NfcAdapter get() = NfcAdapter(this, "/org/neard/nfc0")
