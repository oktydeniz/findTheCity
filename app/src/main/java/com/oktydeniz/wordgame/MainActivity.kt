package com.oktydeniz.wordgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.oktydeniz.wordgame.ViewSetting.createNewTextView
import com.oktydeniz.wordgame.ViewSetting.getCity
import com.oktydeniz.wordgame.ViewSetting.hideView
import com.oktydeniz.wordgame.ViewSetting.removeSelf
import com.oktydeniz.wordgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewList = ArrayList<TextView>()
    private var buffer = StringBuffer()
    private var score: Int = 0
    private var helpBtn: Int = 0
    private var currentCityString: String? = null
    private lateinit var params: LinearLayout.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startGame()
        stringsViews()
        actions()
    }

    private fun startGame() {
        params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
        )
        params.weight = 1F
        params.setMargins(10, 10, 10, 10)
        hide()
    }

    private fun hide() {
        currentCityString = getCity(applicationContext).concatToString()
        helpBtn = currentCityString!!.length

        for (i in currentCityString!!) {
            val stringView = createNewTextView(this, i.toString(), params)
            viewList.add(stringView)
            binding.dynamically.addView(stringView)
        }

        hideView(viewList)
        buffer.delete(0,buffer.length)
        buffer.append(getString(R.string.word_count))
        buffer.append(" : ")
        buffer.append(viewList.size)
    }

    private fun toast(str: String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    private fun actions() {

        binding.help.setOnClickListener {
            if (helpBtn == 0) {
                binding.help.isEnabled = false
                toast(getString(R.string.all_words_opened))
            } else {
                if (helpBtn >= 0) {
                    helpBtn--
                    if (!ViewSetting.showOne(viewList)) {
                        binding.help.isEnabled = true
                    }
                }

            }
            stringsViews()
        }

        binding.reStart.setOnClickListener {
            reStart()
        }
        binding.checkLetter.setOnClickListener {
            val str = binding.editTextGetWord.text.toString()
            if (str.trim() == currentCityString) {
                Toast.makeText(applicationContext, "Same", Toast.LENGTH_LONG).show()
                again()
            } else {
                Toast.makeText(applicationContext, "Wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun again() {
        currentCityString = null
        currentCityString = getCity(applicationContext).concatToString()
        score += 5
        binding.editTextGetWord.setText("")
        helpBtn = currentCityString!!.length
        buffer.setLength(0)

        viewList.forEach {
            it.removeSelf()
        }
        viewList.clear()
        hide()
        stringsViews()

    }

    private fun stringsViews() {
        binding.helpToFind.text = helpBtn.toString()
        binding.letterCount.text = buffer
        binding.score.text = score.toString()
    }

    private fun reStart() {
        score = 0
        buffer.setLength(0)
        currentCityString = null
        binding.help.isEnabled = true
        viewList.forEach {
            it.removeSelf()
        }
        binding.editTextGetWord.setText("")
        viewList.clear()
        startGame()
        stringsViews()
    }


}