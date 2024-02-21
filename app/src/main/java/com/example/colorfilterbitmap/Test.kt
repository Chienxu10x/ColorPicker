//package com.example.demofilter
//
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.ColorMatrix
//import android.graphics.ColorMatrixColorFilter
//import android.graphics.Paint
//import android.graphics.PorterDuff
//import android.os.Bundle
//import android.widget.Button
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val originalBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.a1)
//        val testImage = findViewById<ImageView>(R.id.testFilter)
//        val dendo = findViewById<Button>(R.id.btndendo)
//        val doden = findViewById<Button>(R.id.btndoden)
//        val vangden = findViewById<Button>(R.id.btnvangden)
//        val denvang = findViewById<Button>(R.id.btndenvang)
//
//        dendo.setOnClickListener {
//            val grayBitmap: Bitmap = convertRedToBlack(originalBitmap)
//            testImage.setImageBitmap(grayBitmap)
//        }
//        doden.setOnClickListener {
//            val grayBitmap: Bitmap = convertToRed(originalBitmap)
//            testImage.setImageBitmap(grayBitmap)
//        }
//        denvang.setOnClickListener {
//            val grayBitmap: Bitmap = convertToYellow(originalBitmap)
//            testImage.setImageBitmap(grayBitmap)
//        }
//        vangden.setOnClickListener {
//            val grayBitmap: Bitmap = convertToBlackFromYellow(originalBitmap)
//            testImage.setImageBitmap(grayBitmap)
//        }
//
//
//
////        testImage.setColorFilter(Color.BLACK,PorterDuff.Mode.MULTIPLY)
//    }
//
//    //chuyển thành màu xám
//    fun convertToGrayScale(inputBitmap: Bitmap): Bitmap {
//        // Tạo một ColorMatrix để chuyển đổi màu về màu xám
//        val colorMatrix = ColorMatrix().apply {
//            setSaturation(0f)
//        }
//
//        // Tạo một Paint và thiết lập ColorMatrixColorFilter
//        val paint = Paint().apply {
//            colorFilter = ColorMatrixColorFilter(colorMatrix)
//        }
//
//        // Tạo Bitmap mới và vẽ hình ảnh với màu xám lên nó
//        val grayBitmap =
//            Bitmap.createBitmap(inputBitmap.width, inputBitmap.height, inputBitmap.config)
//        val canvas = Canvas(grayBitmap)
//        canvas.drawBitmap(inputBitmap, 0f, 0f, paint)
//
//        return grayBitmap
//    }
////chuyển đen thành đỏ
//    fun convertRedToBlack(originalBitmap: Bitmap): Bitmap {
//        // Tạo một bản sao của Bitmap để tránh ảnh hưởng đến ảnh gốc
//        val modifiedBitmap = originalBitmap.copy(originalBitmap.config, true)
//
//        // Lấy kích thước của ảnh
//        val width = modifiedBitmap.width
//        val height = modifiedBitmap.height
//
//        // Lặp qua từng điểm ảnh và thực hiện chuyển đổi màu
//        for (x in 0 until width) {
//            for (y in 0 until height) {
//                val pixel = modifiedBitmap.getPixel(x, y)
//
//                // Lấy giá trị màu đỏ của điểm ảnh
//                val redValue = Color.red(pixel)
//
//                // Chuyển giá trị màu đỏ thành giá trị độ tối (đen)
//                val darkness = 255 - redValue
//
//                // Tạo màu mới từ giá trị độ tối đã tính toán cho màu đỏ và giữ nguyên giá trị màu xanh, màu xanh dương
//                val newPixel = Color.rgb(darkness, 0, 0)
//
//                // Đặt màu mới cho điểm ảnh
//                modifiedBitmap.setPixel(x, y, newPixel)
//            }
//        }
//
//        return modifiedBitmap
//    }
//    // chuyển đỏ thành đen
//    fun convertToRed(bitmap: Bitmap): Bitmap {
//        // Tạo một bản sao của Bitmap để tránh ảnh hưởng đến ảnh gốc
//        val modifiedBitmap = bitmap.copy(bitmap.config, true)
//
//        // Lấy kích thước của ảnh
//        val width = modifiedBitmap.width
//        val height = modifiedBitmap.height
//
//        // Lặp qua từng điểm ảnh và thực hiện chuyển đổi màu
//        for (x in 0 until width) {
//            for (y in 0 until height) {
//                val pixel = modifiedBitmap.getPixel(x, y)
//
//                // Lấy giá trị độ tối của điểm ảnh (giá trị màu trung bình)
//                val darkness = (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel)) / 3
//
//                // Chuyển đổi giá trị độ tối thành màu đỏ
//                val redValue = darkness
//                val greenValue = 0 // Không sử dụng màu xanh
//                val blueValue = 0 // Không sử dụng màu xanh dương
//
//                // Tạo màu mới từ các giá trị đã tính toán
//                val newPixel = Color.rgb(redValue, greenValue, blueValue)
//
//                // Đặt màu mới cho điểm ảnh
//                modifiedBitmap.setPixel(x, y, newPixel)
//            }
//        }
//
//        return modifiedBitmap
//    }
//    //đen sang vàng
//    fun convertToYellow(bitmap: Bitmap): Bitmap {
//        // Tạo một bản sao của Bitmap để tránh ảnh hưởng đến ảnh gốc
//        val modifiedBitmap = bitmap.copy(bitmap.config, true)
//        // Lấy kích thước của ảnh
//        val width = modifiedBitmap.width
//        val height = modifiedBitmap.height
//
//        // Lặp qua từng điểm ảnh và thực hiện chuyển đổi màu
//        for (x in 0 until width) {
//            for (y in 0 until height) {
//                val pixel = modifiedBitmap.getPixel(x, y)
//                // Lấy giá trị độ sáng (luminance) của điểm ảnh
//                val luminance = (0.299 * Color.red(pixel) + 0.587 * Color.green(pixel) + 0.114 * Color.blue(pixel)).toInt()
//                // Giữ nguyên giá trị độ sáng, áp dụng màu vàng cho phần còn lại của mỗi pixel
//                val newPixel = Color.rgb(255, 255, luminance)
//                // Đặt màu mới cho điểm ảnh
//                modifiedBitmap.setPixel(x, y, newPixel)
//            }
//        }
//        return modifiedBitmap
//    }
//    //vàng sang đen
//    fun convertToBlackFromYellow(bitmap: Bitmap): Bitmap {
//        // Tạo một bản sao của Bitmap để tránh ảnh hưởng đến ảnh gốc
//        val modifiedBitmap = bitmap.copy(bitmap.config, true)
//
//        // Lấy kích thước của ảnh
//        val width = modifiedBitmap.width
//        val height = modifiedBitmap.height
//
//        for (x in 0 until width) {
//            for (y in 0 until height) {
//                val pixel = modifiedBitmap.getPixel(x, y)
//
//                // Lấy giá trị độ sáng (luminance) của điểm ảnh
//                val luminance = (0.299 * Color.red(pixel) + 0.587 * Color.green(pixel) + 0.114 * Color.blue(pixel)).toInt()
//
//                // Chuyển màu đen với cùng giá trị độ sáng
//                modifiedBitmap.setPixel(x, y, Color.rgb(luminance, luminance, luminance))
//            }
//        }
//        return modifiedBitmap
//    }
//}
//
