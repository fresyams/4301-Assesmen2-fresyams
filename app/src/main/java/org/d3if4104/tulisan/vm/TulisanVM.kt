package org.d3if4104.tulisan.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import org.d3if4104.tulisan.db.Tulisan
import org.d3if4104.tulisan.db.TulisanDAO

class TulisanVM (

    val database: TulisanDAO,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val tulisan = database.getDiary()

    fun onClickInsert(message: String) {
        uiScope.launch {
            val tulisan = Tulisan(0,message)

            insert(tulisan)
        }
    }


    private suspend fun insert(tulisan: Tulisan) {
        withContext(Dispatchers.IO) {
            database.insert(tulisan)
        }
    }

    fun onClickClear() {
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onClickUpdate(tulisan: Tulisan) {
        uiScope.launch {
            update(tulisan)
        }
    }

    private suspend fun update(tulisan: Tulisan) {
        withContext(Dispatchers.IO) {
            database.update(tulisan)
        }
    }

    fun onClickDelete(diaryId: Long) {
        uiScope.launch {
            delete(diaryId)
        }
    }

    private suspend fun delete(diaryId: Long) {
        withContext(Dispatchers.IO) {
            database.delete(diaryId)
        }
    }

}