package com.example.notdefteri;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Serializable {

    public static final String TAG = "MyAdapter";
    public ArrayList<String> mDersNotlari = new ArrayList<>();
    public Context mContext;
    public ArrayList<Gorev> gorevler;
    public List<Integer> mSelectedItemsIds;
    public Iterator<Integer> iterator;
    public static FloatingActionButton gorevSil;
    //private static FloatingActionButton gorevSilButtonAction;



    //interface OnItemCheckListener {
    //    void onItemCheck(Gorev gorev);
    //    void onItemUncheck(Gorev gorev);
    //}

    public MyAdapter(Context mContext, ArrayList<Gorev> gorevler) {
        this.mContext = mContext;
        this.gorevler =  gorevler;
        this.mSelectedItemsIds = new ArrayList<>();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkbox;
        CircleImageView image;
        TextView gorevBasligi, oncelik;
        RelativeLayout parentLayout;
        Button saatButon, tarih;
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            gorevBasligi = itemView.findViewById(R.id.gorevBasligiTextView);
            oncelik = itemView.findViewById(R.id.oncelikTextView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            checkbox = itemView.findViewById(R.id.checkBox);
            gorevSil = itemView.findViewById(R.id.notSil);
            saatButon = itemView.findViewById(R.id.buttonSaatSec);
            //gorevSilButtonAction = itemView.findViewById(R.id.notSil);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Gorev currentItem = gorevler.get(position);


        Log.d(TAG, "onBindViewHolder: called.");

        // Download the pictures from URL
        // Glide.with(mContext)
        //         .asBitmap()
        //         .load(mImages.get(position))
        //         .into(holder.image);

        RelativeLayout layout = holder.parentLayout;
        //layout.setBackgroundColor(Color.BLUE);
        String oncelikk = gorevler.get(position).oncelik.toString();
        //Log.d("renk:-", oncelikk);


        if (oncelikk.equals("Yüksek") ) {
            layout.setBackgroundColor(Color.RED);
        }
        else if (oncelikk.equals("Orta") ) {
            layout.setBackgroundColor(Color.YELLOW);
        }
        else if (oncelikk.equals("Düşük") ){
            layout.setBackgroundColor(Color.GREEN);
        }
        //layout.setBackgroundColor(Color.RED);


        holder.gorevBasligi.setText(gorevler.get(position).getBaslik());
        holder.oncelik.setText(gorevler.get(position).getOncelik());
        //int c = (Integer) evaluate(0.01, Color.parseColor("#dddddd"), Color.parseColor("#4C535B"));

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck){
                    Log.d("bas", String.valueOf(position));
                    mSelectedItemsIds.add(position);

                }
                else{
                    Log.d("Çek", String.valueOf((position)));
                    //mSelectedItemsIds.remove(mSelectedItemsIds.indexOf(position));
                    iterator = mSelectedItemsIds.iterator();
                    while(iterator.hasNext())
                    {
                        Integer value = iterator.next();
                        if (value == position)
                        {
                            iterator.remove();
                            break;
                        }
                    }
                }
                //Log.d("sayisi: ", String.valueOf(mSelectedItemsIds.size()));
                //if (mSelectedItemsIds.size() >0 ){
                //    gorevSilButtonAction.show();
                //}
                //else if (mSelectedItemsIds.size() <= 0 ){
                //    gorevSilButtonAction.hide();
                //}
            }
        });



        //holder.checkbox.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        if (((CheckBox) view).isChecked()) {
        //            Log.d("bas", String.valueOf(position));
        //            mSelectedItemsIds.add(position);
        //        }
        //        else {
        //            Log.d("Çek", String.valueOf(position));
        //            mSelectedItemsIds.remove(position);
        //        }
        //
        //    }
        //});




        //((MyViewHolder) holder).setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        ((MyViewHolder) holder).checkbox.setChecked(
        //                !((MyViewHolder) holder).checkbox.isChecked());
        //        if (((MyViewHolder) holder).checkbox.isChecked()) {
        //            Log.d("isChecked: ", "basti");
        //            onItemClick.onItemCheck(currentItem);
        //        } else {
        //            Log.d("isChecked: ", "cekti");
        //            onItemClick.onItemUncheck(currentItem);
        //        }
        //    }
        //});


        /* Bu kısım göreve basıldığında DÜZENLE için açılacak kısım */
        //holder.parentLayout.setOnClickListener(new View.OnClickListener(){
        //    @Override
        //    public void onClick(View view) {
        //        Log.d(TAG, "onClick: click on: "+ gorevler.get(position).getBaslik());
        //        Intent intent = new Intent(mContext, dersAyrinti.class);
        //        intent.putExtra("dersAdi", gorevler.get(position).getDersIsmi());
        //        intent.putExtra("notOrt", gorevler.get(position).getNotOrt());
        //        intent.putExtra("hocaAdi", gorevler.get(position).getHocaAdi());
        //        intent.putExtra("ogrSayisi", gorevler.get(position).getOgrSayisi());
        //        mContext.startActivity(intent);
        //        Toast.makeText(mContext, gorevler.get(position).getDersIsmi(), Toast.LENGTH_SHORT).show();
        //    }
        //});

    }

    public List<Integer> getSelectedIds() {
        return mSelectedItemsIds;
    }


    public int getSelectedIdCount(){
        return mSelectedItemsIds.size();
    }

    @Override
    public int getItemCount() {
        return gorevler.size();
    }
}
