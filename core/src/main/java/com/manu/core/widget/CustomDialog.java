//package com.manu.core.widget;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//public class CustomDialog extends Dialog {
//    public CustomDialog(Context context, int theme) {
//        super(context, theme);
//    }
//
//    public static class Builder {
//        private int year;
//        private int month;
//        private int day;
//        int y;
//        private Context context;
//        private DialogInterface.OnClickListener positiveButtonClickListener;
//        private DialogInterface.OnClickListener negativeButtonClickListener;
//        private ArrayList year_list, mouth_list, day_list;
//        ListView lv1, lv2, lv3;
//
//        public Builder(Context context) {
//            this.context = context;
//        }
//
//        public Builder setPositiveButton(DialogInterface.OnClickListener listener) {
//            this.positiveButtonClickListener = listener;
//            return this;
//        }
//
//        public Builder setNegativeButton(DialogInterface.OnClickListener listener) {
//            this.negativeButtonClickListener = listener;
//            return this;
//        }
//
//        public CustomDialog create() {
//            //创建日期选择器界面
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
//            View layout = inflater.inflate(R.layout.time_dialog, null);
//            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            if (positiveButtonClickListener != null) {
//                layout.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        positiveButtonClickListener.onClick(dialog,
//                                DialogInterface.BUTTON_POSITIVE);
//                    }
//                });
//            }
//
//            if (negativeButtonClickListener != null) {
//                layout.findViewById(R.id.cancel)
//                        .setOnClickListener(new View.OnClickListener() {
//                            public void onClick(View v) {
//                                negativeButtonClickListener.onClick(dialog,
//                                        DialogInterface.BUTTON_NEGATIVE);
//                            }
//                        });
//            }
//            lv1 = (ListView) layout.findViewById(R.id.lv1);//年listview
//            lv2 = (ListView) layout.findViewById(R.id.lv2);//月listview
//            lv3 = (ListView) layout.findViewById(R.id.lv3);//日listview
//            initListViews();
//            dialog.setContentView(layout);
//            return dialog;
//        }
//
//        private void initListViews() {
//
//            Calendar calendar = Calendar.getInstance();
//            //获得当前的日期
//            year = calendar.get(Calendar.YEAR);
//            month = calendar.get(Calendar.MONTH) + 1;
//            day = calendar.get(Calendar.DAY_OF_MONTH);
//            y = year - 2000 + 1;//这个y是从2000年到当前年份2016年中间间隔的年数，17年，这个可以看需求自己定
//            year_list = new ArrayList();
//            mouth_list = new ArrayList();
//            day_list = new ArrayList();
//            getContent(year_list, mouth_list, day_list);
//            lv1.setAdapter(new MyAdapter( year_list));
//            lv1.setSelection(year_list.size() - 3);//默认年份现实的是今年的年份，因为年份list最后一位是“”，当前年份是倒数第二位，即lv1.setSelection(year_list.size() - 3)显示的是当前的年份
//            //给listview设置滚动监听，当滚动停止后让listview显示特定的一项
//            lv1.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                    switch (scrollState) {
//                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://空闲状态
//                            //获得当滑动结束后listview可见的第0个item的滚动距离
//                            int a = 0 - lv1.getChildAt(0).getTop();
//                            //获得listview的每个item的高度
//                            int b = lv1.getMeasuredHeight() / 3;
//                            float f = (float) a / b;
//                            //如果滑动出屏幕的item的大小占item大小的比重在0到0.75之间的话，显示第一个可见的，就是说如果移动范围小的话显示的日期是不变的
//                            if (f < 0.75) {
//                                lv1.setSelection(lv1.getFirstVisiblePosition());
//                            }
//                            //如果滑动出屏幕的item的大小占item大小的比重在0。75到1之间的话，显示第二个可见的，就是说如果移动范围大的话显示的日期是要加一的
//                            if (f > 0.75 && f < 1) {
//                                lv1.setSelection(lv1.getFirstVisiblePosition() + 1);
//                            }
//                    }
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                }
//            });
//            //显示月份同上
//            lv2.setAdapter(new MyAdapter( mouth_list));
//            lv2.setSelection(calendar.get(Calendar.MONTH));
//            lv2.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                    switch (scrollState) {
//                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://空闲状态
//
//                            int a = 0 - lv2.getChildAt(0).getTop();
//                            int b = lv2.getMeasuredHeight() / 3;
//                            float f = (float) a / b;
//                            if (f < 0.75) {
//                                lv2.setSelection(lv2.getFirstVisiblePosition());
//                            }
//                            if (f > 0.75 && f < 1) {
//                                lv2.setSelection(lv2.getFirstVisiblePosition() + 1);
//                            }
//                    }
//                }
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                }
//            });
//            //显示日期同上
//            lv3.setAdapter(new MyAdapter( day_list));
//            lv3.setSelection(day - 1);
//            lv3.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                    switch (scrollState) {
//                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://空闲状态
//
//                            int a = 0 - lv3.getChildAt(0).getTop();
//                            int b = lv3.getMeasuredHeight() / 3;
//                            float f = (float) a / b;
//                            if (f < 0.75) {
//                                lv3.setSelection(lv3.getFirstVisiblePosition());
//                            }
//                            if (f > 0.75 && f < 1) {
//                                lv3.setSelection(lv3.getFirstVisiblePosition() + 1);
//                            }
//                    }
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                }
//            });
//        }
//
//        /**
//         * 给选择器添加内容
//         */
//        private void getContent(List year_list, List mouth_list, List day_list) {
//            //因为选择器是分3格，中间显示的数字才算是时间，拉到最上方和最下方的时候都要留白，所以要赋值的话list前后要填加“”，
//            //所以年份的list的长度是y+2,月份的长度是12+2=14，日期的话就是31+2=33。
//            String my;
//            for (int i = 0; i < y + 2; i++) {
//                if (i == 0 || i == y + 1) {
//                    my = "";
//                } else {
//                    int m = 2000 + i - 1;
//                    my = String.valueOf(m);
//                }
//                year_list.add(i, my);
//            }
//            String mm;
//            for (int i = 0; i < 14; i++) {
//                if (i == 0 || i == 13) {
//                    mm = "";
//                } else {
//                    mm = i + "";
//                }
//                mouth_list.add(i, mm);
//            }
//            String md;
//            for (int i = 0; i < 33; i++) {
//                if (i == 0 || i == 32) {
//                    md = "";
//                } else {
//                    md = i + "";
//                }
//                day_list.add(i, md);
//            }
//        }
//
//        public String getStr() {
//            int year = lv1.getFirstVisiblePosition() + 2000;
//            int month = lv2.getFirstVisiblePosition() + 1;
//            int day = lv3.getFirstVisiblePosition() + 1;
//            String m = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
//            String d = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
//            return String.valueOf(year) + "-" + m + "-" + d;
//        }
//
//        private class MyAdapter extends BaseAdapter {
//            private ArrayList list;
//
//            public MyAdapter(ArrayList list) {
//                this.list = list;
//            }
//            @Override
//            public int getCount() {
//                return list.size();
//            }
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                convertView = LayoutInflater.from(context).inflate(
//                        R.layout.list_item, null);
//                TextView tv = (TextView) convertView.findViewById(R.id.tv);
//                tv.setText(list.get(position));
//                return convertView;
//            }
//        }
//
//    }
//
//
//}
