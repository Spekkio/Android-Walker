/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.andrewsfirstjointresearch;

import android.app.Activity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.Integer;
import java.lang.NumberFormatException;

/**
 *
 * @author pelle
 */
public class MainActivity extends Activity {

    Toast toast;
/*
    private OnClickListener mCorkyListener = new OnClickListener() {
        public void onClick(View v) {
        // do something when the button is clicked
            EditText edit1 = (EditText) findViewById(R.id.widget28);
            EditText edit2 = (EditText) findViewById(R.id.widget29);
            EditText edit3 = (EditText) findViewById(R.id.widget31);


            try{
                Integer int1 = new Integer(Integer.parseInt(edit1.getText().toString()));
                Integer int2 = new Integer(Integer.parseInt(edit2.getText().toString()));
                Integer int3 = new Integer(int1.intValue()+int2.intValue());
                edit3.setText(int3.toString());
            }
            catch(NumberFormatException e)
            {
                toast = Toast.makeText(getApplicationContext(),"Not a number",Toast.LENGTH_SHORT);
                toast.show();
            }

           
        }
    };
*/

    private Background bground;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        setContentView(R.layout.main);

       // Button myButton = (Button) findViewById(R.id.widget30);
        bground = (Background)findViewById(R.id.lunar);
        bground.setText((TextView)findViewById(R.id.widget32));
       // myButton.setOnClickListener(mCorkyListener);

/*
        TextView tv = new TextView(this);
        tv.setText("Hello World!");
        setContentView(tv);
*/

    }

}
