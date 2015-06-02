package org.mhacks.bestkorea.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.keysolutions.ddpclient.DDPClient
import com.keysolutions.ddpclient.DDPListener
import org.mhacks.bestkorea.model.nfc.DBusModel
import org.mhacks.bestkorea.model.remote.Event
import org.mhacks.bestkorea.model.nfc.NfcRecord
import org.mhacks.bestkorea.model.nfc.NfcTag
import org.mhacks.bestkorea.model.remote.RemoteModel
import org.mhacks.bestkorea.serialization.KotlinTypeSerializer
import kotlin.reflect.jvm.kotlin
import kotlin.properties.Delegates

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 25/05/15
 * (C) 2015 Damian Wieczorek
 */
class DdpApi(val mac: String) {

  enum class Action {
    NONE,
    REGISTRATION
  }

  val ddp = DDPClient("rho", 3000, GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(javaClass<NfcTag>(), KotlinTypeSerializer())
      .registerTypeAdapter(javaClass<NfcRecord>(), KotlinTypeSerializer())
      .create())

  var action: Action by Delegates.notNull()

  init {
    ddp.connect()
    ddp.addObserver { observable, result ->
      println("Observed ${observable.toString()}, ${result.toString()}, (${result.javaClass})")
      if (result is Map<*, *>) {
        when (result["msg"]) {
          "connected" -> {
            println("Connected!")
            ddp.subscribe("device_details_by_mac", arrayOf(mac));
          }
          "added" -> {
            if (result["collection"] == "device_details_by_mac") result["fields"] let { fields ->
              val added = ((fields as Map<*, *>)["diff"] as Map<*, *>)["added"] as List<*>? ?: arrayListOf<Any?>();
              if (added.isNotEmpty()) {
                action = when ((((added[0] as Map<*, *>)["device_action"] as Map<*, *>)["id"] as Double).toInt()) {
                  1 -> Action.NONE
                  2 -> Action.REGISTRATION
                  else -> Action.NONE
                }
                println("Action: $action")
              }
            }
          }
        }
        if (result.get("msg") == "connected") {
          println("Connected!")

        }
      }
    }
  }

  fun insert(model: RemoteModel) = ddp.collectionInsert(model.collection, serialize(model))
  fun subscribe(collection: String, params: Array<*> = arrayOf<Any?>(), onUpdated: (String) -> Unit) = ddp.subscribe(collection, params, object: DDPListener() {
    override fun onUpdated(callId: String) = onUpdated(callId)
  })
  fun addTapEvent(event: Event) = ddp.call("addTapEvent", arrayOf(serialize(event)))

  fun serialize(source: Any): Map<String, *> = mapOf(*source.javaClass.kotlin.properties.mapNotNull {
    it.name to it.get(source)
  }.toTypedArray())
}