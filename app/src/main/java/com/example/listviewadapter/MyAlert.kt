package com.example.listviewadapter

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.androidstoreproduct.Product

class MyAlert : DialogFragment() {

    private var removable:Removable? = null
    private var informable:Informable? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        removable = context as Removable?
        informable = context as Informable?
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val product = requireArguments().getSerializable("person")
        val builder = AlertDialog.Builder(
            requireActivity()
        )

        return builder
            .setTitle("Внимание")
            .setMessage("Предпологаемые действия")
            .setPositiveButton("Удалить") { dialog, which ->
                removable?.remove(product as Product)
            }.setNeutralButton("Информация") { dialog, which ->
                informable?.info(product as Product)
            }.setNegativeButton("Отмена", null)
            .create()
    }
}