package com.example.quotes;

import static com.example.quotes.R.id.quote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    TextView quote;
    ImageView share, copy;
    public static String QuoteParse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        quote = findViewById(R.id.quote);
        share = findViewById(R.id.share);
        copy = findViewById(R.id.copy);

        //===================================================================

        quote.setText(QuoteParse);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, quote.getText().toString());
                shareIntent.setType("text/plain");
                shareIntent = Intent.createChooser(shareIntent, "Share Via : ");
                startActivity(shareIntent);
                //

            }
        });
        //
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("copy", quote.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity2.this, "Copied", Toast.LENGTH_SHORT).show();
                //
            }
        });



    }
}