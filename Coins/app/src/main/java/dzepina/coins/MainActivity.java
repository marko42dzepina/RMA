package dzepina.coins;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterListe.ItemClickInterface {

    private AdapterListe adapterListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterListe = new AdapterListe(this);
        adapterListe.setClickListener(this);
        recyclerView.setAdapter(adapterListe);

        RESTTask restTask = new RESTTask();
        restTask.execute("https://api.coinranking.com/v1/public/coins?base=EUR&limit=20");
    }

    private class RESTTask extends AsyncTask<String, Void, List<Coin>> {

        @Override
        protected List<Coin> doInBackground(String... strings) {

            List<Coin> coins = new ArrayList<>();

            try {
                URL url = new URL(strings[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.connect();
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);

                StringBuilder output = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }

                try {

                    JSONObject jsonObject = new JSONObject(output.toString()).getJSONObject("data");
                    JSONArray coinsArray = jsonObject.getJSONArray("coins");

                    for (int i = 0; i < coinsArray.length(); i++) {
                        Coin coin = new Coin();
                        coin.setSymbol(coinsArray.getJSONObject(i).getString("symbol"));
                        coin.setName(coinsArray.getJSONObject(i).getString("name"));
                        coin.setDescription(coinsArray.getJSONObject(i).getString("description"));
                        coin.setIconUrl(coinsArray.getJSONObject(i).getString("iconUrl"));
                        coin.setPrice(coinsArray.getJSONObject(i).getString("price"));
                        coins.add(coin);
                    }

                } catch (JSONException e) {
                   e.getMessage();
                }
                reader.close();
                streamReader.close();

                return coins;

            } catch (Exception e) {
                e.getMessage();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(List<Coin> coins) {
            adapterListe.setCoins(coins);
            adapterListe.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        Coin coin = adapterListe.getCoin(position);

        Intent intent = new Intent(this, DetaljiActivity.class);
        intent.putExtra("coin", coin);
        startActivity(intent);
    }
}
