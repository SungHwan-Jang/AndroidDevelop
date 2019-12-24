package com.example.besfirmtestapp.Utils

import java.util.zip.CRC32

class ArrayUtil {
    fun extractBytes(data: ByteArray, start: Int, length: Int): ByteArray {
        val bytes = ByteArray(length)
        System.arraycopy(data, start, bytes, 0, length)
        return bytes
    }

    fun isEqual(array_1: ByteArray?, array_2: ByteArray?): Boolean {
        if (array_1 == null) {
            return array_2 == null
        }
        if (array_2 == null) {
            return false
        }
        if (array_1 == array_2) {
            return true
        }
        if (array_1.size != array_2.size) {
            return false
        }
        for (i in array_1.indices) {
            if (array_1[i] != array_2[i]) {
                return false
            }
        }
        return true
    }

    fun contains(parent: ByteArray?, child: ByteArray?): Boolean {
        if (parent == null) {
            return child == null
        }
        if (child == null || child.size == 0) {
            return true
        }
        return if (parent == child) {
            true
        } else String(parent).contains(String(child))
    }

    fun crc32(data: ByteArray, offset: Int, length: Int): Long {
        val crc32 = CRC32()
        crc32.update(data, offset, length)
        return crc32.value
    }

//    fun checkSum(data: ByteArray, len: Int): Byte {
//        var sum = 0.toByte()
//        for (i in 0 until len) {
//            sum = sum xor data[i]
//        }
//        return sum
//    }

    fun toHex(data: ByteArray): String {
        val buffer = StringBuffer()
        for (i in data.indices) {
            buffer.append(String.format("%02x", data[i])).append(",")
        }
        return buffer.toString()
    }

    fun toASCII(data: ByteArray): String {
        val buffer = StringBuffer()
        for (i in data.indices) {
            buffer.append(data[i].toChar())
        }
        return buffer.toString()
    }


    fun startsWith(data: ByteArray?, param: ByteArray?): Boolean {
        if (data == null) {
            return param == null
        }
        if (param == null) {
            return true
        }
        if (data.size < param.size) {
            return false
        }
        for (i in param.indices) {
            if (data[i] != param[i]) {
                return false
            }
        }
        return true
    }
}