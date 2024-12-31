package br.ufrn.imd.projetofinal.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GerenciadorUsuarios {
    public static String hashSenha(String senha){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public static String unHashSenha(String senhaHash){
        System.out.println("Desencriptar a senha");
        return senhaHash;
    }

    public static void salvarUsuario(String usuario, String senha){
        String senhaHash = hashSenha(senha);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("infos.txt", true))) {
            writer.write(usuario + ";" + senhaHash);
            writer.newLine();
        } catch (IOException e){
            System.out.println("Erro ao salvar Usuário " + e.getMessage());
        }
    }

    public static void carregarUsuario(){

    }
}
