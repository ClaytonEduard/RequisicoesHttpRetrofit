package com.claytoneduard.requisicoeshttpretrofit.api;

import com.claytoneduard.requisicoeshttpretrofit.model.Foto;
import com.claytoneduard.requisicoeshttpretrofit.model.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    //put atualiza todo o Objeto postagem
    @PUT("/posts/{id}")
    Call<Postagem> atualizarPostagem(@Path("id") int id, @Body Postagem postagem);

    // patch só atualiza os campos que  foram passados como parametro
    @PATCH("/posts/{id}")
    Call<Postagem> atualizarPostagemPatch(@Path("id") int id, @Body Postagem postagem);

    @DELETE("/posts/{id}")
    Call<Void> removerPostagem(@Path("id") int id);

}
