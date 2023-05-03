package net.updategames.gra.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

@SuppressLint("ClickableViewAccessibility")
class DraggableImageView(context: Context, attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {

    private var _xPos = 0
    private var _yPos = 0
    private var _lastTouchX = 0f
    private var _lastTouchY = 0f

    init {
        this.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    _lastTouchX = event.rawX
                    _lastTouchY = event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = (event.rawX - _lastTouchX).toInt()
                    val dy = (event.rawY - _lastTouchY).toInt()
                    _xPos += dx
                    _yPos += dy
                    this.layout(_xPos, _yPos, _xPos + this.width, _yPos + this.height)
                    _lastTouchX = event.rawX
                    _lastTouchY = event.rawY
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}
