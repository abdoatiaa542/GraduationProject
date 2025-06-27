package com.abdoatiia542.GraduationProject.utils.backup;


import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class BackupUtils {

    private static final String TRANSFORMATION = "AES";

    public MultipartFile compressAndEncrypt(MultipartFile file, SecretKey secretKey) {
        try {
            byte[] compressedData = compressFile(file);
            byte[] encryptedData = encryptFile(compressedData, secretKey);
            String filename = file.getOriginalFilename() + ".enc";
            return new MockMultipartFile(
                    filename,
                    filename,
                    "text/plain",
                    new ByteArrayInputStream(encryptedData)
            );
        } catch (Exception e) {
            log.error("Compression or encryption failed: {}", e.getMessage());
            throw new IllegalStateException("Compression or encryption failed", e);
        }
    }

    public byte[] compressFile(MultipartFile file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());
            zos.putNextEntry(zipEntry);
            InputStream is = file.getInputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
        }
        return baos.toByteArray();
    }

    public byte[] encryptFile(byte[] data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (CipherOutputStream cos = new CipherOutputStream(baos, cipher)) {
            cos.write(data);
        }
        return baos.toByteArray();
    }


    public byte[] decryptFile(byte[] inputData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (CipherInputStream cis = new CipherInputStream(new ByteArrayInputStream(inputData), cipher)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        }
        return baos.toByteArray();
    }

    public byte[] decompressFile(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipInputStream zis = new ZipInputStream(bais)) {
            ZipEntry zipEntry = zis.getNextEntry();
            if (zipEntry != null) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                zis.closeEntry();
            }
        }
        return baos.toByteArray();
    }


}
