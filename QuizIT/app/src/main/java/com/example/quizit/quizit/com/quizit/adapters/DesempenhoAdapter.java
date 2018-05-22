package com.example.quizit.quizit.com.quizit.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.AreaRatio;
import com.example.quizit.quizit.com.quizit.objetos.IconeAreaPojo;
import com.example.quizit.quizit.com.quizit.objetos.ImagemPojo;

import java.util.List;

public class DesempenhoAdapter extends BaseAdapter {

    private AreaRatio areaRatio;
    private Context context;
    private List<AreaRatio> areaList;

    public DesempenhoAdapter (Context context, List<AreaRatio> areaList){
        this.context = context;
        this.areaList = areaList;
    }

    @Override
    public int getCount() {
        return areaList.size();
    }

    @Override
    public Object getItem(int position) {
        return areaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        areaRatio = (AreaRatio) getItem(position);

        View linha = LayoutInflater.from(context).inflate(R.layout.item_desempenho, null);

        TextView txtAcertos = linha.findViewById(R.id.txtAcertosDesempenho);
        TextView txtErros = linha.findViewById(R.id.txtErrosDesempenho);
        ImageView imageView = linha.findViewById(R.id.imgAreaDesempenho);
        TextView txtArea = linha.findViewById(R.id.txtAreaDesempenho);
        TextView txtRatio = linha.findViewById(R.id.txtRatio);

        Resources resources = context.getResources();

        //TypedArray typedArray = resources.obtainTypedArray(R.array.logos);


        imageView.setImageResource(IconeAreaPojo.idImagem[position]);
        txtArea.setText(areaRatio.getArea());
        txtRatio.setText(String.valueOf(areaRatio.getRatio()));
        txtAcertos.setText(String.valueOf(areaRatio.getAcertos()));
        txtErros.setText(String.valueOf(areaRatio.getErros()));

        txtAcertos.setTypeface(null, Typeface.BOLD);
        txtErros.setTypeface(null, Typeface.BOLD);
        txtRatio.setTypeface(null, Typeface.BOLD);
        return linha;
    }
}
