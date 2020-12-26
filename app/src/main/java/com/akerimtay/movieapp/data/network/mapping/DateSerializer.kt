package com.akerimtay.movieapp.data.network.mapping

import com.google.gson.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateSerializer : JsonSerializer<Date> {
    override fun serialize(
        src: Date?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        if (src != null) {
            return JsonPrimitive(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(src))
        }
        return JsonNull.INSTANCE
    }
}