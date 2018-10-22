package com.ispeak.handgesture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GestureSearch extends Activity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1234;
    private EditText search;
    private ImageView find, files, imageView;

    private Bitmap bitmap;
    private StringBuffer url;
    private List<StringBuffer> mURLs = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesturesearch);

        find = (ImageView) findViewById(R.id.find);
        files = (ImageView) findViewById(R.id.files);
        search = (EditText) findViewById(R.id.search);
        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setOnClickListener(this);
        files.setOnClickListener(this);
        find.setOnClickListener(this);
    }

    public void onClick(View v) {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        if (v.getId() == R.id.find) {
            inputManager = (InputMethodManager) this.getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            Toast.makeText(getApplicationContext(), R.string.message_processing, Toast.LENGTH_SHORT).show();

            /* ImageView which shows the result images*/
            imageView = (ImageView) findViewById(R.id.imageView);
            String strtxt;

            if (search.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), R.string.message_empty, Toast.LENGTH_SHORT).show();
                return;
            } else
                strtxt = search.getText().toString();

            /* Split the input sentence into words */
            String[] words = strtxt.split("\\s+");

            /*Remove special characters and convert uppercase characters to lowercase characters*/
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].replaceAll("[^\\w]", "");
                words[i] = words[i].toLowerCase();
            }

            /*Create a URL for each word and it to the URLs list*/
            for (int i = 0; i < words.length; i++) {
                /*
                * "http://www.lifeprint.com/asl101/pages-signs/" + FIRST_CHAR + WORD + ".htm" has images for WORD
                * */
                url = new StringBuffer("http://www.lifeprint.com/asl101/pages-signs/");
                url.append(words[i].charAt(0));
                url.append('/');
                url.append(words[i]);
                url.append(".htm");
                mURLs.add(url);
            }
            try {
                loadNext();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /* Open the Sign Language dictionary with a Toast message */
        if (v.getId() == R.id.files) {
            Toast.makeText(getApplicationContext(), "Moving to sign language dictionary!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(GestureSearch.this, FilesActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            /* matches will contain possible words for voice */
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String topResult;
            /* Choose the first word from the result and append to the edit TextBox */
            topResult = matches.get(0);
            search.append(topResult);

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    /* Removes URL one by one from the list and delegates to LoadImage method */
    private void loadNext() throws InterruptedException, ExecutionException {
        if (mURLs.isEmpty()) {
            return;
        }
        url = mURLs.remove(0);
        if (url != null) {
            new LoadImage().execute();
        }
    }

    private class LoadImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... args) {
            /* if url is URL of number/alphabet */
            if (url.indexOf("numbers") >= 0 || url.indexOf("fingerspelling") >= 0) {
                try {
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(url.toString()).getContent());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            /* if url is URL of a word */
            else {
                try {
                    /* Connect to the website using Jsoup and retrieve the first "jpg" image as Bitmap*/
                    Document doc = Jsoup.connect(url.toString()).ignoreContentType(true).get();
                    Element img = doc.select("img[src$=.jpg]").first();
                    String Str = img.attr("abs:src");
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(Str).getContent());
                } catch (UnknownHostException e) {
                    /* UnKnownHostException raised when there is no internet */
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(GestureSearch.this, "No Internet", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    /* Exception is raised when the word is not there in lifeprint.com */
                    bitmap = null;
                    String t = url.toString();

                    /* Get the word from the URL */
                    String str = t.substring(46, t.lastIndexOf('.'));

                    /* Traverse the word from last and add URL for each character to the beginning of URLs list*/
                    for (int i = str.length() - 1; i >= 0; i--) {
                        /* If the character is a number use "http://www.lifeprint.com/asl101/signjpegs/numbers/number0" and add it to the beginning of URLs list */
                        if (str.charAt(i) <= '9' && str.charAt(i) >= '0')
                            mURLs.add(0, new StringBuffer("http://www.lifeprint.com/asl101/signjpegs/numbers/number0" + str.charAt(i) + ".jpg"));

                        /* If the character is an alphabet use "http://www.lifeprint.com/asl101/fingerspelling/abc-gifs/" and add it to the beginning of URLs list */
                        else
                            mURLs.add(0, new StringBuffer("http://www.lifeprint.com/asl101/fingerspelling/abc-gifs/" + str.charAt(i) + "_small.gif"));
                    }

                    /* Remove the newly added character URL and get the image as bitmap */
                    StringBuffer url = mURLs.remove(0);
                    try {
                        bitmap = BitmapFactory.decodeStream((InputStream) new URL(url.toString()).getContent());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            /* The bitmap is set to ImageView onPost downloading and loadNext method is called for next URL in the URLs list*/
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            try {
                loadNext();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}