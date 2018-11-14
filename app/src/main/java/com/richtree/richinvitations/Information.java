package com.richtree.richinvitations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.richtree.richinvitations.activities.MainActivity;

public class Information extends AppCompatActivity {
    ListView information;
    String items[] = {"About Us", "Delivery Information", "Privacy Policy", "Terms And Conditions"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        information = (ListView) findViewById(R.id.info);
        information.setAdapter(itemsAdapter);

        information.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                switch (pos) {
                    case 0:
                        Intent aboutus = new Intent(Information.this, AboutUS.class);
                        startActivity(aboutus);
                        break;
                    case 1:
                        Intent delivery = new Intent(Information.this, DeliveryInformation.class);
                        startActivity(delivery);
                        break;
                    case 2:
                        Intent privacy = new Intent(Information.this, PrivacyPolicy.class);
                        startActivity(privacy);
                        break;
                    case 3:
                        Intent conditions = new Intent(Information.this, TermsAndConditions.class);
                        startActivity(conditions);
                        break;
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(Information.this, MainActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
