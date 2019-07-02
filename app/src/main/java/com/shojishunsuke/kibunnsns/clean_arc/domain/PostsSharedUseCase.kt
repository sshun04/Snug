package com.shojishunsuke.kibunnsns.clean_arc.domain


import com.shojishunsuke.kibunnsns.algorithm.LoadPostsAlgorithm
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.RoomDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataConfigRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class PostsSharedUseCase(private val analysisRepository: LanguageAnalysisRepository,private val dataConfigRepository: DataConfigRepository) {

    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val roomRepository = RoomDatabaseRepository()
    private val postsLoadingAlgorithm = LoadPostsAlgorithm()

    suspend fun generatePost(content: String,actID:String): Post = runBlocking {
        dataConfigRepository.updateCollection(actID)


        val analysisResult = analysisRepository.analyzeText(content)
        val sentiScore = analysisResult.first
        val category = analysisResult.second
        val post = Post(contentText = content,sentiScore =  sentiScore, actID = actID,keyWord = category)

        runBlocking {
            fireStoreRepository.savePost(post)
            roomRepository.savePost(post)
        }

        return@runBlocking post
    }

    suspend fun loadWholePosts():List<Post> = runBlocking {
        fireStoreRepository.loadWholeCollection()
    }

    suspend fun loadRelatedPosts(post: Post): List<Post> {
//        TODO 関連した投稿を取得するアルゴリズムを実装

        return fireStoreRepository.loadFilteredCollection("sentiScore", post.sentiScore)
    }

    fun loadCurrentEmoji():List<String> = dataConfigRepository.getLatestCollection()

    fun formatDate(postedDate: Date): String {
        val currentDate = Date()
        val timeDiffInSec = (currentDate.time - postedDate.time) / 1000

        val hourDiff = timeDiffInSec / 3600
        val minuteDiff = (timeDiffInSec % 3600) / 60
        val secDiff = timeDiffInSec % 60

        val outPutText = when {
            timeDiffInSec in 3600 * 24 until 3600 * 48 -> {
                "昨日"
            }
            timeDiffInSec in 3600 until 3600 * 24 -> {
                "$hourDiff" + "時間前"
            }
            timeDiffInSec in 360 until 3600 -> {
                "$minuteDiff" + "分前"
            }
            timeDiffInSec < 360 -> {
                "$secDiff" + "秒前"
            }
            else -> {
                val formatter = SimpleDateFormat("MM月dd日", Locale.JAPAN)
                formatter.format(postedDate)
            }

        }

        return outPutText

    }


}