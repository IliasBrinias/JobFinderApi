package com.unipi.msc.jobfinderapi.Model.User.Image;

import com.unipi.msc.jobfinderapi.Shared.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image uploadImage(MultipartFile file) throws IOException {
        return imageRepository.save(Image.builder()
                .name(file.getName())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());
    }
}
