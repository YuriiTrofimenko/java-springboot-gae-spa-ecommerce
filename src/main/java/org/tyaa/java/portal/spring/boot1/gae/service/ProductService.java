package org.tyaa.java.portal.spring.boot1.gae.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tyaa.java.portal.spring.boot1.gae.model.JsonHttpResponse;
import java.util.stream.Collectors;
import org.tyaa.java.portal.spring.boot1.gae.dao.CategoryObjectifyDAO;
import org.tyaa.java.portal.spring.boot1.gae.dao.ProductObjectifyDAO;
import org.tyaa.java.portal.spring.boot1.gae.entity.Category;
import org.tyaa.java.portal.spring.boot1.gae.entity.Product;
import org.tyaa.java.portal.spring.boot1.gae.model.Cart;
import org.tyaa.java.portal.spring.boot1.gae.model.CartItem;
import org.tyaa.java.portal.spring.boot1.gae.model.CategoryModel;
import org.tyaa.java.portal.spring.boot1.gae.model.ProductFilter;
import org.tyaa.java.portal.spring.boot1.gae.model.ProductModel;

@Service
public class ProductService {

    @Autowired
    private CategoryObjectifyDAO categoryDAO;

    @Autowired
    private ProductObjectifyDAO productDAO;

    public JsonHttpResponse createCategory(CategoryModel _categoryModel) {

        Category category = new Category(_categoryModel.name);
        categoryDAO.create(category);
        return new JsonHttpResponse(
                JsonHttpResponse.createdStatus,
                 "Category '" + _categoryModel.name + "' created successfully"
        );
    }

    // Создание продукта с категорией по categoryId
    public JsonHttpResponse createProduct(ProductModel _productModel) throws Exception {

        // Получаем из хранилища объект Category
        Category category = categoryDAO.read(_productModel.getCategoryId());
        // Создаем пользователя с заданными именем и паролем и
        // со ссылкой на объект роли по умолчанию
        productDAO.create(
            new Product(
                _productModel.getTitle()
                , _productModel.getDescription()
                , _productModel.getPrice().doubleValue()
                , _productModel.getQuantity()
                , _productModel.getImage()
                , category
            )
        );
        return new JsonHttpResponse(
            JsonHttpResponse.createdStatus,
             "Product '" + _productModel.getTitle() + "' created successfully"
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
                 "Category list fetched successfully",
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
                                p.getTitle(),
                                p.getDescription(),
                                new BigDecimal(p.getPrice()),
                                p.getQuantity(),
                                p.getImage(),
                                new CategoryModel(
                                    p.getCategory().getId(),
                                    p.getCategory().getName()
                                )
                            );
                        })
                        .collect(Collectors.toList());
        return new JsonHttpResponse(
                JsonHttpResponse.fetchedStatus,
                 "Product list fetched successfully",
                 productModels
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
                ? "Category fetched successfully"
                : "Not found";
        CategoryModel categoryModel = new CategoryModel(category.getId(), category.getName());
        return new JsonHttpResponse(
                status
                , message
                , categoryModel
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
                ? "Product fetched successfully"
                : "Not found";
        ProductModel productModel = null;
        if (product != null && product.getId() != null) {
            productModel
                    = new ProductModel(
                            product.getId(),
                                product.getTitle(),
                                product.getDescription(),
                                new BigDecimal(product.getPrice()),
                                product.getQuantity(),
                                product.getImage(),
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
    // Search for a product by title
    public JsonHttpResponse<ProductModel> readProduct(String _title) throws Exception {

        Product product
                = productDAO.read(_title);
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
                                product.getTitle(),
                                product.getDescription(),
                                new BigDecimal(product.getPrice()),
                                product.getQuantity(),
                                product.getImage(),
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

    public JsonHttpResponse deleteCategory(Long _id) {

        categoryDAO.delete(_id);
        return new JsonHttpResponse(
                JsonHttpResponse.deletedStatus,
                 "Category deleted successfully"
        );
    }

    public JsonHttpResponse deleteProduct(Long _id) {

        productDAO.delete(_id);
        return new JsonHttpResponse(
                JsonHttpResponse.deletedStatus,
                 "Product deleted successfully"
        );
    }

    public JsonHttpResponse getFiltered(ProductFilter filter) {
        
        List<Product> products = productDAO.getFiltered(filter);
        List<ProductModel> productModels
                = products.stream()
                        .map((p) -> {
                            
                            return new ProductModel(
                                p.getId(),
                                p.getTitle(),
                                p.getDescription(),
                                new BigDecimal(p.getPrice()),
                                p.getQuantity(),
                                p.getImage(),
                                new CategoryModel(
                                    p.getCategory().getId(),
                                    p.getCategory().getName()
                                )
                            );
                        })
                        .collect(Collectors.toList());
        
        return new JsonHttpResponse(
                JsonHttpResponse.fetchedStatus,
                 "Product list fetched successfully",
                 productModels
        );
    }

    public JsonHttpResponse getCartItems(Cart cart) {
        return new JsonHttpResponse(
                JsonHttpResponse.fetchedStatus
                , "Cart data fetched successfully"
                , cart.getCartItems());
    }

    public JsonHttpResponse changeCartItemCount(Cart cart, Long id, CartItem.Action action) throws InstantiationException, IllegalAccessException {
        
        CartItem currentCartItem = null;
        Product product = productDAO.read(id);

        List<CartItem> currentCartItemList =
            cart.getCartItems().stream().filter((i) -> {
                    return i.id.equals(id);
                }).collect(Collectors.toList());
        
        if (currentCartItemList.size() > 0) {
            currentCartItem = currentCartItemList.get(0);
        } else {
            currentCartItem = new CartItem(id, product.getTitle(), 0);
            cart.getCartItems().add(currentCartItem);
        }
        
        if (null != action) {
            switch (action) {
                case ADD:
                    currentCartItem.count++;
                    break;
                case NEG:
                    currentCartItem.count--;
                    if (currentCartItem.count <= 0) {
                        cart.getCartItems().remove(currentCartItem);
                    }   break;
                case REM:
                    cart.getCartItems().remove(currentCartItem);
                    break;
                default:
                    break;
            }
        }
        
        return new JsonHttpResponse(
                JsonHttpResponse.successStatus
                , "Cart data changed successfully"
                , cart.getCartItems());
    }
}
