package com.example.flashcardactivity;

import android.content.Intent;
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

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> implements Filterable {

    ArrayList<CardGroup> Mainset;
    private ArrayList<CardGroup> filteredNotes= new ArrayList<CardGroup>();
    public MainAdapter.GroupItemClickListener listener;
    private Filter filter;


    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new SetFilter();
        }
        return filter;
    }

    public MainAdapter(ArrayList<CardGroup> ds, MainAdapter.GroupItemClickListener ls) {

        Mainset= ds;
        filteredNotes = ds;
        listener= ls;

    }
    public class MainViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView count;
        public Button open;


        public MainViewHolder(View itemView) {


            super(itemView);
            name = (TextView) itemView.findViewById(R.id.cardname);
            count = (TextView) itemView.findViewById(R.id.count);
            open = (Button) itemView.findViewById(R.id.slideshow);

         /*   open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //

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



    @NonNull
    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardgroup, parent, false);
        MainAdapter.MainViewHolder vh = new MainAdapter.MainViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {


        String content = Mainset.get(position).getName();
        int eol = content.indexOf("\n");
        holder.name.setText(content.substring(0, eol > 0 ? eol : content.length()));

        holder.itemView.setTag(position);


    }



    @Override
    public int getItemCount() {
        return filteredNotes.size();
    }

    public class SetFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence query) {

            FilterResults result = new FilterResults();

            if (query != null && query.length() > 0) {
                ArrayList<CardGroup> filteredList = new ArrayList<CardGroup>();
                for (int i = 0; i < Mainset.size(); i++) {
                    if (Mainset.get(i).getName().contains(query)){
                        filteredList.add(Mainset.get(i));
                    }
                }

                result.count = filteredList.size();
                result.values = filteredList;
            } else {
                result.count = Mainset.size();
                result.values = Mainset;

            }
            return result;
        }



        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            filteredNotes = (ArrayList<CardGroup>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public void reload ( ArrayList<CardGroup> a){

            Mainset=a;
            filteredNotes= a;



    }

    public interface GroupItemClickListener{

        public void onClick(CardGroup n);
    }

    public void updateData(ArrayList<CardGroup> ds){
        Mainset = ds;
        filteredNotes = Mainset;
        notifyDataSetChanged();
    }

}
