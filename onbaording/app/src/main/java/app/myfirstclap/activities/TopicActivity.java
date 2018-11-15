package app.myfirstclap.activities;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.R;
import app.myfirstclap.adapters.TopicAdapter;
import app.myfirstclap.controllers.RetrofitController;
import app.myfirstclap.interfaces.BaseRetrofitCallback;
import app.myfirstclap.model.Topic;
import app.myfirstclap.model.User;
import app.myfirstclap.model.UserTopics;
import app.myfirstclap.utils.CONSTANTS;
import app.myfirstclap.utils.TinyDB;

/**
 * Created by TGT on 1/10/2018.
 */

public class TopicActivity extends BaseActivity implements TopicAdapter.selectTopicsCallback {
    GridView gridview;
    MyFirstClapApplication myapplication;
    TopicAdapter.selectTopicsCallback selecttopicscallback;
    ArrayList<String> selectedtopiclist = new ArrayList<>();
    TextView tv_heading;
    ImageView iv_selection;
    TinyDB tinydb;
    String userid;
    ProgressDialog dialog;

    List<Topic> topiclist = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        gridview = (GridView) findViewById(R.id.gridview);
        myapplication = (MyFirstClapApplication) getApplication();
        dialog = new ProgressDialog(TopicActivity.this);
        tv_heading = (TextView) findViewById(R.id.tv_select_the_categories);
        iv_selection = (ImageView) findViewById(R.id.iv_selection);
        selecttopicscallback = this;
        tinydb = new TinyDB(TopicActivity.this);

        if (tinydb.getUserObject("user", User.class) != null) {
            userid = tinydb.getUserObject("user", User.class).getId();
            try {
                topiclist = new Gson().fromJson(new JSONObject(tinydb.getString
                        ("topicobject")).getJSONArray
                        ("topics").toString(), new TypeToken<List<Topic>>() {
                }.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        TopicAdapter adapter = new TopicAdapter(TopicActivity.this, topiclist, this);
        gridview.setAdapter(adapter);

        myapplication = (MyFirstClapApplication) getApplication();


        iv_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedtopiclist.size() > 2) {
                    sendSelectedTopics(selectedtopiclist);
                } else {
                    Toast.makeText(TopicActivity.this, "Please Select a minimum of 3", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    @Override
    public void selectedtopic(int id) {
        if (selectedtopiclist.contains(String.valueOf(id))) {


            selectedtopiclist.remove(selectedtopiclist.indexOf(String.valueOf(id)));
        } else {
            selectedtopiclist.add(String.valueOf(id));
        }
    }

    public void sendSelectedTopics(ArrayList<String> selectedtopics) {
        ArrayList<UserTopics> usertopicslist = new ArrayList<>();

        for (int i = 0; i < selectedtopics.size(); i++) {
            UserTopics usertopics = new UserTopics();
            usertopics.setTopicId(selectedtopics.get(i));
            usertopicslist.add(usertopics);
        }
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(usertopicslist, new TypeToken<List<UserTopics>>() {
        }.getType
                ());
        JsonArray selectedtopicsarray = element.getAsJsonArray();

        for (int i = 0; i < selectedtopicsarray.size(); i++) {
            selectedtopicsarray.get(i).getAsJsonObject().addProperty("UserId", userid);

        }


        RetrofitController.postRequestController(myapplication, dialog,
                selectedtopicsarray.toString().toString(),
                CONSTANTS.ADD_USER_TOPICS, new BaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        try {
                            JSONObject object = new JSONObject(success);
                            List<UserTopics> user_topiclist = new ArrayList<>();
                            user_topiclist = new Gson().fromJson
                                    (object
                                                    .getJSONArray("user_topics").toString(),
                                            new TypeToken<List<UserTopics>>() {
                                            }.getType());
                            User existingUserObject = tinydb.getUserObject("user", User.class);
                            existingUserObject.setUsertopicslist(new
                                    ArrayList<UserTopics>
                                    (user_topiclist));
                            tinydb.putObject("user", existingUserObject);

                            Intent intent = new Intent(TopicActivity.this,
                                    HomeActivity.class);
                            startActivity(intent);
                            ComponentName cn = intent.getComponent();
                            Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                            startActivity(mainIntent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


        /*UserLoginController.newAddUserTopics(application, dialog, object.toString(), new BaseRetrofitCallback() {
            @Override
            public void Success(String success) {
                try {
                    JSONObject object = new JSONObject(success);
                    if (object.getInt("code") == 100) {
                        ArrayList<Topic> userselectiontopicslist = new ArrayList<>();
                        JSONArray messagearray = object.getJSONArray("message");
                        for (int i = 0; i < messagearray.length(); i++) {
                            Topic topic = new Topic();
                            topic.setId(Integer.valueOf(messagearray.getJSONObject(i).getString("Id")));
                            topic.setTopicImage(messagearray.getJSONObject(i).getString("Topic Image"));
                            topic.setTopicName(messagearray.getJSONObject(i).getString("Topic Name"));
                            userselectiontopicslist.add(topic);


                        }

                        User user = tinydb.getUserObject("user", User.class);
                        user.setTopicslist(userselectiontopicslist);
                        tinydb.putObject("user", user);
                        Intent intent = new Intent(TopicActivity.this,
                                HomeActivity.class);
                        startActivity(intent);
                        ComponentName cn = intent.getComponent();
                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                        startActivity(mainIntent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });*/
   /* UserLoginController.addUserTopics(application, array.toString(), new RetrofitCallbacks() {
      @Override
      public void Sucess(Response response) {
        Gson gson = new Gson();
        BaseResponseBodyArray array = (BaseResponseBodyArray) response.body();
        List<UserSettings> usertopiclist = array.getData();
        JsonArray jsonArray = gson.toJsonTree(usertopiclist).getAsJsonArray();


        tinydb.putListObject("usertopics", usertopiclist);
        Intent intent = new Intent(TopicActivity.this,
                HomeActivity.class);
        startActivity(intent);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        startActivity(mainIntent);
        finish();

      }
    });*/
    }

}
