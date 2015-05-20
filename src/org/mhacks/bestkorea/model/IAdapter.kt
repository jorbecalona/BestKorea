package org.mhacks.bestkorea.model

import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.DBusInterfaceName
import org.freedesktop.dbus.DBusSignal

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 15/05/15
 * (C) 2015 Damian Wieczorek
 */
DBusInterfaceName(NfcAdapter.INTERFACE) public trait IAdapter : DBusInterface {
  public fun StartPollLoop(name: String)
  public fun StopPollLoop()

  public class TagFound(val objectPath: String, val address: String) : DBusSignal(objectPath, address)
  public class TagLost(val objectPath: String, val address: String) : DBusSignal(objectPath, address)
}