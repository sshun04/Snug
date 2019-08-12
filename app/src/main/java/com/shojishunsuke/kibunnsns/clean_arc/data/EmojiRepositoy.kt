package com.shojishunsuke.kibunnsns.clean_arc.data


class EmojiRepositoy {
    val uniCodeList = listOf(
//        生活


//        スポーツ
        "\u26BD",
        "\u26be",
        "\uD83C\uDFC0",
        "\uD83C\uDFD0",
        "\uD83C\uDFF8",
//        音楽
        "\uD83C\uDFB5",
        "\uD83C\uDFA4",
        "\uD83C\uDFB9",
//        アウトドア
        "\uD83C\uDFD5️",
        "\uD83C\uDFD6️",
        "\uD83D\uDEB4",
        "\uD83D\uDE97",

//        イベント
        "\uD83C\uDF82",
        "\uD83C\uDF81",
        "\uD83D\uDC87\uD83C\uDFFB",

//        トラベル
        "\uD83C\uDF74",
        "✈️",
        "\uD83D\uDCD6",

//        芸術
        "\uD83C\uDFA8",
        "\uD83D\uDC8C",

//        アワード
        "\uD83D\uDCB0",
        "\uD83C\uDFC6"
//        "\",
//        "\",
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

    fun loadCurrentEmoji(): List<String> {
        return currentUniCodeList
    }
}