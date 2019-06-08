package com.shojishunsuke.kibunnsns

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.atilika.kuromoji.Tokenizer
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sentiMap = HashMap<String, Int>()

        try {
            val file = this.assets.open("pnc.trim")
           val bufferReader = BufferedReader(InputStreamReader(file,"UTF-8"))

            var str = bufferReader.readLine()
            while (str != null) {
                var split: List<String> = str.split("\t")
//                System.out.println(split)
                if (split.size > 1) {
                    var emotion = split[1].trim()
                    var sentiScore = 0

                    if (emotion.equals("p")) {
                        sentiScore = 1
                    } else if (emotion.equals("n")) {
                        sentiScore = -1
                    }

                    sentiMap.put(split[0].trim(), sentiScore)
                }
                str = bufferReader.readLine()
            }



            bufferReader.close()

        } catch (e: Exception) {
            e.stackTrace
        }


//        val sentiMap = HashMap<String, Int>()
//
//        var str = bufferReader.readLine()
//        while (str != null) {
//            var split: List<String> = str.split("¥t")
//            if (split.size > 1) {
//                var emotion = split[1].trim()
//                var sentiScore = 0
//
//                if (emotion.equals("p")) {
//                    sentiScore = 1
//                } else if (emotion.equals("n")) {
//                    sentiScore = -1
//                }
//
//                sentiMap.put(split[0].trim(), sentiScore)
//            }
//            str = bufferReader.readLine()
//        }
//
//
//
//        bufferReader.close()


        val tokenizer = Tokenizer.builder().build()
        val tokens = tokenizer.tokenize("めっちゃ楽しみ")

        var score = 0

        for (token in tokens) {
            var surface = token.surfaceForm

            if (sentiMap.containsKey(surface)) {
                score += sentiMap.get(surface)!!
            }
        }

        System.out.println(score)
    }
}

fun main(args: Array<String>) {
    val file =
        File("/Users/shojishunsuke/AndroidStudioProjects/KibunnSns/app/src/main/java/assets/pn.csv.m3.120408.trim")
    val bufferReader = BufferedReader(FileReader(file))

    val sentiMap = HashMap<String, Int>()

    var str = bufferReader.readLine()
    while (str != null) {
        var split: List<String> = str.split("¥t")
        if (split.size > 1) {
            var emotion = split[1].trim()
            var sentiScore = 0

            if (emotion.equals("p")) {
                sentiScore = 1
            } else if (emotion.equals("n")) {
                sentiScore = -1
            }

            sentiMap.put(split[0].trim(), sentiScore)
        }
        str = bufferReader.readLine()
    }

    bufferReader.close()


    val tokenizer = Tokenizer.builder().build()
    val tokens = tokenizer.tokenize("とても楽しい")

    var score = 0

    for (token in tokens) {
        var surface = token.surfaceForm

        if (sentiMap.containsKey(surface)) {
            score += sentiMap.get(surface)!!
        }
    }

    System.out.println(score)
}
