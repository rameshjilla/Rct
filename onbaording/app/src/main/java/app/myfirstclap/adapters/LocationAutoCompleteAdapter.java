package app.myfirstclap.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.myfirstclap.R;
import app.myfirstclap.model.Location;

/**
 * Created by TGT on 11/29/2017.
 */

public class LocationAutoCompleteAdapter extends BaseAdapter implements Filterable {

  Context context;
  private List<Location> citieslist;
  private final List<Location> fitercitieslist;


  /**
   * @param context
   * @param citieslist
   */
  public LocationAutoCompleteAdapter(Context context,
                                     List<Location> citieslist) {
    // TODO Auto-generated constructor stub

    this.citieslist = citieslist;
    this.context = context;
    fitercitieslist = citieslist;

  }


  /**
   * Count of items.
   */
  @Override
  public int getCount() {
    // TODO Auto-generated method stub

    if (citieslist == null) {
      return 0;
    } else if (citieslist.size() == 0) {

      Log.d("products", "entered");
    }
    return citieslist.size();
  }

  /**
   * @param position The position of the item
   * @return The data at the specified position.
   */
  @Override
  public Object getItem(int position) {
    // TODO Auto-generated method stub
    return citieslist.get(position);
  }

  /**
   * @param position The position of the item within the adapter's data set whose
   *                 row id we want.
   */
  @Override
  public long getItemId(int position) {
    // TODO Auto-generated method stub
    return position;
  }

  /**
   * Get View that displays the data at the specified position in the data
   * set.
   *
   * @param position The position of the item within the adapter's data set of the
   *                 item whose view we want.
   * @param view     The old view to reuse, if possible.
   * @param arg2     The parent that this view will eventually be attached to
   * @return A View corresponding to the data at the specified position.
   */

  @SuppressLint("NewApi")
  @Override
  public View getView(int position, View view, ViewGroup arg2) {
    // TODO Auto-generated method stub
    ViewHolder vh = new ViewHolder();

    if (view == null) {

      // inflate the layout
      LayoutInflater inflat = (LayoutInflater) context
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflat.inflate(R.layout.location_list_item, null);

      // inflate all the views
      vh.cityname = (TextView) view.findViewById(R.id.city_textview);


      // set tag to view
      view.setTag(vh);
    } else {
      // get the view based on tag
      vh = (ViewHolder) view.getTag();
    }

    // set data to views
    vh.cityname.setText(citieslist.get(position)
            .getCityname());


    // return view
    return view;

  }

  public Filter getFilter() {
    // TODO Auto-generated method stub

    Filter filter = new Filter() {

      @SuppressWarnings("unchecked")
      @Override
      protected void publishResults(CharSequence constraint,
                                    FilterResults results) {
        // TODO Auto-generated method stub
        citieslist = (ArrayList<Location>) results.values;
        notifyDataSetChanged();

      }

      // getting results based on the search text view text
      @SuppressLint("DefaultLocale")
      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
        boolean added;
        FilterResults results = new FilterResults();


        // checks the searched key word is valid or not
        if (constraint != null && !constraint.equals("")) {
          String seq = constraint.toString().toLowerCase();
          ArrayList<Location> list = new ArrayList<Location>();
          if (seq.trim().length() == 0 || seq == null) {
            results.count = fitercitieslist.size();
            results.values = fitercitieslist;
            list.addAll(fitercitieslist);
            System.out.println("the seq is " + seq);
          } else {

            // get all contact

            for (Location bean : fitercitieslist) {
              added = false;
              // add all related contacts to list

              if (bean.getCityname().toLowerCase()
                      .startsWith(seq.toLowerCase())) {
                list.add(bean);
                added = true;
              }

              

			    /*
                 * // add all related contacts to list if
			     * (bean.getId().toLowerCase()
			     * .contains(seq.toLowerCase())) list.add(bean);
			     */
            }
            System.out.println("the seqcode is " + seq);
          }
          results.count = list.size();
          results.values = list;
        }
        return results;
      }
    };
    return filter;
  }

  /**
   * ViewHolder inner class of the SearchAdapter contains the views
   *
   * @author android
   */
  static class ViewHolder {
    TextView cityname;


  }

}
