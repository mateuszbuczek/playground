package com.example.demo.totp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.buf.HexUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.time.Instant;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Totp {

    private static final Integer STEP_SIZE = 30_000;

    @Getter
    private final String key;

    public static Totp create() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return Totp.of(base32.encodeAsString(bytes));
    }

    public static Totp of(String key) {
        return new Totp(key);
    }

    public String getCurrentPassword() {
        return getPassword(0L);
    }

    // relative to current step
    public String getPassword(Long relativeStep) {
        byte[] keyBytes = new Base32().decode(this.key);
        String keyHex = Hex.encodeHexString(keyBytes);
        return getOTP(getCurrentStep() + relativeStep, keyHex);
    }

    private Long getCurrentStep() {
        return Instant.now().toEpochMilli() / STEP_SIZE;
    }

    // key as hex string
    private String getOTP(final long step, final String key) {
        String steps = Long.toHexString(step).toUpperCase();
        while (steps.length() < 16) {
            steps = "0" + steps;
        }

        // Get the HEX in a Byte[]
        final byte[] msg = hexStringToBytes(steps);
        final byte[] k = hexStringToBytes(key);

        final byte[] hash = computeHash(k, msg);

        // put selected bytes into result int
        final int offset = hash[hash.length - 1] & 0xf;
        final int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
        final int otp = binary % 1000000;

        String result = Integer.toString(otp);
        while (result.length() < 6) {
            result = "0" + result;
        }
        return result;
    }

    /** converts HEX string to Byte[] */
    private byte[] hexStringToBytes(final String hex) {
        // Adding one byte to get the right conversion
        // values starting with "0" can be converted
        final byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
        final byte[] ret = new byte[bArray.length - 1];

        // Copy all the REAL bytes, not the "first"
        System.arraycopy(bArray, 1, ret, 0, ret.length);
        return ret;
    }

    /** HMAC computes a Hashed Message Authentication Code with the crypto hash
     * algorithm as a parameter.
     */
    private byte[] computeHash(final byte[] keyBytes, final byte[] text) {
        try {
            final Mac hmac = Mac.getInstance("HmacSHA1");
            final SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (final GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

}
