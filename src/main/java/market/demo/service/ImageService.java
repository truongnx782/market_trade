//package market.demo.service;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import lombok.RequiredArgsConstructor;
//import market.demo.dto.ImageDTO;
//import market.demo.entity.Image;
//import market.demo.repository.ImageRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ImageService {
//
//    private final ImageRepository imageRepositoty;
//    private final Cloudinary cloudinary;
//    private final ExecutorService executorService;
//
//
//    public List<ImageDTO> create(List<MultipartFile> files, List<Long> images, Long roomId, Integer status, Long cid) throws IOException {
//        List<Image> listImage = new ArrayList<>();
//        if (files != null) {
//            List<Image> finalListImage = listImage;
//            files.parallelStream().forEach(file -> {
//                try {
//                    Map r = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
//                    Image image = new Image();
//                    Room room = new Room();
//                    room.setId(roomId);
//                    image.setRoom(room);
//                    image.setUrl((String) r.get("secure_url"));
//                    image.setStatus(status);
//                    image.setCompanyId(cid);
//                    finalListImage.add(image);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//
//        List<Image> imagesRepo = imageRepositoty.findAllByRoomIdAndCompanyId(roomId,cid);
//        List<Long> imageRemove;
//
//        if (images == null) {
//            imageRemove = imagesRepo.stream()
//                    .map(Image::getId)
//                    .collect(Collectors.toList());
//        } else {
//            imageRemove = imagesRepo.stream()
//                    .map(Image::getId)
//                    .filter(imageId -> !images.contains(imageId))
//                    .collect(Collectors.toList());
//        }
//
//        // Xóa ảnh khỏi Cloudinary
//        imagesRepo.stream()
//                .filter(image -> imageRemove.contains(image.getId()))
//                .forEach(image -> {
//                    try {
//                        deleteImageByPublicId(image.getUrl());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//
//        // Xóa ảnh khỏi repository
//        imageRepositoty.deleteAllByIdAndCompanyId(imageRemove,cid);
//
//        listImage = imageRepositoty.saveAll(listImage);
//        return listImage.stream()
//                .map(Image::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    public void deleteImageByPublicId(String url) throws IOException {
//        String publicId = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
//        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
//    }
//
//    public List<ImageDTO> getAllByRoomId( Long roomId,Long cid) {
//        List<Image> images = imageRepositoty.findAllByRoomIdAndCompanyId(roomId,cid);
//        return images.stream()
//                .map(Image::toDTO)
//                .collect(Collectors.toList());
//    }
//}
