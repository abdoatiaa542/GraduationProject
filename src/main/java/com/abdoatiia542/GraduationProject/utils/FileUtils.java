package com.abdoatiia542.GraduationProject.utils;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

public abstract class FileUtils {
    public static Set<String> SUPPORTED_IMAGE_TYPES = Set.of(
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE
    );

    public static Set<String> SUPPORTED_VIDEO_TYPES = Set.of(
            "video/mp4",
            "video/mpeg"
    );

    public static Set<String> SUPPORTED_AUDIO_TYPES = Set.of(
            "audio/mpeg"
    );

    public static boolean isSupportedImageOrVideoOrPDFOrDocx(MultipartFile file) {
        return isSupportedImage(file) || isSupportedVideo(file) ||
                isSupportedPdf(file) || isSupportedDocx(file);
    }

    public static boolean isSupportedImage(MultipartFile file) {
        return SUPPORTED_IMAGE_TYPES.contains(file.getContentType());
    }

    public static boolean isSupportedVideo(MultipartFile file) {
        return SUPPORTED_VIDEO_TYPES.contains(file.getContentType());
    }

    public static boolean isSupportedAudio(MultipartFile file) {
        return SUPPORTED_AUDIO_TYPES.contains(file.getContentType());
    }

    public static boolean isSupportedImagesAndVideos(MultipartFile[] files) {
        Predicate<MultipartFile> predicate = (file) -> isSupportedImage(file) || isSupportedVideo(file);
        return Arrays.stream(files).allMatch(predicate);
    }

    public static boolean isSupportedImagesAndAudios(MultipartFile[] files) {
        Predicate<MultipartFile> predicate = (file) -> isSupportedImage(file) || isSupportedAudio(file);
        return Arrays.stream(files).allMatch(predicate);
    }

    public static boolean isSupportedPdf(MultipartFile file) {
        return MediaType.APPLICATION_PDF_VALUE.equalsIgnoreCase(file.getContentType());
    }

    public static boolean isSupportedDocx(MultipartFile file) {
        return "docx".equalsIgnoreCase(file.getContentType());
    }

    public static MultipartFile compressJpeg(MultipartFile originalFile, double reduceQuality) {
        if (reduceQuality <= 0.0) {
            return originalFile;
        }
        try {
            BufferedImage inputImage = ImageIO.read(originalFile.getInputStream());
            if (inputImage == null) {

                return originalFile;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
            if (!writers.hasNext()) {
                // No writer found, skip
                return originalFile;
            }
            ImageWriter writer = writers.next();

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

                float quality = (float) (1.0 - reduceQuality);
                param.setCompressionQuality(quality);
            }

            try (MemoryCacheImageOutputStream mcios = new MemoryCacheImageOutputStream(baos)) {
                writer.setOutput(mcios);
                writer.write(null, new IIOImage(inputImage, null, null), param);
            } finally {
                writer.dispose();
            }

            byte[] compressedBytes = baos.toByteArray();
            return new MockMultipartFile(
                    originalFile.getName(),
                    originalFile.getOriginalFilename(),
                    originalFile.getContentType(),
                    compressedBytes
            );
        } catch (IOException e) {
            return originalFile;
        }
    }
}