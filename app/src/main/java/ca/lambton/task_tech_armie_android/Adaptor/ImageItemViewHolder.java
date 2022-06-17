package ca.lambton.task_tech_armie_android.Adaptor;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.lambton.task_tech_armie_android.R;


public class ImageItemViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;

    public ImageItemViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.subImageView);
    }
}
