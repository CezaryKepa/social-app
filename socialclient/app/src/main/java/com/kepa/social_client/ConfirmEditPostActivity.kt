package com.kepa.social_client

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ConfirmEditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_edit_post)
        val title = intent.getStringExtra(POST_TITLE)
        val content = intent.getStringExtra(POST_CONTENT)

        val titleTextView = findViewById<TextView>(R.id.editTitleTextView)
        val contentTextView = findViewById<TextView>(R.id.editContentTextView)
        titleTextView.text = title
        contentTextView.text = content

        val btnYes = findViewById<View>(R.id.yesEditButton)
        val btnNo = findViewById<View>(R.id.noEditButton)

        btnNo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnYes.setOnClickListener {
            sendPostUpdate(intent.getStringExtra(POST_ID), title, content)

            val intent = Intent()
            intent.putExtra(POST_TITLE, title)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun sendPostUpdate(id: String?, title: String?, content: String?) {
        val requestQueue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8080/api/posts/$id"
        val jsonBody = JSONObject()
        jsonBody.put(POST_TITLE, title)
        jsonBody.put(POST_CONTENT, content)
        val stringRequest = JsonObjectRequest(
            Request.Method.PUT, url, jsonBody,
            { response -> println(response) },
            { println("Could not edit post") }
        )
        requestQueue.add(stringRequest)

    }
}