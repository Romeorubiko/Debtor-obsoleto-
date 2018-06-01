package apk.romero.debtor.adapter;

import android.content.Context;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import apk.romero.debtor.R;
import apk.romero.debtor.javaClass.myLists.Debt;

/**
 * Created by Raul on 01/11/2016.
 */


//This is where we fill up the recycler view

public class AdapterHelper extends RecyclerView.Adapter<AdapterHelper.AdapterHolder>{

    private List<Debt> listData;
    private LayoutInflater inflater;
    private Context context;

    private ItemClickCallBack itemClickCallBack;
        //On click events that will handle Mylists class
    public interface ItemClickCallBack{
//      void onItemClick(int p);
        void onDeleteClick(int p, boolean b);
        void onModifyClick(View view ,int p, boolean b);
    }

    public void setItemClickCallBack(final ItemClickCallBack itemClickCallBack){
        this.itemClickCallBack = itemClickCallBack;
    }

    public AdapterHelper (List<Debt> listItem, Context context){
        this.listData = listItem;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_view_list, parent, false);
        return new AdapterHolder(view);
    }

    //Bond a position to its item
    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        Debt item = listData.get(position);
        holder.name.setText(item.getPerson());
        holder.reason.setText(item.getReason());
        DecimalFormat df = new DecimalFormat("0.00");
        holder.amount.setText(df.format(item.getAmount()));
        SimpleDateFormat datef = new SimpleDateFormat("dd"+" "+"MMM"+ ". " + "yyyy");
        if(item.isIown())holder.amount.setTextColor(Color.RED);
        holder.date.setText(datef.format(item.getDate()));
        holder.modify.setImageResource(R.drawable.ic_euro_symbol_white_24dp);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    //nest class to inicialize each item on the recycler view
    class AdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name, reason, amount, date;
        private ImageView modify;
        private View container;

        public AdapterHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.recycled_text_name);
            reason = (TextView) itemView.findViewById(R.id.recycled_text_reason);
            amount = (TextView) itemView.findViewById(R.id.recycled_text_amount);
            date = (TextView) itemView.findViewById(R.id.recycled_date);
            modify = (ImageView) itemView.findViewById(R.id.recycled_modify);
            modify.setOnClickListener(this);
            container = itemView.findViewById(R.id.recycled_layout);
            container.setOnClickListener(this);
        }

        //on click events
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.recycled_modify: itemClickCallBack.onModifyClick(view , getAdapterPosition(), listData.get(getAdapterPosition()).isIown());
                    break;
//                case R.id.recycled_delete: itemClickCallBack.onDeleteClick(getAdapterPosition(),listData.get(getAdapterPosition()).isIown());
//                    break;
//                case R.id.recycled_layout: itemClickCallBack.onItemClick(getAdapterPosition());
//                    break;
            }
        }
    }
}
