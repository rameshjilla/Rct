package app.myfirstclap.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import app.myfirstclap.R;
import app.myfirstclap.model.Feeds;

public class NewsEndLessAdapter extends ArrayAdapter<Feeds> {

  private Context context;
  private List<Feeds> newsfeedlist;
  private int layoutId;
  private NewsEndLessAdapter adapter;
  String previousdate = "";
  String currentdate = "";
  private int lastPosition = -1;


  public NewsEndLessAdapter(Context context, List<Feeds> newsfeedlist, int LayoutId) {
    super(context, LayoutId, newsfeedlist);
    this.context = context;
    this.newsfeedlist = newsfeedlist;
    this.layoutId = LayoutId;
  }

  @Override
  public View getView(final int i, View convertView, ViewGroup viewGroup) {
    View view = convertView;
    final ViewHolder holder;
    LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (convertView == null) {


      view = inflater.inflate(layoutId, viewGroup,
              false);
      holder = new ViewHolder();
      holder.description = (TextView) view.findViewById(R.id.news_description);
      holder.caption = (TextView) view.findViewById(R.id.news_caption_link);
          /*  holder.date_created = (TextView) view.findViewById(R.id.date_created);*/
      holder.thumbnail = (ImageView) view.findViewById(R.id.news_thumbnail);

      Typeface description_typeface = Typeface.createFromAsset(context.getAssets(),
              "fonts/Poppins-Regular.ttf");
      Typeface caption_typeface = Typeface.createFromAsset(context.getAssets(),
              "fonts/Poppins-Medium.ttf");
      holder.description.setTypeface(description_typeface);
      holder.caption.setTypeface(caption_typeface);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }


        /*if (previousdate.isEmpty()) {
            previousdate = newsfeedlist.get(i).getCreated_time();
            holder.date_created.setVisibility(View.VISIBLE);
        } else if (i > 0) {
            previousdate = newsfeedlist.get(i - 1).getCreated_time();
            currentdate = newsfeedlist.get(i).getCreated_time();
            if (previousdate.equals(currentdate)) {
                holder.date_created.setVisibility(View.GONE);
            } else {
                holder.date_created.setVisibility(View.VISIBLE);
            }
        }*/


    holder.description.setText(newsfeedlist.get(i).getTitle());
       /* holder.caption.setText(newsfeedlist.get(i).getFrom().getId());*/

    Glide.with(context).load(newsfeedlist.get(i).getImageUrl()).thumbnail(1f)


            .diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.thumbnail);

      /*  Picasso.with(context)
                .load(newsfeedlist.get(i).getFullPicture())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.placeholder_image)
                .noFade()
                .into(holder.thumbnail);*/







        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("image", newsfeedlist.get(i).getImage());
                bundle.putString("content", newsfeedlist.get(i).getContent());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });*/
      /*  Animation animation = AnimationUtils.loadAnimation(getContext(), (i > lastPosition) ? R
                .anim.up_from_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
        lastPosition = i;*/

    return view;
  }

  public class ViewHolder {
    public TextView description, caption, date_created;
    public ImageView thumbnail;

  }

  @Override
  public int getViewTypeCount() {

    return getCount();
  }

  @Override
  public int getItemViewType(int position) {

    return position;
  }



}