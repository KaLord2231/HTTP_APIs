package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.api.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private TextView textViewResult;
private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult=findViewById(R.id.text_view_result);
        Gson gson=new GsonBuilder().serializeNulls().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         apiService=retrofit.create(APIService.class);
        //getPosts();
        //getComments();
        //updatePost();
        deletePost();
    }
    private void getPosts(){
        Call<List<Post>> call=apiService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post:posts){
                    String content = " ";
                    content += "ID"+post.getId()+"\n";
                    content+="User ID: "+post.getUserId()+"\n";
                    content+="Title: "+post.getTitle()+"\n";
                    content+= "Body: "+post.getBody()+"\n\n";
                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    private void getComments(){
        Call<List<Comment>> call=apiService.getComments(3);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
            if(!response.isSuccessful()){
                textViewResult.setText("Code: "+response.code());
                return;
                }
            List<Comment> comments=response.body();
                for(Comment comment:comments){
                    String content = "";
                    content += "ID: "+ comment.getId()+"\n";
                    content+="Post ID: "+comment.getPostId()+"\n";
                    content+="Name: "+comment.getName()+"\n";
                    content+="Email: "+comment.getEmail()+"\n";
                    content+= "Body: "+comment.getBody()+"\n\n";
                    textViewResult.append(content);
                 }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
            textViewResult.setText(t.getMessage());
            }
        });

}
    private void updatePost(){
        Post post=new Post(12,null,"new text");
    Post post1=new Post(13);

        Call<Post> call=apiService.patchPost(5,post1);//patch
        call.enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;

                }
                Post postResponse=response.body();
                String content = "";
                content += "Code: "+ response.code()+"\n";
                content+="ID: "+postResponse.getId()+"\n";
                content+="User ID: "+postResponse.getUserId()+"\n";
                content+="Title: "+postResponse.getTitle()+"\n";
                content+= "Body: "+postResponse.getBody()+"\n\n";
                textViewResult.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    private void deletePost(){
        Call<Void> call = apiService.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Delete successful : Code: "+ response.code());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

}