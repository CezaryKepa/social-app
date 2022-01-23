package com.kepa.social_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kepa.social_client.model.Post

const val POST_ID = "id"
const val POST_TITLE = "title"
const val POST_CONTENT = "content"
const val POST_LIKES = "likes"
const val DETAILS_REQUEST = 1
const val ADD_POST_REQUEST = 2
const val DETAILS_RESPONSE_DELETE = 68
const val DETAILS_RESPONSE_LIKE = 69
const val DETAILS_RESPONSE_EDIT = 70

class MainActivity : AppCompatActivity() {
    private val gson = Gson()
    private lateinit var titles: MutableList<String>
    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.listView1);
        getPosts(listView)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    private fun getPosts(listView: ListView) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8080/api/posts"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val mutableListPostType = object : TypeToken<MutableList<Post>>() {}.type
                posts = gson.fromJson(response, mutableListPostType)
                titles = posts.map { it.title }.toMutableList()
                adapter = ArrayAdapter(this, R.layout.row, titles)
                listView.adapter = adapter
                listView.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this, PostDetails::class.java)
                    intent.putExtra(POST_ID, posts[position].id)
                    intent.putExtra(POST_TITLE, posts[position].title)
                    intent.putExtra(POST_CONTENT, posts[position].content)
                    intent.putExtra(POST_LIKES, posts[position].likes)

                    startActivityForResult(intent, 1)
                }

            },
            { println("Could not get posts") })

        queue.add(stringRequest)
    }

    private fun fabOnClick() {
        val intent = Intent(this, AddPostActivity::class.java)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if(deleteOperation(requestCode, resultCode)) {
            val title = intentData?.getStringExtra("title")
            val id = intentData?.getStringExtra("id")
            if (title != null) {
                titles.remove(title)
            }
            if (id != null) {
                posts.removeIf { it.id.equals(id) }
            }
        }

        if(likeOperation(requestCode, resultCode)) {
            val id = intentData?.getStringExtra("id")
            val filtered = posts.filter { it.id.equals(id) }
            val post = filtered.get(0);
            post.likes++
        }

        if(deleteOperation(requestCode, resultCode) || requestCode == ADD_POST_REQUEST || resultCode == DETAILS_RESPONSE_EDIT) {
            val listView = findViewById<ListView>(R.id.listView1);
            getPosts(listView)
        }
        adapter.notifyDataSetChanged()
    }

    private fun likeOperation(requestCode: Int, resultCode: Int) =
        requestCode == DETAILS_REQUEST && resultCode == DETAILS_RESPONSE_LIKE

    private fun deleteOperation(requestCode: Int, resultCode: Int) =
        requestCode == DETAILS_REQUEST && resultCode == DETAILS_RESPONSE_DELETE

}