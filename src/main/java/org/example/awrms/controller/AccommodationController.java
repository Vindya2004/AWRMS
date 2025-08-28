package org.example.awrms.controller;

import org.example.awrms.dto.AccommodationDTO;
import org.example.awrms.dto.ResponseDTO;
import org.example.awrms.entity.Accommodation;
import org.example.awrms.service.AccommodationService;
import org.example.awrms.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/accommodation")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    private static final String UPLOAD_DIR = "src/main/resources/uploads/";

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO>SaveAccommodation(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("category") String category,
            @RequestParam("costPerDay") String costPerDay,
            @RequestParam(value = "imageUrl",required = false)MultipartFile image){
        try{
            AccommodationDTO accommodationDTO = new AccommodationDTO();
            accommodationDTO.setName(name);
            accommodationDTO.setDescription(description);
            accommodationDTO.setLocation(location);
            accommodationDTO.setCategory(category);
            accommodationDTO.setCostPerDay(costPerDay);

            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                accommodationDTO.setImageUrl(imagePath);
            }
            int res = accommodationService.saveAccommodation(accommodationDTO);

            switch (res) {
                case VarList.Created:
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Accommodation Saved Successfully", accommodationDTO));
                case VarList.Not_Acceptable:
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Accommodation Already Exists", null));
                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + uniqueFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateAccommodation(
            @PathVariable Long id,
            @RequestParam("editAccommodationName") String name,
            @RequestParam("editAccommodationDescription") String description,
            @RequestParam("editAccommodationLocation") String location,
            @RequestParam("editAccommodationCategory") String category,
            @RequestParam("editAccommodationCostPerDay") String costPerDay,
            @RequestParam(value = "editAccommodationImage", required = false) MultipartFile image) {
        System.out.println(id);
        try {

            AccommodationDTO accommodationDTO = new AccommodationDTO();
            accommodationDTO.setName(name);
            accommodationDTO.setDescription(description);
            accommodationDTO.setLocation(location);
            accommodationDTO.setCategory(category);
            accommodationDTO.setCostPerDay(costPerDay);

            // Handle Image Upload
            if (image != null && !image.isEmpty()) {
                String imagePath = saveFile(image);
                accommodationDTO.setImageUrl(imagePath);
            }

            int res = accommodationService.updateAccommodation(id, accommodationDTO);

            switch (res) {
                case VarList.Created:
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseDTO(VarList.Created, "Accommodation Updated Successfully", accommodationDTO));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Accommodation Not Found", null));
                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteAccommodation(@PathVariable Long id) {
        try {
            int res = accommodationService.deleteAccommodation(id);
            switch (res) {
                case VarList.Created:
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseDTO(VarList.Created, "Accommodation Deleted Successfully", null));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Accommodation Not Found", null));
                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllAccommodation() {
        try {
            List<AccommodationDTO> allAccommodation = accommodationService.getAllAccommodation();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.Created, "All Accommodation Retrieved Successfully", allAccommodation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
