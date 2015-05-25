package org.mhacks.bestkorea

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.DBusInterfaceName
import org.freedesktop.dbus.Variant
import org.freedesktop.dbus.exceptions.DBusExecutionException
import org.freedesktop.dbus.types.DBusListType
import org.mhacks.bestkorea.api.ApiService
import org.mhacks.bestkorea.common.asAddress
import org.mhacks.bestkorea.common.hardwareAddress
import org.mhacks.bestkorea.common.networkInterface
import org.mhacks.bestkorea.model.*
import org.mhacks.bestkorea.serialization.KotlinTypeSerializer
import retrofit.RestAdapter
import retrofit.converter.GsonConverter
import java.net.InetAddress
import java.net.NetworkInterface
import java.nio.charset.Charset
import java.util.*

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 08/05/15
 * (C) 2015 Damian Wieczorek
 */

/**
* Neard package namespace
*/
val NEARD = "org.neard"

fun main(args: Array<String>) {
  val interfaces = NetworkInterface.getNetworkInterfaces().asSequence()
  val mac = interfaces.firstOrNull()!!.hardwareAddress.asAddress()
  println("MAC Address: $mac")

  val conn = DBusConnection.getConnection(DBusConnection.SYSTEM)
  val adapter = conn.adapter
  println("Powering...")
  adapter.powered = true
  println("Creating API service...")
  val service = RestAdapter.Builder()
    .setEndpoint("http://rho:3000/")
    .setConverter(GsonConverter(GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(javaClass<NfcTag>(), KotlinTypeSerializer())
      .registerTypeAdapter(javaClass<NfcRecord>(), KotlinTypeSerializer())
      .create()))
    .build()
    .create(javaClass<ApiService>())
  println("Ready!")
  while (true) {
    if (!adapter.polling) adapter.startPollLoop(NfcAdapter.PollMode.DUAL)
    val foo = adapter.tags
    if (foo.isNotEmpty())  {
      try {
        val tag = foo.firstOrNull()
        if (tag != null) {
          service.createEvent(Event(mac, tag))
        }
        println(tag)
      } catch (error: DBusExecutionException) {
        println("Oops, couldn't read :(")
      }
    }
  }
}