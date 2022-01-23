package com.kepa.social_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

const val DELETE_REQUEST_CODE = 6
const val EDIT_REQUEST_CODE = 7

class PostDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        val title = intent.getStringExtra(POST_TITLE)
        val content = intent.getStringExtra(POST_CONTENT)
        val id = intent.getStringExtra(POST_ID);

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val contentTextView = findViewById<TextView>(R.id.contentTextView)
        val likesTextView = findViewById<TextView>(R.id.likesTextView)
        titleTextView.text = title
        contentTextView.text = content
        likesTextView.text = "Likes: " + intent.getIntExtra("likes", -2)

        val btnDelete: View = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener {
            btnDeleteOnClick(id)
        }

        val btnLike: View = findViewById(R.id.btnLike)
        btnLike.setOnClickListener {
            btnLikeOnClick(id)
        }

        val btnEdit: View = findViewById(R.id.btnEdit)
        btnEdit.setOnClickListener {
            btnEditOnClick(id, title, content)
        }

    }
    private fun btnLikeOnClick(id: String?) {
        sendPostLike(id)

        val result = Intent()
        result.putExtra(POST_ID, id)
        setResult(DETAILS_RESPONSE_LIKE, result)
        finish()
    }

    private fun btnDeleteOnClick(id: String?) {
        val intent = Intent(this, DeletePostActivity::class.java)
        intent.putExtra(POST_ID, id)
        startActivityForResult(intent, DELETE_REQUEST_CODE)
    }

    private fun btnEditOnClick(id: String?, title: String?, content: String?) {
        val intent = Intent(this, EditPostActivity::class.java)
        intent.putExtra(POST_ID, id)
        intent.putExtra(POST_TITLE, title)
        intent.putExtra(POST_CONTENT, content)

        startActivityForResult(intent, EDIT_REQUEST_CODE)
    }

    private fun sendPostLike(id: String?) {
        val requestQueue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8080/api/posts/$id/like"

        val stringRequest = JsonObjectRequest(
            Request.Method.PATCH, url, null,
            { response -> println(response) },
            { println("Could not like post") }
        )
        requestQueue.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (requestCode == DELETE_REQUEST_CODE) {
            val result = Intent()
            result.putExtra(POST_TITLE, intent.getStringExtra(POST_TITLE))
            result.putExtra(POST_ID, intent.getStringExtra(POST_ID))

            setResult(DETAILS_RESPONSE_DELETE, result)
            finish()
        }

        if(requestCode == EDIT_REQUEST_CODE) {
            val title = intentData?.getStringExtra(POST_TITLE)
            val content = intentData?.getStringExtra(POST_CONTENT)
            if (title != null) {
                val titleTextView = findViewById<TextView>(R.id.titleTextView)
                titleTextView.text = title
                Toast.makeText(
                    this,
                    "Post $title was edited",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (content != null) {
                val contentTextView = findViewById<TextView>(R.id.contentTextView)
                contentTextView.text = content
            }

            setResult(DETAILS_RESPONSE_EDIT, Intent())
        }
    }
}
