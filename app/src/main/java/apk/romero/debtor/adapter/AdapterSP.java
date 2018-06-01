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
import java.util.List;

import apk.romero.debtor.R;
import apk.romero.debtor.javaClass.deals.PaymentIndv;

/**
 * Created by Raul on 29/12/2016.
 */

public class AdapterSP extends RecyclerView.Adapter<AdapterSP.AdapterHolder>{

private List<PaymentIndv> listData;
private LayoutInflater inflater;
private Context context;

private ItemClickCallBack itemClickCallBack;
//On click events that will handle Mylists class
public interface ItemClickCallBack{
    //      void onItemClick(int p);
    void onDeleteClick(int p);
//    void onModifyClick(View view , int p, boolean b);
}

    public void setItemClickCallBack(final AdapterSP.ItemClickCallBack itemClickCallBack){
        this.itemClickCallBack = itemClickCallBack;
    }

    public AdapterSP (List<PaymentIndv> listItem, Context context){
        this.listData = listItem;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AdapterSP.AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_view_shared_payment, parent, false);
        return new AdapterSP.AdapterHolder(view);
    }

//    Bond a position to its item
    @Override
    public void onBindViewHolder(AdapterSP.AdapterHolder holder, int position) {
        PaymentIndv item = listData.get(position);
        holder.name.setText(item.getName());
        DecimalFormat df = new DecimalFormat("0.00");
        holder.own.setText(df.format(item.getOwe()));
        holder.paid.setText(df.format(item.getPaid()));
        holder.delete.setImageResource(android.R.drawable.ic_delete);
        holder.delete.setColorFilter(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


//nest class to inicialize each item on the recycler view
class AdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView name, own, paid;
    private ImageView delete;
    private View container;

    public AdapterHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.recycled_text_name);
        own = (TextView) itemView.findViewById(R.id.tv_recycled_own);
        paid = (TextView) itemView.findViewById(R.id.tv_recycled_paid);
        delete = (ImageView) itemView.findViewById(R.id.iv_recycled_delete);
        delete.setOnClickListener(this);
        container = itemView.findViewById(R.id.recycled_layout_sp);
        container.setOnClickListener(this);
    }

    //on click events
    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.recycled_modify: itemClickCallBack.onModifyClick(view , getAdapterPosition(), listData.get(getAdapterPosition()).isIown());
//                break;
            case R.id.iv_recycled_delete: itemClickCallBack.onDeleteClick(getAdapterPosition());
                break;
//                case R.id.recycled_layout: itemClickCallBack.onItemClick(getAdapterPosition());
//                    break;
        }
    }
}
}
