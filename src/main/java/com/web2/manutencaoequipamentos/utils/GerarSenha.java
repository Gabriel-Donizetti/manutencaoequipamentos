package com.web2.manutencaoequipamentos.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GerarSenha {
    public static String gerarSenha() {
        try {
            String currentTime = String.valueOf(System.currentTimeMillis() / 1000);

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(currentTime.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar o hash MD5", e);
        }
    }
}
