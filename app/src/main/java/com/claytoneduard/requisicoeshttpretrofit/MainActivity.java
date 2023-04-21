package com.claytoneduard.requisicoeshttpretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.claytoneduard.requisicoeshttpretrofit.api.CEPService;
import com.claytoneduard.requisicoeshttpretrofit.api.DataService;
import com.claytoneduard.requisicoeshttpretrofit.model.CEP;
import com.claytoneduard.requisicoeshttpretrofit.model.Foto;
import com.claytoneduard.requisicoeshttpretrofit.model.Postagem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;
    private Retrofit retrofit;
    private List<Foto> listaFotos = new ArrayList<>();
    private List<Postagem> listaPostagems = new ArrayList<>();

    private DataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.buttonRecuperar);
        textoResultado = findViewById(R.id.textResultado);
        // configuração retrofit
        retrofit = new Retrofit.Builder().
                //baseUrl("https://viacep.com.br/ws/").
                        baseUrl("https://jsonplaceholder.typicode.com").
                addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(DataService.class);
        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recuperarCEPRetrofit();
                //recuperarListaRetrofit();
                //recuperarListaPostagem();
                //salvarPostagem();
                //atualizarPostagem();
                removerPostagem();
            }
        });
    }

    private void removerPostagem() {
        Call<Void> call = service.removerPostagem(2);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    textoResultado.setText("Status: " + response.code() + " Excluído com sucesso");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void atualizarPostagem() {
        //Postagem postagem = new Postagem("1234", null, "Minhas férias belissímas");
        //Call<Postagem> call = service.atualizarPostagem(2, postagem);
        Postagem postagem = new Postagem();
        postagem.setBody("Corpo da postagem alterada");
        Call<Postagem> call = service.atualizarPostagemPatch(2, postagem);

        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if (response.isSuccessful()) {
                    Postagem postagemResposta = response.body();
                    textoResultado.setText("" +
                            " Status: " + response.code()
                            + " Id: " + postagemResposta.getId()
                            + " UserId: " + postagemResposta.getUserId()
                            + " Título: " + postagemResposta.getTitle()
                            + " Corpo: " + postagemResposta.getBody()
                    );
                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });
    }

    private void salvarPostagem() {
        //configurar objeto postagem
        Postagem postagem = new Postagem("1234", "Férias", "Minhas férias belissímas");


        //recupera o serviço e salva a postagem
        Call<Postagem> call = service.salvarPostagem(postagem);

        //executando serviço
        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if (response.isSuccessful()) {
                    Postagem postagemResposta = response.body();
                    textoResultado.setText("" +
                            "Código: " + response.code()
                            + " Id: " + postagemResposta.getId()
                            + " Título: " + postagemResposta.getTitle()
                            + " Corpo: " + postagemResposta.getBody()
                    );
                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });

    }

    private void recuperarListaPostagem() {

        Call<List<Postagem>> call = service.recuperarPostagems();
        call.enqueue(new Callback<List<Postagem>>() {
            @Override
            public void onResponse(Call<List<Postagem>> call, Response<List<Postagem>> response) {
                if (response.isSuccessful()) {
                    listaPostagems = response.body();
                    for (int i = 0; i < listaPostagems.size(); i++) {
                        Postagem postagem = listaPostagems.get(i);
                        Log.d("Resultado", "resultado" + postagem.getId() + " / " + postagem.getTitle());
                        textoResultado.setText("Dados recuperados com sucesso!");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Postagem>> call, Throwable t) {

            }
        });


    }

    //recuperar lista de itens
    private void recuperarListaRetrofit() {


        Call<List<Foto>> call = service.recuperarFotos();

        call.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                if (response.isSuccessful()) {
                    listaFotos = response.body();

                    for (int i = 0; i < listaFotos.size(); i++) {
                        Foto foto = listaFotos.get(i);
                        Log.d("Resultado", "resultado" + foto.getId() + " / " + foto.getTitle());
                        textoResultado.setText("Dados recuperados com sucesso!");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {

            }
        });


    }


    //metodo para consumir o recurso
    private void recuperarCEPRetrofit() {

        CEPService cepService = retrofit.create(CEPService.class);

        Call<CEP> call = cepService.recuperarCEP("75535210");

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if (response.isSuccessful()) {
                    CEP cep = response.body();
                    textoResultado.setText(cep.getLogradouro() + " - " + cep.getBairro());
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });

    }


}