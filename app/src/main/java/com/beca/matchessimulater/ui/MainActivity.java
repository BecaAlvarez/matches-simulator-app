package com.beca.matchessimulater.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import com.beca.matchessimulater.R;
import com.beca.matchessimulater.data.MatchesApi;
import com.beca.matchessimulater.databinding.ActivityMainBinding;
import com.beca.matchessimulater.domain.Match;
import com.beca.matchessimulater.ui.adapter.MatchesAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesApi matchesApi;
    private RecyclerView.Adapter matchesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupHttpClient();
        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingActionButton();

    }
    //O proprio retrofit. Tudo que ele precisa para criar
    private void setupHttpClient(){
        //Cria instancia.Implementar a interface retrofit
        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/BecaAlvarez/matches-simulator-api/main/matches.json") //URL base
                .addConverterFactory(GsonConverterFactory.create()) //a biblioteca responsavel por (des)serilizar no contexto json
                .build();
        //Utilizar em diferentes contextos
        matchesApi = retrofit.create(MatchesApi.class);
    }

    //Vai fazer a configuração para listar as partidas
    private void setupMatchesList() {
        //configurações do recicle view, setando as propriedades
        binding.rvMatches.setHasFixedSize(true); //tamanho fixo
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this)); //definição do layout
        findMatchesFromApi();
    }

    //Loading para atualizar as partidas na ação de swipe
    private void setupMatchesRefresh() {
        binding.srlMatches.setOnRefreshListener(this::findMatchesFromApi);
    }

    //criar eventos de clique e simulação de partidas. Botão flutuante
    private void setupFloatingActionButton() {
        binding.floatingButton.setOnClickListener(view -> {
            //efeito de animação do dado. Listener para callback qd terminar a animação
            view.animate().rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
        });
    }

    private void findMatchesFromApi() {
        //Antes de consumir a Api, fag que faz o controle do refresh é true p o simbolo não aparecer fixo
        //Antes de incializar a chamada, refresh é true
        binding.srlMatches.setRefreshing(true);
        matchesApi.getMaches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if(response.isSuccessful()){
                    //A lista de partidas vindo do corpo do response
                    List<Match>matches = response.body();
                    matchesAdapter = new MatchesAdapter(matches); //instancia que passa as partidas
                    binding.rvMatches.setAdapter(matchesAdapter);
                }else{
                    showErrorMessage();
                }
                //Refresh caso conclua
                binding.srlMatches.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                showErrorMessage();
                //Refresh caso de error é false
                binding.srlMatches.setRefreshing(false);
            }
        });
    }


    private void showErrorMessage() {
        Snackbar.make(binding.floatingButton, R.string.error_api, Snackbar.LENGTH_LONG).show();
    }
}