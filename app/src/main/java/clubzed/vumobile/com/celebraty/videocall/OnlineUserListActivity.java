package clubzed.vumobile.com.celebraty.videocall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sinch.android.rtc.calling.Call;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import clubzed.vumobile.com.celebraty.BaseActivity;
import clubzed.vumobile.com.celebraty.CallScreenActivity;
import clubzed.vumobile.com.celebraty.R;
import clubzed.vumobile.com.celebraty.SinchService;
import clubzed.vumobile.com.celebraty.adapter.UserDataAdapter;
import clubzed.vumobile.com.celebraty.config.Config;
import clubzed.vumobile.com.celebraty.utils.UserClass;

public class OnlineUserListActivity extends BaseActivity {

    UserClass userClass;
    List<UserClass> userClassList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_user_list);

        initRecycler();
        parseUserData(Config.URL_GET_UER_DATA);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(OnlineUserListActivity.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String userName = userClassList.get(position).getUserName();

                Toast.makeText(getApplicationContext(),userName,Toast.LENGTH_LONG).show();

                Call call = getSinchServiceInterface().callUserVideo(userName);
                String callId = call.getCallId();

                Intent callScreen = new Intent(OnlineUserListActivity.this, CallScreenActivity.class);
                callScreen.putExtra(SinchService.CALL_ID, callId);
                startActivity(callScreen);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void parseUserData(String urlGetUerData) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlGetUerData, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray array = response.getJSONArray("user");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        userClass = new UserClass();
                        userClass.setUserName(obj.getString(Config.USER_NAME));

                        userClassList.add(userClass);
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(OnlineUserListActivity.this);

        //Adding request to the queue
        requestQueue.add(request);

    }

    private void initRecycler() {
        adapter = new UserDataAdapter(OnlineUserListActivity.this, userClassList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_user);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @SuppressWarnings("deprecation")
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public interface ClickListener {
            void onClick(View view, int position);

            void onLongClick(View view, int position);
        }
    }
}



