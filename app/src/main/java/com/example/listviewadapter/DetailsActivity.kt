package com.example.listviewadapter

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstoreproduct.Product

class DetailsActivity : AppCompatActivity() {
    private lateinit var photoIV: ImageView
    private lateinit var nameTV: TextView
    private lateinit var priceTV: TextView
    private lateinit var button: Button

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        photoIV = findViewById(R.id.photoIW)
        nameTV = findViewById(R.id.nameET)
        priceTV = findViewById(R.id.priceET)
        button = findViewById(R.id.button)

        val product = intent.getSerializableExtra("product", Product::class.java)
        photoIV.setImageURI(product!!.image.toUri())
        nameTV.text = product.name
        priceTV.text = product.price.toString()
        button.setOnClickListener {
            finish()
        }
    }
}