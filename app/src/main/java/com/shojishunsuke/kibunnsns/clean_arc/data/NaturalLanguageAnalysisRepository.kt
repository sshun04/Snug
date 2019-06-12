package com.shojishunsuke.kibunnsns.clean_arc.data

import android.util.Log
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.services.language.v1.CloudNaturalLanguage
import com.google.api.services.language.v1.CloudNaturalLanguageRequestInitializer
import com.google.api.services.language.v1.model.AnnotateTextRequest
import com.google.api.services.language.v1.model.Document
import com.google.api.services.language.v1.model.Features
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LanguageAnalysisRepository
import kotlinx.coroutines.runBlocking


class NaturalLanguageAnalysisRepository(apiKey:String) : LanguageAnalysisRepository {

    private val naturalLanguageService: CloudNaturalLanguage

    init {
        naturalLanguageService = CloudNaturalLanguage.Builder(
            AndroidHttp.newCompatibleTransport(), AndroidJsonFactory(), null
        ).setCloudNaturalLanguageRequestInitializer(
            CloudNaturalLanguageRequestInitializer(apiKey)
        ).build()
    }

    override suspend fun getScore(text: String): Int = runBlocking {
        val document = Document()
        document.language = "ja_JP"
        document.type = "PLAIN_TEXT"
        document.content = text

        val features = Features()
        features.extractDocumentSentiment = true
        features.extractEntities = true

        val annotateTextRequest = AnnotateTextRequest()
        annotateTextRequest.document = document
        annotateTextRequest.features = features


        val response = naturalLanguageService.documents().annotateText(annotateTextRequest).execute()
        val sentiScore = response.documentSentiment.score.toInt()

        Log.d("SentiScore", "$sentiScore")

        sentiScore
    }
}