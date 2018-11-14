package com.richtree.richinvitations.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.GridView;

import com.richtree.richinvitations.R;
import com.richtree.richinvitations.adapters.ProductOptionValueAdapter;
import com.richtree.richinvitations.model.ProductDetailsData;
import com.richtree.richinvitations.model.ProductOptionValue;
import com.richtree.richinvitations.utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

public class WeddingAlbum extends AppCompatActivity {

  GridView simpleGrid;
  int photos[] = {};
  ArrayList<Integer> list;
  Bundle bundle;
  TinyDB tinyDB;
  List<ProductOptionValue> productoptionvalueslist = new ArrayList<>();
  ProductDetailsData productbetailsbasemodel;
  int position;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.wedding_album);
    bundle = getIntent().getExtras();
    tinyDB = new TinyDB(WeddingAlbum.this);


    if (bundle != null) {
      position = bundle.getInt("pos");
      productbetailsbasemodel = tinyDB.getProductDetailsObject("productdetails", ProductDetailsData.class);
      productoptionvalueslist = productbetailsbasemodel.getOptions().get(position).getOptionValue();

    }

    if (getSupportActionBar() != null)
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    list = new ArrayList<>();
    for (int i = 0; i < photos.length; i++) {
      list.add(photos[i]);
    }

    simpleGrid = (GridView) findViewById(R.id.simpleGridView);
    if (productoptionvalueslist.size() > 0) {
      ProductOptionValueAdapter adapter = new ProductOptionValueAdapter(WeddingAlbum.this, productoptionvalueslist);
      simpleGrid.setAdapter(adapter);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();
    if (id == android.R.id.home) {

      onBackPressed();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}

