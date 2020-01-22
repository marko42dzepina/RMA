package dzepina.coins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ahmadrosid.svgloader.SvgLoader;

public class DetaljiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);

        Intent intent = getIntent();
        Coin coin = (Coin)intent.getSerializableExtra("coin");

        TextView sifra = findViewById(R.id.symbol);
        sifra.setText(coin.getSymbol());


        TextView ime = findViewById(R.id.name);
        ime.setText(coin.getName());


        TextView prezime = findViewById(R.id.price);
        prezime.setText(coin.getPrice()+" €");

        TextView desc = findViewById(R.id.desc);
        desc.setText(coin.getDescription());

        Button nazad = findViewById(R.id.nazad);
        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageView = findViewById(R.id.image);
        SvgLoader.pluck()
                .with(this)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(coin.getIconUrl(), imageView);
    }


}

