package com.rom.rm.moneylog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Rơm on 4/6/2018.
 */

public class EditActivity extends AppCompatActivity {
    private EditText edt_content;
    private EditText edt_amount;
    private EditText edt_note;
    private EditText edt_date;

    private RadioGroup radioGroup;
    private RadioButton radioThu;
    private RadioButton radioChi;

    private Button btn_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

    }
    public void init(){
        edt_content=findViewById(R.id.edt_content);
        edt_amount=findViewById(R.id.edt_amount);
        edt_note=findViewById(R.id.edt_note);
        edt_date=findViewById(R.id.edt_date);

        radioGroup=findViewById(R.id.radio_group);
        radioThu=findViewById(R.id.radio_thu);
        radioChi=findViewById(R.id.radio_chi);

        btn_save=findViewById(R.id.btn_save);
    }

}
