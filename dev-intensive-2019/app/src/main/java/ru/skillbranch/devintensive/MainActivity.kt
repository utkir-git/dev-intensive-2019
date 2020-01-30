package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send
        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))
        Log.d("M_MainActivity", "onCreate ${status} ${question}")
        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.setText(benderObj.askQuestion())
        sendBtn.setOnClickListener(this)

        messageEt.setOnEditorActionListener()
        { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val (prase, color) = benderObj.listenAnswer(messageEt.text.toString())
                    messageEt.setText("")
                    val (r, g, b) = color
                    benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
                    textTxt.text = prase
                    hideKeyboard()
                    true
                }
                else -> false
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy")
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            val (prase, color) = benderObj.listenAnswer(messageEt.text.toString())
            messageEt.setText("")
            val (r, g, b) = color
            benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
            textTxt.text = prase
            hideKeyboard()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d(
            "M_MainActivity",
            "onSaveInstanceState ${benderObj.status.name}  ${benderObj.question.name}"
        )
        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
    }
}
//            when(benderObj.question){
//                Bender.Question.NAME->messageEt.inputType=InputType.TYPE_TEXT_FLAG_CAP_WORDS
//                // Bender.Question.PROFESSION->messageEt.inputType=InputType.
//                Bender.Question.MATERIAL->messageEt.inputType=InputType.TYPE_TEXT_VARIATION_PERSON_NAME
//                Bender.Question.BDAY->messageEt.inputType=InputType.TYPE_CLASS_NUMBER
//                Bender.Question.SERIAL->{messageEt.inputType=InputType.TYPE_CLASS_NUMBER; messageEt.setEms(7)}
//                Bender.Question.IDLE->messageEt.inputType=InputType.TYPE_NULL
//            }

