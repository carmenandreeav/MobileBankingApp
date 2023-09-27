package com.example.bankingapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.bankingapp.database.NewTransferDP;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataSource;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.PasswordAuthentication;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class DialogExtras extends DialogFragment{
    Button btnCancel;
    Button btnSolicita;
    FloatingActionButton fabDeLa;
    FloatingActionButton fabPanaLa;

//    TransactionsFragment transactionsFragment = new TransactionsFragment();

    TextView tvDeLa, tvPanaLa;

    Date startDate;
    Date endDate;
    XSSFWorkbook workbook;

    public static final int REQUEST_SAVE_EXCEL_FILE = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_extras, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnCancel=view.findViewById(R.id.btnRenunta);
        btnSolicita=view.findViewById(R.id.btnSolicita);
        fabDeLa=view.findViewById(R.id.fabDeLa);
        fabPanaLa = view.findViewById(R.id.fabPanaLa);

        tvDeLa = view.findViewById(R.id.tvDeLa);
        tvPanaLa = view.findViewById(R.id.tvPanaLa);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        fabDeLa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogSTART();
            }
        });

        fabPanaLa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogEND();
            }
        });

        btnSolicita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateExcelFile();
            }
        });





        return view;
    }

    public void showDatePickerDialogSTART(){
        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.customDatePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                startDate=cal.getTime();



                Calendar newDate = Calendar.getInstance();
                newDate.set(2023,6,19);



                Calendar targetDate = Calendar.getInstance();
                targetDate.set(2023, 0, 22); // 22-01-2023
                if (cal.before(targetDate)) {
                    // Create and show the dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Data inexistenta!")
                            .setPositiveButton("Close", null)
                            .create()
                            .show();
                    return; // Exit the method
                }else if(cal.after(newDate)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Nu se poate alege o data din viitor")
                            .setPositiveButton("Close", null)
                            .create()
                            .show();
                    return; // Exit the method
                }

                tvDeLa.setText(DateConverter.fromDate(startDate));
            }
        }, 2023, 0, 15);

        dialog.show();
    }

    public void showDatePickerDialogEND(){
        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.customDatePicker,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                endDate=cal.getTime();


                Calendar newDate = Calendar.getInstance();
                newDate.set(2023,6,19);

                String primaData = null;
                primaData = tvDeLa.getText().toString();

                int comparinonResult = startDate.compareTo(endDate);

                Calendar targetDate = Calendar.getInstance();
                targetDate.set(2023, 0, 22); // 22-01-2023
                if (cal.before(targetDate)) {
                    // Create and show the dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Data inexistenta!")
                            .setPositiveButton("Close", null)
                            .create()
                            .show();
                    return; // Exit the method
                }else if(cal.after(newDate)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Nu se poate alege o data din viitor")
                            .setPositiveButton("Close", null)
                            .create()
                            .show();
                    return; // Exit the method
                }else if(comparinonResult>0) {
                    // Create and show the dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Nu se poate alege o data mai veche decat prima!")
                            .setPositiveButton("Close", null)
                            .create()
                            .show();
                    return;
                }



                tvPanaLa.setText(DateConverter.fromDate(endDate));
            }
        }, 2023, 0, 15);

        dialog.show();
    }

    public void generateExcelFile(){
        workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Extras de cont");
        XSSFRow headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("Suma");
        headerRow.createCell(1).setCellValue("Beneficiar");
        headerRow.createCell(2).setCellValue("IBAN");
        headerRow.createCell(3).setCellValue("Descriere");
        headerRow.createCell(4).setCellValue("Data");
        headerRow.createCell(5).setCellValue("Venit");
        headerRow.createCell(6).setCellValue("Factura");

       // Log.d("Excel", "Transfer List Size: " + newTransferList.size());

        NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());
        List<NewTransfer>newTransferList1 = newTransferDP.getNewTransferDAO().getAllTransfers();

        int rowNum = 1;
        for(NewTransfer transfer:newTransferList1){
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(transfer.getSuma());
            row.createCell(1).setCellValue(transfer.getBeneficiar());
            row.createCell(2).setCellValue(transfer.getIban());
            row.createCell(3).setCellValue(transfer.getDescriere());
            row.createCell(4).setCellValue(transfer.getDataTranzactie());
            row.createCell(5).setCellValue(transfer.isVenit());
            row.createCell(6).setCellValue(transfer.isFactura());
            Log.d("Excel", "Transfer " + ": " + transfer);
        }



        for (int i = 0; i < 7; i++) {
            sheet.setColumnWidth(i, calculateColumnWidth(sheet, i));
        }

        // Check if the WRITE_EXTERNAL_STORAGE permission is granted
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_SAVE_EXCEL_FILE);
        } else {
            // Permission is granted, save the file
            saveExcelFile();
        }

    }



    public int calculateColumnWidth(XSSFSheet sheet, int columnIndex){
        int maxWidth = 0;

        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex);
            if (cell != null && cell.getCellType() == CellType.STRING) {
                int cellWidth = cell.getStringCellValue().getBytes().length;
                maxWidth = Math.max(maxWidth, cellWidth);
            }
        }
        return (maxWidth + 2) * 256;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_SAVE_EXCEL_FILE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, save the file
                saveExcelFile();
            } else {
                // Permission denied, handle accordingly (e.g., show a message to the user)
                Toast.makeText(getContext(), "Write external storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveExcelFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.putExtra(Intent.EXTRA_TITLE, "file.xlsx");
        startActivityForResult(intent, REQUEST_SAVE_EXCEL_FILE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SAVE_EXCEL_FILE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if(uri!=null){
                try (OutputStream outputStream = getActivity().getContentResolver().openOutputStream(uri)) {
                    workbook.write(outputStream);
                    Toast.makeText(getContext(), "File saved successfully", Toast.LENGTH_SHORT).show();

                    sendEmailWithAttachment(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getContext(), "File URI is null", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void sendEmailWithAttachment(Uri attachmentUri) {


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        final String senderEmail = "andreea.carmen03@gmail.com";
        final String password = "jgvvsmfcbjhkyglo";

        final String recipientEmail = "andreea.carmen03@gmail.com";


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, password);
            }
        });

        AsyncTask<Void, Void, Boolean> emailTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    // Create a new message
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                    message.setSubject("Excel File");

                    // Create the message body
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setText("Attached to this email, you will find an Excel file containing a detailed overview of your account activity for the specified period. The statement includes information regarding transactions, balances, and other relevant details." +
                            "Should you require any assistance or have any questions regarding the account statement, please do not hesitate to contact our customer support team. We are available to assist you in any way we can.\n" +
                            "\n" +
                            "Thank you for choosing C-MobileGroup as your financial partner." +
                            "\nBest regards,\n" +
                            "\n" +
                            "\n" +
                            "C-MobileGroup Bank");

                    // Create a temporary file to store the attachment data
                    File attachmentFile = new File(getContext().getCacheDir(), "attachment.xlsx");
                    OutputStream outputStream = new FileOutputStream(attachmentFile);

                    // Create the attachment

                    InputStream inputStream = getActivity().getContentResolver().openInputStream(attachmentUri);
                    if (inputStream != null) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        outputStream.close();
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(attachmentFile);
                        attachmentPart.setDataHandler(new DataHandler(source));
                        attachmentPart.setFileName("file.xlsx");

                        // Create a multipart message and add the message body and attachment
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(messageBodyPart);
                        multipart.addBodyPart(attachmentPart);

                        // Set the multipart message as the content of the email
                        message.setContent(multipart);

                        // Send the email
                        Transport.send(message);
                        return true;


                    } else {
                        // Handle the case when the input stream is null
                        Toast.makeText(getContext(), "Attachment file not found", Toast.LENGTH_SHORT).show();
                        return false;
                    }


                } catch (IOException | MessagingException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    Toast.makeText(getContext(), "Email sent successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to send email", Toast.LENGTH_SHORT).show();
                }
            }


        };

        emailTask.execute();
    }




}
