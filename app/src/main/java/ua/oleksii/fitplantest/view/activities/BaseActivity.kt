package ua.oleksii.fitplantest.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.*
import ua.oleksii.fitplantest.R
import ua.oleksii.fitplantest.model.entities.RequestError
import ua.oleksii.fitplantest.utils.extensions.hideKeyBoard
import ua.oleksii.fitplantest.utils.extensions.showMessageDialog

abstract class BaseActivity : AppCompatActivity() {

    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }

    protected fun showErrorMessage(error : RequestError, okClickListener: View.OnClickListener? = null) {
        when (error.type) {
            RequestError.CONNECTION_ERROR -> showMessageDialog(getString(R.string.connection_error), okClickListener)
            RequestError.WRONG_CREDENTIALS_ERROR -> showMessageDialog(getString(R.string.wrong_creds), okClickListener)
            RequestError.REQUEST_ERROR -> showMessageDialog("${getString(R.string.request_error)}${if (error.code != null) " ${error.code}" else ""}", okClickListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyBoard()
        clearFindViewByIdCache()
        compositeDisposable.dispose()
    }

}