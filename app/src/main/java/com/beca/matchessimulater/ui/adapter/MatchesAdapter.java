package com.beca.matchessimulater.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beca.matchessimulater.databinding.MatchItemBinding;
import com.beca.matchessimulater.domain.Match;
import com.beca.matchessimulater.ui.DetailActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder>{

    private List<Match> matches;

    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    @NonNull
    @Override
    //Instanciar o viewHolder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MatchItemBinding binding = MatchItemBinding.inflate(layoutInflater,parent, false);//
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context =  holder.itemView.getContext();
        Match match = matches.get(position);
        //adapta os dados da partida (recuperada da API) para o nosso layout

        //Passar o contexto > ler o url > obter a imagem > deixar a imagem redonda com circleCrop() > carregar (into) a imagem no binding
        Glide.with(context).load(match.getHomeTeam().getImage()).circleCrop().into(holder.binding.ivHomeTeam);
        holder.binding.tvHomeTeamName.setText(match.getHomeTeam().getName());
        //Para mostrar o score na tela
        if(match.getHomeTeam().getScore() != null){
            //setText é forçado que o resultado seja string atraves do string.valueof
            holder.binding.tvHomeTeamScore.setText(String.valueOf(match.getHomeTeam().getScore()));
        }
        Glide.with(context).load(match.getAwayTeam().getImage()).circleCrop().into(holder.binding.ivAwayTeam);
        holder.binding.tvAwayTeamName.setText(match.getAwayTeam().getName());

        if(match.getAwayTeam().getScore() != null){
            holder.binding.tvAwayTeamScore.setText(String.valueOf(match.getAwayTeam().getScore()));
        }
        holder.itemView.setOnClickListener(view ->{
            Intent intent = new Intent(context, DetailActivity.class);
            //acessar os extras e passa-las da partida
            intent.putExtra(DetailActivity.Extras.MATCH, match);
            context.startActivity(intent);
        });
    }
    //tamanho da lista
    @Override
    public int getItemCount() {

        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MatchItemBinding binding;

        public ViewHolder(MatchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
