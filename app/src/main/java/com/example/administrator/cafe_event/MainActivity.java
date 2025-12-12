package com.example.administrator.cafe_event;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InterfaceAddress;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static int countIndexes = 6;
    LinearLayout buttonLayout;
    ImageView[] indexButtons;
    ImageView cafeTitle;
    View[] views;
    ViewFlipper flipper;
    float downX;
    float upX;
    int currentIndex = 0;
    ImageView newMenuImage;
    Bitmap bitmap;
    int Numresult;
    ArrayList<ListItem> listnews;// = new ArrayList<ListItem>();
    ListItem Item;
    String linksss;

    public GettingnewPHP newPHP;
    LoadImage loadImg;
    LoadImage loadImg1;
    LoadImage loadImg2;
    LoadImage loadImg3;
    LoadImage loadImg4;
    LoadImage loadImg5;
    LoadImage loadImg6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenview);
        listnews = new ArrayList<ListItem>();

        String news = "http://172.16.209.60/news.php";

        newPHP = new GettingnewPHP();
        newPHP.execute(news);


        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v != flipper) return false;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = event.getX();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    upX = event.getX();

                    if (upX < downX) {  // in case of right direction
                        /*flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.slide_in_left));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.push_left_out));*/

                        if (currentIndex < (countIndexes - 1)) {
                            flipper.showNext();

                            // update index buttons
                            currentIndex++;
                            updateIndexes();
                        }
                    } else if (upX > downX) { // in case of left direction

                        /*flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.push_right_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.push_right_out));*/

                        if (currentIndex > 0) {
                            flipper.showPrevious();

                            // update index buttons
                            currentIndex--;
                            updateIndexes();
                        }
                    }
                }
                return true;
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 50;

        indexButtons = new ImageView[countIndexes];
        views = new ImageView[countIndexes];
        newMenuImage = (ImageView) findViewById(R.id.newMenu);

        for (int i = 0; i < countIndexes; i++) {
            indexButtons[i] = new ImageView(this);

            if (i == currentIndex) {
                indexButtons[i].setImageResource(R.drawable.green);
            } else {
                indexButtons[i].setImageResource(R.drawable.white);
            }

            indexButtons[i].setPadding(10, 10, 10, 10);
            buttonLayout.addView(indexButtons[i], params);

            cafeTitle = (ImageView) findViewById(R.id.cafaTitleImg);
            currentIndex = 0;
            cafeTitle.setImageResource(R.drawable.tom_2);
            newMenuImage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tomntoms.com/menu/newMenu.php"));
                    startActivity(webIntent);
                }
            });
            //newMenuImage.setImageResource(R.drawable.been_new_menu);

        }

        Button eventBtn = (Button) findViewById(R.id.goEvent);
        eventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), EventActivity.class);
                startActivity(myIntent);
            }
        });

    }


    public class ListItem {
        private String[] mData;

        public ListItem(String[] data) {
            mData = data;
        }

        public ListItem(String name, String img, String link) {
            mData = new String[3];
            mData[0] = name;
            mData[1] = img;
            mData[2] = link;
        }

        public String[] getData() {
            return mData;
        }

        public String getData(int index) {
            return mData[index];
        }

        public void setData(String[] data) {
            mData = data;
        }

    }

    class GettingnewPHP extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Log.d("LOG", " 신상 연결성공~!!!!");
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while (true) {
                            String line = br.readLine();
                            if (line == null)
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                } else {
                    Log.d("LOG", "phpDown  backroundAsyncTask 끝!!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("LOG", "phpDown  backroundAsyncTask 끝!!!");
            return jsonHtml.toString();
        }

        @Override
        protected void onPostExecute(String str) {
            String name;
            String img;
            String link;
            try {
                Log.d("TAG", "onPost!!!!");
                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                JSONObject jObject = new JSONObject(str);
                // results라는 key는 JSON배열로 되어있다.
                JSONArray results = jObject.getJSONArray("results");
                //zz += jObject.get("num_results");
                //Log.d("tag", zz);
                Numresult = results.length();
                String stst = String.valueOf(Numresult);
                Log.d("Numresult(before)", stst);
                for (int i = 0; i < results.length(); i++) {
                    JSONObject temp = results.getJSONObject(i);
                    name = temp.getString("name");
                    img = temp.getString("img");
                    link = temp.getString("link");
                    listnews.add(new ListItem(name, img, link));

                    for (int j = 0; j < 3; j++) {
                        Log.d("TAG", listnews.get(i).getData(j));
                    }

                }

                String stsst = String.valueOf(Numresult);
                Log.d("Numresult(middle)", stsst);

                loadImg = new LoadImage();
                Log.d("st", "dddafdasfasdfa");
                String url = find_url("할리스");
                Log.d("st111", url);
                loadImg.execute(url);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String find_url(String str) {
        String url = "";
        for (int i = 0; i < Numresult; ++i) {
            if (listnews.get(i).getData(0).equals(str)) {
                url = listnews.get(i).getData(1);
                Log.d("find!!!", url);
            }
        }
        return url;
    }
    public String find_link(String str) {
        String link = "";
        for (int i = 0; i < Numresult; ++i) {
            if (listnews.get(i).getData(0).equals(str)) {
                link = listnews.get(i).getData(2);
                Log.d("find!!!", link);
            }
        }
        return link;
    }

    private void updateIndexes() {
        for (int i = 0; i < countIndexes; i++) {
            if (i == currentIndex) {
                indexButtons[i].setImageResource(R.drawable.green);
            } else {
                indexButtons[i].setImageResource(R.drawable.white);
            }

            cafeTitle = (ImageView) findViewById(R.id.cafaTitleImg);
            if (currentIndex == 0) {
                cafeTitle.setImageResource(R.drawable.tom_2);
                loadImg1 = new LoadImage();
                loadImg1.execute(find_url("탐엔탐스"));
                linksss = find_link("탐엔탐스");
                newMenuImage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linksss));
                        startActivity(webIntent);
                    }
                });
            } else if (currentIndex == 1) {
                cafeTitle.setImageResource(R.drawable.twosome_2);
                loadImg2 = new LoadImage();
                loadImg2.execute(find_url("투썸플레이스"));
                linksss = find_link("투썸플레이스");
                newMenuImage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linksss));
                        startActivity(webIntent);
                    }
                });
            } else if (currentIndex == 2) {
                cafeTitle.setImageResource(R.drawable.angel_2);
                loadImg3 = new LoadImage();
                loadImg3.execute(find_url("엔제리너스"));
                linksss = find_link("엔제리너스");
                newMenuImage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linksss));
                        startActivity(webIntent);
                    }
                });
            } else if (currentIndex == 3) {
                cafeTitle.setImageResource(R.drawable.bean_2);
                loadImg4 = new LoadImage();
                loadImg4.execute(find_url("커피빈"));
                linksss = find_link("커피빈");
                newMenuImage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linksss));
                        startActivity(webIntent);
                    }
                });
            } else if (currentIndex == 4) {
                cafeTitle.setImageResource(R.drawable.yoger_2);
                loadImg5 = new LoadImage();
                loadImg5.execute(find_url("요거프레소"));
                linksss = find_link("요거프레소");
                newMenuImage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linksss));
                        startActivity(webIntent);
                    }
                });

            } else if (currentIndex == 5) {
                cafeTitle.setImageResource(R.drawable.hollys_2);
                loadImg6 = new LoadImage();
                loadImg6.execute(find_url("할리스"));
                linksss = find_link("할리스");
                newMenuImage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linksss));
                        startActivity(webIntent);
                    }
                });

            }
        }
    }

    class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                newMenuImage.setImageBitmap(image);
            }
        }
    }
    class LoadImage1 extends AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                newMenuImage.setImageBitmap(image);
            }
        }
    }
    class LoadImage2 extends AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                newMenuImage.setImageBitmap(image);
            }
        }
    }
    class LoadImage3 extends AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                newMenuImage.setImageBitmap(image);
            }
        }
    }
    class LoadImage4 extends AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                newMenuImage.setImageBitmap(image);
            }
        }
    }
    class LoadImage5 extends AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                newMenuImage.setImageBitmap(image);
            }
        }
    }
    class LoadImage6 extends AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                newMenuImage.setImageBitmap(image);
            }
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (v != flipper) return false;
        Animation push_left_in, push_left_out;
        push_left_in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        push_left_out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            upX = event.getX();

            if (upX < downX) {  // in case of right direction

                /*flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.push_left_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.push_left_out));*/
                flipper.setInAnimation(push_left_in);
                flipper.setOutAnimation(push_left_out);

                if (currentIndex < (countIndexes - 1)) {
                    flipper.showNext();

                    // update index buttons
                    currentIndex++;
                    updateIndexes();
                }
            } else if (upX > downX) { // in case of left direction
                Animation push_right_in, push_right_out;
                push_right_in = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
                push_right_out = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);

               /* flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.push_right_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.push_right_out));*/

                flipper.setAnimation(push_right_in);
                flipper.setAnimation(push_right_out);

                if (currentIndex > 0) {
                    flipper.showPrevious();

                    // update index buttons
                    currentIndex--;
                    updateIndexes();
                }
            }
        }

        return true;
    }

}






