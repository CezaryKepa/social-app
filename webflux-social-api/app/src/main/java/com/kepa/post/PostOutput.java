package com.kepa.post;

import java.util.List;

record PostOutput(String id, String content, String title, long likes, List<CommentOutput> comments) {
}
