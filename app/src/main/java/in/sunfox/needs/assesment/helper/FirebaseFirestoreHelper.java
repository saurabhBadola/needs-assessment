package in.sunfox.needs.assesment.helper;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.sunfox.needs.assesment.authentication.model.Volunteer;
import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.dashboard.tasks.Task;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;

public class FirebaseFirestoreHelper {

    private static FirebaseFirestore firebaseFirestore;

    public static void initialize() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public static void addVolunteer() {

    }

    public static interface DocumentQueryListener<T> {
        void onDocumentFound(T document);

        void onDocumentNotFound(String reason);
    }

    public static interface DocumentQueryListenerWithId<T> {
        void onDocumentFound(T document,String documentId);

        void onDocumentNotFound(String reason);
    }

    public static interface ListQueryListener<T> {
        void onSuccess(List<T> dataList);

        void onFailed(String message);
    }

//    public static interface ListQueryListener<T> {
//        void onSuccess(List<T> dataList);
//
//        void onFailed(String message);
//    }

    public static void getVolunteer(String phone, DocumentQueryListener<Volunteer> documentQueryListener) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("volunteers").document(phone).get().addOnSuccessListener(documentSnapshot -> {
            Volunteer volunteer = documentSnapshot.toObject(Volunteer.class);
            if (volunteer != null)
                documentQueryListener.onDocumentFound(volunteer);
            else
                documentQueryListener.onDocumentNotFound("Not exist");
        }).addOnFailureListener(e -> {
            documentQueryListener.onDocumentNotFound(e.getLocalizedMessage());
        });
    }


    public static void addBeneficiary(Beneficiary beneficiary, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("beneficiaries").add(beneficiary).addOnFailureListener(onFailureListener).addOnSuccessListener(onSuccessListener);
    }

    public static void addVolunteer(Volunteer volunteer, String volunteerPhone, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("volunteers").document(volunteerPhone).set(volunteer).addOnFailureListener(onFailureListener).addOnSuccessListener(onSuccessListener);
    }

    public static void updateBeneficiaryDetails(String beneficiaryId, String tasksDone, String needsDescription, String recipientSchemes, OnSuccessListener onSuccessListener,OnFailureListener onFailureListener){

        Map<String,Object> map = new HashMap<>();
        map.put("needsDescription",needsDescription);
        map.put("recipientOfAnyScheme",recipientSchemes);
        map.put("tasksDone",tasksDone);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("beneficiaries").document(beneficiaryId).update(map).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void getBeneficiaryByPhone(String beneficiaryPhone, DocumentQueryListenerWithId<Beneficiary> listener) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("beneficiaries").whereEqualTo("phone", beneficiaryPhone)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {

            if (queryDocumentSnapshots.getDocuments().size() > 0) {
                Beneficiary beneficiary = queryDocumentSnapshots.getDocuments().get(0).toObject(Beneficiary.class);
                if (beneficiary != null) {
                    listener.onDocumentFound(beneficiary,queryDocumentSnapshots.getDocuments().get(0).getId());
                }
            }
            listener.onDocumentNotFound("Error: Document not available");
        }).addOnFailureListener(e -> {
            listener.onDocumentNotFound(e.getLocalizedMessage());
        });
    }


    public static void observeBeneficiaryList(Context context, ListQueryListener<Beneficiary> listener) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("beneficiaries").whereEqualTo("referrerVolunteerPhoneNumber", FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    ArrayList<Beneficiary> beneficiaries = new ArrayList<>();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        beneficiaries.add(snapshot.toObject(Beneficiary.class));
                    }
                    listener.onSuccess(beneficiaries);
                } else {
                    if (e != null) {
                        listener.onFailed("Error: " + e.getLocalizedMessage());
                    } else {
                        listener.onFailed("Error: Could not query. Please check your internet connection and try again");
                    }
                }
            }
        });
    }

    public static void observePendingTasksList(Context context, ListQueryListener<Task> listener) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("tasks")
                .whereEqualTo("assignedTo", FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber())
                .whereEqualTo("status", Task.Status.PENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            ArrayList<Task> task = new ArrayList<>();
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                                task.add(snapshot.toObject(Task.class));
                            }
                            listener.onSuccess(task);
                        } else {
                            if (e != null) {
                                listener.onFailed("Error: " + e.getLocalizedMessage());
                            } else {
                                listener.onFailed("Error: Could not query. Please check your internet connection and try again");
                            }
                        }
                    }
                });
    }


    public static void observeCompletedList(Context context, ListQueryListener<Task> listener) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("tasks")
                .whereEqualTo("assignedTo", FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber())
                .whereEqualTo("status", Task.Status.COMPLETED)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            ArrayList<Task> tasks = new ArrayList<>();
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                                tasks.add(snapshot.toObject(Task.class));
                            }
                            listener.onSuccess(tasks);
                        } else {
                            if (e != null) {
                                listener.onFailed("Error: " + e.getLocalizedMessage());
                            } else {
                                listener.onFailed("Error: Could not query. Please check your internet connection and try again");
                            }
                        }
                    }
                });
    }

    public static void observeTaskByToken(String token, Context context, DocumentQueryListener<Task> listener) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("tasks")
                .whereEqualTo("assignedTo", FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber())
                .whereEqualTo("token", token)
                .limit(1)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (queryDocumentSnapshots != null) {
                        if (queryDocumentSnapshots.size() > 0) {
                            // Log.d(TAG, "onEvent: "+queryDocumentSnapshots.getDocuments().get(0).toObject(Task.class).toString());
                            Task task = queryDocumentSnapshots.getDocuments().get(0).toObject(Task.class);
                            listener.onDocumentFound(task);
                        } else {
                            listener.onDocumentNotFound("Error: Task not found.");
                        }
                    } else {
                        if (e != null) {
                            listener.onDocumentNotFound("Error: " + e.getLocalizedMessage());
                        } else {
                            listener.onDocumentNotFound("Error: Could not query. Please check your internet connection and try again");
                        }
                    }
                });
//        firebaseFirestore.collection("tasks").
//                .whereEqualTo("assignedTo", FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber())
//                .whereEqualTo("status", Task.Status.COMPLETED)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                        if (queryDocumentSnapshots != null) {
//                            ArrayList<Task> tasks = new ArrayList<>();
//                            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
//                                tasks.add(snapshot.toObject(Task.class));
//                            }
//                            listener.onSuccess(tasks);
//                        } else {
//                            if (e != null) {
//                                listener.onFailed("Error: " + e.getLocalizedMessage());
//                            } else {
//                                listener.onFailed("Error: Could not query. Please check your internet connection and try again");
//                            }
//                        }
//                    }
//                });
    }
}
