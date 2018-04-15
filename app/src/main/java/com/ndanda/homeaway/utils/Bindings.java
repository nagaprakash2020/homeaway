package com.ndanda.homeaway.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.ndanda.homeaway.R;

public class Bindings {

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, String imageUrl) {

        if(imageUrl != null){
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .listener(new RequestListener<Drawable>() {

                        @Override
                        public boolean onLoadFailed(@android.support.annotation.Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher_background));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            imageView.setImageDrawable(resource);
                            return false;
                        }
                    })
                    .into(imageView);
        }else {
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher_background));
        }
    }

}
