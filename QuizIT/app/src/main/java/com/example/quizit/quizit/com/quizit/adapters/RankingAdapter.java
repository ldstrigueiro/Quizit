package com.example.quizit.quizit.com.quizit.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.ImagemPojo;
import com.example.quizit.quizit.com.quizit.objetos.RankingObject;

import org.w3c.dom.Text;

import java.util.List;

public class RankingAdapter extends BaseAdapter {
    private RankingObject rankingObject;
    private Context context;
    private List<RankingObject> rankingList;

    public RankingAdapter(Context context, List<RankingObject> rankingList) {
        this.context = context;
        this.rankingList = rankingList;
    }

    @Override
    public int getCount() {
        return rankingList.size();
    }

    @Override
    public Object getItem(int position) {
        return rankingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        rankingObject = (RankingObject) getItem(position);
        View linha = LayoutInflater.from(context).inflate(R.layout.item_ranking, null);

        ImageView avatar = linha.findViewById(R.id.imgAvatarRank);
        TextView rank = linha.findViewById(R.id.txtPosicaoRank);
        TextView nome = linha.findViewById(R.id.txtNomeRank);
        TextView pontos = linha.findViewById(R.id.txtPontosRank);

        avatar.setImageResource(ImagemPojo.idImagem[rankingObject.getAvatar()]);
        rank.setText("#"+rankingObject.getRanking());
        nome.setText(rankingObject.getNome());
        pontos.setText(rankingObject.getPontos());

        nome.setTypeface(null, Typeface.BOLD);
        return linha;

    }
}
