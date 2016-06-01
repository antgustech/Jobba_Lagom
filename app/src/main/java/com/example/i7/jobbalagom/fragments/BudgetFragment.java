package com.example.i7.jobbalagom.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.BudgetFragmentCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Handles the Budget screen.
 * TODO: Class not yet finished.
 */

public class BudgetFragment extends Fragment {
    private ListView listExpenses;
    private ListView listIncomes;
    private BudgetFragmentCallback callback;
    private int selectedMonth, selectedYear;
    private TextView budgetDate;



    /**
     * Initializes fragment.
     *
     * @param inflater           layout object that is used to show the layout of fragment.
     * @param container          the parent view this fragment is added to.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchy associated with fragment.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }


    /**
     * Called after the onCreateView has executed makes final UI initializations.
     *
     * @param view               this fragment view.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     */
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    /**
     * Initializes components.
     *
     * @param v this fragment view.
     */

    private void initComponents(View v) {
        listExpenses = (ListView) v.findViewById(R.id.listExpenses);
        listIncomes = (ListView) v.findViewById(R.id.listIncomes);

        selectedMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        selectedYear = Calendar.getInstance().get(Calendar.YEAR) % 100;

        RelativeLayout budgetLayout = (RelativeLayout) v.findViewById(R.id.budgetLayout);
        budgetLayout.setOnTouchListener(new OnSwipeTouchListener(getActivity()));

        budgetDate = (TextView) v.findViewById(R.id.budgetDate);
        budgetDate.setText(callback.getDate(selectedMonth,selectedYear));

        initListIncome(selectedMonth);
        initListExpense(selectedMonth);

        listExpenses.setOnItemClickListener(new ExpenseListener());
        listIncomes.setOnItemClickListener(new IncomeListener());

    }

    private void initListExpense(int month){

        String[] arrayExpenses = callback.getExpenses(month);
        ArrayList<String> expenseList = new ArrayList<>();
        Collections.addAll(expenseList, arrayExpenses);
        ArrayAdapter<String> adapterExpense = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayExpenses);
        listExpenses.setAdapter(adapterExpense);
    }

    private void initListIncome(int month){

        String[] arrayIncomes = callback.getIncomes(month);
      //  String[] arrayIncomes = {"öpö","lol"};

        ArrayList<String> incomeList = new ArrayList<>();
        Collections.addAll(incomeList, arrayIncomes);
        ArrayAdapter<String> adapterIncome = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayIncomes);
        listIncomes.setAdapter(adapterIncome);

        for(int i = 0; i<arrayIncomes.length;i++){
          //  Log.e("initListIncome", "After all is done loop array " + arrayIncomes[i] );
        }
    }

    /**
     * Sets Callback
     *
     * @param callback listener for this class.
     */

    public void setCallBack(BudgetFragmentCallback callback) {
        this.callback = callback;
    }

    private class ExpenseListener implements ListView.OnItemClickListener {

        /**
         * Checks what button is clicked.
         *
         * @param parent   the AdapterView to check on.
         * @param view     the current View.
         * @param position which position the button that was clicked have.
         * @param id       the id of the element pressed.
         */
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String itemValue = (String) listExpenses.getItemAtPosition(position);
            String[] splitSelected = itemValue.split(",");
            final int itemID = Integer.parseInt(splitSelected[3].substring(1));

            new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle)
                    .setTitle("Radera Utgift")
                    .setMessage("Radera utgiften nr " + itemID +"?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            callback.removeExpense(itemID);
                            loadList();

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }


    private class IncomeListener implements ListView.OnItemClickListener {

        /**
         * Checks what button is clicked.
         *
         * @param parent   the AdapterView to check on.
         * @param view     the current View.
         * @param position which position the button that was clicked have.
         * @param id       the id of the element pressed.
         */
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String itemValue = (String) listExpenses.getItemAtPosition(position);
            String[] splitSelected = itemValue.split(",");
            final int itemID = Integer.parseInt(splitSelected[3].substring(1));

            new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle)
                    .setTitle("Radera Inkomst")
                    .setMessage("Radera inkomsten nr " + itemID +"?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            callback.removeIncome(itemID);

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }




    private class OnSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;

        /**
         * Creates a new GestureDetector in the given context.
         *
         * @param ctx the current context.
         */

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }


        /**
         * Listener for event.
         *
         * @param v     the view the even occurred in.
         * @param event the kind of event.
         * @return true if a touchevent happend.
         */

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        /**
         * Shows new data if the swipe went right.
         */

        public void onSwipeRight() {
            if (selectedMonth == 1) {
                selectedYear--;
                selectedMonth = 12;
            } else {
                selectedMonth--;
            }

            loadList();



        }

        /**
         * Shows new data if the swipe went left.
         */

        public void onSwipeLeft() {
            if (selectedMonth == 12) {
                selectedYear++;
                selectedMonth = 1;
            } else {
                selectedMonth++;
            }

            loadList();
        }

        /**
         * On swipeTop, do nothing.
         */

        public void onSwipeTop() {
        }

        /**
         * On swipeBotton, do nothing.
         */

        public void onSwipeBottom() {
        }



        /**
         * Listener for swipe motions on the main graph view.
         */

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            /**
             * If you swipe down, nothing happends.
             *
             * @param e where the swipe ocurred.
             * @return true if there was a swipe going downwards.
             */

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            /**
             * Checks if the swipe is valid and which direction it happend.
             *
             * @param e1        where the swipe started.
             * @param e2        where the swipe ended.
             * @param velocityX the speed of the swipe motion on the x axis.
             * @param velocityY the speed of the swipe motion on the y axis.
             * @return true of the swipe was valid.
             */

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }
    }

    private void loadList(){
        budgetDate.setText(callback.getDate(selectedMonth, selectedYear));
        initListIncome(selectedMonth);
        initListExpense(selectedMonth);
    }


}
