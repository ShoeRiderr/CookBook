package com.example.cook_book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeRVAdapter extends ListAdapter<RecipeModal, RecipeRVAdapter.ViewHolder> {

    private OnItemClickListener listener;

    RecipeRVAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<RecipeModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<RecipeModal>() {
        @Override
        public boolean areItemsTheSame(RecipeModal oldItem, RecipeModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(RecipeModal oldItem, RecipeModal newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getDuration().equals(newItem.getDuration());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_rv_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeModal model = getCourseAt(position);
        holder.courseNameTV.setText(model.getName());
        holder.courseDescTV.setText(model.getDescription());
        holder.courseDurationTV.setText(model.getDuration());
    }

    public RecipeModal getCourseAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseNameTV, courseDescTV, courseDurationTV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTV = itemView.findViewById(R.id.idTVName);
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription);
            courseDurationTV = itemView.findViewById(R.id.idTVCourseDuration);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RecipeModal model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
