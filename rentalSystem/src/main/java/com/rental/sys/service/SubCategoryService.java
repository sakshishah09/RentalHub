package com.rental.sys.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.convertor.entities.SubCategoryModelToEntity;
import com.rental.sys.convertor.model.SubCategoryEntityToModel;
import com.rental.sys.entities.Subcategory;
import com.rental.sys.model.response.SubCategoryResponse;
import com.rental.sys.repo.SubCategoryRepo;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepo subCategoryRepo;

    @Autowired
    private SubCategoryModelToEntity subCategoryModelToEntity;

    @Autowired
    private SubCategoryEntityToModel subCategoryEntityToModel;

    // Create subcategory
    public SubCategoryResponse createSubCategory(String name, Integer categoryId, MultipartFile image) throws Exception {
        Subcategory subcategory = subCategoryModelToEntity.createSubCategory(name, categoryId, image);
        Subcategory saved = subCategoryRepo.save(subcategory);
        return subCategoryEntityToModel.convertToResponse(saved);
    }
    public SubCategoryResponse updateSubCategory(Integer id, String name, Integer categoryId, MultipartFile image) throws Exception {
        Subcategory subcategory = subCategoryModelToEntity.updateSubCategory(id, name, categoryId, image);
        Subcategory updated = subCategoryRepo.save(subcategory);
        return subCategoryEntityToModel.convertToResponse(updated);
    }
    public List<SubCategoryResponse> findAllSubCategories(Integer page, Integer size) throws Exception {
        List<Subcategory> subcategoryList = subCategoryRepo.findAllSubCategories(PageRequest.of(page, size));
        return subCategoryEntityToModel.convertToList(subcategoryList);
    }

    // Count all subcategories
    public long countAllSubCategories() {
        return subCategoryRepo.count();
    }
    public List<SubCategoryResponse> getSubCategoriesByCategory(Integer categoryId) {
        List<Subcategory> list = subCategoryRepo.findByCategoryId(categoryId);
        return list.stream()
                .map(subCategoryEntityToModel::convertToResponse)
                .collect(Collectors.toList());
    }

    public void deleteSubCategory(Integer id) {
        subCategoryRepo.deleteById(id);
    }
}
