package com.lounah.vkmc.feature.image_viewer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.*
import androidx.appcompat.app.AppCompatActivity
import com.lounah.vkmc.core.extensions.getOrThrow
import com.lounah.vkmc.feature.image_viewer.R
import kotlinx.android.synthetic.main.activity_image_viewer.*

typealias Coordinates = Pair<Int, Int>

class ImageViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(FLAG_LAYOUT_NO_LIMITS)
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)
        intent.extras?.run {
            val images = getStringArrayList(ARG_IMAGES)
            val revealX = getInt(ARG_CLICK_COORDINATE_X)
            val revealY = getInt(ARG_CLICK_COORDINATE_Y)
            val startFrom = getInt(ARG_START_FROM)
            initUi(images.getOrThrow(), revealX, revealY, startFrom)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    private fun initUi(images: ArrayList<String>, revealX: Int, revealY: Int, startFrom: Int) {
        val uiHelper = ImageViewerUiHelper(this, revealX, revealY)
        val onMove = uiHelper::handleImageMove
        val onDismiss = uiHelper::handleImageDismiss
        val onClick = uiHelper::onImageClicked
        val adapter = ImageViewerViewPagerAdapter(images, this, onClick, onMove, onDismiss)
        viewPager.adapter = adapter
        viewPager.currentItem = startFrom
        back.setOnClickListener { finish() }
    }

    companion object {

        private const val ARG_IMAGES = "arg_images"
        private const val ARG_CLICK_COORDINATE_X = "arg_click_coordinate_x"
        private const val ARG_CLICK_COORDINATE_Y = "arg_click_coordinate_y"
        private const val ARG_START_FROM = "arg_start_from"

        fun start(
            context: Context,
            images: ArrayList<String>,
            startFrom: Int = 0,
            clickedOn: Coordinates = 0 to 0
        ) {
            Intent(context, ImageViewerActivity::class.java)
                .apply {
                    val bundle = Bundle().apply {
                        putStringArrayList(ARG_IMAGES, images)
                        putInt(ARG_CLICK_COORDINATE_X, clickedOn.first)
                        putInt(ARG_CLICK_COORDINATE_Y, clickedOn.second)
                        putInt(ARG_START_FROM, startFrom)
                    }
                    putExtras(bundle)
                }.also(context::startActivity)
        }
    }
}