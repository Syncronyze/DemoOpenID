package trade.cs.demoopenid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    protected final int REQUEST_64STEAMID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), REQUEST_64STEAMID);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (requestCode == REQUEST_64STEAMID) {
            if (resultCode == RESULT_OK) {
                Toast t = Toast.makeText(getApplicationContext(), "The returned Steam ID is: " + i.getStringExtra("steamID"), Toast.LENGTH_LONG);
                t.show();
            }
        }
    }
}
