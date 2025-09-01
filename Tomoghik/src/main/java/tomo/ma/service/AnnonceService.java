package tomo.ma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tomo.ma.dto.AnnonceDTO;
import tomo.ma.model.Annonce;
import tomo.ma.model.Defect;
import tomo.ma.model.Image;
import tomo.ma.repositrory.AnnonceRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    NotificationService notificationService;
    private Path uploadPath = Paths.get("uploads");


    // Ajouter une nouvelle annonce
    public Optional<Annonce> saveAnnonce(Annonce annonce) {
        try {
            if(annonce.getDefects() != null ) {
                annonce.getDefects().forEach(defect -> {
                    defect.setAnnonce(annonce);
                });
            }
            Annonce savedAnnonce = annonceRepository.save(annonce);
            notificationService.notify(savedAnnonce);
            return Optional.ofNullable(savedAnnonce);
        } catch (Exception e) {
            e.printStackTrace(); // Pour debugging
            return Optional.empty();
        }
    }

    public Optional<Annonce> createAnnonce(Annonce annonce ,List<MultipartFile> images) {
        if(images.isEmpty()){
            return saveAnnonce(annonce);
        }
        List<Image> annonceImages = new ArrayList<>();
        images.forEach(image -> {
            if (!image.isEmpty()) {
                try {
                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                Image newImage = new Image();
                newImage.setImage("/uploads/" + fileName);
                newImage.setAnnonce(annonce);
                annonceImages.add(newImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        annonce.setImages(annonceImages);
        return saveAnnonce(annonce);
    }

    // Modifier une annonce existante
    public Optional<Annonce> updateAnnonce(Annonce annonce, long id) {
        try {
            return annonceRepository.findById(id).map(existing -> {
                existing.setBrand(annonce.getBrand());
                existing.setModel(annonce.getModel());
                existing.setFuelType(annonce.getFuelType());
                existing.setLocation(annonce.getLocation());
                existing.setPrice(annonce.getPrice());
                existing.setYear(annonce.getYear());
                existing.setMileage(annonce.getMileage());
                existing.setTransmission(annonce.getTransmission());
                existing.setDescription(annonce.getDescription());

                // Gérer images
                existing.getImages().clear();
                if (annonce.getImages() != null) {
                    annonce.getImages().forEach(img -> {
                        img.setAnnonce(existing);
                        existing.getImages().add(img);
                    });
                }

                // Gérer defects
                existing.getDefects().clear();
                if (annonce.getDefects() != null) {
                    annonce.getDefects().forEach(def -> {
                        def.setAnnonce(existing);
                        existing.getDefects().add(def);
                    });
                }

                return annonceRepository.save(existing);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Supprimer une annonce
    public boolean deleteAnnonce(long annonceId) {
        try {
            return annonceRepository.findById(annonceId).map(annonce -> {
                annonceRepository.delete(annonce);
                return true;
            }).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtenir une annonce par son ID
    public Optional<Annonce> getAnnonceById(long id) {
        try {
            return annonceRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Obtenir toutes les annonces d'un utilisateur donné
    public Optional<List<Annonce>> getAnnoncesByUser(long userId) {
        try {
            return Optional.ofNullable(annonceRepository.findByUserId(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public Optional<List<AnnonceDTO>> getAnnoncesByUserDTO(long userId) {
        try {
            List<Annonce> annonces = annonceRepository.findByUserId(userId);
            return Optional.ofNullable(annonces.stream().map(this::convertToDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.ofNullable(null); // Retourner une liste vide en cas d'erreu
        }
    }

    public Optional<List<Annonce>> getAllAnnonces() {
        try {
            return Optional.ofNullable(annonceRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public List<AnnonceDTO> getAllAnnoncesDTO() {
        try {
            List<Annonce> annonces = annonceRepository.findAll();
            return annonces.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Retourner une liste vide en cas d'erreur
        }
    }

    private AnnonceDTO convertToDTO(Annonce annonce) {
        try {
            AnnonceDTO dto = new AnnonceDTO();
            dto.setId(annonce.getId());
            dto.setBrand(annonce.getBrand());
            dto.setModel(annonce.getModel());
            dto.setFuelType(annonce.getFuelType());
            dto.setLocation(annonce.getLocation());
            dto.setPrice(annonce.getPrice());
            dto.setYear(annonce.getYear());
            dto.setMileage(annonce.getMileage());
            dto.setTransmission(annonce.getTransmission());
            dto.setDescription(annonce.getDescription());

            // Images - Avec vérification null safety
            if (annonce.getImages() != null) {
                dto.setImages(annonce.getImages().stream()
                        .filter(image -> image != null && image.getImage() != null)
                        .map(Image::getImage)
                        .collect(Collectors.toList()));
            } else {
                dto.setImages(List.of());
            }

            // Defects - Avec vérification null safety
            if (annonce.getDefects() != null) {
                dto.setDefects(annonce.getDefects().stream()
                        .filter(defect -> defect != null && defect.getDefect() != null)
                        .map(Defect::getDefect)
                        .collect(Collectors.toList()));
            } else {
                dto.setDefects(List.of());
            }

            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            // Retourner un DTO minimal en cas d'erreur
            AnnonceDTO dto = new AnnonceDTO();
            dto.setId(annonce.getId());
            dto.setBrand(annonce.getBrand());
            dto.setModel(annonce.getModel());
            dto.setImages(List.of());
            dto.setDefects(List.of());
            return dto;
        }
    }
}