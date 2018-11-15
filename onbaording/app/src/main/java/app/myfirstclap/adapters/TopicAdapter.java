package app.myfirstclap.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import app.myfirstclap.R;
import app.myfirstclap.model.Topic;

/**
 * Created by TGT on 1/10/2018.
 */

public class TopicAdapter extends BaseAdapter {
  List<Topic> topiclist;
  Context context;
  public static int sCorner = 15;
  public static int sMargin = 2;
  public static int sBorder = 10;
  public static String sColor = "#7D9067";
  selectTopicsCallback selecttopicscallback;


  @Override
  public int getCount() {
    return topiclist.size();
  }

  @Override
  public Object getItem(int i) {
    return topiclist.get(i);
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  public TopicAdapter(Context context, List<Topic> topiclist, selectTopicsCallback selecttopicscallback
  ) {
    this.context = context;
    this.topiclist = topiclist;
    this.selecttopicscallback = selecttopicscallback;


  }


  public interface selectTopicsCallback {
    void selectedtopic(int id);
  }

  @Override
  public View getView(final int i, View convertView, ViewGroup viewGroup) {
    View view = convertView;
    final ViewHolder holder;

    LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (convertView == null) {

      view = inflater.inflate(R.layout.topic_grid_item, viewGroup,
              false);
      holder = new ViewHolder();
      holder.heading = (TextView) view.findViewById(R.id.tv_topicname);
      holder.icon = (ImageView) view.findViewById(R.id.iv_topicimage);
      holder.checkbox = (AppCompatCheckBox) view.findViewById(R.id.chbx_selection);


      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    Typeface description_typeface = Typeface.createFromAsset(context.getAssets(),
            "fonts/Poppins-Regular.ttf");
    holder.heading.setText(topiclist.get(i).getTopicName());
    holder.heading.setTypeface(description_typeface);
    Glide.with(context).load(topiclist.get(i).getTopicImage())
            .thumbnail(0.1f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.icon);


    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (holder.checkbox.isChecked()) {
          holder.checkbox.setChecked(false);
        } else {
          holder.checkbox.setChecked(true);
        }
        selecttopicscallback.selectedtopic(topiclist.get(i).getId());

      }
    });


    return view;
  }

  @Override
  public int getViewTypeCount() {

    return getCount();
  }

  @Override
  public int getItemViewType(int position) {

    return position;
  }

  public class ViewHolder {
    public TextView heading;
    public ImageView icon;
    public AppCompatCheckBox checkbox;

  }
}
