package in.sunfox.needs.assesment.volunteer_registration;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.sunfox.needs.assesment.helper.LocalCacheHelper;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;

public class SyncLocalCacheService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new SyncLocalCacheThread(this).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static class SyncLocalCacheThread extends Thread {

        private Context context;

        SyncLocalCacheThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
//            CollectionReference reference = FirebaseFirestore.getInstance().collection("pincodes");
//            reference.addSnapshotListener((querySnapshot, e) -> {
//                if(querySnapshot!=null) {
//                    ArrayList<String> pinCodes = new ArrayList<>();
//                    for (DocumentSnapshot snapshot : querySnapshot.getDocuments()){
//                        Log.d(TAG, "onChange: "+snapshot.getString("name"));
//                        pinCodes.add(snapshot.getString("name"));
//                    }
//                    LocalCacheHelper.updatePinCodesList(context, pinCodes);
//                }
//            });
//
//            reference = FirebaseFirestore.getInstance().collection("mandals");
//            reference.addSnapshotListener((querySnapshot, e) -> {
//                if(querySnapshot!=null) {
//                    ArrayList<String> mandals = new ArrayList<>();
//                    for (DocumentSnapshot snapshot : querySnapshot.getDocuments()){
//                        Log.d(TAG, "onChange: "+snapshot.getString("name"));
//                        mandals.add(snapshot.getString("name"));
//                    }
//                    LocalCacheHelper.updateMandalsList(context, mandals);
//                }
//            });
//
//            reference = FirebaseFirestore.getInstance().collection("panchayats");
//            reference.addSnapshotListener((querySnapshot, e) -> {
//                if(querySnapshot!=null) {
//                    ArrayList<String> panchayats = new ArrayList<>();
//                    for (DocumentSnapshot snapshot : querySnapshot.getDocuments()){
//                        Log.d(TAG, "onChange: "+snapshot.getString("name"));
//                        panchayats.add(snapshot.getString("name"));
//                    }
//                    LocalCacheHelper.updatePanchayatsList(context, panchayats);
//
//                }
//            });

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("pincodes");
            ArrayList<String> pinCodes = new ArrayList<>();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String value = (String) snapshot.getValue();
                            pinCodes.add(value);
                        }
                        LocalCacheHelper.updatePinCodesList(context,pinCodes);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }});

             ref = database.getReference("panchayats");
            ArrayList<String> panchayats = new ArrayList<>();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String value = (String) snapshot.getValue();
                            panchayats.add(value);
                        }
                        LocalCacheHelper.updatePanchayatsList(context,panchayats);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }});


             ref = database.getReference("mandals");
            ArrayList<String> mandals = new ArrayList<>();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String value = (String) snapshot.getValue();
                            mandals.add(value);
                        }
                        LocalCacheHelper.updateMandalsList(context,mandals);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }});


        }
    }

}
