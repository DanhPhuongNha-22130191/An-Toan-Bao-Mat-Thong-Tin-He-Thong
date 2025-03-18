package controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CategoryDTO;
import models.Category;
import services.CategoryService;

public class CategoryController extends HttpServlet {
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            listCategories(req, resp);
        } else if ("details".equals(action)) {
            showCategoryDetails(req, resp);
        }
    }

    private void listCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CategoryDTO> categories = categoryService.getAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("shop.jsp").forward(req, resp);
    }

    private void showCategoryDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long categoryId = Long.parseLong(req.getParameter("id"));
        Category category = categoryService.getById(categoryId);
        
        if (category != null) {
            req.setAttribute("category", mapToDTO(category));
            req.getRequestDispatcher("categoryDetails.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("categories.jsp?error=notfound");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            addCategory(req, resp);
        } else if ("delete".equals(action)) {
            deleteCategory(req, resp);
        } else if ("update".equals(action)) {
            updateCategory(req, resp);
        }
    }

    private void addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryDTO categoryDTO = extractCategoryDTO(req);
        Category category = mapToEntity(categoryDTO);
        categoryService.insert(category);
        resp.sendRedirect("CategoryController?action=list");
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long categoryId = Long.parseLong(req.getParameter("id"));
        categoryService.delete(categoryId);
        resp.sendRedirect("CategoryController?action=list");
    }

    private void updateCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CategoryDTO categoryDTO = extractCategoryDTO(req);
        Category category = mapToEntity(categoryDTO);
        categoryService.update(category);
        resp.sendRedirect("CategoryController?action=list");
    }

    private CategoryDTO extractCategoryDTO(HttpServletRequest req) {
        CategoryDTO categoryDTO = new CategoryDTO();
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            categoryDTO.setCategoryId(Long.parseLong(idParam));
        }
        categoryDTO.setName(req.getParameter("name"));
        categoryDTO.setImage(req.getParameter("image"));
        return categoryDTO;
    }
    
    private CategoryDTO mapToDTO(Category category) {
        return new CategoryDTO(
            category.getCategoryId(),
            category.getName(),
            category.getImage()
        );
    }
    
    private Category mapToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setName(categoryDTO.getName());
        category.setImage(categoryDTO.getImage());
        return category;
    }
}