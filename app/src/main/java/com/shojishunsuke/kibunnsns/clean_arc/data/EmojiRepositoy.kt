package com.shojishunsuke.kibunnsns.clean_arc.data


class EmojiRepositoy {
    val uniCodeList = listOf(

        "\uD83C\uDF74",
        "\uD83D\uDED2",
        "✍️",
        "☕",
        "\uD83D\uDCBB",
        "\uD83D\uDCFA",
        "\uD83D\uDCD6",
        "\uD83C\uDFAE",
        "\uD83C\uDF82",
        "\uD83D\uDC8C",
        "\uD83C\uDF81",
        "\uD83C\uDFA8",
        "\uD83D\uDE97",
        "✈️",
//        音楽
        "\uD83C\uDFBC",
        "\uD83C\uDFA4",
        "\uD83C\uDFB9",
//        アウトドア、スポーツ
        "\uD83C\uDFD5️",
        "\uD83C\uDFD6️",
        "\uD83D\uDEB4",
        "\u26BD",
        "\u26be",
        "\uD83C\uDFC0",
//        アワード
        "\uD83D\uDCB0",
        "\uD83C\uDFC6" ,
        "\uD83D\uDC87\uD83C\uDFFB"
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
}