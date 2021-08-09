package com.android.study.tablayout.Model;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class PostDB {
    public static void createPost(String name, String msg, int imgId, boolean like, int likeCount) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Post post = realm.createObject(Post.class);
                post.setId(java.lang.System.currentTimeMillis());
                post.setName(name);
                post.setMsg(msg);
                post.setImgId(imgId);
                post.setLike(like);
                post.setLikeCount(likeCount);
            }
        });
    }

    public static void deletePost(long id) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            Post deletePost = Realm.getDefaultInstance()
                    .where(Post.class)
                    .equalTo("id", id)
                    .findFirst();

            @Override
            public void execute(Realm realm) {
                deletePost.deleteFromRealm();
            }
        });
    }

    public static ArrayList<Post> loadList() {
        ArrayList<Post> postList = new ArrayList<>();

        RealmResults<Post> resultList = Realm.getDefaultInstance()
                .where(Post.class)
                .sort("id", Sort.DESCENDING)
                .findAll();

        postList.addAll(Realm.getDefaultInstance().copyFromRealm(resultList));

        return postList;
    }


}
