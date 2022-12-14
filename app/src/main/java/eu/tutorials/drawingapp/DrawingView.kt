package eu.tutorials.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) 	: View(context, attrs) {

	private var mDrawPath : CustomPath? = null
	private var mCanvasBitmap : Bitmap? = null
	private var mDrawPaint : Paint? = null
	private var mCanvasPaint : Paint? = null
	private var mBrashSize : Float = 0.toFloat()
	private var color = Color.BLACK
	private var canvas: Canvas? = null
	private val mPaths = ArrayList<CustomPath>()

	init {
		setUpDrawing()
	}

	private fun setUpDrawing() {
		mDrawPaint = Paint()
		mDrawPath = CustomPath(color, mBrashSize)
		mDrawPaint!!.color = color
		mDrawPaint!!.style = Paint.Style.STROKE
		mDrawPaint!!.strokeJoin = Paint.Join.ROUND
		mDrawPaint!!.strokeCap = Paint.Cap.ROUND
		mCanvasPaint = Paint(Paint.DITHER_FLAG)
//		mBrashSize = 20.toFloat()
	}

	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
		super.onSizeChanged(w, h, oldw, oldh)
		mCanvasBitmap = Bitmap.createBitmap(w, h,Bitmap.Config.ARGB_8888)
		canvas = Canvas(mCanvasBitmap!!)
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)

		for (path in mPaths){
			mDrawPaint!!.color = path.color
			mDrawPaint!!.strokeWidth = path.brashThickness
			canvas.drawPath(path!!, mDrawPaint!!)
		}

		if(!mDrawPath!!.isEmpty){
			mDrawPaint!!.strokeWidth = mDrawPath!!.brashThickness
			mDrawPaint!!.color = mDrawPath!!.color
			canvas.drawPath(mDrawPath!!, mDrawPaint!!)
		}
	}

	override fun onTouchEvent(event: MotionEvent?): Boolean {
		val touchX = event?.x
		val touchY = event?.y

		when(event?.action){
			MotionEvent.ACTION_DOWN ->{
				mDrawPath!!.color = color
				mDrawPath!!.brashThickness = mBrashSize

				mDrawPath!!.reset()
				if (touchX != null) {
					if (touchY != null) {
						mDrawPath!!.moveTo(touchX, touchY)
					}
				}
			}
			MotionEvent.ACTION_MOVE ->{
				if (touchX != null) {
					if (touchY != null) {
						mDrawPath!!.lineTo(touchX, touchY)
					}
				}
			}
			MotionEvent.ACTION_UP ->{
				mPaths.add(mDrawPath!!)
				mDrawPath = CustomPath(color, mBrashSize)
			}
			else -> false
		}
		invalidate()


		return true
	}

	fun setBrashSize (newSize : Float){
		mBrashSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, resources.displayMetrics)
		mDrawPaint!!.strokeWidth = mBrashSize
	}

	fun setColor(newColor: String){
		color = Color.parseColor(newColor)
		mDrawPaint!!.color = color
	}

	internal inner class CustomPath(var color: Int, var brashThickness: Float) : Path(){

	}
}