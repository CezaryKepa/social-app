package com.kepa.social_client

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class DeletePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_post)
        val btnYes: View = findViewById(R.id.yesButton)
        val btnNo: View = findViewById(R.id.noButton)

        btnNo.setOnClickListener {
           startActivity(Intent(this, MainActivity::class.java))
        }

        btnYes.setOnClickListener {
            sendPostDelete(intent.getStringExtra(POST_ID))
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun sendPostDelete(id: String?) {
        val requestQueue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8080/api/posts/$id"

        val stringRequest = JsonObjectRequest(
            Request.Method.DELETE, url,null,
            { response -> println(response)},
            { println("Could not delete post") }
        )
        requestQueue.add(stringRequest)
    }
}