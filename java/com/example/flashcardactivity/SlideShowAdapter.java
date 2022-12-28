package com.example.flashcardactivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class SlideShowAdapter extends RecyclerView.Adapter<SlideShowAdapter.SlideShowViewHolder> {

   private ArrayList<Card> setcard;




    public SlideShowAdapter(ArrayList<Card> ds) {

        setcard=ds;

    }

    public class SlideShowViewHolder extends RecyclerView.ViewHolder {

        public TextView front;
        public TextView back;



        public SlideShowViewHolder(View itemView) {
            super(itemView);
            front = (TextView) itemView.findViewById(R.id.text_front);
            back = (TextView) itemView.findViewById(R.id.text_back);

        }

    }

    @NonNull
    @Override
    public SlideShowAdapter.SlideShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card, parent, false);
        SlideShowAdapter.SlideShowViewHolder vh = new SlideShowAdapter.SlideShowViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SlideShowViewHolder holder, int position) {

        String content = setcard.get(position).getFront();
        String back = setcard.get(position).getFront();
        int eol = content.indexOf("\n");
        holder.front.setText(content.substring(0, eol > 0 ? eol : content.length()));
        int eol2 = back.indexOf("\n");
        holder.front.setText(content.substring(0, eol2 > 0 ? eol2 : content.length()));
        holder.itemView.setTag(position);

    }



    @Override
    public int getItemCount() {
        return setcard.size();
    }
}