package com.keydom.ih_common

import com.alibaba.fastjson.JSON
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//        println(3047811 / 3093117f)
        var set:LinkedHashSet<String>
        set = LinkedHashSet()
        set.add("1")
        set.add("2")
        set.add("3")
        val str = JSON.toJSONString(set)

        print(JSON.parseObject(str,Set::class.java))
    }
}
