package app.myfirstclap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.theartofdev.fastimageloader.FastImageLoader;
import com.theartofdev.fastimageloader.ImageLoadSpec;

import app.myfirstclap.R;
import app.myfirstclap.utils.Specs;

/**
 * Created by TGT on 6/11/2018.
 */

public class ImageTestAdapter extends BaseAdapter {
  private String[] mItems;
  Context context;


  @Override
  public int getCount() {
    return mItems.length;
  }

  @Override
  public Object getItem(int i) {
    return mItems[i];
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  public ImageTestAdapter(Context context, String[] images
  ) {
    this.context = context;
    this.mItems = images;


  }

  public ImageTestAdapter() {

  }


  @Override
  public View getView(final int i, View convertView, ViewGroup viewGroup) {
    View view = convertView;
    final ViewHolder holder;
    ImageLoadSpec spec = FastImageLoader.getSpec(Specs.IMG_IX_IMAGE);
    LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (convertView == null) {

      view = inflater.inflate(R.layout.image_test_list_item, viewGroup,
              false);
      holder = new ViewHolder();
      holder.icon = (ImageView) view.findViewById(R.id.image_view);


      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    Glide.with(context).load(mItems[i]).thumbnail(0.1f).centerCrop().dontTransform().into(holder.icon);

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

    public ImageView icon;


  }
}