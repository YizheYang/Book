package com.github.book

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.2 上午 11:03
 * version 1.0
 * update none
 **/
object Constant {
    private const val ip = "http://47.106.89.121:8080"
    const val login = "$ip/login"
    const val search = "$ip/search"
    const val reserve = "$ip/reserve"
    const val searchWithUser = "$search/order"
    const val unsubscribe = "$ip/unsubscribe"
    const val expassword = "$ip/expassword"
    const val register = "$ip/register"
    const val exusername = "$ip/exusername"
    const val searchFloor = "$ip/searchFloor"
    const val searchArea = "$ip/searchArea"

    val colorList = listOf(
        R.color.peerAvatarRedTop, R.color.peerAvatarRedBottom,
        R.color.peerAvatarOrangeTop, R.color.peerAvatarOrangeBottom,
        R.color.peerAvatarVioletTop, R.color.peerAvatarVioletBottom,
        R.color.peerAvatarGreenTop, R.color.peerAvatarGreenBottom,
        R.color.peerAvatarCyanTop, R.color.peerAvatarCyanBottom,
        R.color.peerAvatarBlueTop, R.color.peerAvatarBlueBottom,
        R.color.peerAvatarPinkTop, R.color.peerAvatarPinkBottom
    )

    fun String.format8date(): String {
        return this.let {
            it.substring(0, 4) + "." + it.substring(4, 6) + "." + it.substring(6, 8)
        }
    }

    fun String.format12date(): String {
        return this.let {
            it.substring(0, 4) + "." +
                    it.substring(4, 6) + "." +
                    it.substring(6, 8) + "." +
                    it.substring(8, 10) + ":" +
                    it.substring(10, 12)
        }
    }

    fun List<Array<Long>>.mergeTime(): List<Array<Long>> {
        var i = 1
        val tempList = mutableListOf<Array<Long>>()
        while (i < size) {
            if (tempList.isEmpty()) {
                tempList.add(this[0])
                continue
            }
            if (tempList.last()[1] == this[i][0]) {
                tempList.add(arrayOf(tempList.last()[0], this[i][1]))
                tempList.removeAt(tempList.size - 2)
            } else {
                tempList.add(this[i])
            }
            i++
        }
        return tempList
    }
}

const val TAG = "TAG"