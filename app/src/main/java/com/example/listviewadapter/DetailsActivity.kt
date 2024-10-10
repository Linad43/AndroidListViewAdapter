package com.example.listviewadapter

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstoreproduct.Product

class DetailsActivity : AppCompatActivity() {
    private lateinit var photoIV: ImageView
    private lateinit var nameTV: TextView
    private lateinit var priceTV: TextView
    private lateinit var button: Button
    private lateinit var infoTV:TextView
    private lateinit var toolbar:Toolbar

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
        nameTV = findViewById(R.id.nameTV)
        priceTV = findViewById(R.id.priceTV)
        button = findViewById(R.id.addProductBTN)
        infoTV = findViewById(R.id.infoTV)

        val product = intent.getSerializableExtra("product", Product::class.java)
        photoIV.setImageURI(product!!.image.toUri())
        nameTV.text = product.name
        priceTV.text = product.price.toString()
        infoTV.text = product.info
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        button.setOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exit->{
                finishAffinity()
            }
        }
        return true
    }
}