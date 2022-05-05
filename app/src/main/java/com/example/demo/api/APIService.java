package com.example.demo.api;

import com.example.demo.Comment;
import com.example.demo.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface APIService {

@GET("posts")
Call<List<Post>> getPosts();
@GET("posts/{id}/comments")
Call<List<Comment>> getComments(@Path("id") int postId);
@FormUrlEncoded
@PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);
@PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);
@DELETE("posts/{id}")
   Call<Void>deletePost(@Path("id") int id);
}
