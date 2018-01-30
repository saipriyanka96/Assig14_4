package com.example.layout.assig14_4;
//Package objects contain version information about the implementation and specification of a Java package
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    //public keyword is used in the declaration of a class,method or field;public classes,method and fields can be accessed by the members of any class.
//extends is for extending a class. implements is for implementing an interface
//AppCompatActivity is a class from e v7 appcompat library. This is a compatibility library that back ports some features of recent versions of
// Android to older devices.
    private static final int PERM_REQ_CODE=123;
    //create a int value
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Variables, methods, and constructors, which are declared protected in a superclass can be accessed only by the subclasses
        // in other package or any class within the package of the protected members class.
        //void is a Java keyword.  Used at method declaration and definition to specify that the method does not return any type,
        // the method returns void.
        //onCreate Called when the activity is first created. This is where you should do all of your normal static set up: create views,
        // bind data to lists, etc. This method also provides you with a Bundle containing the activity's previously frozen state,
        // if there was one.Always followed by onStart().
        //Bundle is most often used for passing data through various Activities.
// This callback is called only when there is a saved instance previously saved using onSaveInstanceState().
// We restore some state in onCreate() while we can optionally restore other state here, possibly usable after onStart() has
// completed.The savedInstanceState Bundle is same as the one used in onCreate().

        super.onCreate(savedInstanceState);
// call the super class onCreate to complete the creation of activity like the view hierarchy
        setContentView(R.layout.activity_main);
        //R means Resource
        //layout means design
        //  main is the xml you have created under res->layout->main.xml
        //  Whenever you want to change your current Look of an Activity or when you move from one Activity to another .
        // The other Activity must have a design to show . So we call this method in onCreate and this is the second statement to set
        // the design
        //A user interface element the user can tap or click to perform an action.
        ////findViewById:A user interface element that displays text to the user.
        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasPermission()){
                    //if it has permission then it will create a folder otherwise we will request for permission
                    makeFolder();

                }else { requestPermission();

                }
            }
        });
    }
    //METHOD TO CHECK THE PERMISSION
    @SuppressLint("WrongConstant")
    private boolean hasPermission(){
        int res=0;//result is integer value
        //we are giving permission external storage to write
        String[]permisson= new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for(String perm:permisson){
            res=checkCallingOrSelfPermission(perm);
            //checkCallingOrSelfPermission:Determine whether the calling process of an IPC or you have been granted a particular permission.
            //Parameters
            //permission	String: The name of the permission being checked.This value must never be null.
            if(!(res== PackageManager.PERMISSION_GRANTED)){
                //if permission is not granted the return false
                return false;
            }
        }
        return true;
    }
    //Method to request for the permission
    private void requestPermission(){
        String[]permisson= new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //if we are having marshalmellow then we need to give build version
            requestPermissions(permisson,PERM_REQ_CODE);
            //if it is the request for permission
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Callback for the result from requesting permissions. This method is invoked for every call on requestPermissions(android.app.Activity, String[], int).
        //Parameters
        //requestCode	int: The request code passed in requestPermissions(android.app.Activity, String[], int)
        //permissions	String: The requested permissions. Never null.
          //      grantResults	int: The grant results for the corresponding permissions which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.

        boolean allowed = true;//giving a boolean value
        switch (requestCode) {
            case PERM_REQ_CODE:
                for (int res : grantResults) {
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }//if the res is equal to grant then permission will be granted otherwise false
                break;
            default:
                //if user does not granted the permission
                allowed = false;
                break;
        }
        if (allowed) {
            //user granted all the permission we can perform our work
            makeFolder();
        }
        else {
            //giving warning to the user that permission is not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
                Toast.makeText(getApplicationContext(),"User permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }
    //Method that would make folder if all permissions has been granted
    private void makeFolder(){
        File file= new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"MYFILE");
        if(!file.exists()){
            boolean ff=  file.mkdir();//to create a directory in the actual path
            // toast:A toast provides simple feedback about an operation in a small popup
            //Make a standard toast that just contains a text view with the text from a resource.

            // Parameters
            //context	Context: The context to use. Usually your Application or Activity object.
            //resId	int: The resource id of the string resource to use. Can be formatted text.
            //duration	int: How long to display the message. Either LENGTH_SHORT or LENGTH_LONG
//show(): it show the toast
            if(ff){
                Toast.makeText(getApplicationContext(),"Folder created sucessfully",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "Folder not created", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getApplicationContext(),"Folder already exist",Toast.LENGTH_LONG).show();

        }
    }
}

