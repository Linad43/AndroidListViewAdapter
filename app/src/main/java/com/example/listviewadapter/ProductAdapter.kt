package com.example.listviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.androidstoreproduct.Product

class ProductAdapter(context: Context, productList: ArrayList<Product>):
ArrayAdapter<Product>(context, R.layout.list_item, productList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val product = getItem(position)
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val image = view?.findViewById<ImageView>(R.id.image)
        val name = view?.findViewById<TextView>(R.id.nameProduct)
        val price = view?.findViewById<TextView>(R.id.priceProduct)

        image?.setImageBitmap(product?.image)
        name?.text = product?.name
        price?.text = product?.price.toString()

        return view!!
    }
}