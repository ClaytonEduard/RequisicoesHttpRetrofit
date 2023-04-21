package com.claytoneduard.requisicoeshttpretrofit.api;

import com.claytoneduard.requisicoeshttpretrofit.model.Foto;
import com.claytoneduard.requisicoeshttpretrofit.model.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("/photos")
    Call<List<Foto>> recuperarFotos();

    @GET("/posts")
    Call<List<Postagem>> recuperarPostagems();

    @POST("/posts")
    Call<Postagem> salvarPostagem(@Body Postagem postagem);

    //userId=123&title=Titulo postagem&body=Corpo postagem
    // formato XML de enviar
    @FormUrlEncoded
    @POST("/posts")
    Call<Postagem> salvarPostagem(
            @Field("userId") String userId,
            @Field("title") String title,
            @Field("body") String body
    );

}
