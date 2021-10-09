package com.java.flightscheduler.ui.base

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants

object MessageHelper {
    fun displayErrorMessage(view : View?, text : String){
        view?.let {
            Snackbar.make(it, text ,Snackbar.LENGTH_LONG).setBackgroundTint(
                ContextCompat.getColor(view.context,R.color.red_google)
            ).show()
        }
    }
    fun displayWarningMessage(view : View?, text : String){
        view?.let {
            Snackbar.make(it, text ,Snackbar.LENGTH_LONG).setBackgroundTint(
                ContextCompat.getColor(view.context,R.color.yellow_A700)
            ).show()
        }
    }
    fun displaySuccessMessage(view : View?, text : String){
        view?.let {
            Snackbar.make(it, text ,Snackbar.LENGTH_LONG).setBackgroundTint(
                ContextCompat.getColor(view.context,R.color.green_google)
            ).show()
        }
    }
    inline fun <reified T: Enum<T>> iterator():Iterator<T> = enumValues<T>().iterator()

    /*fun initializeDialog(enum : Enum<T>,){
        val builderSingle = AlertDialog.Builder(context)
        adapter = ArrayAdapter(
            context,
            android.R.layout.simple_list_item_1,
            E
        )
        builderSingle.setAdapter(
            adapter
        ) { _, which ->
            binding.txtHotelSort.text = AppConstants.FilterOptions.values()[which].toString()
        }
        builderSingle.show()
    }*/
}