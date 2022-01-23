package com.kepa.social_client

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText



class EditPostActivity : AppCompatActivity() {
    private lateinit var editPostTitle: TextInputEditText
    private lateinit var editPostContent: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        findViewById<Button>(R.id.edit_done_button).setOnClickListener {
            editPost(intent.getStringExtra(POST_ID))
        }
        editPostTitle = findViewById(R.id.edit_post_title)
        editPostContent = findViewById(R.id.edit_post_content)
        editPostTitle.setText(intent.getStringExtra(POST_TITLE))
        editPostContent.setText(intent.getStringExtra(POST_CONTENT))

    }

    private fun editPost(id: String?) {
        if (editPostTitle.text.isNullOrEmpty() || editPostContent.text.isNullOrEmpty()) {
            Toast.makeText(this, "You have to fill out both fields", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, ConfirmEditPostActivity::class.java)
            intent.putExtra(POST_TITLE, editPostTitle.text.toString())
            intent.putExtra(POST_CONTENT, editPostContent.text.toString())
            intent.putExtra(POST_ID, id)
            startActivityForResult(intent, 5)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (resultCode == RESULT_OK && intentData != null) {
            val intent = Intent()
            intent.putExtra(POST_TITLE, editPostTitle.text.toString())
            intent.putExtra(POST_CONTENT, editPostContent.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

}