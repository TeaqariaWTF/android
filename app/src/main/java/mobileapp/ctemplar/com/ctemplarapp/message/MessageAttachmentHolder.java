package mobileapp.ctemplar.com.ctemplarapp.message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mobileapp.ctemplar.com.ctemplarapp.R;

public class MessageAttachmentHolder extends RecyclerView.ViewHolder {
    public View root;
    public TextView txtName;
    public ImageView imgExt;

    public MessageAttachmentHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView;
        txtName = itemView.findViewById(R.id.name_text_view);
        imgExt = itemView.findViewById(R.id.ext_image_view);
    }
}
