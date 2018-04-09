package activitytest.example.com.myapplication.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.List;

import activitytest.example.com.myapplication.OfferActivity;
import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.entity.Offer;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
  private Context mContext;
    private List<Offer> mOfferList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView offerImage;
        TextView offertitle;
        TextView offertime;
        TextView offerpay;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView) view;
            offerImage=(ImageView)view.findViewById(R.id.offer_image);
            offertitle=(TextView)view.findViewById(R.id.offer_title);
            offertime=(TextView)view.findViewById(R.id.offer_time);
            offertitle=(TextView)view.findViewById(R.id.offer_title);
            offerpay=(TextView)view.findViewById(R.id.offer_pay);
        }
    }
    public OfferAdapter (List<Offer> offerList){
        mOfferList=offerList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.offer_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= holder.getAdapterPosition();
                Offer Offer =mOfferList.get(position);
                Intent intent=new Intent(mContext,OfferActivity.class);
//                Bitmap bmp=Offer.getPicturee();
//                ByteArrayOutputStream baos=new ByteArrayOutputStream();
//                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte [] bitmapByte =baos.toByteArray();
                intent.putExtra(OfferActivity.OFFER_NAME, Offer.getTitle());
                intent.putExtra(OfferActivity.OFFER_MORE_INFORMATION, Offer.getDescription());
                intent.putExtra(OfferActivity.OFFER_PICTURE, Offer.getPicture());
                intent.putExtra("offerid", Offer.getId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder ,int position){
        Offer Offer =mOfferList.get(position);
        holder.offertitle.setText(Offer.getTitle());
        holder.offertime.setText(Offer.getTime());
        holder.offerpay.setText(Offer.getPaying());
        holder.offerImage.setImageBitmap(Offer.getPicturee());
    }
    @Override public int getItemCount(){
        return mOfferList.size();
    }
}
