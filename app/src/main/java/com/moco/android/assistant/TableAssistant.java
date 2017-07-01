package com.moco.android.assistant;

import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Hannah on 27.06.2017.
 */

class TableAssistant {
    private int columns;
    private int rows;
    private boolean labelColumns = false;
    private boolean labelRows = false;
    private ArrayList<String> rowNames;
    private ArrayList<String> columnNames;
    private ArrayList<int[]> scoreTable;

    TableAssistant(Map<String, String> tableSettings, ArrayList playerNames) {

        if (Integer.parseInt(tableSettings.get("labelCols")) == 1) {
            labelColumns = true;

        }

        if (Integer.parseInt(tableSettings.get("usePlayerNames")) == 1) {
            columnNames = playerNames;

            columns = playerNames.size();
        }
        if (Integer.parseInt(tableSettings.get("labelRows")) == 1) {
            labelRows = true;
        }
        String value = tableSettings.get("columnsNumber");
        if(value != null){  this.columns = Integer.parseInt(value) ;}
        value = tableSettings.get("rowsNumber");
        if(value!=null) {this.rows = Integer.parseInt(value);}

        scoreTable = new ArrayList<>();
        for(int i = 0; i<rows;i++){
            scoreTable.add(new int[columns]);
        }
        rowNames = new ArrayList<>();
            for(int i=0;i<rows;i++){
                rowNames.add("Round "+i);

        }

    }

    void drawTable(final InGameActivity inGameActivity){
        System.out.println("Column: "+ columns + " Row: "+rows+ " labelColumn: "+ labelColumns+ " labelRows: "+labelRows+ " PlayerNames: "+ columnNames);

        final TableLayout table = (TableLayout) inGameActivity.findViewById(R.id.tlScoreTable);
        if(table.getChildCount() > 0){
            table.removeAllViews();
        }
        table.setWeightSum(columns+1);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        rowParams.setMargins(2,2,2,2);
        /*
         * Create TableHead with Textviews
         */
        TableRow tableRow = new TableRow(inGameActivity);
        tableRow.setLayoutParams(tableParams);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(0,0,4,2);

        for(int column = -1; column < columnNames.size();column++){
                final TextView textView = new TextView(inGameActivity);

                textView.setPadding(10,10,10,10);
                textView.setBackgroundColor(Color.LTGRAY);

                if (column >= 0) {
                    textView.setText(columnNames.get(column));
                } else {
                    textView.setText("");
                }
                textView.setLayoutParams(rowParams);
                tableRow.addView(textView);

        }
        table.addView(tableRow);

        table.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT));

        /*
         * Create TableRows
         */
        for(int row = 0; row < rows; row++) {
            tableRow = new TableRow(inGameActivity);
            tableRow.setLayoutParams(tableParams);

            int[] rowContent = scoreTable.get(row);
            /*
            Create TABLECOLUMNS
             */
            for (int column = -1; column < columns; column++) {
                final EditText textView = new EditText(inGameActivity);
                textView.setSelectAllOnFocus(true);
                textView.setBackgroundColor(Color.LTGRAY);
                textView.setPadding(10,10,10,10);

                final int rowNumber = row;
                final int columnNumber = column;
                textView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        textView.getText();
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(columnNumber>=0) {
                            System.out.println(rowNumber + "" + columnNumber);
                            int[] ary = scoreTable.get(rowNumber);
                            ary[columnNumber] = Integer.parseInt(s.toString());
                            createTableFooter(inGameActivity);
                        }
                        if(columnNumber==-1){
                            rowNames.set(rowNumber,s.toString());
                            System.out.println(rowNames);
                        }
                    }
                });
                /*
               Create the first column
                 */
                if(column == -1) {
                    textView.setLayoutParams(rowParams);
                    tableRow.addView(textView);
                    if (labelRows) {
                        textView.setText(rowNames.get(row));

                    } else {
                        textView.setText("Round " + (row + 1));
                        textView.setFocusable(false);
                        textView.setFocusableInTouchMode(false);
                    }
                }

                if (column >= 0) {
                    textView.setText(""+rowContent[column]);
                    textView.setInputType(InputType.TYPE_CLASS_NUMBER);

                    textView.setLayoutParams(rowParams);// TableRow is the parent view
                    tableRow.addView(textView);
                }
            }
            table.addView(tableRow);
        }

        table.setBackgroundColor(Color.BLACK);
        createTableFooter(inGameActivity);

    }
    /*
     * Create TableFoot with Textviews
     */
    private void createTableFooter(InGameActivity inGameActivity){
        TableLayout footer = (TableLayout)  inGameActivity.findViewById(R.id.tlResultTable);
        if(footer.getChildCount() > 0){
            footer.removeAllViews();
        }
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        rowParams.setMargins(2,2,2,2);
        TableRow tableRow = new TableRow(inGameActivity);

        tableRow.setLayoutParams(tableParams);
        tableRow.setPadding(0,0,4,2);

        for(int column = -1; column < columnNames.size();column++){
            final int columnNumber = column;
                final TextView textView = new TextView(inGameActivity);
                if (column >= 0) {
                    textView.setText(""+calculateColumn(columnNumber));
                } else {
                    textView.setText("Sum: ");
                }
                textView.setTextSize(20);
                textView.setBackgroundColor(Color.LTGRAY);
                textView.setPadding(10,10,10,10);
                textView.setLayoutParams(rowParams);
                tableRow.addView(textView);
            }

        footer.setBackgroundColor(Color.BLACK);
        footer.addView(tableRow);
    }

    void addRow(InGameActivity inGameActivity){
        this.rows++;
        this.scoreTable.add(new int[columns]);
        rowNames.add("Round: "+ rows);
        drawTable(inGameActivity);
    }

    void saveScore(ArrayList<Integer> currentDiceThrow, int currentPlayer, int currentRound){
        int sum = 0;
        System.out.println(currentDiceThrow);
        for(int i = 0; i<currentDiceThrow.size();i++){
            sum += currentDiceThrow.get(i);
        }
        scoreTable.get(currentRound-1)[currentPlayer] = sum;
    }

   int calculateColumn(int column){
        int sum=0;
        for(int i=0;i<scoreTable.size();i++){
            sum+=scoreTable.get(i)[column];
        }
        return sum;
    }

}
