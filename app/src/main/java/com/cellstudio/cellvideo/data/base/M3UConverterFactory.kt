package com.cellstudio.cellvideo.data.base

import com.cellstudio.cellvideo.data.entities.m3u.general.M3UItem
import com.cellstudio.cellvideo.data.parser.m3u.M3UParser
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * A [converter][Converter.Factory] which uses Gson for JSON.
 *
 *
 * Because Gson is so flexible in the types it supports, this converter assumes that it can handle
 * all types. If you are mixing JSON serialization with something else (such as protocol buffers),
 * you must [add this instance][Retrofit.Builder.addConverterFactory]
 * last to allow the other converters a chance to see their types.
 */
class M3UConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, MutableList<M3UItem>>? {
        return Converter<ResponseBody, MutableList<M3UItem>> { value ->
            val responseString = value.byteStream()
            val cx = M3UParser.parse(responseString)
            cx
        }
    }
}
