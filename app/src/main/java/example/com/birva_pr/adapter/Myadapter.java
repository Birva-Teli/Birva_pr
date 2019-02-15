package example.com.birva_pr.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import example.com.birva_pr.OnOptionClickListener;
import example.com.birva_pr.R;
import example.com.birva_pr.database.UserDetailsBean;
import example.com.birva_pr.helpers.AppConstants;

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> implements Filterable {
    private Activity activity;
    ArrayList<UserDetailsBean> users;
    ArrayList<UserDetailsBean> searchedUsers;
    OnOptionClickListener onOptionClickListener;


    public Myadapter(ArrayList<UserDetailsBean> users, Activity activity) {
        this.users = users;
        searchedUsers=new ArrayList<>(users);
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_registered_list_view,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(AppConstants.AppName,""+position);
        final UserDetailsBean userDetailsBean=users.get(position);
        holder.tvName.setText(userDetailsBean.getName());
        holder.tvEmail.setText(userDetailsBean.getEmail());
        holder.tvPhn.setText(userDetailsBean.getMobNo());
        holder.tvPass.setText(userDetailsBean.getPassword());
        holder.tvOptionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionClickListener.onOptionClick(position,userDetailsBean,holder.tvOptionMenu);
            }
        });

    }
    public void setOnOptionClickListener(OnOptionClickListener onOptionClickListener){
        this.onOptionClickListener = onOptionClickListener;
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addUser(UserDetailsBean user,Boolean isNew,int position)
    {
        if(isNew)
        {
            users.add(user);
            notifyItemInserted(users.size()-1);
        }
        else {
            if(position<0)
                return;
            users.set(position,user);
            notifyItemChanged(position);
        }

    }

    @Override
    public Filter getFilter() {
        return filterdUser;
    }

    private Filter filterdUser=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<UserDetailsBean> filteredList=new ArrayList<>();

            Gson gson=new Gson();

            if(charSequence==null||charSequence.length()==0){
                filteredList.addAll(searchedUsers);
            }
            else {
                String filteredPattern=charSequence.toString().toLowerCase().trim();
                for(UserDetailsBean item:searchedUsers){
                    if(item.serialize().contains(filteredPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            users.clear();
            users.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName,tvEmail,tvPass,tvPhn,tvOptionMenu;

            public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=(TextView) itemView.findViewById(R.id.tvName);
            tvEmail=(TextView) itemView.findViewById(R.id.tvemail);
            tvPass=(TextView) itemView.findViewById(R.id.tvPassword);
            tvPhn=(TextView) itemView.findViewById(R.id.tvPhnNo);
            tvOptionMenu=(TextView)itemView.findViewById(R.id.tvOPtionMenu);

        }

    }

}
