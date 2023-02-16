package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.main.MainListAdapter

//  Binding Adapter for PictureOfDAy
@BindingAdapter("imageUrl")
fun bindPictureOfDay(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    if (pictureOfDay?.mediaType == "image") {
        Picasso.get()
            .load(pictureOfDay.url)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_connection_error)
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.ic_broken_image)
    }
}

//  Binding Adapter for Asteroid List Item
@BindingAdapter("asteroidListData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Asteroid>?
) {
    val adapter = recyclerView.adapter as MainListAdapter
    adapter.submitList(data)
}

//  statusIcon for fragment_main
@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("statusIconContentDescription")
fun bindAsteroidStatusImageToDescription(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("statusImageContentDescription")
fun bindDetailStatusImageToDescription(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
