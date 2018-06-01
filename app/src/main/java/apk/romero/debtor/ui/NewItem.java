package apk.romero.debtor.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import apk.romero.debtor.R;

public class NewItem extends AppCompatActivity {

    private EditText name,amount, reason;
    private RadioButton iown;
    private Button btCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);



        name = (EditText) findViewById(R.id.tx_name);
        amount = (EditText) findViewById(R.id.tx_amount);
        reason = (EditText) findViewById(R.id.tx_reason);
        iown = (RadioButton) findViewById(R.id.rb_iown);

        btCreate = (Button) findViewById(R.id.bt_create);
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("") || amount.getText().toString().equals("") || reason.getText().toString().equals("") || Double.parseDouble(amount.getText().toString()) == 0){
                    Snackbar.make(view, view.getResources().getString(R.string.SErroronfields), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    Intent extra = new Intent();
                    extra.putExtra(Mylists.ENAME,name.getText().toString());
                    extra.putExtra(Mylists.EAMOUNT, Double.parseDouble(amount.getText().toString()));
                    extra.putExtra(Mylists.EREASON, reason.getText().toString());
                    extra.putExtra(Mylists.EIOWN, iown.isChecked());
                    setResult(RESULT_OK, extra);
                    finish();
                }
            }
        });

        ImageView btBack = (ImageView) findViewById(R.id.bt_mylists_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            //back to menu and no create a new item
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}
