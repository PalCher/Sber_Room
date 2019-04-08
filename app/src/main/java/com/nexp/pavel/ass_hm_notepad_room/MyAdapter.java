package com.nexp.pavel.ass_hm_notepad_room;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Note> notes;

    public MyAdapter(List<Note> list) {
        this.notes = list;
    }


    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(notes.get(position).title);
        holder.date.setText(notes.get(position).lastDate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(UpdateNote.newIntent(v.getContext(), (int) notes.get(position).id));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (notes == null){
            return 0;

        }
        return notes.size();
    }

    public void refreshData(List<Note> newList){

        //Чистим коллекцию с данными
        notes.clear();

        //наполняем измененными данными
        notes=newList;

        //передергиваем адаптер
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView date;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rvNoteTitle);
            date = itemView.findViewById(R.id.rvNoteDate);
        }
    }

}
