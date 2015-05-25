package org.mhacks.bestkorea.api

import com.keysolutions.ddpclient.DDPClient
import org.jetbrains.kotlin.utils.keysToMap
import org.mhacks.bestkorea.model.Event
import kotlin.reflect.jvm.kotlin

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 25/05/15
 * (C) 2015 Damian Wieczorek
 */
class DdpApi {
  val ddp = DDPClient("rho", 3000)

  fun createEvent(event: Event) {
    println("Insert: ${ddp.collectionInsert("events", serialize(event))}")
  }

  fun serialize(source: Any): Map<String, *> = mapOf(*source.javaClass.kotlin.properties.mapNotNull {
    it.name to it.get(source)
  }.toTypedArray())
}