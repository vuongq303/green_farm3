package com.quanhoang.greenfarm3.S800x480

import android.os.SystemClock

/** Original ProfilerEvent timing and bounded event history. */
open class bl(displayName: String?) : Comparable<Any?> {
    private var startedAtMillis = nowMillis()
    private var stoppedAtMillis = 0L
    private var minimumElapsedMillis = 0L
    private var totalElapsedMillis = 0L
    private val groupEventCounts = ArrayList<Int>(w.Zq)
    private val durationsMillis = ArrayList<Long>(w.Zq)
    private var eventCount = 1
    private val displayName = displayName
    private var maximumElapsedMillis = -1L
    private var groupIndex = -1

    open fun nz() {
        groupIndex++
        if (groupIndex >= w.Zq) { groupEventCounts.removeAt(0); groupIndex-- }
        groupEventCounts.add(0)
    }
    open fun nA() {
        if (startedAtMillis != 0L) {
            if (stoppedAtMillis == 0L) stoppedAtMillis = nowMillis()
            recordElapsed(stoppedAtMillis - startedAtMillis)
            eventCount++
            if (groupIndex >= 0) groupEventCounts[groupIndex] = groupEventCounts[groupIndex] + 1
        }
        startedAtMillis = nowMillis()
        stoppedAtMillis = 0L
    }
    private fun recordElapsed(elapsedMillis: Long) {
        if (maximumElapsedMillis == -1L || elapsedMillis > maximumElapsedMillis) maximumElapsedMillis = elapsedMillis
        if (elapsedMillis < minimumElapsedMillis) minimumElapsedMillis = elapsedMillis
        totalElapsedMillis += elapsedMillis
        if (durationsMillis.size >= w.Zq) durationsMillis.removeAt(0)
        durationsMillis.add(elapsedMillis)
    }
    open fun nB(): Long = totalElapsedMillis / eventCount.toLong()
    open fun getCount(): Int = eventCount
    open fun nC() {
        if (startedAtMillis != 0L) {
            if (stoppedAtMillis == 0L) stoppedAtMillis = nowMillis()
            recordElapsed(stoppedAtMillis - startedAtMillis)
            startedAtMillis = 0L
            eventCount++
            if (groupIndex >= 0) groupEventCounts[groupIndex] = groupEventCounts[groupIndex] + 1
        }
    }
    override fun equals(other: Any?): Boolean = if (other is bl) displayName!!.equals(other.displayName) else super.equals(other)
    // The original class deliberately inherits Object.hashCode despite overriding equals.
    override fun toString(): String {
        val prefix = "$displayName\nSum: $totalElapsedMillis Count:$eventCount\nMax: $maximumElapsedMillis Avg: ${nB()} Min: $minimumElapsedMillis\nReal: "
        val history = StringBuffer()
        for (index in durationsMillis.indices) {
            history.append(durationsMillis[index])
            if (index < durationsMillis.size - 1) history.append("; ")
        }
        if (groupEventCounts.isNotEmpty()) {
            history.append("\nNumber of events by groups: ")
            for (index in groupEventCounts.indices) {
                history.append(groupEventCounts[index])
                if (index < groupEventCounts.size - 1) history.append("; ")
            }
        }
        return prefix + history.toString()
    }
    override fun compareTo(other: Any?): Int = a(other as bl?)
    open fun a(other: bl?): Int = if (other == null) 1 else if (maximumElapsedMillis == other.maximumElapsedMillis) 0 else if (maximumElapsedMillis > other.maximumElapsedMillis) -1 else 1
    private fun nowMillis(): Long = if (w.Vl) (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To else System.currentTimeMillis()
}
