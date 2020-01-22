package dzepina.coins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdapterListe extends RecyclerView.Adapter<AdapterListe.Red> {

    private List<Coin> coins;
    private LayoutInflater layoutInflater;
    private ItemClickInterface itemClickInterface;

    public AdapterListe(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }


    @Override
    public Red onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.red_liste,parent,false);
        return new Red(view);
    }

    @Override
    public void onBindViewHolder(Red holder, int position) {
        Coin coin = coins.get(position);
        holder.symbol.setText(coin.getSymbol());
        holder.name.setText(coin.getName());
    }

    @Override
    public int getItemCount() {

        return coins==null ? 0 : coins.size();
    }

    public Coin getCoin(int position) {
        return coins.get(position);
    }


    public class Red extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private TextView symbol;
        private TextView name;

        public Red(View view){
            super(view);
            symbol = view.findViewById(R.id.symbol);
            name = view.findViewById(R.id.name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickInterface != null){
                itemClickInterface.onItemClick(v,getAdapterPosition());
            }
        }
    }


    public void setClickListener(ItemClickInterface clickListener) {
        this.itemClickInterface=clickListener;
    }

    public interface ItemClickInterface{
        void onItemClick(View view, int position);
    }

}
