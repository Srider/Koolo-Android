package com.example.srravela.koolo.main.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.home.activities.KooloHomeActivity;

public class KooloMainActivity extends KooloBaseActivity implements OnClickListener{
    Button unlockButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_main);
        mContext=getApplicationContext();
        getSupportActionBar().hide();
        initUI();

    }

    /**
     * Method for configuring the UI
     */
    private void initUI(){
        unlockButton =(Button)findViewById(R.id.unlock_button);
        unlockButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_koolo_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.unlock_button:
                loadHomeActivity();
                break;
        }
    }

    void loadHomeActivity() {
        Intent homeIntent = new Intent(KooloMainActivity.this, KooloHomeActivity.class);
        startActivity(homeIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
