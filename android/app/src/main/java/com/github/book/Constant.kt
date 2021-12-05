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
}