package ua.oleksii.fitplantest.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.net.ConnectivityManager
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import ua.oleksii.fitplantest.R

/**
 * Created by AlexLampa on 13.08.2019.
 */
fun Activity.hideKeyBoard(): Unit {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}

fun Activity.showKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Activity.getScreenWidth() = this.resources.displayMetrics.widthPixels

fun Activity.getScreenHeight() = this.resources.displayMetrics.heightPixels

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, "" + text, duration).show()
}

fun Context.generateProgressDialog(): MaterialDialog =
    MaterialDialog(this)
        .cornerRadius(literalDp = 8.0f)
        .cancelable(false)
        .cancelOnTouchOutside(false)
        .customView(viewRes = R.layout.progress_dialog_view)

fun Context.showMessageDialog(
    message: String,
    okClickListener: View.OnClickListener? = null
) {
    MaterialDialog(this).show {
        cornerRadius(literalDp = 8.0f)
        cancelable(false)
        cancelOnTouchOutside(false)
        message(text = message) {
            messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        }
        positiveButton(res = R.string.ok, click = {
            it.dismiss()
            okClickListener?.onClick(null)
        })
    }
}