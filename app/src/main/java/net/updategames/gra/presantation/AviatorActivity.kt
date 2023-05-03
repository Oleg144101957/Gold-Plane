package net.updategames.gra.presantation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import net.updategames.gra.R
import net.updategames.gra.databinding.ActivityAviatorBinding

class AviatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAviatorBinding
    private var isDragged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.black)
        binding = ActivityAviatorBinding.inflate(layoutInflater).also { setContentView(it.root) }


        val moveStartAnim = AnimationUtils.loadAnimation(this, R.anim.move_anim)
        binding.draggableImage.startAnimation(moveStartAnim)


        binding.btn2.setOnClickListener {
            if (isDragged){
                val moveAnim = AnimationUtils.loadAnimation(this, R.anim.move_anim)
                animateWinner(moveAnim)
                Toast.makeText(this, "You win !!!", Toast.LENGTH_LONG).show()
                isDragged = false
            } else {
                Toast.makeText(this, "Drag image !", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnChoose.setOnClickListener {
            val btn2X = binding.element2.x
            val btn2Y = binding.element2.y
            val dragImageX = binding.draggableImage.x
            val dragImageY = binding.draggableImage.y

            val xRange = btn2X-40..btn2X+40
            val yRange = btn2Y-40..btn2Y+40

            if (dragImageX in xRange && dragImageY in yRange){
                val pulseAnim = AnimationUtils.loadAnimation(this, R.anim.pulse_anim)
                binding.btn2.startAnimation(pulseAnim)
                isDragged = true
            } else {
                Toast.makeText(this, "Drag image !", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun animateWinner(anim: Animation){
        with(binding){
            btn1.startAnimation(anim)
            btn2.startAnimation(anim)
            btn3.startAnimation(anim)
            btn4.startAnimation(anim)
            draggableImage.startAnimation(anim)
            btnChoose.startAnimation(anim)
            element1.startAnimation(anim)
            element2.startAnimation(anim)
            element3.startAnimation(anim)
            element4.startAnimation(anim)
        }
    }
}