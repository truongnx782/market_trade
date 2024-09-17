package market.demo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import market.demo.Util.Excel;
import market.demo.Util.Utils;
import market.demo.dto.CategoryDTO;
import market.demo.entity.Category;
import market.demo.repository.CategoryRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Cloudinary cloudinary;

    public Page<CategoryDTO> search(Map<String, Object> payload) {
        int page = (int) payload.getOrDefault("page", 0);
        int size = (int) payload.getOrDefault("size", 5);
        String search = (String) payload.getOrDefault("search", "");
        Integer status = (Integer) payload.getOrDefault("status", null);
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> data = categoryRepository.search(search, status, pageable);
        return data.map(Category::toDTO);
    }

    public List<CategoryDTO> getAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(Category::toDTO).collect(Collectors.toList());
    }

    public CategoryDTO ceate(String categoryName, List<MultipartFile> files, Long uid) {
        if(categoryName == null|| categoryName.isEmpty()){
            throw  new IllegalArgumentException("categoryName cannot be empty");
        }
        Optional<Category> maxIdSP = categoryRepository.findMaxId();
        Long maxId = maxIdSP.isPresent() ? maxIdSP.get().getId()+1 : 1;
        Category category = new Category();
        category.setCategoryCode("DM"+maxId);
        category.setCategoryName(categoryName);
        category.setCreateBy(uid);
        category.setCreatedAt(LocalDateTime.now());
        category.setStatus(Utils.Status.ACTIVE);

        if (files != null) {
            Category finalCategory = category;
            files.parallelStream().forEach(file -> {
                try {
                    Map r = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    finalCategory.setUrl((String) r.get("secure_url"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        category = categoryRepository.save(category);
        return Category.toDTO(category);
    }

    public CategoryDTO update(Long id, String categoryName, List<MultipartFile> files, String url, Long uid) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        if(categoryName == null|| categoryName.isEmpty()){
            throw  new IllegalArgumentException("categoryName cannot be empty");
        }
        Category category = optionalCategory.get();
        category.setCategoryName(categoryName);
        category.setUpdateBy(uid);
        category.setUpdatedAt(LocalDateTime.now());

        if (files != null) {
            Category finalCategory = category;
            files.parallelStream().forEach(file -> {
                try {
                    Map r = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    deleteImageByPublicId(optionalCategory.get().getUrl());
                    finalCategory.setUrl((String) r.get("secure_url"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        category = categoryRepository.save(category);
        return Category.toDTO(category);
    }

    public CategoryDTO getById(Long id, Long uid) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        return Category.toDTO(optionalCategory.get());
    }

    public CategoryDTO delete(Long id, Long uid) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("category not found");
        }
        Category category = optionalCategory.get();
        category.setStatus(Utils.Status.IN_ACTIVE);
        category.setUpdateBy(uid);
        category.setUpdatedAt(LocalDateTime.now());
        category = categoryRepository.save(category);
        return Category.toDTO(category);
    }

    public CategoryDTO restore(Long id, Long uid) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("category not found");
        }
        Category category = optionalCategory.get();
        category.setStatus(Utils.Status.ACTIVE);
        category.setUpdateBy(uid);
        category.setUpdatedAt(LocalDateTime.now());
        category = categoryRepository.save(category);
        return Category.toDTO(category);
    }


    public Object importExcel(MultipartFile file, Long uid) throws IOException {
        Optional<Category> maxIdOpt = categoryRepository.findMaxId();
        List<Category> existingCategorys = categoryRepository.findAllByOrderByIdDesc();
        Long maxId = maxIdOpt.map(utility -> utility.getId()+1).orElse(Long.valueOf(1));

        Set<String> uniqueNames = new HashSet<>();
        List<String> duplicateNames = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String cellValue = Excel.getCellValue(row.getCell(0));
                Category category = new Category();
                category.setCategoryName(cellValue);
                category.setCategoryCode("C" + maxId);
                category.setStatus(Utils.Status.ACTIVE);

                boolean isDuplicate = !uniqueNames.add(cellValue) ||
                        existingCategorys.stream().anyMatch(existing ->
                                existing.getCategoryName().equals(cellValue));

                if (isDuplicate) {
                    duplicateNames.add(cellValue);
                    continue;
                }

                categories.add(category);
                maxId = maxId+1;
            }

            List<Category> savedCategorys = categoryRepository.saveAll(categories);
            if (!duplicateNames.isEmpty()) {
                throw new IllegalArgumentException("Tên danh mục bị trùng: " + duplicateNames);
            }
            return savedCategorys.stream().map(Category::toDTO).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Dữ liệu không đúng định dạng!", e);
        }
    }


    public byte[] exportTemplate() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Template");
            sheet.createRow(0).createCell(0).setCellValue("Name");
            // Tạo style cho text
            CellStyle textStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            textStyle.setDataFormat(format.getFormat("@"));
            sheet.setDefaultColumnStyle(0, textStyle);

            workbook.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate template", e);
        }
    }

    public byte[] exportData(Map<String, Object> payload) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");
            // Create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Code");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Status");

            // Fetch data
            Page<CategoryDTO> categoryDTOs = search(payload);
            List<Category> categories = categoryDTOs.stream()
                    .map(Category::toEntity)
                    .collect(Collectors.toList());

            // Write data to sheet
            int rowIndex = 1;
            for (Category category : categories) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(category.getCategoryCode());
                row.createCell(1).setCellValue(category.getCategoryName());
                row.createCell(2).setCellValue(category.getStatus());
            }
            // Tạo CellStyle với định dạng text
            DataFormat format = workbook.createDataFormat();
            CellStyle textStyle = workbook.createCellStyle();
            textStyle.setDataFormat(format.getFormat("@")); // "@" là định dạng cho text

            // Áp dụng định dạng text cho tất cả các cột
            for (int i = 0; i < 3; i++) {
                sheet.setDefaultColumnStyle(i, textStyle);
            }

            workbook.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data", e);
        }
    }

    public void deleteImageByPublicId(String url) throws IOException {
        String publicId = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }


}
