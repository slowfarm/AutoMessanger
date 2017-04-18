package ru.eva.automessanger.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.eva.automessanger.Models.Girls;
import ru.eva.automessanger.R;


class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {

    private List<Girls> text;

    MainFragmentAdapter(List<Girls> text) {
        this.text = text;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mTextView.setText(text.get(i).getFirst_name() + " " + text.get(i).getLast_name());
        Picasso.with(viewHolder.itemView.getContext())
                .load(text.get(i).getPhoto())
                .into(viewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return text.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;
        private final ImageView mImageView;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.text_view);
            mImageView = (ImageView) v.findViewById(R.id.photo);
        }
    }

}