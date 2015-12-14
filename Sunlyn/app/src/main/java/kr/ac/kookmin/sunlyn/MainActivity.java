package kr.ac.kookmin.sunlyn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends Activity implements TimePicker.OnTimeChangedListener {

    CalendarView monthView;
    CalendarAdapter monthViewAdapter;

    TextView monthText;
    private final int DIALOG_CUSTOM_ID = 1;

    // Database 관련 객체들
    SQLiteDatabase db;
    String dbName = "ScheduleList.db"; // name of Database;
    String tableName = "ScheduleListTable"; // name of Table;
    int dbMode = Context.MODE_PRIVATE;

    ListView lv;
    ArrayList<DayData> dayData;
    ArrayAdapter<String> adapter;
    ArrayList<String> as;

    int curYear;
    int curMonth;
    int curDay;
    String txt ="";
    String am_pm="";
    int curHour;
    int curMin;
    String curTime="";

    EditText et;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayData = new ArrayList<DayData>();

        // // Database 생성 및 열기
        db = openOrCreateDatabase(dbName,dbMode,null);
        // 테이블 생성
        createTable();

        lv = (ListView)findViewById(R.id.listView);

        monthView = (CalendarView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarAdapter(this);
        monthView.setAdapter(monthViewAdapter);

        monthView.setOnDataSelectionListener(new OnDataSelectionListener(){
            public void onDataSelected(AdapterView parent, View v, int position, long id){
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                curDay = curItem.getDay();
                curYear = monthViewAdapter.getCurYear();
                curMonth = monthViewAdapter.getCurMonth();
                as = new ArrayList<String>();

                selectData(curYear, curMonth, curDay);
                updateLv();
            }
        });

        monthText = (TextView)findViewById(R.id.monthText);
        setMonthText();

        //이전 달 보기
        Button monthPrevious = (Button)findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

        //다음 달 보기
        Button monthNext = (Button)findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

        //밀어서 스케줄 삭제
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(lv,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    adapter.remove(adapter.getItem(position));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
        lv.setOnTouchListener(touchListener);
        lv.setOnScrollListener(touchListener.makeScrollListener());
    }


    // Table 생성
    public void createTable() {
        try {
            String sql = "create table " + tableName + "(time text, "+"year integer, "
                    + "month integer, " + "day integer, "
                    + "schedule text not null)";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite", "error: " + e);
        }
    }

    // Table 삭제
    public void removeTable() {
        String sql = "drop table " + tableName;
        db.execSQL(sql);
    }

    // Data 추가
    public void insertData(String time, int year, int month, int day, String schedule) {
        String sql = "insert into " + tableName + " values('"+time+"', "+year+", " +
                month +", "+ day +", '" + schedule + "');";
        db.execSQL(sql);
    }
    /*
       // Data 업데이트
       public void updateData(int year, int month, int day, String schedule) {
          String sql = "update " + tableName + " set schedule = '" + schedule + "' where id = " +";";
          db.execSQL(sql);
       }
    */
    // Data 삭제
    public void removeData(int year, int month, int day, String schedule) {
        String sql = "delete from " + tableName + " where (year = " + year + " and " + "month = "+month+
                " and day = "+day+" and schedule = "+ schedule + ");";
        db.execSQL(sql);
    }

    // Data 읽기(꺼내오기)
    public void selectData(int year, int month, int day) {
        String sql = "select * from " + tableName + " where year = " + year + " and " + "month = "+month+
                " and day = "+day+";";

        Cursor result = db.rawQuery(sql, null);

        // result(Cursor 객체)가 비어 있으면 false 리턴
        if (result.moveToFirst()) {
            String schedule = result.getString(4);
            Log.d("TestDB", "\"schedule=\" + schedule ");
        }

        result.moveToFirst();
        while (!result.isAfterLast()) {
            int id = result.getInt(0);
            String schedule =  result.getString(0) + result.getString(4);
            Log.d("TestDB", "year= " + id + " schedule=" + schedule);

            as.add(schedule);
            result.moveToNext();
        }
        result.close();
    }

    public void updateLv(){
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,as);
        lv.setAdapter(adapter);
    }

    private void setMonthText(){
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();
        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(final MenuItem item){
        int curId = item.getItemId();

        if(curId == R.id.action_settings){
            final DayData dd = new DayData(curYear, curMonth, curDay);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View dia = View.inflate(this, R.layout.dia, null);
            builder.setTitle("일정추가");
            builder.setView(dia);
            save = (Button)dia.findViewById(R.id.BtnSave);
            et = (EditText)dia.findViewById(R.id.editText1);
            final TimePicker tp = (TimePicker)dia.findViewById(R.id.timePicker1);

            tp.setOnTimeChangedListener(this);

            View.OnClickListener saveListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt = et.getText().toString();
                    dd.setTime(tp.getCurrentHour(), tp.getCurrentMinute());
                    dd.setString(txt);
                    insertData(curTime, curYear, curMonth, curDay, txt);
                }
            };
            // Cancel 버튼 이벤트
            builder.setNegativeButton("닫기",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            save.setOnClickListener(saveListener);
            builder.show();
        }
        return true;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        curHour = hourOfDay;
        curMin = minute;
        am_pm = (hourOfDay < 12) ? "오전" : "오후";
        if(curHour>12){curHour = curHour-12;}
        curTime = am_pm+" "+curHour+"시 "+curMin+"분 ";
    }
}