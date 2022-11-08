package com.example.fetchrewardsapprenticeshipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Pattern;

public class DisplayDataActivity extends AppCompatActivity {

    public static DisplayDataActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        thisActivity = this;

        //retrieve data
        FetchData process = new FetchData();
        process.execute();
    }

    public void SortAndDisplayData (String dataString)
    {
        //convert string to 2d array adapted from:
        //https://stackoverflow.com/questions/29546564/convert-string-into-a-two-dimensional-array

        //sort data
        System.out.println("dataString: " + dataString);
        // first need to clean up the string so that is can be displayed all nice and pretty
        // remove "null" at start, spaces, [, ], {, data labels
        dataString = dataString.substring(4, dataString.length()-1);
        dataString = dataString.replace(" ", "");
        dataString = dataString.replace("[", "");
        dataString = dataString.replace("]", "");
        dataString = dataString.replace("{", "");
        dataString = dataString.replace("\"id\":", "");
        dataString = dataString.replace("\"listId\":", "");
        dataString = dataString.replace("\"name\":", "");

        // split at "}"
        String tempStrArr[] = dataString.split(Pattern.quote("},"));

        // separate data into 2d array
        String dataArray[][] = new String[tempStrArr.length][3];
        for (int i = 0; i < tempStrArr.length; i++)
        {
            String separatedDataLine[] = tempStrArr[i].split(",");
            for (int j = 0; j < 3; j++)
                dataArray[i][j] = separatedDataLine[j];
        }

        // now that our data is in a format we can more easily work with, let's sort it by listID and then by name
        //  sort by listID
        Arrays.sort(dataArray, (a, b) -> a[1].compareTo(b[1]));

        //  sort by name
        //   find all elements in same group
        int i = 0;
        for (int j = 1; j < dataArray.length; j++)
        {
            if (dataArray[i][1].compareTo(dataArray[j][1]) != 0 || j == dataArray.length-1)
            {
                String tempArr[][] = Arrays.copyOfRange(dataArray, i, j);
                Arrays.sort(tempArr, (a, b) -> a[2].compareTo(b[2]));
                for (int a = 0; a < tempArr.length; a++)
                    dataArray[a+i] = tempArr[a];
                i = j;
            }
        }

        //display data
        String listID = "";
        String name = "";
        String ID = "";

        //the data will be displayed in columns. So get each column into a string
        for (i = 0; i < dataArray.length; i++)
        {
            if (!dataArray[i][2].equals("\"\"") && !dataArray[i][2].equals("null"))
            {
                listID += dataArray[i][1] + "\n";
                name += dataArray[i][2] + "\n";
                ID += dataArray[i][0] + "\n";
            }
        }

        //set the text of the textViews
        TextView listIDText = (TextView)findViewById(R.id.ListIDText);
        TextView nameText = (TextView)findViewById(R.id.NameText);
        TextView IDText = (TextView)findViewById(R.id.IDText);

        listIDText.setText(listID);
        nameText.setText(name);
        IDText.setText(ID);
    }
}