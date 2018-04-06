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
        addMoneyLog();

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
      public void addMoneyLog(){
        int id=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(id);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoPost().execute("https://192.168.1.11:9000/api/money", edt_content.getText().toString()
                        ,edt_amount.getText().toString(),edt_note.getText().toString()
                        ,edt_date.getText().toString(),radioButton.getText().toString());
            }
        });

    }
     //Tham số doInBackground, dữ liệu đầu vào, mã lỗi
    class DoPost extends AsyncTask<String,Void,Integer> {

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 400) {
                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                Log.d("Test", "Failed");
            } else if (integer == 200) {
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                Log.d("Test", "Successful");
            }
            super.onPostExecute(integer);
        }

        @Override
        protected Integer doInBackground(String... paramas) {
            String urlString = paramas[0];
            URL url = null;
            HttpsURLConnection httpsURLConnection = null;
            InputStream inputStream = null;
            OutputStream outputStream;
            String result = "";
            int c;
            try {
                url = new URL(urlString);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();

                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                // đẩy dữ liệu lên server Do Output
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setRequestProperty("Content-Type","application/json");
                httpsURLConnection.setRequestProperty("Accept","application/json");

                JSONObject jsonObject=new JSONObject();
                jsonObject.put("content",paramas[0]);
                jsonObject.put("amount",paramas[1]);
                jsonObject.put("note",paramas[2]);
                jsonObject.put("date",paramas[3]);
                jsonObject.put("mType",paramas[4]);

                outputStream = new BufferedOutputStream(httpsURLConnection.getOutputStream());
                outputStream.write(jsonObject.toString().getBytes(Charset.forName("UTF-8")));
                outputStream.flush();
                outputStream.close();

                inputStream=httpsURLConnection.getInputStream();
                //endoffile=-1
                while ((c = inputStream.read()) != -1) {
                    result += (char) c;

                }
                Log.d("test", result);

                JSONArray jsonArray = new JSONArray(result);
                List<MoneyLog> books = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    moneyLogs.add(new MoneyLog(jsonObject.getInt("id"), jsonObject.getString("content"),
                            jsonObject.getInt("amount"), jsonObject.getString("note"), jsonObject.get("date"),
                            jsonObject.getInt("mType")));
                }
                for (MoneyLog moneyLog : moneyLogs) {
                    Log.d("Kết quả", moneyLog.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace();

                return 400;
            }
            return 200;
        }
    }

}
