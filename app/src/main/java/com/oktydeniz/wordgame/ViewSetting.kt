package com.oktydeniz.wordgame

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlin.random.Random

object ViewSetting {

    private val temp = ArrayList<Int>()
    fun getCity(context: Context): CharArray {
        val city = context.resources.getStringArray(R.array.turkey_city)
        val rnd = Random.nextInt(city.size)
        return city[rnd].toCharArray()
    }


    fun createNewTextView(
        context: Context,
        str: String,
        params: LinearLayout.LayoutParams
    ): TextView {
        val textView = TextView(context)
        textView.text = str
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.layoutParams = params
        textView.textSize = 38F
        textView.background = ContextCompat.getDrawable(context, R.drawable.bottom_text)
        return textView
    }


    fun hideView(viewList: ArrayList<TextView>) {
        viewList.forEach {
            it.visibility = View.INVISIBLE
            println(it.text)
        }
    }

    fun showOne(viewList: ArrayList<TextView>): Boolean {
        val intArray = intArray(viewList)
        val rnd = intArray.random()
        if (intArray.size != temp.size) {
            if (rnd !in temp) {
                println("Random : $rnd")
                viewList[rnd].visibility = View.VISIBLE
                temp.add(rnd)
            } else {
                showOne(viewList)
            }
        } else {
            temp.clear()
            return false
        }
        return true
    }

    private fun intArray(viewList: ArrayList<TextView>): ArrayList<Int> {
        val numberArray = ArrayList<Int>()
        for (i in 0 until viewList.size) {
            numberArray.add(i)
        }
        return numberArray
    }
    fun View?.removeSelf() {
        this ?: return
        val parent = parent as? ViewGroup ?: return
        parent.removeView(this)
    }


}