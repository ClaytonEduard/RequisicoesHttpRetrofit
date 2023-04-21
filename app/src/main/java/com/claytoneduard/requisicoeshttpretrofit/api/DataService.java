package com.claytoneduard.requisicoeshttpretrofit.api;

import com.claytoneduard.requisicoeshttpretrofit.model.Foto;
import com.claytoneduard.requisicoeshttpretrofit.model.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {
    @GET("/photos")
    Call<List<Foto>> recuperarFotos();

    @GET("/posts")
    Call<List<Postagem>> recuperarPostagems();

}
