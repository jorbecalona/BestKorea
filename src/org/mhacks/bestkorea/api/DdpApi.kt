package org.mhacks.bestkorea.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.keysolutions.ddpclient.DDPClient
import com.keysolutions.ddpclient.DDPListener
import org.jetbrains.kotlin.utils.keysToMap
import org.mhacks.bestkorea.model.DBusModel
import org.mhacks.bestkorea.model.Event
import org.mhacks.bestkorea.model.NfcRecord
import org.mhacks.bestkorea.model.NfcTag
import org.mhacks.bestkorea.serialization.KotlinTypeSerializer
import kotlin.reflect.jvm.kotlin

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 25/05/15
 * (C) 2015 Damian Wieczorek
 */
class DdpApi {
  val ddp = DDPClient("rho", 3000, GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(javaClass<NfcTag>(), KotlinTypeSerializer())
      .registerTypeAdapter(javaClass<NfcRecord>(), KotlinTypeSerializer())
      .create())

  init {
    ddp.connect()
  }

  fun createEvent(event: Event) {
    println("Insert: ${ddp.collectionInsert("events", serialize(event), object: DDPListener() {
      override fun onReady(callId: String?) {
        println("Ready: $callId")
      }

      override fun onUpdated(callId: String?) {
        println("Updated: $callId")
      }

      override fun onNoSub(callId: String?, errorFields: MutableMap<String, Any>?) {
        println("NoSub: $callId, $errorFields")
      }

      override fun onPong(pingId: String?) {
        println("Pong: $pingId")
      }

      override fun onResult(resultFields: MutableMap<String, Any>?) {
        println("Result: $resultFields")
      }
    })}")
  }

  fun serialize(source: Any): Map<String, *> = mapOf(*source.javaClass.kotlin.properties.mapNotNull {
    it.name to it.get(source)
  }.toTypedArray())
}