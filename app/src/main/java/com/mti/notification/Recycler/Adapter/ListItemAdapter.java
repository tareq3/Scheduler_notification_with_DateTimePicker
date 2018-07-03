/*
 * Created by Tareq Islam on 7/3/18 12:45 AM
 *
 *  Last modified 7/2/18 11:43 PM
 */

/*
 * Created by Tareq Islam on 6/22/18 10:52 PM
 *
 *  Last modified 6/22/18 10:52 PM
 */

package com.mti.notification.Recycler.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mti.notification.MainActivity;
import com.mti.notification.R;
import com.mti.notification.Recycler.ModelEntity.AlarmModel;


import java.util.List;

/***
 * Created by Tareq on 22,June,2018.
 */

 class ListItemViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    ItemClickListener mItemClickListener;
    TextView item_title, item_description;


    public ListItemViewHolder(View itemView) {
        super(itemView);

        item_title = itemView.findViewById(R.id.item_Title);
        item_description = itemView.findViewById(R.id.item_Description);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    //Todo: Need setItemClickListener to use onClick funtion with holder object
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View v) {
        mItemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the action");
        menu.add(0, 0,getAdapterPosition() , "DELETE");

    }

}


public class ListItemAdapter extends RecyclerView.Adapter<ListItemViewHolder> {

    MainActivity mMainActivity;

    List<AlarmModel> mToDoListFiltered;


    public ListItemAdapter(MainActivity mainActivity, List<AlarmModel> toDoList) {
        mMainActivity = mainActivity;
        mToDoListFiltered = toDoList;

    }

    @NonNull
       @Override
       public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mMainActivity.getBaseContext());

        View view=inflater.inflate(R.layout.list_item,parent,false);


           return new ListItemViewHolder(view);
       }

       @Override
       public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
//Todo: Load Data

           //Set data for item
           holder.item_title.setText(mToDoListFiltered.get(position).getAlramId()+" : " +mToDoListFiltered.get(position).getTitle());
           holder.item_description.setText("At: "+ MainActivity.ConvertMilliSecondsToFormattedDate(mToDoListFiltered.get(position).getSec()));

           holder.setItemClickListener(new ItemClickListener() {
               @Override
               public void onClick(View view, int position, boolean isLongClick) {
                   //when user select item, data will auto set for text view
                 /*  mMainActivity.title.setText(mToDoListFiltered.get(position).getTitle());
                   mMainActivity.description.setText(mToDoListFiltered.get(position).getDescription());

                   mMainActivity.isUpdate=true; //Set flag is update -true


                   mMainActivity.idSelectedItem= mToDoListFiltered.get(position).getAlramId();
*/
               }
           });
       }

       @Override
       public int getItemCount() {
           return mToDoListFiltered.size();
       }

       //this method for filtering
  }
