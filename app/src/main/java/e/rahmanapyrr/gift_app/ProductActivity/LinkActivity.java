package e.rahmanapyrr.gift_app.ProductActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import e.rahmanapyrr.gift_app.Paypal.BuyingActivity;
import e.rahmanapyrr.gift_app.R;

public class LinkActivity extends AppCompatActivity {
    private Button buyButton;
    private TextView giftIdea;
    private String linkBase;
    private Button paypalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        buyButton = findViewById(R.id.buyNowButton);
        giftIdea = findViewById(R.id.linkSearch);
        linkBase = "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";
        paypalButton = findViewById(R.id.takePaypal);
        paypalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(LinkActivity.this, BuyingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchID = makeSearchUrl();

                takeToBuy(view);
                makeSearchUrl();
            }
        });

    }

    // Take the recommendation from a user's profile and converts it into the ending for a url search

    public String makeSearchUrl(){
        String recommendation=giftIdea.getText().toString();
        String[]recommendationList=recommendation.split(" ");
        String result = "";
        int size=recommendationList.length;
        if(size>1){
            for(int i=0;i<size-1;i++){
                result+=recommendationList[i];
                result+="+";
            }
            result+=recommendationList[size-1];
        } else {
            result+=recommendationList[0];
            System.out.println(result);
        }
        return result;
        }


    // Uses users recommendation ID created with makeSearchUrl() and creates a Browser intent that sends
    // user to the search page for that specific item on Amazon
    public void takeToBuy(View view){
        String searchId = makeSearchUrl();
        // puts together the search link base with the actual item id created in makeSearchUrl()
        linkBase = linkBase+searchId;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkBase));
        startActivity(browserIntent);
    }

}
