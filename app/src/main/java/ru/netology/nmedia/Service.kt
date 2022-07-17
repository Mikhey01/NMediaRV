//package ru.netology.nmedia
//
//class Service {
//    //private val service : Service = Service()
//
//    fun likesCounters(i: UInt): String {
//        val count = when {
//
//            i < 1000U -> i.toString()
//            i < 10000U -> "${i / 1000U}.${i / 100U % 10U} K"
//            i < 100000U -> "${i / 10000U} K"
//            i < 1000000U -> "${i / 100000U}.${i / 10000U % 10U} M"
//            i < 10000000U -> "${i / 1000000U} M"
//            else -> {
//                "i.toString()"
//            }
//        }
//        return count
//    }
//
//
//}