package main.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import fekedew.R;
import main.AppBase;
import main.bookloan.BookActivity;
import main.bookloan.BorrowNew;

public class StudentRegistration extends AppCompatActivity {


    private static final String TAG = "StudentRegistration";
    Activity activity = this;
    Button save, importStudent;
    Intent fileIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__registartion);
        save = findViewById(R.id.buttonSAVE);
        importStudent = findViewById(R.id.buttonImport);
        importStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("*/*");
                startActivityForResult(fileIntent, 10);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if (resultCode == RESULT_OK){
                    Uri filePath = data.getData();
                    toastMessage(filePath.getPath());
                    //decarle input file
                    File inputFile = new File(filePath.getPath());
                    try {
                        InputStream inputStream = new FileInputStream(inputFile);
                        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                        XSSFSheet sheet = workbook.getSheetAt(0);
                        int rowsCount = sheet.getPhysicalNumberOfRows();
                        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
                        StringBuilder sb = new StringBuilder();

                        //outter loop, loops through rows
                        for (int r = 1; r < rowsCount; r++) {
                            Row row = sheet.getRow(r);
                            int cellsCount = row.getPhysicalNumberOfCells();
                            //inner loop, loops through columns
                            for (int c = 0; c < cellsCount; c++) {
                                //handles if there are to many columns on the excel sheet.
                                String value = getCellAsString(row, c, formulaEvaluator);
                                String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                                Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
                                sb.append(value + ", ");

                            }
                            sb.append(":");
                        }
                        Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());

                        toastMessage(sb.toString());
//                        parseStringBuilder(sb);

                    }catch (FileNotFoundException e) {
                        Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage() );
                    } catch (IOException e) {
                        Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage() );
                    }
                }
                break;
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("MM/dd/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage() );
        }
        return value;
    }
//    public void parseStringBuilder(StringBuilder mStringBuilder){
//        Log.d(TAG, "parseStringBuilder: Started parsing.");
//
//        // splits the sb into rows.
//        String[] rows = mStringBuilder.toString().split(":");
//
//        //Add to the ArrayList<XYValue> row by row
//        for(int i=0; i<rows.length; i++) {
//            //Split the columns of the rows
//            String[] columns = rows[i].split(",");
//
//            //use try catch to make sure there are no "" that try to parse into doubles.
//            try{
//                double x = Double.parseDouble(columns[0]);
//                double y = Double.parseDouble(columns[1]);
//
//                String cellInfo = "(x,y): (" + x + "," + y + ")";
//                Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);
//
//            }catch (NumberFormatException e){
//
//                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());
//
//            }
//        }
//
//    }

    public void saveToDatabase() {
        EditText name = findViewById(R.id.edit_name);
        EditText roll = findViewById(R.id.roll);
        EditText contact = findViewById(R.id.contact);
        EditText age = findViewById(R.id.age);
        RadioGroup gender = findViewById(R.id.gender);

        if (name.getText().length() < 2 || roll.getText().length() == 0 ||
                contact.getText().length() < 2) {
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setTitle("Invalid");
            alert.setMessage("Insufficient Data");
            alert.setPositiveButton("OK", null);
            alert.show();
            return;
        }
        int contactn = 0, rolln = 0, agen = 0;
        String gendern = "M";
        try {
            rolln = Integer.parseInt(roll.getText().toString());
            agen = Integer.parseInt(age.getText().toString());
            if (gender.getCheckedRadioButtonId() == R.id.male)
                gendern = "M";
            else gendern = "F";

            String qu = "INSERT INTO STUDENT VALUES('" + name.getText().toString() + "'," +
                    "'" + Integer.parseInt(contact.getText().toString()) + "'," + rolln + ", '" + gendern + "', " + agen + ");";
            if (AppBase.handler.execAction(qu)) {
                Toast.makeText(getBaseContext(), "Student Added", Toast.LENGTH_SHORT).show();
                Toast.makeText(StudentRegistration.this, "Successfully added.", Toast.LENGTH_LONG).show();
                Intent in = new Intent(StudentRegistration.this, ProfileActivity.class);
                startActivity(in);
                this.finish();
            }else{
                Toast.makeText(getBaseContext(), "Student roll already exists." ,Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(activity, "Please enter correct format", Toast.LENGTH_LONG).show();
        }
    }
}
