package com.kk.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kk.api.Constant.QRConstant;

public class QRCodeGenerator {

   // private static final String QR_CODE_IMAGE_PATH = "D:/QRCode/";

    public static String generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

//        boolean ISO = Charset.forName("ISO8859-1").newEncoder()
//                .canEncode(text);
//        if (ISO) {
//            text = new String(text.getBytes("ISO8859-1"), "GB2312");
//        }

        text = new String(text.getBytes("UTF-8"), "ISO8859-1");

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);


        String url = new Date(System.currentTimeMillis()).getTime() +".png";

        String fileUrl = filePath + url;

        Path path = FileSystems.getDefault().getPath(fileUrl);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return url;

    }

    public static void main(String[] args) {
        try {
            System.out.println(generateQRCodeImage("This is my first QR Code", QRConstant.QR_CODE_IMAGE_WIDTH, QRConstant.QR_CODE_IMAGE_HEIGHT, QRConstant.QR_CODE_IMAGE_PATH));
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }

    }


}