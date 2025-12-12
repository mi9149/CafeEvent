package com.example.administrator.cafe_event;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class EventActivity extends AppCompatActivity {
    public static int countIndexes = 7;
    public static int maxEventImgs = 10;
    LinearLayout buttonLayout;
    LinearLayout eventList;
    ImageView[] indexButtons;
    ImageView[] indexEventImgs;
    ImageView cafeTitle;
    View[] views;
    ViewFlipper flipper;
    float downX;
    float upX;
    int currentIndex = 0;
    LinearLayout.LayoutParams eventListParams;
    Intent webIntent1;
    Intent webIntent2;
    Intent webIntent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);


        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        eventList = (LinearLayout) findViewById(R.id.eventList);

        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v != flipper) return false;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = event.getX();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    upX = event.getX();

                    if (upX < downX) {  // in case of right directio

                        /*flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.push_left_in));
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
        eventListParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        eventListParams.topMargin = 50;

        indexButtons = new ImageView[countIndexes];
        views = new ImageView[countIndexes];
        indexEventImgs = new ImageView[maxEventImgs];


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

        }


        for (int i = 0; i < maxEventImgs; i++) {
            indexEventImgs[i] = new ImageView(this);

            if (i == 0) {
                indexEventImgs[i].setImageResource(R.drawable.tom_event_1);
                indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tomntoms.com/event/main.php"));
                        startActivity(webIntent1);
                    }

                });

            } else if (i == 1) {
                indexEventImgs[i].setImageResource(R.drawable.tom_event_2);
                indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        webIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tomntoms.com/event/main.php"));
                        startActivity(webIntent2);
                    }

                });
            } else if (i == 2) {
                indexEventImgs[i].setImageResource(R.drawable.angel_event_3);
                indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        webIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tomntoms.com/event/main.php"));
                        startActivity(webIntent3);
                    }

                });
            }

            indexEventImgs[i].setPadding(10, 0, 10, 0);
            indexEventImgs[i].setMaxHeight(10);
            eventList.addView(indexEventImgs[i], eventListParams);


        }


        Button eventBtn = (Button) findViewById(R.id.go_newmenu);
        eventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });


    }


    private void updateIndexes() {
        eventList.removeAllViewsInLayout();
        for (int i = 0; i < countIndexes; i++) {
            if (i == currentIndex) {
                indexButtons[i].setImageResource(R.drawable.green);
            } else {
                indexButtons[i].setImageResource(R.drawable.white);
            }

            cafeTitle = (ImageView) findViewById(R.id.cafaTitleImg);
            if (currentIndex == 0)
                cafeTitle.setImageResource(R.drawable.tom_2);
            else if (currentIndex == 1)
                cafeTitle.setImageResource(R.drawable.twosome_2);
            else if (currentIndex == 2)
                cafeTitle.setImageResource(R.drawable.angel_2);
            else if (currentIndex == 3)
                cafeTitle.setImageResource(R.drawable.bean_2);
            else if (currentIndex == 4)
                cafeTitle.setImageResource(R.drawable.pascucci_2);
            else if (currentIndex == 5)
                cafeTitle.setImageResource(R.drawable.yoger_2);
            else if (currentIndex == 6)
                cafeTitle.setImageResource(R.drawable.hollys_2);
        }

        if (currentIndex == 0) {
            for (int i = 0; i < maxEventImgs; i++) {
                indexEventImgs[i] = new ImageView(this);
                if (i == 0) {
                    indexEventImgs[i].setImageResource(R.drawable.tom_event_1);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tomntoms.com/event/main.php"));
                            startActivity(webIntent1);
                        }

                    });
                } else if (i == 1) {
                    indexEventImgs[i].setImageResource(R.drawable.tom_event_2);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            webIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tomntoms.com/event/main.php"));
                            startActivity(webIntent2);
                        }

                    });
                }else if (i == 2) {
                    indexEventImgs[i].setImageResource(R.drawable.tom_event_2);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            webIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tomntoms.com/event/main.php"));
                            startActivity(webIntent3);
                        }

                    });
                }

                indexEventImgs[i].setPadding(10, 0, 10, 0);
                indexEventImgs[i].setMaxHeight(100);
                eventList.addView(indexEventImgs[i], eventListParams);
            }
        } else if (currentIndex == 1) {
            for (int i = 0; i < maxEventImgs; i++) {
                indexEventImgs[i] = new ImageView(this);
                if (i == 0) {
                    indexEventImgs[i].setImageResource(R.drawable.twosom_event_1);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twosome.co.kr/event/list.asp"));
                            startActivity(webIntent1);
                        }

                    });
                } else if (i == 1) {
                    indexEventImgs[i].setImageResource(R.drawable.twosom_event_2);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twosome.co.kr/event/list.asp"));
                            startActivity(webIntent2);
                        }

                    });
                }


                indexEventImgs[i].setPadding(10, 0, 10, 0);
                indexEventImgs[i].setMaxHeight(100);
                eventList.addView(indexEventImgs[i], eventListParams);
            }
        } else if (currentIndex == 2) {
            for (int i = 0; i < 9; i++) {
                indexEventImgs[i] = new ImageView(this);
                if (i == 0) {
                    indexEventImgs[i].setImageResource(R.drawable.angel_event_1);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.angelinus.com/Event/Event_View.asp?Mode=VIEW&EventType=Event&Idx=403&SearchEventGubun=0"));
                            startActivity(webIntent1);
                        }

                    });
                } else if (i == 1) {
                    indexEventImgs[i].setImageResource(R.drawable.angel_event_2);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.angelinus.com/Event/Event_View.asp?Mode=VIEW&EventType=Event&Idx=401&SearchEventGubun=0"));
                            startActivity(webIntent2);
                        }

                    });
                } else if (i == 2) {
                    indexEventImgs[i].setImageResource(R.drawable.angel_event_3);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.angelinus.com/Event/Event_View.asp?Mode=VIEW&EventType=Event&Idx=399&SearchEventGubun=0"));
                            startActivity(webIntent3);
                        }

                    });
                }else if (i == 3) {
                    indexEventImgs[i].setImageResource(R.drawable.angel_event_4);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.angelinus.com/Event/Event_View.asp?Mode=VIEW&EventType=Event&Idx=392&SearchEventGubun=0"));
                            startActivity(webIntent3);
                        }

                    });
                }else if (i == 4) {
                    indexEventImgs[i].setImageResource(R.drawable.angel_event_5);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.angelinus.com/Event/Event_View.asp?Mode=VIEW&EventType=Event&Idx=388&SearchEventGubun=0"));
                            startActivity(webIntent3);
                        }

                    });
                }else if (i == 5) {
                    indexEventImgs[i].setImageResource(R.drawable.angel_event_6);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.angelinus.com/Event/Event_View.asp?Mode=VIEW&EventType=Event&Idx=402&SearchEventGubun=0"));
                            startActivity(webIntent3);
                        }

                    });
                }else if (i == 6) {
                    indexEventImgs[i].setImageResource(R.drawable.angel_event_7);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.angelinus.com/Event/Event_View.asp?Mode=VIEW&EventType=Event&Idx=400&SearchEventGubun=0"));
                            startActivity(webIntent3);
                        }

                    });
                }else if (i == 7) {
                    indexEventImgs[i].setImageResource(R.drawable.angel_event_8);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.angelinus.com/Event/Event_View.asp?Mode=VIEW&EventType=Event&Idx=398&SearchEventGubun=0"));
                            startActivity(webIntent3);
                        }

                    });
                }else if (i == 8) {
                    indexEventImgs[i].setImageResource(R.drawable.angel_event_9);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.angelinus.com/Event/Event_View.asp?Mode=VIEW&EventType=Event&Idx=391&SearchEventGubun=0"));
                            startActivity(webIntent3);
                        }

                    });
                }

                indexEventImgs[i].setPadding(10, 0, 10, 0);
                indexEventImgs[i].setMaxHeight(100);
                eventList.addView(indexEventImgs[i], eventListParams);
            }
        } else if (currentIndex == 3) {
            for (int i = 0; i < maxEventImgs; i++) {
                indexEventImgs[i] = new ImageView(this);
                // if (i == 0)
                //indexEventImgs[i].setImageResource(R.drawable.);
                // else if (i == 1)
                //indexEventImgs[i].setImageResource(R.drawable.tom_event_2);
                // else if (i == 2)
                //indexEventImgs[i].setImageResource(R.drawable.angel_event_1);

                indexEventImgs[i].setPadding(10, 0, 10, 0);
                indexEventImgs[i].setMaxHeight(100);
                eventList.addView(indexEventImgs[i], eventListParams);
            }
        } else if (currentIndex == 5) {
            for (int i = 0; i < maxEventImgs; i++) {
                indexEventImgs[i] = new ImageView(this);
                if (i == 0) {
                    indexEventImgs[i].setImageResource(R.drawable.yoger_event_1);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yogerpresso.co.kr/event/plan?bc_seq=11&method=view&b_seq=7103"));
                            startActivity(webIntent1);
                        }

                    });
                } else if (i == 1) {
                    indexEventImgs[i].setImageResource(R.drawable.yoger_event_2);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yogerpresso.co.kr/event/plan?bc_seq=11&method=view&b_seq=7102"));
                            startActivity(webIntent2);
                        }

                    });
                }

                indexEventImgs[i].setPadding(10, 0, 10, 0);
                indexEventImgs[i].setMaxHeight(100);
                eventList.addView(indexEventImgs[i], eventListParams);
            }
        } else if (currentIndex == 4) {
            for (int i = 0; i < maxEventImgs; i++) {
                indexEventImgs[i] = new ImageView(this);
                if (i == 0) {
                    indexEventImgs[i].setImageResource(R.drawable.pas_event_1);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.caffe-pascucci.co.kr/pages/event/event_view.asp?seq=279"));
                            startActivity(webIntent1);
                        }

                    });
                } else if (i == 1) {
                    indexEventImgs[i].setImageResource(R.drawable.pas_event_2);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.caffe-pascucci.co.kr/pages/event/event_view.asp?seq=273"));
                            startActivity(webIntent1);
                        }

                    });
                } else if (i == 2) {
                    //indexEventImgs[i].setImageResource(R.drawable.angel_event_1);
                }

                indexEventImgs[i].setPadding(10, 0, 10, 0);
                indexEventImgs[i].setMaxHeight(100);
                eventList.addView(indexEventImgs[i], eventListParams);
            }
        }else if (currentIndex == 6) {
            for (int i = 0; i < maxEventImgs; i++) {
                indexEventImgs[i] = new ImageView(this);
                if (i == 0) {
                    indexEventImgs[i].setImageResource(R.drawable.hollys_event_1);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hollys.co.kr/news/event/view.do?idx=46&pageNo=1&division="));
                            startActivity(webIntent1);
                        }

                    });
                } else if (i == 1) {
                    indexEventImgs[i].setImageResource(R.drawable.hollys_event_2);
                    indexEventImgs[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hollys.co.kr/news/event/view.do?idx=45&pageNo=1&division="));
                            startActivity(webIntent1);
                        }

                    });
                } else if (i == 2) {
                    //indexEventImgs[i].setImageResource(R.drawable.angel_event_1);
                }

                indexEventImgs[i].setPadding(10, 0, 10, 0);
                indexEventImgs[i].setMaxHeight(100);
                eventList.addView(indexEventImgs[i], eventListParams);
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











