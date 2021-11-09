package com.java.flightscheduler

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun seatMapLayoutTest(){
        val seats = arrayListOf<String>()
        seats.add("1A");seats.add("1B");seats.add("1C");seats.add("1D");seats.add("1E");seats.add("1F")
        seats.add("2A");seats.add("2B");seats.add("2C");seats.add("2D");seats.add("2E");seats.add("2F");
        seats.add("3A");seats.add("3B");seats.add("3C");seats.add("3D");seats.add("3E");seats.add("3F");

        val indexList = mutableListOf<Int>()
        when {
            seats.size % 6 == 0 -> {//medium deck - contains size / 6 aisle
                seats.forEachIndexed { index, element ->
                    if (element.contains("C"))
                        indexList.add(index + 1)
                }
                indexList.sortedDescending().forEach { element ->
                    seats.add(element ," ")
                }
            }
        }
        for (i in 0..seats.size) {
            if (i % 7 == 0) print("\n")
            print(seats[i]+ " ")
        }
        assert(seats.isNotEmpty())
    }
}
