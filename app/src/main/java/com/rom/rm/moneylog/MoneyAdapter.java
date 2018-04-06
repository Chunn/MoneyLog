package com.rom.rm.moneylog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rơm on 4/6/2018.
 */

public class MoneyAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List <MoneyLog> moneyLogs;

    public MoneyAdapter(@NonNull Context context, int resource, List<MoneyLog> moneyLogs) {
        super(context, resource,moneyLogs);
        this.context = context;
        this.resource = resource;
        this.moneyLogs = moneyLogs;
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.row_list,null);
        }
        TextView tvContent = view.findViewById(R.id.tvContent);
        tvContent.setText(moneyLogs.get(i).getContent());

        TextView tvDate = view.findViewById(R.id.tvDate);
        tvDate.setText(moneyLogs.get(i).getDate().toString());

        TextView tvAmount = view.findViewById(R.id.tvAmount);
        tvAmount.setText(moneyLogs.get(i).getAmount());

        TextView tvNote = view.findViewById(R.id.tvNote);
        tvNote.setText(moneyLogs.get(i).getNote());

        ImageView img=view.findViewById(R.id.mType);
        img.setImageResource(moneyLogs.get(i).getmType());

        return view;

    }
}
