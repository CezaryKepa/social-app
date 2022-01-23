package com.kepa.social_client.model

class Post {

    var id: String = ""
    var content: String = ""
    var title: String = ""
    var likes: Int = 0
    var comments: MutableList<Comment> = mutableListOf()

    constructor()
    constructor(
        id: String,
        content: String,
        title: String,
        likes: Int,
        comments: MutableList<Comment>
    ) {
        this.id = id
        this.content = content
        this.title = title
        this.likes = likes
        this.comments = comments
    }


}