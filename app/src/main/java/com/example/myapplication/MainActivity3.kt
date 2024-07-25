package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val overlayLayout = findViewById<LinearLayout>(R.id.overlayLayout)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isButtonHidden = sharedPreferences.getBoolean("isButtonHidden", false)
        if (isButtonHidden) {
            overlayLayout.visibility = View.GONE // 如果按钮状态为隐藏，则设置按钮为不可见
        } else {
            overlayLayout.visibility = View.VISIBLE
            link()
            val textView = findViewById<TextView>(R.id.toask_button1)
            textView.setOnClickListener {
                val editor = sharedPreferences.edit()
                editor.putBoolean("isButtonHidden", true)
                editor.apply()
                    val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val overlayLayout = findViewById<LinearLayout>(R.id.overlayLayout)
        val isButtonHidden = sharedPreferences.getBoolean("isButtonHidden", false)
        if (isButtonHidden) {
            overlayLayout.visibility = View.GONE
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }, 2000)
        }
    }

    fun link() {
        val textView = findViewById<TextView>(R.id.toask_text2)
        val spannableString = SpannableString("欢迎使用音乐社区，我们将严格遵守相关法律和隐私政策保护您的个人隐私，请您阅读并同意《用户协议》与《隐私政策》。")
        val userAgreementSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity3, "《用户协议》", Toast.LENGTH_SHORT).show()
            }
        }
        val privacyPolicySpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity3, "《隐私政策》", Toast.LENGTH_SHORT).show()
            }
        }
        val startUserAgreement = spannableString.indexOf("《用户协议》")
        val endUserAgreement = startUserAgreement + "《用户协议》".length
        spannableString.setSpan(userAgreementSpan, startUserAgreement, endUserAgreement, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val startPrivacyPolicy = spannableString.indexOf("《隐私政策》")
        val endPrivacyPolicy = startPrivacyPolicy + "《隐私政策》".length
        spannableString.setSpan(privacyPolicySpan, startPrivacyPolicy, endPrivacyPolicy, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance() // 设置TextView可点击
    }
}
