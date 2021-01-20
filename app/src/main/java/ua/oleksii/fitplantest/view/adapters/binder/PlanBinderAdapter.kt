package ua.oleksii.fitplantest.view.adapters.binder

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.GenericTransitionOptions
import ua.oleksii.fitplantest.R
import ua.oleksii.fitplantest.utils.glide.GlideApp

/**
 * Created by AlexLampa on 12.09.2019.
 */

@BindingAdapter("imageUrl", "needCenterCrop")
fun ImageView.setImageUrl(imageUrl: String?, needCenterCrop: Boolean? = true) {
    var glideRequest = GlideApp.with(this.context)
        .load(imageUrl)
        .transition(GenericTransitionOptions.with(R.anim.animation_appear))
        .placeholder(R.color.colorPrimary)
        .error(R.color.colorPrimary)
    glideRequest = if (needCenterCrop == true) {
        glideRequest.centerCrop()
    } else {
        glideRequest.fitCenter()
    }
    glideRequest.into(this)
}

@BindingAdapter("planItemImageUrl", "needCenterCrop", "needShowImage")
fun ImageView.setPlanListImageUrl(imageUrl: String?, needCenterCrop: Boolean? = true, needShowImage: Boolean? = true) {
    if (needShowImage == true && !imageUrl.isNullOrEmpty()) {
        this.setImageUrl(imageUrl, needCenterCrop)
        this.visibility = View.VISIBLE
    } else {
        this.setImageDrawable(null)
        this.visibility = View.GONE
    }
}

@BindingAdapter("app:setImage")
fun ImageView.setImage(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        this.setImageUrl(imageUrl)
        this.visibility = View.VISIBLE
    } else {
        this.setImageDrawable(null)
        this.visibility = View.GONE
    }
}