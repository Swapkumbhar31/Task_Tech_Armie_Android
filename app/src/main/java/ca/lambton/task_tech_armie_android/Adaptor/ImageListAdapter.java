package ca.lambton.task_tech_armie_android.Adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import ca.lambton.task_tech_armie_android.R;

public class ImageListAdapter extends BaseAdapter {

    Context context;
    List<String> imageList;
    LayoutInflater inflater;

    public ImageListAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageViewHolder holder;
        if(view==null)
        {
            view=inflater.inflate(R.layout.image_list_adapter,null);
            holder=new ImageViewHolder();
            holder.subImgView=view.findViewById(R.id.subImageView);
            view.setTag(holder);
        }
        else {
            holder = (ImageViewHolder) view.getTag();
        }
        final Uri imageUri = Uri.parse(imageList.get(position));
        final InputStream imageStream;
        try {
            imageStream = context.getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            holder.subImgView.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return view;
    }

    static class ImageViewHolder{
        private ImageView subImgView;
    }
}
