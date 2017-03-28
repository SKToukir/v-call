package clubzed.vumobile.com.celebraty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import clubzed.vumobile.com.celebraty.R;
import clubzed.vumobile.com.celebraty.utils.UserClass;

/**
 * Created by toukirul on 27/3/2017.
 */

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    private Context mContext;
    private List<UserClass> userClasses;

    public UserDataAdapter(Context context, List<UserClass> userClassList) {
        this.mContext = context;
        this.userClasses = userClassList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        UserClass primaryClass = userClasses.get(position);
        //holder.videoImageView.setImageUrl(primaryClass.getContent_image(),imageLoader);
        holder.txtUname.setText(primaryClass.getUserName());

    }

    @Override
    public int getItemCount() {
        return userClasses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtUname;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtUname = (TextView) itemView.findViewById(R.id.txtUname);
        }
    }
}