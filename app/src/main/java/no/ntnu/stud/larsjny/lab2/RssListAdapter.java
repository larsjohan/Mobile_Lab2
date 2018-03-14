package no.ntnu.stud.larsjny.lab2;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Johan on 13.03.2018.
 */

public class RssListAdapter extends ArrayAdapter<Article> {

    private Context context;

    private List<Article> articles;

    public RssListAdapter(@NonNull Context context, int resource, ArrayList<Article> articles) {
        super(context, resource, articles);
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Article current = this.articles.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rss_list_item_layout, null);

        ImageView image = (ImageView) view.findViewById(R.id.listItemImage);
        TextView title = (TextView) view.findViewById(R.id.listItemTitle);
        TextView summary = (TextView) view.findViewById(R.id.listItemSummary);

        Bitmap img = current.getImage();

        if (img != null)
            image.setImageBitmap(img);

        title.setText(current.getTitle());
        summary.setText(current.getSummary());

        return view;
    }
}
