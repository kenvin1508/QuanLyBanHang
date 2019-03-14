package vn.edu.vtn.quanlybanhang.util.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFireBaseIDService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("AAAAB", s);
        //saveTokenIntoDatabase(s);
        //a650d682547cb039
        // fP3iO-A7fgI:APA91bErv_EkqRr9JSTWeJtwWnYCGgB58cnw1R6xKhTn4-k2gse4ufTXRlKqsd1kp5SCCXB7qvfICPwcT9d9q8iO54-grVNHMHT-CgMSr6OqyuR2Q8NcZXd1ZL1n-PcIrvTezs8-is3N

    }

    private void saveTokenIntoDatabase(String token) {
        //new FireBaseIDTask().execute(token);
    }

}
