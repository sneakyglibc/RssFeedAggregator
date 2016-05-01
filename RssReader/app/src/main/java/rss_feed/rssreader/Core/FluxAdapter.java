package rss_feed.rssreader.Core;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import java.util.List;

import rss_feed.rssreader.Data.Flux;
import rss_feed.rssreader.Data.FluxNode;
import rss_feed.rssreader.R;

/**
 * Created by Phil on 30/04/2016.
 */
public class FluxAdapter extends RecyclerView.Adapter<FluxAdapter.MyViewHolder> {
    private List<FluxNode> fluxList;

    public static interface OnItemClickListener {
        public void onItemClick(FluxNode flux);
    }

    public interface OnItemLongClickListener {
        public void onItemClick(FluxNode flux);
    }

    private final OnItemClickListener   listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, url, description;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_flux);
            url = (TextView) view.findViewById(R.id.url_flux);
            description = (TextView) view.findViewById(R.id.description_flux);
        }

        public void bind(final FluxNode flux, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v){
                    listener.onItemClick(flux);
                }
            });
        }
    }

    public FluxAdapter(List<FluxNode> fluxList, OnItemClickListener listener) {
        this.fluxList = fluxList;
        this.listener = listener;
    }


    public FluxNode    getFlux(int position){
        return fluxList.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flux_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FluxNode flux = fluxList.get(position);
        holder.title.setText(flux.getTitle());
        holder.url.setText(flux.getLink());
        holder.bind(fluxList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return fluxList.size();
    }

}
