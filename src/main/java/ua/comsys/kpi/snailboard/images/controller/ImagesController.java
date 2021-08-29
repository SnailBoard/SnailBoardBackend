package ua.comsys.kpi.snailboard.images.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.comsys.kpi.snailboard.images.service.ImagesService;

@RestController
@RequestMapping("/images")
public class ImagesController {

    final ImagesService imagesService;

    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String uploadImage(@RequestParam("image") MultipartFile image) {
        return imagesService.saveImages(image);
    }

    @PutMapping("/change/{oldImageName}")
    public String changeImage(@RequestParam("image") MultipartFile image, @PathVariable("oldImageName") String oldImageName) {
        return imagesService.change(image, oldImageName);
    }

    @GetMapping("/location")
    public String getImagesLocation() {
        return imagesService.getLocation();
    }

}
