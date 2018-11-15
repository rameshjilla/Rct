package app.myfirstclap.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import app.myfirstclap.R;
import app.myfirstclap.adapters.ImageTestAdapter;

/**
 * Created by TGT on 6/11/2018.
 */

public class ImageActivity extends AppCompatActivity {
  ListView listview;
  private String[] mItems = new String[]{
          "http://assets.imgix.net/examples/clownfish.jpg",
          "http://assets.imgix.net/examples/espresso.jpg",
          "http://assets.imgix.net/examples/kayaks.png",
          "http://assets.imgix.net/examples/leaves.jpg",
          "http://assets.imgix.net/examples/puffins.jpg",
          "http://assets.imgix.net/examples/redleaf.jpg",
          "http://assets.imgix.net/examples/butterfly.jpg",
          "http://assets.imgix.net/examples/blueberries.jpg",
          "http://assets.imgix.net/examples/octopus.jpg",
          "https://images.fandango.com/ImageRenderer/0/0/redesign/static/img/default_poster.png/0/images/masterrepository/other/INTRO_AvengersAgeUltron_FINAL.jpg",
          "https://lumiere-a.akamaihd.net/v1/images/r_blackpanther_nowonhero_e6434d76.jpeg?region=0,0,2048,832",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPjworoTCfZdLIShoHknDDdOCeCjdIHzMkpzlSjb0E0G7QAuvkAg",
          "https://hips.hearstapps.com/esq.h-cdn.co/assets/17/26/1498666131-es-062817-adam-sandler-movies-ranked.jpg",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2Z1apwuXg_HhRoPVIe-csvwtGPvlGAgoirrlyDOB6U8eqaZLT",
          "http://cdn.collider.com/wp-content/uploads/2016/04/tom-cruise-movies-slice-600x200.jpg",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuosjmfgNzN1OmocQT3iO-AGo6slKC4NiJUzpTntOQP_CqhFPR8Q",
          "https://i.ytimg.com/vi/h-a4wVVjQC4/hqdefault.jpg",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSMaV1mBA18FDl1TiRgVTOQqv80Ivai1oxoXCrN0F2e6mh-JQZj",
          "https://res.cloudinary.com/jerrick/image/upload/f_auto,fl_progressive,q_auto,c_fit,w_1560/juofe8bgxaxxjxwuoy9f",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdIy96FB_vj3RVyPiXC9qUJvfM1YO3rrpnJButTzQyC8sv6Smd",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3UxVfdk9VUz9uHC0wcdG_yGoqqINAFRQBus-tbVbdS9NqgvTF",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmAcL053TYtAcN0olFkSSyY-tNvGvA2mSuOA2tGDd1anGuqXD7",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIIeaH19u4BkdeDY5EREbSR0c2rF89qoZ9w9urt4ZzocrXiUGA",
          "http://www.joblo.com/posters/images/full/Spiderman-poster-7-large.jpg",
          "http://www.joblo.com/posters/images/full/Pirates-of-the-Caribbean-Dead-Men-Tell-No-Tales-poster-3-large.jpg",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRREdwCwYC-8gTcRyIixZE0I1nEu5B1wxiKeEmK3qzcsCdlRXdc",
          "http://blog.visme.co/wp-content/uploads/2017/12/Redesigned-Movie-Posters-to-Inspire-your-Creativity-The-Scream.png",
          "https://i.ytimg.com/vi/BHoKs9tFjtc/maxresdefault.jpg",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwRKUGQw33_TNgj3R7023StQh2P8haWuvSTOjBDAzw73yCLTFr",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcToRxf9SMFDtPCejh5I_Duq7V3NPY7s0i0QdUA2Arp5xTdfOuIw",
          "https://petapixel.com/assets/uploads/2016/05/johncho_1-800x543.jpg",
          "https://i.pinimg.com/originals/d2/d7/27/d2d727600a33c781ff50dd8ce1fb72c5.jpg",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNqzqbF_myn1riz4wwL8c_FReY7KVnAGwWQB3tjosfwLf7TFetsA",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXBqVQmT1FDxlFkHeaJOu0RHmKRjBCjon2d-zsUPYwyf9gaMgU",
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuoABcKTdd2Wu604fhZlJhLmWZfxddQuGeMize3MLIy-7K6Aqs"
  };


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_imagetest);
    listview = findViewById(R.id.listview);
    ImageTestAdapter adapter = new ImageTestAdapter(ImageActivity.this, mItems);
    listview.setAdapter(adapter);
  }
}
