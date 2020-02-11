package dzepina.plovila.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dzepina.plovila.R;
import dzepina.plovila.adapter.PloviloAdapter;
import dzepina.plovila.adapter.PloviloClickListener;
import dzepina.plovila.model.Plovilo;
import dzepina.plovila.viewmodel.PloviloViewModel;


public class ReadFragment extends Fragment {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;

    private PloviloViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);

        model = ((MainActivity)getActivity()).getModel();

        definirajListu();
        definirajSwipe();
        osvjeziPodatke();


        return view;
    }

    private void osvjeziPodatke(){
        model.dohvatiPlovila().observe(this, new Observer<List<Plovilo>>() {
            @Override
            public void onChanged(@Nullable List<Plovilo> plovila) {
                 swipeRefreshLayout.setRefreshing(false);
                ((PloviloAdapter)listView.getAdapter()).setPodaci(plovila);
                ((PloviloAdapter) listView.getAdapter()).osvjeziPodatke();

            }
        });
    }
    private void definirajSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                osvjeziPodatke();
            }
        });

    }

    private void definirajListu() {

        listView.setAdapter( new PloviloAdapter(getActivity(), R.layout.red_liste, new PloviloClickListener() {
            @Override
            public void onItemClick(Plovilo plovilo) {
                model.setPlovilo(plovilo);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

    @OnClick(R.id.fab)
    public void novoPlovilo(){
        model.setPlovilo(new Plovilo());
        ((MainActivity)getActivity()).cud();
    }


}