package com.capgemini.middleware.domain.utills;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

public class Keccak256Encoder {
    public static String encode(String input) {
        // Initialize the Keccak-256 digest
        Keccak.Digest256 keccak256 = new Keccak.Digest256();

        // Convert the input string to bytes
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

        // Update the digest with the input data
        keccak256.update(inputBytes, 0, inputBytes.length);

        // Perform the final digest calculation
        byte[] hashBytes = keccak256.digest();

        // Convert the hash to a hexadecimal string
        return "0x" + Hex.toHexString(hashBytes);
    }
}
