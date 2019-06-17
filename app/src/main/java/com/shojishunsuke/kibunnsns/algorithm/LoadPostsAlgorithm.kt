package com.shojishunsuke.kibunnsns.algorithm

import com.shojishunsuke.kibunnsns.clean_arc.utils.bd
import java.math.BigDecimal
import kotlin.random.Random

class LoadPostsAlgorithm {


    fun rangedRandom(sentiScore: Float): Float {



        var index = when {
            1.0 >= sentiScore && sentiScore > 0.5 -> {
                Random.nextInt(3, 10)
            }
            0.5 >= sentiScore && sentiScore >= 0.0 -> {
                Random.nextInt(-3, 8)
            }
            0.0 > sentiScore && sentiScore >= -0.5 -> {
                Random.nextInt(-6, 3)
            }
            -0.5 > sentiScore && sentiScore >= -1.0 -> {
                Random.nextInt(-10, -1)
            }
            else -> {
                0
            }
        }

        return BigDecimal(index).divide(BigDecimal(10)).toFloat()
    }


}