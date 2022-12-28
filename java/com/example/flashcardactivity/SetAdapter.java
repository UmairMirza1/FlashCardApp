package com.example.flashcardactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetViewHolder> implements Filterable {

    private ArrayList<Card> list;
    private ArrayList<Card> filteredNotes;
    public CardItemClickListener listener;
    private Filter filter;

    public SetAdapter(ArrayList<Card> ds, CardItemClickListener ls) {

        list = ds;
        filteredNotes = ds;
        listener = ls;

    }

    public class SetViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public Button open;


        public SetViewHolder(View itemView) {


            super(itemView);

            name = (TextView) itemView.findViewById(R.id.cardname);
            open = (Button) itemView.findViewById(R.id.Open_card);

          /*open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    // intent pass krana hai card ka

                }
            });*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                      listener.onClick(filteredNotes.get(pos));
                }
            });
        }

    }


    @Override
    public SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_set, parent, false);
        SetViewHolder vh = new SetViewHolder(v);
        return vh;


    }

    @Override
    public void onBindViewHolder(@NonNull SetAdapter.SetViewHolder holder, int position) {

        String content = filteredNotes.get(position).getFront();
        int eol = content.indexOf("\n");
        holder.name.setText(content.substring(0, eol > 0 ? eol : content.length()));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return filteredNotes.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CardFilter();
        }
        return filter;
    }



    public interface CardItemClickListener {
        public default void onClick(Card n) {
        }

    }


    public class CardFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence query) {

            FilterResults result = new FilterResults();

            if (query != null && query.length() > 0) {
                ArrayList<Card> filteredList = new ArrayList<Card>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getBack().contains(query) || list.get(i).getFront().contains(query)) {
                        filteredList.add(list.get(i));
                    }
                }

                result.count = filteredList.size();
                result.values = filteredList;
            } else {
                result.count = list.size();
                result.values = list;

            }
            return result;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            filteredNotes = (ArrayList<Card>) filterResults.values;
            notifyDataSetChanged();
        }
    }


}






