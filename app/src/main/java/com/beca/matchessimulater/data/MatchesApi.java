package com.beca.matchessimulater.data;

import com.beca.matchessimulater.domain.Match;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MatchesApi {

    //Acesso aos recursos da api
    @GET("matches.json")
    Call<List<Match>> getMaches();
}
