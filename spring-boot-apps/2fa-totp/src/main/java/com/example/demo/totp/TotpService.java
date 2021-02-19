package com.example.demo.totp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

@Service
@NoArgsConstructor
public class TotpService {

    private static final String ISSUER = "demoApp";

    public String createSecretKey() {
        return Totp.create().getKey();
    }

    @SneakyThrows
    public byte[] createQrCode(String accountName, String secretKey) {
        String totpUrl = getTotpUrl(secretKey, accountName);
        return getQRCode(totpUrl, 500, 500);
    }

    public boolean validate(String key, String inputTotp) {
        return Totp.of(key).getCurrentPassword().equals(inputTotp);
    }

    @SneakyThrows
    private String getTotpUrl(String secretKey, String account) {
            return "otpauth://totp/"
                    + URLEncoder.encode(ISSUER + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(ISSUER, "UTF-8").replace("+", "%20");
    }

    private byte[] getQRCode(String totpUrl, int height, int width)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(
                totpUrl, BarcodeFormat.QR_CODE,
                width, height);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
            out.flush();

            return out.toByteArray();
        }
    }
}
