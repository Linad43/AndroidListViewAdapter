package com.example.androidstoreproduct

import java.io.Serializable


class Product(
    val name: String,
    val price: Double,
    val image: String,
) : Serializable