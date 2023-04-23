package com.example.quotes;

import static com.example.quotes.R.id.progressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ProgressBar progressBar;
    SearchView search;
    HashMap<String, String> hashMap;
    ArrayList< HashMap<String, String> > arrayList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridView);
        search = findViewById(R.id.search);
        progressBar = findViewById(R.id.progressBar);


        //=====================================================



        if (!CheckInternet()){
        }




        //data persing from server in using json=============
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://dummyjson.com/quotes";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //////////////////
                        progressBar.setVisibility(View.GONE);
                        Log.d("serverRes", response.toString());


                        try {
                            JSONArray jsonArray = response.getJSONArray("quotes");

                            for (int x=0; x<jsonArray.length(); x++){
                                //
                                JSONObject jsonObject = jsonArray.getJSONObject(x);

                                String quote = jsonObject.getString("quote");
                                String author = jsonObject.getString("author");

                                //arraylist start=================
                                hashMap = new HashMap<>();
                                hashMap.put("quote", quote);
                                hashMap.put("author", author);
                                arrayList.add(hashMap);
                                //end
                                //
                            }

                            //////
                            myAdaptar adaptar = new myAdaptar();
                            gridView.setAdapter(adaptar);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                CheckInternet();

            }
        });

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jsonObjectRequest);
        //end=================





        //searchView=============







    }//=======================================================================================




    //create a adaptar========
    public class myAdaptar extends BaseAdapter{
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.item, null);

            hashMap = arrayList.get(position);
            //id add==========
            LinearLayout itemlly = myView.findViewById(R.id.itemlly);
            TextView quote = myView.findViewById(R.id.quote);
            TextView righter = myView.findViewById(R.id.righter);
            TextView numComment = myView.findViewById(R.id.numComment);
            TextView numLove = myView.findViewById(R.id.numLove);
            ImageView commentImg = myView.findViewById(R.id.commentImg);
            ImageView loveImg = myView.findViewById(R.id.loveImg);

            //
            String Quote = hashMap.get("quote");
            String Author = hashMap.get("author");

            //data add=========
            quote.setText(Quote);
            righter.setText(Author);
            loveImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loveImg.setImageResource(R.drawable.love);
                }
            });
            itemlly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MainActivity2.QuoteParse = Quote;
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));

                }
            });

            return myView;
        }
    }
    //====================================
    //====================================






    //=======================================
    private boolean CheckInternet(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo==null || !networkInfo.isAvailable() || !networkInfo.isConnected()){
            Dialog dialog = new Dialog(MainActivity.this, R.style.InternetDialog);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.setContentView(R.layout.no_internet);
            dialog.setCancelable(false);
            //
            SwipeRefreshLayout swiplly = dialog.findViewById(R.id.swiplly);
            swiplly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    recreate();
                    swiplly.setRefreshing(false);
                }
            });
            dialog.show();

            return false;

        }
        return true;




    }
    //====================================
    //====================================






    //================================================
    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Dialog customExitDialog = new Dialog(MainActivity.this);
        customExitDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        customExitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customExitDialog.getWindow().getAttributes().windowAnimations
                = android.R.style.Animation_Dialog;
        customExitDialog.setContentView(R.layout.exitdialog);
        customExitDialog.setCancelable(true);
        //
        ImageButton noBtn = customExitDialog.findViewById(R.id.nobtn);
        ImageButton yesBtn = customExitDialog.findViewById(R.id.yesbtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customExitDialog.dismiss();
                Toast.makeText(MainActivity.this, "Thank You😊", Toast.LENGTH_SHORT).show();
            }
        });
        //
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customExitDialog.show();

    }
    //==================================
    //==================================



}