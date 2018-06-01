package apk.romero.debtor.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import apk.romero.debtor.R;
import apk.romero.debtor.adapter.AdapterHelper;
import apk.romero.debtor.javaClass.InternalStorage;
import apk.romero.debtor.javaClass.myLists.Debt;

import apk.romero.debtor.javaClass.myLists.Lists;




public class Mylists extends AppCompatActivity implements AdapterHelper.ItemClickCallBack, TabHost.OnTabChangeListener{

    public static final int RES_OK = 1;
    public static final String ENAME = "name", EAMOUNT = "amount" , EREASON = "reason", EIOWN = "iown";
    public static  Resources res;

    private TabHost tHost;
    private Lists lists;
    private RecyclerView recycleViewiown, recycleViewtheyown;  //recycle view where will be the data list
    private AdapterHelper adapteriown, adaptertheyown;
    private ImageView btBack, btadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylists);
        res = getResources();
        lists = new Lists();

        try {
            lists = (Lists) InternalStorage.readObject(this, Menu.FileMyLists);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // test();
        startTapHost(this.getApplicationContext());
        startListiown();
        startListtheyown();
        startButtons();
    }


//    private void test(){
//        lists.addDebt("Manolo Perez", 9956.3, "non reason");
//        lists.addDebt("Manuel mucho texto por aqui", 99990.2, "because yes");
//        lists.addRight("Elisa", 10, "park");
//        lists.addRight("Marta hernandaz", 10020.30, "rent");
//        lists.addDebt("Elena beteta", 20, "supermarket");
//        lists.addDebt("Daniel Kim", 0.2, "because yes");
//    }


    @Override
    protected void onPause() {
        super.onPause();

        try {
            InternalStorage.writeObject(this, Menu.FileMyLists, lists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startTapHost(Context context){

        tHost = (TabHost)findViewById(R.id.tabHost);
        TabHost.TabSpec spec;
        tHost.setup();
        //Tab 1
        spec = tHost.newTabSpec("TabOne");
        spec.setContent(R.id.tab1);
        spec.setIndicator(context.getString(R.string.iown));
        tHost.addTab(spec);

        //Tab 2
        spec = tHost.newTabSpec("TabTwo");
        spec.setContent(R.id.tab2);
        spec.setIndicator(context.getString(R.string.theyown));
        tHost.addTab(spec);

        tHost.setCurrentTab(0); // Default Selected Tab

        TextView tv = (TextView) tHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextSize(18);

        tv = (TextView) tHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        tv.setTextColor(Color.BLACK);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextSize(18);


        tHost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabID) {
        if (tabID.equals("TabOne")) {
            TextView tv = (TextView) tHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
            tv = (TextView) tHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
            tv.setTextColor(Color.BLACK);
        } else if (tabID.equals("TabTwo")) {
            TextView tv = (TextView) tHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
            tv.setTextColor(Color.BLACK);
            tv = (TextView) tHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
        }

    }

    //data display in the recycle view
    private void startListiown(){
        recycleViewiown = (RecyclerView)findViewById(R.id.recycler_view_iown);
        recycleViewiown.setLayoutManager(new LinearLayoutManager(this));
        adapteriown = new AdapterHelper(this.getRecycleDataiown(), this);
        recycleViewiown.setAdapter(adapteriown);
        adapteriown.setItemClickCallBack(this);
    }

    private void startListtheyown(){
        recycleViewtheyown = (RecyclerView)findViewById(R.id.recycler_view_theyown);
        recycleViewtheyown.setLayoutManager(new LinearLayoutManager(this));
        adaptertheyown = new AdapterHelper(this.getRecyleDatatheyown(), this);
        recycleViewtheyown.setAdapter(adaptertheyown);
        adaptertheyown.setItemClickCallBack(this);
    }

    //this prevents from errors
    public List<Debt> getRecycleDataiown() {
        return lists.getIown();
    }

    public  List<Debt> getRecyleDatatheyown(){
        return lists.getTheyown();
    }

    //just a graphic back button and add new item
    private void startButtons(){

        btBack = (ImageView) findViewById(R.id.bt_mylists_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            //back to menu
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Menu.class));
                finish();
            }
        });
        //add new item
        btadd= (FloatingActionButton) findViewById(R.id.bt_add);
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), NewItem.class), RES_OK);
            }
        });
        btadd.bringToFront();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent extra){
        if (requestCode == RES_OK && resultCode == RESULT_OK ){
            String name, reason;
            double amount;
            boolean iown;
            name = extra.getStringExtra(ENAME);
            reason = extra.getStringExtra(EREASON);
            amount = extra.getDoubleExtra(EAMOUNT,0);
            iown = extra.getBooleanExtra(EIOWN,false);
            if (iown){
                lists.addDebt(name,amount, reason);
                adapteriown.notifyItemInserted(0);
            }
            else {
                lists.addRight(name ,amount, reason);
                adaptertheyown.notifyItemInserted(0);
            }

        }
    }


    //take over the methods of onClick events
//    @Override
//    public void onItemClick(int p) {
//        ListItem item = (ListItem) recycleDataiown.get(p); // the cast is unnecessary but prevents from errors
//        Intent i = new Intent(this, DetailLists.class);
//
//        Bundle extras = new Bundle();
//        extras.putString(EXTRA_QUOTE,item.getName());
//        extras.putDouble(EXTRA_ATTR,item.getAmount());
//        i.putExtra(BUNDLE_EXTRAS, extras);
//
//        startActivity(i);
//    }
//
    @Override
    public void onDeleteClick(int position, boolean b) {
        if(b) {
            lists.getIown().remove(position);
            adapteriown.notifyItemRemoved(position);
        }
        else{
            lists.getTheyown().remove(position);
            adaptertheyown.notifyItemRemoved(position);
        }
    }
// creates a popup to modify the amount.
    @Override
    public void onModifyClick(View view, final int p, final boolean b) {
        LayoutInflater li =(LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = li.inflate(R.layout.pop_up_amount, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown((ImageView)view , -250, 0);

        //content of the popout
        DecimalFormat df = new DecimalFormat("0.00");
        if(b) {//this is iown
            ((TextView) popupView.findViewById(R.id.tv_pu_amount)).setText(df.format(lists.getIown().get(p).getAmount()));
            ((TextView) popupView.findViewById(R.id.tv_pu_amount)).setTextColor(Color.RED);
            ((TextView) popupView.findViewById(R.id.tv_pu_total)).setText(df.format(lists.getIown().get(p).getAmount()));
        }
        else{//this is they own
            ((TextView) popupView.findViewById(R.id.tv_pu_amount)).setText(df.format(lists.getTheyown().get(p).getAmount()));
            ((TextView) popupView.findViewById(R.id.tv_pu_total)).setText(df.format(lists.getTheyown().get(p).getAmount()));
        }

        //buttons in the pop up

        //dynamics view of the changes that the user wants to apply
        EditText input = (EditText) popupView.findViewById(R.id.bt_pu_input);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((TextView)view).getText().toString().equals(getResources().getString(R.string.S000)))((TextView)view).setText(new String());
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DecimalFormat df = new DecimalFormat("0.00");
                double result = 0;
                double newone;

                if(charSequence.length() != 0) newone = Double.parseDouble(charSequence.toString().replace(",","."));
                else newone = 0;

                double fixed = Double.parseDouble(((TextView) popupView.findViewById(R.id.tv_pu_amount)).getText().toString().replace(",","."));
                if ((int) (((ImageView) popupView.findViewById(R.id.bt_pu_plus)).getTag()) == android.R.drawable.ic_input_add)
                    result = newone + fixed;
                else{
                    if (fixed - newone < 0)result = 0;
                    else result = fixed - newone;
                }
                ((TextView) popupView.findViewById(R.id.tv_pu_total)).setText(df.format(result));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //changes + and -
        ImageView plus = (ImageView) popupView.findViewById(R.id.bt_pu_plus);
        plus.setTag(android.R.drawable.button_onoff_indicator_off);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView iv = (ImageView) view;
                if(((int)iv.getTag()) == android.R.drawable.ic_input_add){
                    iv.setImageResource(android.R.drawable.button_onoff_indicator_off);
                    iv.setTag(android.R.drawable.button_onoff_indicator_off);
                }
                else {
                    iv.setImageResource(android.R.drawable.ic_input_add);
                    iv.setTag(android.R.drawable.ic_input_add);
                }
                ((EditText) popupView.findViewById(R.id.bt_pu_input)).setText(((EditText) popupView.findViewById(R.id.bt_pu_input)).getText());
            }
        });
        //add all
        Button all = (Button) popupView.findViewById(R.id.bt_pu_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText) popupView.findViewById(R.id.bt_pu_input)).setText(((TextView) popupView.findViewById(R.id.tv_pu_amount)).getText().toString());
            }
        });
        //confirms the operation
        Button confirm = (Button) popupView.findViewById(R.id.bt_pu_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount = Double.parseDouble(((TextView) popupView.findViewById(R.id.tv_pu_total)).getText().toString().replace(",","."));
                if (amount == 0){
                    onDeleteClick(p,b);
                }
                else{
                    if(b){
                        lists.setAmount(lists.getIown().get(p).getId(),amount);
                        adapteriown.notifyItemChanged(p);
                    }
                    else{
                        lists.setAmount(lists.getTheyown().get(p).getId(),amount);
                        adaptertheyown.notifyItemChanged(p);
                    }

                }

                popupWindow.dismiss();
            }
        });
    }


}

