package com.example.listviewadapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstoreproduct.Product
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val products = arrayListOf<Product>()
    private lateinit var bitmap: Bitmap
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var photoIW: ImageView
    private lateinit var nameET: EditText
    private lateinit var priceET: EditText
    private lateinit var addProductBTN: Button
    private lateinit var listLV: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        photoIW = findViewById(R.id.photoIW)
        nameET = findViewById(R.id.nameET)
        priceET = findViewById(R.id.priceET)
        addProductBTN = findViewById(R.id.addProductBTN)
        listLV = findViewById(R.id.listView)
        bitmap = (ResourcesCompat.getDrawable(
            resources, R.drawable.image_default, null
        ) as BitmapDrawable).bitmap

        photoIW.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, IntentsScore.GALLERY_REQUEST.ordinal)
        }

        addProductBTN.setOnClickListener {
            if (nameET.text.isNotEmpty() || priceET.text.isNotEmpty()) {
                val product = Product(
                    nameET.text.toString(),
                    priceET.text.toString().toDouble(),
                    bitmap
                )
                products.add(product)
                val productAdapter = ProductAdapter(this, products)
                listLV.adapter = productAdapter
                productAdapter.notifyDataSetChanged()
                nameET.text.clear()
                priceET.text.clear()
                photoIW.setImageResource(R.drawable.downloads_image)
                bitmap = (ResourcesCompat.getDrawable(
                    resources, R.drawable.image_default, null
                ) as BitmapDrawable).bitmap
            } else{
                Toast.makeText(
                    this,
                    "Поле имени и цены не может быть пустым",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IntentsScore.GALLERY_REQUEST.ordinal -> {
                if (resultCode == RESULT_OK) {
                    val selectedImage: Uri? = data?.data
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        photoIW.setImageBitmap(bitmap)
    }

}