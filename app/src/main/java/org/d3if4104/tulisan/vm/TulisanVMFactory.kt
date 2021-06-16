import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if4104.tulisan.db.TulisanDAO
import org.d3if4104.tulisan.vm.TulisanVM

class TulisanVMFactory(
    private val dataSource: TulisanDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TulisanVM::class.java)) {
            return TulisanVM(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}