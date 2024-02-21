package com.example.colorfilterbitmap

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.PorterDuffXfermode
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.File
import kotlin.math.min


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image = findViewById<ImageView>(R.id.color_image)

        val icon = BitmapFactory.decodeResource(resources,
            R.drawable.abv)
            .copy(Bitmap.Config.ARGB_8888, true)

//        val paint = Paint()
//        val canvas = Canvas(icon)
//        canvas.drawBitmap(destinationImage, 0f, 0f, paint)
//
//        val mode: PorterDuff.Mode = // chọn một mode ở đây
//            paint.xfermode = PorterDuffXfermode(mode)
//
//        canvas.drawBitmap(sourceImage, 0f, 0f, paint)

        val paint = Paint()
//        val originalBlue = Color.BLUE
//        val mode = PorterDuff.Mode.DARKEN
//        val lightBlue = manipulateColor(originalBlue, 100.8f)
//        val filter = PorterDuffColorFilter(lightBlue, mode)
//        paint.colorFilter = filter
//        val canvas = Canvas(icon)
//        canvas.drawBitmap(icon, 0f, 0f, paint)

        image.setImageBitmap(icon)

        val buttonRoB = findViewById<Button>(R.id.redOnBack)
        val buttonBoR = findViewById<Button>(R.id.backOnRed)
        val buttonBoY = findViewById<Button>(R.id.backOnYellow)
        val buttonYoB = findViewById<Button>(R.id.yellowOnBlack)
        val buttonBloY = findViewById<Button>(R.id.blueOnYellow)
        val buttonYoBl = findViewById<Button>(R.id.yellowOnBlue)
        val buttonBloW = findViewById<Button>(R.id.blueOnWhile)
        val buttonWoBl = findViewById<Button>(R.id.whileOnBlue)

        buttonBoR.setOnClickListener { image.setImageBitmap(convertBitmapToBlackOnRed(icon)) }
        buttonRoB.setOnClickListener { image.setImageBitmap(convertBitmapToRedOnBlack(icon)) }
        buttonBoY.setOnClickListener { image.setImageBitmap(convertBitmapToBlackOnYellow(icon)) }
        buttonYoB.setOnClickListener { image.setImageBitmap(convertBitmapToYellowOnBlack(icon)) }
        buttonYoBl.setOnClickListener { image.setImageBitmap(convertBitmapToYellowOnBlue(icon)) }
        buttonBloY.setOnClickListener { image.setImageBitmap(convertBitmapToBlueOnYellow(icon)) }
        buttonBloW.setOnClickListener { image.setImageBitmap(convertBitmapToBlueOnWhile(icon)) }
        buttonWoBl.setOnClickListener { image.setImageBitmap(convertBitmapToWhileOnBlue(icon)) }


        // Tạo một Uri để lưu ảnh vào thư viện
        val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, "image_${System.currentTimeMillis()}.jpg")
            Uri.fromFile(image)
        }

// Sử dụng ContentResolver để mở OutputStream và ghi bitmap vào đó

        val  btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            contentResolver.openOutputStream(imageUri!!)?.use { outputStream ->
                convertBitmapToBlueOnYellow(icon)
                    .compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            Toast.makeText(this, "Đã lưu ảnh vào thư viện", Toast.LENGTH_SHORT).show()
        }
    }

    fun convertBitmapToRedOnBlack(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)
        // Lấy tất cả pixel từ ảnh vào mảng pixels một lần
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        // Chuyển đổi màu của từng pixel
        for (i in pixels.indices) {
            val pixel = pixels[i]
            val redValue = pixel shr 16 and 0xFF // Lấy giá trị màu đỏ
            pixels[i] = redValue shl 16 or (0 shl 8) or 0 // Tạo màu mới từ giá trị độ tối
        }
        // Tạo bitmap mới từ mảng pixels đã được chuyển đổi
        val convertedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        convertedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return convertedBitmap
    }


    fun convertBitmapToBlackOnRed(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)

        // Lấy tất cả pixel từ ảnh vào mảng pixels một lần
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        // Chuyển đổi màu của từng pixel
        for (i in pixels.indices) {
            val pixel = pixels[i]
            val redValue = pixel shr 16 and 0xFF // Lấy giá trị màu đỏ
            val darkness = 255 - redValue// Tính giá trị độ tối
            pixels[i] = darkness shl 16 or (0 shl 8) or 0 // Tạo màu mới từ giá trị độ tối
        }

        // Tạo bitmap mới từ mảng pixels đã được chuyển đổi
        val convertedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        convertedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return convertedBitmap
    }

    fun convertBitmapToBlackOnYellow(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)

        // Lấy tất cả pixel từ ảnh vào mảng pixels một lần
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        // Chuyển đổi màu của từng pixel
        for (i in pixels.indices) {
            val pixel = pixels[i]
            val redValue = pixel shr 16 and 0xFF // Extract red component value
            val greenValue = pixel shr 8 and 0xFF // Extract green component value
            // Calculate darkness
            val darkness = 255 - (redValue + greenValue) / 2 // Compute darkness based on red and green components
            // Create new color from darkness value
            pixels[i] = darkness shl 16 or (darkness shl 8) or 0 // Generate new color with equal red and green components
        }

        // Tạo bitmap mới từ mảng pixels đã được chuyển đổi
        val convertedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        convertedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return convertedBitmap
    }
    fun convertBitmapToYellowOnBlack(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)

        // Lấy tất cả pixel từ ảnh vào mảng pixels một lần
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        // Chuyển đổi màu của từng pixel
        for (i in pixels.indices) {
            val pixel = pixels[i]
            val redValue = pixel shr 16 and 0xFF // Extract red component value
            val greenValue = pixel shr 8 and 0xFF // Extract green component value
            // Calculate darkness
            val darkness = (redValue + greenValue) / 2 // Compute darkness based on red and green components
            // Create new color from darkness value
            pixels[i] = darkness shl 16 or (darkness shl 8) or 0 // Generate new color with equal red and green components
        }

        // Tạo bitmap mới từ mảng pixels đã được chuyển đổi
        val convertedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        convertedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return convertedBitmap
    }


    fun convertBitmapToBlueOnYellow(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)

        // Lấy tất cả pixel từ ảnh vào mảng pixels một lần
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        for (i in pixels.indices) {
            val pixel = pixels[i]
            val redValue = pixel shr 16 and 0xFF // Extract red component value
            val greenValue = pixel shr 8 and 0xFF // Extract green component value
            val blueValue = pixel and 0xFF
            val newBlueValue = (blueValue / 2) + 10
//            Log.d("TAG", "convertBitmapToBlueOnYellow: " + newBlueValue)
            // Calculate darkness
            val darkness = 255 - (redValue + greenValue) / 2 // Calculate darkness based on the average of red and green components
            // Set blue component to darkness (making it blue)
            pixels[i] = (darkness shl 16) or (darkness shl 8) or newBlueValue shl 32

        }

        // Tạo bitmap mới từ mảng pixels đã được chuyển đổi
        val convertedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        convertedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return convertedBitmap
    }

    fun convertBitmapToBlueOnWhile(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)

        val threshold = 200
        // Lấy tất cả pixel từ ảnh vào mảng pixels một lần
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        for (i in pixels.indices) {
            val pixel = pixels[i]
            val redValue = pixel shr 16 and 0xFF // Extract red component value
            val greenValue = pixel shr 8 and 0xFF // Extract green component value
            val blueValue = pixel and 0xFF
            val newBlueValue = (blueValue/2)+20
            // Calculate darkness

            val brightness = 255 - (redValue + greenValue + blueValue) / 3 // Tính độ tối dựa trên trung bình của các thành phần màu
            pixels[i] = ((brightness shl 16) or (brightness shl 8) or brightness) or newBlueValue
        }
        // Tạo bitmap mới từ mảng pixels đã được chuyển đổi
        val convertedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        convertedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return convertedBitmap
    }


    fun convertBitmapToYellowOnBlue(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)

        // Lấy tất cả pixel từ ảnh vào mảng pixels một lần
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        for (i in pixels.indices) {
            val pixel = pixels[i]
            val redValue = pixel shr 16 and 0xFF // Trích xuất thành phần màu đỏ
            val greenValue = pixel shr 8 and 0xFF // Trích xuất thành phần màu xanh lá cây
            // Đặt thành phần màu xanh lam thành giá trị của darkness


            val darkness = 255 - (redValue + greenValue) / 2
            // Tạo màu mới với thành phần màu xanh lam là darkness
            pixels[i] = (redValue shl 16) or (greenValue shl 8) or darkness
        }

        // Tạo bitmap mới từ mảng pixels đã được chuyển đổi
        val convertedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        convertedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return convertedBitmap
    }

    fun convertBitmapToWhileOnBlue(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)
        // Lấy tất cả pixel từ ảnh vào mảng pixels một lần
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        val threshold = 200 // Ngưỡng để xác định màu sáng

        for (i in pixels.indices) {
            val pixel = pixels[i]
            val redValue = pixel shr 16 and 0xFF // Trích xuất thành phần màu đỏ
            val greenValue = pixel shr 8 and 0xFF
            val blueValue = pixel and 0xFF// Trích xuất thành phần màu xanh lá cây
            // Đặt thành phần màu xanh lam thành giá trị của darkness
            val darkBlueValue = 255 - (blueValue/2)
            // Tạo màu mới với thành phần màu xanh lam là darkness
            val brightness = (redValue + greenValue + blueValue) / 3
            pixels[i] = darkBlueValue shl 32 or (brightness shl 16) or (brightness shl 8 )or brightness

        }

        // Tạo bitmap mới từ mảng pixels đã được chuyển đổi
        val convertedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        convertedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return convertedBitmap
    }

    fun convertBitmapToWhileOnGreen(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)

        // Lấy tất cả pixel từ ảnh vào mảng pixels một lần
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        val threshold = 200 // Ngưỡng để xác định màu sáng

        for (i in pixels.indices) {
            val pixel = pixels[i]
            val redValue = pixel shr 16 and 0xFF // Trích xuất thành phần màu đỏ
            val greenValue = pixel shr 8 and 0xFF // Trích xuất thành phần màu xanh lá cây
            val lightBlueValue = greenValue + 50
            val newBlueValue = min(lightBlueValue, 255)
            val darkness = (redValue shl 16) or (newBlueValue shl 8) or (redValue and 0xFF)
            pixels[i] = darkness
            // Kiểm tra xem màu có cao hơn ngưỡng không
            if (redValue > threshold && greenValue > threshold) {
                // Nếu màu cao hơn ngưỡng, đặt tất cả các thành phần màu thành 255 để tạo màu trắng
                pixels[i] = 0xFFFFFF // Mã màu trắng với tất cả các thành phần màu là 255
            }
        }

        // Tạo bitmap mới từ mảng pixels đã được chuyển đổi
        val convertedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        convertedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return convertedBitmap
    }










//
}