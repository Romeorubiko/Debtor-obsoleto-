package apk.romero.debtor.ui;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import apk.romero.debtor.R;
import apk.romero.debtor.adapter.AdapterSP;
import apk.romero.debtor.javaClass.deals.PaymentColl;
import apk.romero.debtor.javaClass.deals.PaymentIndv;

public class SharedPayment extends AppCompatActivity implements AdapterSP.ItemClickCallBack{

    public static Resources res;

    private PaymentColl paymentColl;
    private RecyclerView recycledPS;
    private AdapterSP adapterSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_payment);

        test();

        res = getResources();

        startRecycledView();
    }

    private void test(){
        paymentColl = new PaymentColl();
        paymentColl.add(new PaymentIndv(10,20,"paco"));
        paymentColl.add(new PaymentIndv(15.23,20,"maria"));
        paymentColl.add(new PaymentIndv(16.3,20,"juan"));
        paymentColl.add(new PaymentIndv(1000,250,"miguel"));
    }

    private void startRecycledView() {
        recycledPS = (RecyclerView)findViewById(R.id.recycled_view);
        recycledPS.setLayoutManager(new LinearLayoutManager(this));
        adapterSP = new AdapterSP(this.getPaymentIndv(), this);
        recycledPS.setAdapter(adapterSP);
        adapterSP.setItemClickCallBack(this);
    }

    private List<PaymentIndv> getPaymentIndv (){
        return paymentColl.getList();
    }
    @Override
    public void onDeleteClick(int position) {
        paymentColl.getList().remove(position);
        adapterSP.notifyItemRemoved(position);
    }
}
