package com.example.androidstoreproduct

import java.io.Serializable


class Product(
    val name: String,
    val price: Double,
    val image: String,
    val info: String
) : Serializable