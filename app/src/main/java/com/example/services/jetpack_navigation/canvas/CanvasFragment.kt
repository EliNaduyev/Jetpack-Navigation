package com.example.services.jetpack_navigation.canvas

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.services.jetpack_navigation.R
import com.example.services.jetpack_navigation.guide_line.UiEvents
import com.example.services.jetpack_navigation.collectLatestLifecycleFlow
import com.example.services.jetpack_navigation.databinding.FragmentCanvasBinding
import com.example.services.jetpack_navigation.log
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CanvasFragment : Fragment() {
    private lateinit var binding: FragmentCanvasBinding
    val vm: CanvasViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("${this::class.java.name} - onCreate: is called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentCanvasBinding.inflate(layoutInflater)
        log("${this::class.java.name} - onCreateView: is called")
        initLifeCycle()
        initObservers()
//        drawRectAndOvalOnCanvas()
        drawOnCanvasFromImageView()
        return binding.root
    }

    private fun drawOnCanvasFromImageView() {
        val bitmap: Bitmap = binding.imageWithSize.drawable.toBitmap()
        val canvas = Canvas(bitmap)
        canvas.drawColor(ContextCompat.getColor(requireContext(), org.koin.android.R.color.material_blue_grey_800))

//        binding.imageWithSize.background = BitmapDrawable(resources, bitmap) // set the canvas as background of the original image

        val bitMap = BitmapDrawable(resources, bitmap).toBitmap()
        binding.imageWithSize.setImageBitmap(addBorderToBitmap(bitMap, 3, Color.RED)) // set the canvas instead the image view
    }

    /**
     * I create new bitmap a little bit bigger(space for the border), the multiple 2 its for both sides
     * draw on it background of the border color and then draw the original image(bitmap) and move it from left and top
     * of the bigger bitmap and this will make a border effect.
     */
    private fun addBorderToBitmap(bmp: Bitmap, borderSize: Int, color: Int): Bitmap? {
        val bmpBorder = Bitmap.createBitmap(bmp.width + borderSize * 2, bmp.height + borderSize * 2, bmp.config)
        val canvas = Canvas(bmpBorder)
        canvas.drawColor(color)
        canvas.drawBitmap(bmp, borderSize.toFloat(), borderSize.toFloat(), null)
        return bmpBorder
    }

    /**
     * Draw rect and oval on canvas one under the other
     */
    private fun drawRectAndOvalOnCanvas() {
        val bitmap: Bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // draw background color to the canvas
        canvas.drawColor(ContextCompat.getColor(requireContext(), R.color.light_gray))

        var shapeDrawable: ShapeDrawable = createShape(0, 0, 600, 200, RectShape(), R.color.purple_200)
        shapeDrawable.draw(canvas)

        shapeDrawable = createShape(0, 200, 600, 600, OvalShape(), R.color.black)
        shapeDrawable.draw(canvas)

        // set bitmap as background to ImageView
        binding.imageV.background = BitmapDrawable(resources, bitmap)
    }

    /**
     * rectangle positions on the canvas, left, top = width; top, bottom = height
     */
    private fun createShape(left: Int, top: Int, right: Int, bottom: Int, shape: Shape, shapeColor: Int): ShapeDrawable {
        val shapeDrawable = ShapeDrawable(shape)
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.paint.color = requireContext().getColor(shapeColor)
        return shapeDrawable
    }

    private fun initLifeCycle(){
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = vm
    }

    private fun initObservers() {
        lifecycleScope.launch {
            collectLatestLifecycleFlow(vm.uiEventsChannel){
                when(it){
                    UiEvents.Next -> {
                        findNavController().navigate(R.id.action_canvasFragment_to_canvasFragment2)
                    }
                    UiEvents.Back -> findNavController().popBackStack()
                }
            }
        }
    }
}