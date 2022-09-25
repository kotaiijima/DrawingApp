package eu.tutorials.drawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private var drawingView : DrawingView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setBrashSize(20.toFloat())

		val ib_brush : ImageButton = findViewById(R.id.ib_choice_brash_size)
		ib_brush.setOnClickListener{
			showBrushSizeDialog()
		}
    }

	private fun showBrushSizeDialog(){
		val brushDialog = Dialog(this)
		brushDialog.setContentView(R.layout.select_brash_size_button)
//		brushDialog.setTitle("Brush size: ")
		brushDialog.show()
		val smallBtn : ImageButton = brushDialog.findViewById(R.id.small_btn)
		smallBtn.setOnClickListener{
			drawingView!!.setBrashSize(10.toFloat())
			brushDialog.dismiss()
		}
		val mediumBtn : ImageButton = brushDialog.findViewById(R.id.medium_btn)
		mediumBtn.setOnClickListener{
			drawingView!!.setBrashSize(20.toFloat())
			brushDialog.dismiss()
		}
		val largeBtn : ImageButton = brushDialog.findViewById(R.id.large_btn)
		largeBtn.setOnClickListener{
			drawingView!!.setBrashSize(30.toFloat())
			brushDialog.dismiss()
		}
	}
}