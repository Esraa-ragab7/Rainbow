package com.example.esrae.rainbow;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.u;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class MainActivity extends AppCompatActivity  {
    private View progressBar;
    private EditText Gp_na;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseUser user ;
    private DatabaseReference mDatabaseReference=FirebaseDatabase.getInstance().getReference();
    private EditText mPasswordView2;
    private EditText mPasswordView3;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    ScaleAnimation shrinkAnim;
    ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.progress2);
        user = FirebaseAuth.getInstance().getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        shrinkAnim = new ScaleAnimation(1.15f, 0f, 1.15f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        if (mRecyclerView != null) {
            //to enable optimization of recyclerview
            mRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Say Hello to our new FirebaseUI android Element, i.e., FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter<Group,GrouplstHolder> adapter = new FirebaseRecyclerAdapter<Group, GrouplstHolder>(
                Group.class,
                R.layout.grouplst,
                GrouplstHolder.class,
                //referencing the node where we want the database to store the data from our Object
                mDatabaseReference.child("users").child(user.getUid()).child("Groups").getRef()
        ) {
            @Override
            protected void populateViewHolder(GrouplstHolder viewHolder, Group model, int position) {

                viewHolder.lstitemname.setText(model.getGroup_Name());
            }
        };

        mRecyclerView.setAdapter(adapter);
//////////////////////////////////////////////////////////////////////////////////

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(MainActivity.this, "Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Groupdetails.class));
                finish();
            }
        }));

        ////////////////////////
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
            }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.dialog2, null);

                Gp_na=(EditText) promptsView.findViewById(R.id.group_na);
                new AlertDialog.Builder(MainActivity.this)
                        .setView(promptsView)
                        .setCancelable(false)
                        .setPositiveButton(" OK ",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, int id) {

                                     //   if ( && !Gp_na.getText().toString().trim().equals("")) {
                                            if(user != null&&!TextUtils.isEmpty(Gp_na.getText().toString())){
                                                myNewGroup(user.getEmail(),Gp_na.getText().toString());
                                                Toast.makeText(MainActivity.this, "Done !", Toast.LENGTH_SHORT).show();

                                        } else if (Gp_na.getText().toString().trim().equals("")) {
                                            Toast.makeText(MainActivity.this, "Enter Name to the Group", Toast.LENGTH_SHORT).show();

                                            // progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                }).create().show();
            }
        });

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
      //  final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //updateMenuTitles();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    }
                }
            };


    }
/////////////////////////////////////////////////////////////////////////////////////////////
    private void myNewGroup( String adminName, String groupName) {
        //Creating a movie object with user defined variables

        Group o1 = new Group(groupName,adminName);
        //referring to movies node and setting the values from movie object to that location
      //  mDatabaseReference.child("users").child(userId).child("movies").push().setValue(movie);
        mDatabaseReference.child("users").child(user.getUid()).child("Groups").push().setValue(o1);
    }
////////////////////////////////////////////////////////////////////////////////////////////\
private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_scrolling, menu);
        this.menu=menu;
        updateMenuTitles();

        //this.
        return true;
    }
    private void updateMenuTitles() {
        final MenuItem bedMenuItem = menu.findItem(R.id.menu_name);

        mDatabaseReference.child("users").child(user.getUid()).child("user_Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
               // System.out.println("111111"+snapshot.getValue());
                bedMenuItem.setTitle((String)snapshot.getValue());
              //  System.out.println("123456 "+((String)snapshot.getValue()));
                //prints "Do you have data? You'll love Firebase."
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.change_password:
                changepassword();
                return true;
            case R.id.sign_out:
                signout();
                return true;
            case R.id.Delete_Account:
                del();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////
    public void changepassword(){
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.dialog1, null);

        mPasswordView2 =(EditText) promptsView
                .findViewById(R.id.password22);
        mPasswordView3 =(EditText) promptsView
                .findViewById(R.id.password33);
//CBJs9lo_m-ctN6W4pPurHQskrxbz…
        new AlertDialog.Builder(MainActivity.this)
                .setView(promptsView)
                    .setCancelable(false).setTitle("change Password")
                .setPositiveButton("Change password",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int id) {

                                if (user != null && !mPasswordView2.getText().toString().trim().equals("")&&!mPasswordView3.getText().toString().trim().equals("")) {

                                    if (mPasswordView2.getText().toString().trim().length() < 6) {
                                        Toast.makeText(MainActivity.this, "Password too short, enter minimum 6 characters" , Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        if(!mPasswordView2.getText().toString().equals(mPasswordView3.getText().toString())){
                                            Toast.makeText(MainActivity.this, "Not equal passwords !", Toast.LENGTH_SHORT).show();
                                        }
                                    else {
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            user.updatePassword(mPasswordView2.getText().toString().trim())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(MainActivity.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                                                        signout();
                                                        } else {
                                                        Toast.makeText(MainActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        }
                                    } else if (mPasswordView2.getText().toString().trim().equals("")) {
                                    Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                                    }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        }).create().show();
    }
    //////////////////////////////////////////////////////////////////////////////////////
    public void del(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
                        if (user != null) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MainActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                                finish();
                                                progressBar.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(MainActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                            }
                        }
                    })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    //////////////////////////////////////////////////////////////////////////////////////
    public static class GrouplstHolder extends RecyclerView.ViewHolder{

        TextView lstitemname;
        TextView lstitemname2;
        public GrouplstHolder(View v) {
            super(v);
            lstitemname = (TextView) v.findViewById(R.id.lsttext123);
            lstitemname2=null;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        }
    //////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
        }
    //////////////////////////////////////////////////////////////////////////////////////
            @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
           auth.removeAuthStateListener(authListener);
            }
        }
    //////////////////////////////////////////////////////////////////////////////////////
    public void signout(){
        auth.signOut();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    }
            }
            };

    }
    //////////////////////////////////////////////////////////////////////////////////////

}
