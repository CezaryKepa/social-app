package com.kepa.social_client

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.google.android.material.textfield.TextInputEditText
import com.android.volley.toolbox.Volley

import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject


class AddPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_post_layout)
        val addPostTitle: TextInputEditText = findViewById(R.id.add_post_title)
        val addPostContent: TextInputEditText = findViewById(R.id.add_post_content)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addPost(addPostTitle, addPostContent)
        }
    }

    private fun addPost(addPostTitle: TextInputEditText, addPostContent: TextInputEditText) {
        val resultIntent = Intent()

        if (addPostTitle.text.isNullOrEmpty() || addPostContent.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val requestQueue = Volley.newRequestQueue(this)
            val url = "http://10.0.2.2:8080/api/posts"
            val jsonBody = JSONObject()
            jsonBody.put(POST_TITLE, addPostTitle.text.toString())
            jsonBody.put(POST_CONTENT, addPostContent.text.toString())
            val stringRequest = JsonObjectRequest(
                Request.Method.POST, url, jsonBody,
                { response -> println(response)},
                { println("Could not add post") }
            )
            requestQueue.add(stringRequest)
            Toast.makeText(this, "Post " +  addPostTitle.text.toString()+ " was added", Toast.LENGTH_LONG).show()
        }
    }
}