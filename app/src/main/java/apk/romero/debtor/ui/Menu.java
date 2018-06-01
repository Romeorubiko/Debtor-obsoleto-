package apk.romero.debtor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import apk.romero.debtor.R;

public class Menu extends AppCompatActivity {
    public static String FileMyLists = "MyLists";

    private Button btLists, btDeal, btExit;
//    private Lists lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //test();


        startButtons();

    }

    private void test() {
        startActivity(new Intent(getApplicationContext(), Mylists.class));
    }

    private void startButtons() {
        //button 1 - My Lists
        btLists = (Button) findViewById(R.id.bt_mylists);
        btLists.setOnClickListener(new View.OnClickListener() {
            //starts activity Mylists
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Mylists.class));
                finish();

            }
        });

        //button 2 - Deal
        btDeal = (Button) findViewById(R.id.bt_deal);
        btDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SharedPayment.class));
                finish();
            }
        });

        //button 3 - Exit
        btExit = (Button) findViewById(R.id.bt_exit);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}