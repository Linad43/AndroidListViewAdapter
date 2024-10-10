package com.example.listviewadapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstoreproduct.Product
import androidx.appcompat.widget.Toolbar

class ListActivity : AppCompatActivity(), Removable, Informable {

    var check = true
    private var product: Product? = null
    private val products = arrayListOf<Product>()
    private var productAdapter: ProductAdapter? = null
    private var photoUri: Uri? = null
    private lateinit var toolbar: Toolbar
    private lateinit var photoIW: ImageView
    private lateinit var nameET: EditText
    private lateinit var priceET: EditText
    private lateinit var addProductBTN: Button
    private lateinit var listLV: ListView
    private lateinit var infoProductET: EditText
    var item: Int? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list)
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
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        photoUri =
            Uri.parse("android.resourse://com.example.listviewadapter/" + R.drawable.image_default)
        infoProductET = findViewById(R.id.infoProductET)

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
                    photoUri.toString(),
                    if (infoProductET.text.isEmpty()) {
                        ""
                    } else {
                        infoProductET.text.toString()
                    }
                )
                products.add(product)
                productAdapter = ProductAdapter(this, products)
                listLV.adapter = productAdapter
                productAdapter?.notifyDataSetChanged()
                nameET.text.clear()
                priceET.text.clear()
                infoProductET.text.clear()
                photoIW.setImageResource(R.drawable.downloads_image)
                photoUri = Uri.parse(
                    "android.resourse://com.example.listviewadapter/"
                            + R.drawable.image_default
                )
            } else {
                Toast.makeText(
                    this,
                    "Поле имени и цены не может быть пустым",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        listLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, viev, position, id ->
                val product = productAdapter?.getItem(position)
//                item = position
                val dialog = MyAlert()
                val args = Bundle()
                args.putSerializable("product", product)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IntentsScore.GALLERY_REQUEST.ordinal -> {
                if (resultCode == RESULT_OK) {
                    photoUri = data?.data
                }
            }
        }
        photoIW.setImageURI(photoUri)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> {
                finishAffinity()
            }
        }
        return true
    }

    override fun remove(product: Product) {
        productAdapter?.remove(product)
    }

    override fun info(product: Product) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }

}