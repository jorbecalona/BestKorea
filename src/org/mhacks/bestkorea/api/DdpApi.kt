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

  fun insert(model: RemoteModel) = ddp.collectionInsert(model.collection, serialize(model))
  fun subscribe(collection: String, params: Array<*> = arrayOf<Any?>(), onUpdated: (String) -> Unit) = ddp.subscribe(collection, params, object: DDPListener() {
    override fun onUpdated(callId: String) = onUpdated(callId)
  })
  fun addTapEvent(event: Event) = ddp.call("addTapEvent", arrayOf(serialize(event)))

  fun serialize(source: Any): Map<String, *> = mapOf(*source.javaClass.kotlin.properties.mapNotNull {
    it.name to it.get(source)
  }.toTypedArray())
}