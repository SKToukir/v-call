package clubzed.vumobile.com.celebraty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    EditText loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginName = (EditText) findViewById(R.id.loginName);
        Log.d("username",SinchService.uName);
    }

    public void loginButton(View view) {

        if (SinchService.uName == "" || SinchService.uName.isEmpty() || SinchService.uName == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else {
            startActivity(new Intent(MainActivity.this,PlaceCallActivity.class));
        }
    }

    public void audioCall(View view) {

    }
}
