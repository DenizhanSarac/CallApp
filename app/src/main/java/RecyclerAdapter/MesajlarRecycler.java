package RecyclerAdapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callapp.R;

import java.util.ArrayList;

import Details.Cagri;

public class MesajlarRecycler extends RecyclerView.Adapter<MesajlarRecycler.MesajlarHolder> {

    private ArrayList<Cagri> mesajlarList;
    private Context context;
    private onItemClickListener listener;
    private int checkedPosition=0;

    public MesajlarRecycler(ArrayList<Cagri> mesajlarList, Context context) {
        this.mesajlarList = mesajlarList;
        this.context = context;
    }

    @NonNull
    @Override
    public MesajlarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mesajlar_item,parent,false);
        return new MesajlarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MesajlarHolder holder, int position) {
        Cagri cagri = mesajlarList.get(position);
        holder.setData(cagri);

    }

    @Override
    public int getItemCount() {
        System.out.println("Dizi Büyüklüğü: "+mesajlarList.size());
        return mesajlarList.size();
    }

    class MesajlarHolder extends RecyclerView.ViewHolder {
        TextView mesaj;
        ImageView tick;
        public MesajlarHolder(@NonNull View itemView) {
            super(itemView);
            mesaj=(TextView)itemView.findViewById(R.id.mesajlar_item_mesaj);
            tick=(ImageView)itemView.findViewById(R.id.mesajlar_item_tick);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener != null && position !=RecyclerView.NO_POSITION)
                        listener.onItemClick(mesajlarList.get(position),position);

                    tick.setVisibility(View.VISIBLE);
                    if(checkedPosition !=getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition=getAdapterPosition();
                    }
                }
            });
        }

        public void setData(final Cagri cagri){
            if(checkedPosition== -1){
                tick.setVisibility(View.GONE);
            }else{
                if(checkedPosition == getAdapterPosition()){
                    tick.setVisibility(View.VISIBLE);
                }else{
                    tick.setVisibility(View.GONE);
                }
            }
            this.mesaj.setText(cagri.getMesaj());
        }

        public Cagri getSelected(){
            if(checkedPosition !=-1){
                return mesajlarList.get(checkedPosition);
            }return null;
        }
    }
    public interface onItemClickListener{
        void onItemClick(Cagri cagri,int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){this.listener=listener;}
}
