package RecyclerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.callapp.R;

import java.util.ArrayList;
import java.util.List;

import Details.Contacts;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    public List<Contacts> cont;
    Contacts list;
    private ArrayList<Contacts> arraylist;
    boolean checked = false;
    View vv;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onCallClick(int position);
        void onMessageClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){ mListener=listener; }



    public RecyclerAdapter(LayoutInflater inflater, List<Contacts> items) {
        this.layoutInflater = inflater;
        this.cont = items;
        this.arraylist = new ArrayList<Contacts>();
        this.arraylist.addAll(cont);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.contactlist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        list = cont.get(position);
        String name = (list.getName());
        holder.title.setText(name);
        holder.phone.setText(list.getPhone());

    }

    @Override
    public int getItemCount() {
        return cont.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView call,message;
        public TextView title;
        public TextView phone;
        public TextView kisisay;
        public LinearLayout contact_select_layout;

        public ViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            this.setIsRecyclable(false);
            title = (TextView) itemView.findViewById(R.id.fragment_contact_name);
            phone = (TextView) itemView.findViewById(R.id.fragment_contact_number);
            call=(ImageView)itemView.findViewById(R.id.fragment_contact_call);
            message=(ImageView)itemView.findViewById(R.id.fragment_contact_message);
            contact_select_layout = (LinearLayout) itemView.findViewById(R.id.contact_select_layout);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION)
                        {
                            listener.onCallClick(position);
                        }
                    }
                }
            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION)
                        {
                            listener.onMessageClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
