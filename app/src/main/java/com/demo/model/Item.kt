package com.demo.model

data class Item(
    var id: String = "",
    var isChecked: Boolean = false,
    var img: String = "",
    var name: String = "",
    var collectionPath: String = "",
    var price: String = "",
    var mount: Int = 1,
)
