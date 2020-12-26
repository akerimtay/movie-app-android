package com.akerimtay.movieapp.data.network.mapping

import com.google.gson.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateSerializer : JsonSerializer<Date>, JsonDeserializer<Date?> {
    override fun serialize(
        src: Date?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        if (src != null) {
            return JsonPrimitive(simpleDateFormat.format(src))
        }
        return JsonNull.INSTANCE
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        if (json != null) {
            val value = json.asString
            if (!value.isNullOrEmpty()) {
                return simpleDateFormat.parse(value)
            }
        }
        return null
    }

    companion object {
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }
}