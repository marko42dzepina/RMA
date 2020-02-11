package dzepina.plovila.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.List;
import dzepina.plovila.R;
import dzepina.plovila.model.Plovilo;


public class PloviloAdapter extends ArrayAdapter<Plovilo> {

    private List<Plovilo> podaci;
    private PloviloClickListener ploviloClickListener;
    private int resource;
    private Context context;

    public PloviloAdapter(@NonNull Context context, int resource, PloviloClickListener ploviloClickListener) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.ploviloClickListener = ploviloClickListener;
    }


    private static class ViewHolder {

        private TextView naziv;
        private ImageView slika;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        Plovilo plovilo;
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(this.resource, null);

                viewHolder.naziv = view.findViewById(R.id.naziv_tip);
                viewHolder.slika = view.findViewById(R.id.slika);
            } else {
                viewHolder = (ViewHolder) view.getTag();

            }

            plovilo = getItem(position);

            if (plovilo != null) {

                viewHolder.naziv.setText(plovilo.getNaziv() + " - " + context.getResources().getStringArray(R.array.tip_plovila)[plovilo.getTip()]);

                if (plovilo.getSlika() == null) {
                    Picasso.get().load(R.drawable.plovilo).fit().centerCrop().into(viewHolder.slika);
                } else {
                    Picasso.get().load(plovilo.getSlika()).fit().centerCrop().into(viewHolder.slika);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ploviloClickListener.onItemClick(plovilo);
                }
            });
        }
        return view;
    }

    @Override
    public int getCount() {
        return podaci == null ? 0 : podaci.size();
    }

    @Nullable
    @Override
    public Plovilo getItem(int position) {
        return podaci.get(position);
    }

    public void setPodaci(List<Plovilo> plovila) {
        this.podaci = plovila;
    }

    public void osvjeziPodatke() {
        notifyDataSetChanged();
    }

}
