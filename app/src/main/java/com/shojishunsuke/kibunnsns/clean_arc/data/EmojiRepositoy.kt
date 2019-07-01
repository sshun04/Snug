package com.shojishunsuke.kibunnsns.clean_arc.data

import com.shojishunsuke.kibunnsns.clean_arc.data.repository.ActivityRepository

class EmojiRepositoy : ActivityRepository {
    val uniCodeList = listOf(
//        スポーツ
        "\u26BD",
        "\u26be",
        "\uD83C\uDFC0",
        "\uD83C\uDFD0",
        "\uD83C\uDFC8",
        "\uD83C\uDFC9",
        "\uD83C\uDFBE",
        "\uD83C\uDFD3",
        "\uD83C\uDFF8",
        "\uD83E\uDD4D",
//        音楽
        "\uD83C\uDFB5",
        "\uD83C\uDFA4",

//        アウトドア
        "\uD83C\uDFD5️",
        "\uD83C\uDFD6️",
        "\uD83D\uDEB4",
        "\uD83D\uDE97",

//        イベント
        "\uD83C\uDF82",
        "\uD83C\uDF81",
        "\uD83C\uDF74",
        "\uD83D\uDC87\uD83C\uDFFB",


//        場所

//        芸術
        "\uD83C\uDFA8",
//        "\",
//        "\",
        "\uD83D\uDC8C"
    )
    val currentUniCodeList = listOf(
        "\uD83C\uDFC0",
        "\uD83C\uDFD0",
        "\uD83C\uDFB5",
        "\uD83D\uDECD️",
        "\uD83D\uDEB4",
        "\uD83D\uDE97"
    )

    val smileys = listOf(

//        smiling
        "\uD83D\uDE00",
//        slightly smiling
        "\uD83D\uDE42",
//        neutral
        "\uD83D\uDE10",
//        slightly frown
        "\uD83D\uDE41",
//        frown
        "☹️"
    )


    fun loadWholeEmoji(): List<String> {
        return uniCodeList
    }

    fun loadCurrentEmoji():List<String>{
        return currentUniCodeList
    }
}