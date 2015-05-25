package org.mhacks.bestkorea.serialization

import com.google.gson.*
import com.google.gson.annotations.Expose
import org.freedesktop.dbus.exceptions.DBusExecutionException
import org.mhacks.bestkorea.model.NfcTag
import java.lang.reflect.Type
import kotlin.reflect
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.jvm.kotlin

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 25/05/15
 * (C) 2015 Damian Wieczorek
 */
class KotlinTypeSerializer : JsonSerializer<Any> {
  override fun serialize(source: Any, type: Type, context: JsonSerializationContext): JsonElement? = JsonObject() let { dest ->
    source.javaClass.kotlin.properties.forEach {
      val value = try {
        context.serialize(it.get(source))
      } catch (error: DBusExecutionException) { null }
      dest.add(it.name, value)
    }
    dest
  }
}
