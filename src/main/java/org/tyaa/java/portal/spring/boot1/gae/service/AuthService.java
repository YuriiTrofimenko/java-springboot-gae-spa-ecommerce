package org.tyaa.java.portal.spring.boot1.gae.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tyaa.java.portal.spring.boot1.gae.dao.RoleHibernateDAO;
import org.tyaa.java.portal.spring.boot1.gae.dao.UserHibernateDAO;
import org.tyaa.java.portal.spring.boot1.gae.entity.Role;
import org.tyaa.java.portal.spring.boot1.gae.entity.User;
import org.tyaa.java.portal.spring.boot1.gae.model.JsonHttpResponse;
import org.tyaa.java.portal.spring.boot1.gae.model.RoleModel;
import org.tyaa.java.portal.spring.boot1.gae.model.UserModel;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.tyaa.java.portal.spring.boot1.gae.dao.CategoryHibernateDAO;
import org.tyaa.java.portal.spring.boot1.gae.dao.ProductHibernateDAO;
import org.tyaa.java.portal.spring.boot1.gae.entity.Category;
import org.tyaa.java.portal.spring.boot1.gae.entity.Product;
import org.tyaa.java.portal.spring.boot1.gae.model.CategoryModel;
import org.tyaa.java.portal.spring.boot1.gae.model.ProductModel;

@Service
public class AuthService {

    @Autowired
    private RoleHibernateDAO roleDAO;

    @Autowired
    private UserHibernateDAO userDAO;

    @Autowired
    private CategoryHibernateDAO categoryDAO;

    @Autowired
    private ProductHibernateDAO productDAO;

    public JsonHttpResponse createRole(RoleModel _roleModel) {

        Role role = new Role(_roleModel.name);
        roleDAO.create(role);
        return new JsonHttpResponse(
                JsonHttpResponse.createdStatus,
                 "Role '" + _roleModel.name + "' created successfully"
        );
    }

    // Создание пользователя с ролью по умолчанию
    public JsonHttpResponse createUser(UserModel _userModel) throws Exception {

        // Получаем из хранилища объект роли по умолчанию
        Role role = roleDAO.read("user");
        // Создаем пользователя с заданными именем и паролем и
        // со ссылкой на объект роли по умолчанию
        userDAO.create(
                new User(
                        _userModel.name,
                         _userModel.password,
                         role
                )
        );
        return new JsonHttpResponse(
                JsonHttpResponse.createdStatus,
                 "User '" + _userModel.name + "' created successfully"
        );
    }

    public JsonHttpResponse createCategory(CategoryModel _categoryModel) {

        Category category = new Category(_categoryModel.name);
        categoryDAO.create(category);
        return new JsonHttpResponse(
                JsonHttpResponse.createdStatus,
                 "Category '" + _categoryModel.name + "' created successfully"
        );
    }

    // Создание продукта с категорией по умолчанию
    public JsonHttpResponse createProduct(ProductModel _productModel) throws Exception {

        // Получаем из хранилища объект роли по умолчанию
        Product product = productDAO.read("product");
        // Создаем пользователя с заданными именем и паролем и
        // со ссылкой на объект роли по умолчанию
        productDAO.create(
                new Product(
                        _productModel.name,
                         product
                )
        );
        return new JsonHttpResponse(
                JsonHttpResponse.createdStatus,
                 "User '" + _productModel.name + "' created successfully"
        );
    }

    public JsonHttpResponse<List<RoleModel>> readRole() {

        List<Role> roles = roleDAO.read();
        List<RoleModel> roleModels
                = roles.stream()
                        .map((r) -> {
                            return new RoleModel(r.getId(), r.getName());
                        })
                        .collect(Collectors.toList());
        return new JsonHttpResponse(
                JsonHttpResponse.fetchedStatus,
                 "The roles list fetched successfully",
                 roleModels
        );
    }

    public JsonHttpResponse<List<UserModel>> readUser() {

        List<User> users = userDAO.read();
        List<UserModel> userModels
                = users.stream()
                        .map((u) -> {
                            return new UserModel(
                                    u.getId(),
                                     u.getName(),
                                     u.getPassword(),
                                     new RoleModel(
                                            u.getRole().getId(),
                                             u.getRole().getName()
                                    )
                            );
                        })
                        .collect(Collectors.toList());
        return new JsonHttpResponse(
                JsonHttpResponse.fetchedStatus,
                 "The users list fetched successfully",
                 userModels
        );
    }

    public JsonHttpResponse<List<CategoryModel>> readCategory() {

        List<Category> categorys = categoryDAO.read();
        List<CategoryModel> categoryModels
                = categorys.stream()
                        .map((c) -> {
                            return new CategoryModel(c.getId(), c.getName());
                        })
                        .collect(Collectors.toList());
        return new JsonHttpResponse(
                JsonHttpResponse.fetchedStatus,
                 "The categories list fetched successfully",
                 categoryModels
        );
    }
    
     public JsonHttpResponse<List<ProductModel>> readProduct() {

        List<Product> products = productDAO.read();
        List<ProductModel> productModels
                = products.stream()
                        .map((p) -> {
                            return new ProductModel(
                                    p.getId(),
                                     p.getName(),
                                     new CategoryModel(
                                            p.getCategory().getId(),
                                             p.getCategory().getName()
                                    )
                            );
                        })
                        .collect(Collectors.toList());
        return new JsonHttpResponse(
                JsonHttpResponse.fetchedStatus,
                 "The productss list fetched successfully",
                 productModels
        );
    }

    public JsonHttpResponse<RoleModel> readRole(Long _id) throws Exception {
       
        Role role =
                roleDAO.read(_id);
        String status =
                (role != null && role.getId() != null)
                ? JsonHttpResponse.fetchedStatus
                : JsonHttpResponse.warningStatus;
        String message =
                (role != null && role.getId() != null)
                ? "The role fetched successfully"
                : "Not found";
        RoleModel roleModel = new RoleModel(role.getId(), role.getName());
        return new JsonHttpResponse(
                status
                , message
                , roleModel
        );
    }
    
    public JsonHttpResponse<CategoryModel> readCategory(Long _id) throws Exception {
       
        Category category =
                categoryDAO.read(_id);
        String status =
                (category != null && category.getId() != null)
                ? JsonHttpResponse.fetchedStatus
                : JsonHttpResponse.warningStatus;
        String message =
                (category != null && category.getId() != null)
                ? "The category fetched successfully"
                : "Not found";
        CategoryModel categoryModel = new CategoryModel(category.getId(), category.getName());
        return new JsonHttpResponse(
                status
                , message
                , categoryModel
        );
    }
      
    public JsonHttpResponse<UserModel> readUser(Long _id) throws Exception {

        User user
                = userDAO.read(_id);
        String status
                = (user != null && user.getId() != null)
                ? JsonHttpResponse.fetchedStatus
                : JsonHttpResponse.warningStatus;
        String message
                = (user != null && user.getId() != null)
                ? "The user fetched successfully"
                : "Not found";
        UserModel userModel = null;
        if (user != null && user.getId() != null) {
            userModel
                    = new UserModel(
                            user.getId(),
                             user.getName(),
                             user.getPassword(),
                             new RoleModel(
                                    user.getRole().getId(),
                                     user.getRole().getName()
                            )
                    );
        }
        return new JsonHttpResponse(
                status,
                 message,
                 userModel
        );
    }

    public JsonHttpResponse<ProductModel> readProduct(Long _id) throws Exception {

        Product product
                = productDAO.read(_id);
        String status
                = (product != null && product.getId() != null)
                ? JsonHttpResponse.fetchedStatus
                : JsonHttpResponse.warningStatus;
        String message
                = (product != null && product.getId() != null)
                ? "The product fetched successfully"
                : "Not found";
        ProductModel productModel = null;
        if (product != null && product.getId() != null) {
            productModel
                    = new ProductModel(
                            product.getId(),
                             product.getName(),
                             new CategoryModel(
                                    product.getCategory().getId(),
                                     product.getCategory().getName()
                            )
                    );
        }
        return new JsonHttpResponse(
                status,
                 message,
                 productModel
        );
    } 
    
    public JsonHttpResponse<UserModel> readUser(String _name) throws Exception {

        User user
                = userDAO.read(_name);
        String status
                = (user != null && user.getId() != null)
                ? JsonHttpResponse.fetchedStatus
                : JsonHttpResponse.warningStatus;
        String message
                = (user != null && user.getId() != null)
                ? "The user fetched successfully"
                : "Not found";
        UserModel userModel = null;
        if (user != null && user.getId() != null) {
            userModel
                    = new UserModel(
                            user.getId(),
                             user.getName(),
                             user.getPassword(),
                             new RoleModel(
                                    user.getRole().getId(),
                                     user.getRole().getName()
                            )
                    );
        }
        return new JsonHttpResponse(
                status,
                 message,
                 userModel
        );
    }

    public JsonHttpResponse<ProductModel> readProduct(String _name) throws Exception {

        Product product
                = productDAO.read(_name);
        String status
                = (product != null && product.getId() != null)
                ? JsonHttpResponse.fetchedStatus
                : JsonHttpResponse.warningStatus;
        String message
                = (product != null && product.getId() != null)
                ? "The user fetched successfully"
                : "Not found";
        ProductModel productModel = null;
        if (product != null && product.getId() != null) {
            productModel
                    = new ProductModel(
                            product.getId(),
                             product.getName(),
                             new CategoryModel(
                                    product.getCategory().getId(),
                                     product.getCategory().getName()
                            )
                    );
        }
        return new JsonHttpResponse(
                status,
                 message,
                 productModel
        );
    }

    /* public JsonHttpResponse read(String _name) throws Exception {
        
            Author author =
                    authorDAO.read(_name);
            String status =
                    (author != null && author.getId() != null)
                    ? JsonHttpResponse.fetchedStatus
                    : JsonHttpResponse.warningStatus;
            String message =
                    (author != null && author.getId() != null)
                    ? "The author fetched successfully"
                    : "Not found";
            return new JsonHttpResponse(
                    status
                    , message
                    , author
            );
    } */

 /* public JsonHttpResponse update(Author author) {
        
        authorDAO.update(author);
        return new JsonHttpResponse(
                JsonHttpResponse.updatedStatus
                , "The author updated successfully"
        );
    } */
    public JsonHttpResponse deleteRole(Long _id) {

        roleDAO.delete(_id);
        return new JsonHttpResponse(
                JsonHttpResponse.deletedStatus,
                 "The role deleted successfully"
        );
    }

    public JsonHttpResponse deleteUser(Long _id) {

        userDAO.delete(_id);
        return new JsonHttpResponse(
                JsonHttpResponse.deletedStatus,
                 "The user deleted successfully"
        );
    }

    public JsonHttpResponse deleteCategory(Long _id) {

        categoryDAO.delete(_id);
        return new JsonHttpResponse(
                JsonHttpResponse.deletedStatus,
                 "The category deleted successfully"
        );
    }

    public JsonHttpResponse deleteProduct(Long _id) {

        productDAO.delete(_id);
        return new JsonHttpResponse(
                JsonHttpResponse.deletedStatus,
                 "The product deleted successfully"
        );
    }

    public JsonHttpResponse check(Authentication authentication) {

        JsonHttpResponse response = new JsonHttpResponse();
        if (authentication != null && authentication.isAuthenticated()) {
            UserModel userModel = new UserModel();
            userModel.name = authentication.getName();
            response.status = JsonHttpResponse.successStatus;
            response.message
                    = String.format("User %s signed in", userModel.name);
            response.data = userModel;
        } else {
            response.status = JsonHttpResponse.failStatus;
            response.message = "User is a guest";
        }
        return response;
    }

    public JsonHttpResponse onSignOut() {
        JsonHttpResponse response =
                new JsonHttpResponse();
        response.status = JsonHttpResponse.successStatus;
            response.message = "Signed out";
        return response;
    }

    public JsonHttpResponse makeUserAdmin(Long _id) throws Exception {

        // Получаем из хранилища объект роли по умолчанию
        Role role = roleDAO.read("admin");
        // 
        User user
            = userDAO.read(_id);
        user.setRole(role);
        // Создаем пользователя с заданными именем и паролем и
        // со ссылкой на объект роли по умолчанию
        userDAO.update(user);
        return new JsonHttpResponse(
                JsonHttpResponse.updatedStatus,
                 "Admin '" + user.getName() + "' created successfully"
        );
    }
}
