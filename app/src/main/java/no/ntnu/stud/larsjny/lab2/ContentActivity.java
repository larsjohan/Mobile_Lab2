package no.ntnu.stud.larsjny.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import no.ntnu.stud.larsjny.lab2.util.Article;

public class ContentActivity extends AppCompatActivity {

    private ImageView image;

    private TextView title;

    private TextView date;

    private TextView author;

    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // Initialte Views

      //  this.image      = (ImageView) findViewById(R.id.content_Image);
        this.title      = (TextView) findViewById(R.id.content_Title);
        this.date       = (TextView) findViewById(R.id.content_date);
        this.author     = (TextView) findViewById(R.id.content_author);
        this.content    = (TextView) findViewById(R.id.content_content);

        // Fetch Article from Intent
        Intent data = getIntent();

        //this.image.setImageBitmap(data.getParcelableExtra("image"));
        this.title.setText(data.getStringExtra("title"));
        this.date.setText(data.getStringExtra("date"));
        this.author.setText(data.getStringExtra("author"));
        this.content.setText(data.getStringExtra("content"));

    }
}
