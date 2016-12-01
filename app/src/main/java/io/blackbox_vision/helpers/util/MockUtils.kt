package io.blackbox_vision.helpers.util

import android.os.Bundle

import java.util.HashMap

object MockUtils {
    val SAMPLE = "SAMPLE"
    private val map = HashMap<String, Bundle>()
    private val bundle = Bundle()

    //This doesn't make sense at all, it's for educational purpose
    fun getMockedData(id: String): Bundle {
        bundle.putString(SAMPLE, "HELLO MVP!!")
        map.put(SAMPLE, bundle)
        return map[id]!!
    }
}
