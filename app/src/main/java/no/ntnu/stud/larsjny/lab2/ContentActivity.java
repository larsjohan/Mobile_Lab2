package no.ntnu.stud.larsjny.lab2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    private String contentUrl;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // Initialte Views

        this.image      = findViewById(R.id.content_Image);
        this.title      = findViewById(R.id.content_Title);
        this.date       = findViewById(R.id.content_date);
        this.author     = findViewById(R.id.content_author);
        this.content    = findViewById(R.id.content_content);
        this.button     = findViewById(R.id.content_button);

        // Fetch Article from Intent
        Intent data = getIntent();

        //this.image.setImageBitmap(data.getParcelableExtra("image"));
        this.title.setText(data.getStringExtra("title"));
        this.date.setText(data.getStringExtra("date"));
        this.author.setText(data.getStringExtra("author"));
        this.content.setText(data.getStringExtra("summary"));
        this.contentUrl = data.getStringExtra("content");

        if (this.contentUrl == null || this.contentUrl.isEmpty()){
            this.button.setVisibility(View.GONE);
        } else {
            this.button.setOnClickListener(this::onPress);
        }
    }

    private void onPress(View view) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.contentUrl));
            startActivity(browserIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open article", Toast.LENGTH_LONG);
        }
    }


}
