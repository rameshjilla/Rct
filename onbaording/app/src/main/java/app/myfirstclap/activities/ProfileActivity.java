package app.myfirstclap.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.myfirstclap.R;
import app.myfirstclap.adapters.LocationAutoCompleteAdapter;
import app.myfirstclap.model.Location;
import app.myfirstclap.model.User;
import app.myfirstclap.utils.Utils;
import au.com.dstil.atomicauth.callback.FailureCallback;
import io.reactivex.functions.Consumer;

/**
 * Created by TGT on 11/14/2017.
 */

public class ProfileActivity extends BaseActivity implements View.OnClickListener, FailureCallback<String> {
  EditText edit_firstname, edit_lastname, edit_phonenumber, edit_aboutme;
  Button button_submitprofile;
  AppCompatAutoCompleteTextView autocomplete_location;
  Bundle bundle;
  TextInputLayout input_layout_firstname;
  String email, cityid;
  Utils utils;
  ImageView profile_image, upload_image;
  String userChoosenTask;
  private int REQUEST_CAMERA = 0, SELECT_FILE = 1;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    init();

  }

  public void init() {
    setupToolbar();
    setTitle(getResources().getString(R.string.title_profile));
    utils = new Utils(ProfileActivity.this);
    edit_firstname = (EditText) findViewById(R.id.input_firstname);
    edit_lastname = (EditText) findViewById(R.id.input_lastname);
    edit_phonenumber = (EditText) findViewById(R.id.input_phonenumber);
    edit_aboutme = (EditText) findViewById(R.id.input_aboutme);
    upload_image = (ImageView) findViewById(R.id.pick_image);
    button_submitprofile = (Button) findViewById(R.id.submit_profile);
    autocomplete_location = (AppCompatAutoCompleteTextView) findViewById(R.id.input_selection_location);
    input_layout_firstname = (TextInputLayout) findViewById(R.id.input_layout_firstname);
    profile_image = (ImageView) findViewById(R.id.profile_image);
    assignValuesfromBundle();
    button_submitprofile.setOnClickListener(this);
    upload_image.setOnClickListener(this);
    populateCities();
  }

  public void assignValuesfromBundle() {
    bundle = getIntent().getExtras();
    if (bundle != null && !bundle.isEmpty()) {
      edit_firstname.setText(bundle.getString("firstname"));
      edit_lastname.setText(bundle.getString("lastname"));
      email = bundle.getString("email");
    }

  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.submit_profile:
        boolean isEmptyfields = utils.validate(new EditText[]{edit_firstname, edit_lastname, edit_aboutme, edit_aboutme});
        if (!isEmptyfields) {
          Toast.makeText(this, getResources().getString(R.string.error_empytyfields), Toast.LENGTH_SHORT).show();
        } else {
          User user = new User();
          user.setEmail(email);
          user.setFirstName(edit_firstname.getText().toString());
          user.setLastName(edit_lastname.getText().toString());
        /*  user.setAboutme(edit_aboutme.getText().toString());
          user.setLocation(autocomplete_location.getText().toString());
          user.setPhonenumber(edit_phonenumber.getText().toString());*/
          startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
        }

        break;
      case R.id.pick_image:
        selectImage();
        break;
    }


  }

  @Override
  public void failure(String s) {
  }

  public void populateCities() {
    String citiesjsonrespose = utils.loadJSONFromAsset("cities.json");
    GsonBuilder builder = new GsonBuilder();
    Gson mGson = builder.create();
    List<Location> citieslist = new ArrayList<Location>();
    citieslist = Arrays.asList(mGson.fromJson(citiesjsonrespose, Location[].class));
    LocationAutoCompleteAdapter adapter = new LocationAutoCompleteAdapter(ProfileActivity.this, citieslist);
    autocomplete_location.setAdapter(adapter);
    autocomplete_location.setThreshold(1);
    autocomplete_location.setOnItemClickListener(new autocompleteItemClick());

  }

  public class autocompleteItemClick implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
      Location location = (Location) adapterView.getItemAtPosition(i);
      String cityname = location.getCityname();
      cityid = location.getId();
      autocomplete_location.setText(cityname);
      autocomplete_location.setSelection(autocomplete_location.getText().length());
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    switch (requestCode) {
      case Utils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          if (userChoosenTask.equals("Take Photo"))
            pickImage(Sources.CAMERA);
          else if (userChoosenTask.equals("Choose from Library"))
            pickImage(Sources.GALLERY);
        } else {
          //code for deny
        }
        break;
    }
  }

  private void selectImage() {
    final CharSequence[] items = {"Take Photo", "Choose from Library",
            "Cancel"};

    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
    builder.setTitle("Add Photo!");
    builder.setItems(items, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int item) {
        boolean result = Utils.checkPermission(ProfileActivity.this);
        if (items[item].equals("Take Photo")) {
          userChoosenTask = "Take Photo";
          if (result)
            pickImage(Sources.CAMERA);

        } else if (items[item].equals("Choose from Library")) {
          userChoosenTask = "Choose from Library";
          if (result)
            pickImage(Sources.GALLERY);

        } else if (items[item].equals("Cancel")) {
          dialog.dismiss();
        }
      }
    });
    builder.show();
  }


  public void pickImage(Sources sources) {
    RxImagePicker.with(ProfileActivity.this).requestImage(sources).subscribe(new Consumer<Uri>() {
      @Override
      public void accept(Uri uri) throws Exception {
        Glide.with(ProfileActivity.this).load(uri).transform(new Utils.CircleTransform(ProfileActivity.this)).into(profile_image);
      }
    });
  }


}
