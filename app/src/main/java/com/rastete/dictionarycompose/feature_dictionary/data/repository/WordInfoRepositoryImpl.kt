package com.rastete.dictionarycompose.feature_dictionary.data.repository

import com.rastete.dictionarycompose.core.util.Resource
import com.rastete.dictionarycompose.feature_dictionary.data.local.WordInfoDao
import com.rastete.dictionarycompose.feature_dictionary.data.remote.DictionaryApi
import com.rastete.dictionarycompose.feature_dictionary.domain.model.WordInfo
import com.rastete.dictionarycompose.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfoList = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfoList))

        try {
            val remoteDefinitions = api.getDefinition(word)
            dao.deleteWordInfos(remoteDefinitions.map { it.word })
            dao.insertWordInfo(remoteDefinitions.map { it.toWordInfoEntity() })
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Opps! something went wrong!", data = wordInfoList))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check ypur internet connection", data = wordInfoList))
        }

        val newWordInfoList = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfoList))
    }
}