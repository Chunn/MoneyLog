package com.rom.rm.moneylog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private ListView lvShow;
    private MoneyAdapter moneyAdapter;
    private FloatingActionButton fbtn_add;
    private List<MoneyLog> moneyLogs;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        registerForContextMenu(lvShow);
        new DoGets().execute("https://192.168.1.11:9000/api/money");
        addMoneyLog();

    }
    public void init(){
        fbtn_add=findViewById(R.id.fbtn_add);
        lvShow=findViewById(R.id.showMoneyLog);
        moneyLogs=new ArrayList<>();
        moneyAdapter=new MoneyAdapter(MainActivity.this,R.layout.row_list,moneyLogs);
        lvShow.setAdapter(moneyAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        final MoneyAdapter moneyAdapter=(MoneyAdapter) item.getMenuInfo();
        int positon=moneyAdapter.getPosition(item);
        String delItem=moneyLogs.get(positon).getContent();
        switch (item.getItemId()){
            case R.id.item_edit: break;
            case R.id.item_delete:
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("Xóa");
                builder.setMessage("Bạn có muốn xóa" +delItem+ "này không?");
                builder.setCancelable(false);
                builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moneyLogs.remove(moneyAdapter.getPosition(item));
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    //Tham số doInBackground, dữ liệu đầu vào, mã lỗi
    class DoGets extends AsyncTask<String,Void,Integer>{

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer==400){
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                Log.d("Test","Failed");
            }
            else if(integer==200)
            {
                
                moneyAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                Log.d("Test","Successful");
            }
            super.onPostExecute(integer);
        }

        @Override
        protected Integer doInBackground(String... paramas) {
            String urlString=paramas[0];
            URL url=null;
            HttpsURLConnection httpsURLConnection=null;
            InputStream inputStream=null;
            String result="";
            int c;
            try {
                url=new URL(urlString);
                httpsURLConnection=(HttpsURLConnection) url.openConnection();

                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setDoInput(true);
                // đẩy dữ liệu lên server Do Output
                httpsURLConnection.connect();
                //cần luồng đọc dữ liệu đầu vào
                inputStream=httpsURLConnection.getInputStream();
                //endoffile=-1
                while ((c=inputStream.read())!=-1){
                    result +=(char)c;

                }
                Log.d("test",result);

                JSONArray jsonArray=new JSONArray(result);
                JSONObject jsonObject;
                List<MoneyLog> moneyLogs=new ArrayList<>();
                for (int i=0;i<jsonArray.length();i++){
                    jsonObject=jsonArray.getJSONObject(i);
                    moneyLogs.add(new MoneyLog(jsonObject.getInt("id"),jsonObject.getString("content"),
                            jsonObject.getInt("amount"),jsonObject.getString("note"),jsonObject.get("date"),
                            jsonObject.getInt("mType")));
                }
                for (MoneyLog moneyLog:moneyLogs){
                    Log.d("Kết quả",moneyLog.toString());
                }
            }catch (Exception ex) {
                ex.printStackTrace();

                return 400;
            }
            return 200;
        }
    }
   
    public void addMoneyLog(){

        fbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
    }
}
