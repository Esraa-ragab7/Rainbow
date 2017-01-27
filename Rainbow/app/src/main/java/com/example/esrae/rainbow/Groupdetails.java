package com.example.esrae.rainbow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.images.ImageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Groupdetails extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private TextView lstitem ;
    ScaleAnimation shrinkAnim;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupdetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
///////////////////////////////////////////////////////////////////////////////////////////////
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view2);
        shrinkAnim = new ScaleAnimation(1.15f, 0f, 1.15f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        if (mRecyclerView != null) {
            //to enable optimization of recyclerview
            mRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Say Hello to our new FirebaseUI android Element, i.e., FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter<picture,piclstHolder> adapter = new FirebaseRecyclerAdapter<picture, piclstHolder>(
                picture.class,
                R.layout.grouplst,
                piclstHolder.class,
                //referencing the node where we want the database to store the data from our Object
                mDatabaseReference.child("users").child(user.getUid()).child("pictures").getRef()
                ///to do !!!!!!!!!!!!!!
                //الكلام الى فوق فالحته دى مش هيكون كده !
        ) {
            @Override
            protected void populateViewHolder(piclstHolder viewHolder, picture model, int position) {

                viewHolder.pic_name.setText(model.getPic_Name());
                ////to Do !!!!!!!!!!!!!1
            }
        };

        mRecyclerView.setAdapter(adapter);
//////////////////////////////////////////////////////////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                         startActivity(new Intent(Groupdetails.this, DrawActivity.class));

            }
        });
    }
    public void onBackPressed() {

        startActivity(new Intent(Groupdetails.this, MainActivity.class));
        finish();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////
    private void myNewpic( ) {

        //To do !!!!!!!!!
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    public static class piclstHolder extends RecyclerView.ViewHolder{

        TextView pic_name;
        ImageView pic;
        public piclstHolder(View v) {
            super(v);
            pic_name = (TextView) v.findViewById(R.id.pic_name);
            pic=(ImageView) v.findViewById(R.id.pic);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////

}
