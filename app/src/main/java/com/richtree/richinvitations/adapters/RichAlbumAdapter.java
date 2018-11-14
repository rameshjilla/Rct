package com.richtree.richinvitations.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.activities.RichAlbumDetailView;
import com.richtree.richinvitations.fragments.RichAlbum;
import com.richtree.richinvitations.model.Products;
import com.richtree.richinvitations.utils.TinyDB;

import java.util.List;


/**
 * Created by richtree on 11/24/2017.
 */

public class RichAlbumAdapter extends BaseAdapter {
    private Context c;
    List<Products> productslist;
    Context context;
    TinyDB tinydb;


    @Override
    public int getCount() {
        return productslist.size();
    }

    public RichAlbumAdapter(Context context, List<Products> productslist) {
        this.context = context;
        this.productslist = productslist;
        tinydb = new TinyDB(context);
    }

    @Override
    public Object getItem(int position) {
        return productslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.album_list_item, parent, false);
            holder = new ViewHolder();
            holder.textview_name = (TextView) view.findViewById(R.id.title);
            holder.textview_description = (TextView) view.findViewById(R.id.description);
            holder.profileimage = (ImageView) view.findViewById(R.id.image);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textview_name.setText(productslist.get(position).getName());

        Glide.with(context).load(productslist.get(position).getImage())
                .centerCrop()/*.placeholder(R.drawable.loading)*/.crossFade().thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.profileimage);

   /* Glide.with(context)
            .load(fullimageurl).transform(new CircleTransform(context))
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

            .into(holder.profileimage);*/


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      /*  Bitmap bitmap = ((RoundedBitmapDrawable) holder.profileimage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();*/

      /*  Bundle b = new Bundle();
        b.putByteArray("image", byteArray);*/
                Products bean = productslist.get(position);
                Intent intent = new Intent(context, RichAlbumDetailView.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                bundle.putString("product_id", bean.getId());
                bundle.putString("product_name", bean.getName());
                intent.putExtras(bundle);
      /*  intent.putExtras(b);*/
                context.startActivity(intent);
            }
        });


        return view;
    }


    static class ViewHolder {
        TextView textview_name, textview_description;
        ImageView profileimage;
        // TextView counter;

    }

}
