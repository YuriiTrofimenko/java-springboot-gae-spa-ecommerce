package org.tyaa.java.portal.spring.boot1.gae.service;

import static com.google.cloud.Identity.user;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tyaa.java.portal.spring.boot1.gae.dao.RoleObjectifyDAO;
import org.tyaa.java.portal.spring.boot1.gae.dao.UserObjectifyDAO;
import org.tyaa.java.portal.spring.boot1.gae.entity.Role;
import org.tyaa.java.portal.spring.boot1.gae.entity.User;
import org.tyaa.java.portal.spring.boot1.gae.model.JsonHttpResponse;
import org.tyaa.java.portal.spring.boot1.gae.model.RoleModel;
import org.tyaa.java.portal.spring.boot1.gae.model.UserModel;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.tyaa.java.portal.spring.boot1.gae.dao.SubscriptionObjectifyDAO;
import org.tyaa.java.portal.spring.boot1.gae.entity.Subscription;
import org.tyaa.java.portal.spring.boot1.gae.model.SubscriptionModel;

@Service
public class AuthService {

    @Autowired
    private RoleObjectifyDAO roleDAO;

    @Autowired
    private UserObjectifyDAO userDAO;

    @Autowired
    private SubscriptionObjectifyDAO subscriptionDAO;

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
        User newUser = new User(
                        _userModel.name,
                        _userModel.password,
                        _userModel.mail,
                        role
                );
        // Сохраняем в базу объект новый пользователь
        userDAO.create(newUser);
        // Проверяем объект модели, который пришел с клиента:
        //нужно ли подписать данного нового пользователя на
        //событие добавления нового товара
        if (_userModel.subscribe) {
            // Вызываем сохранение объекта подписки в базу
            subscriptionDAO.create(
                    new Subscription(newUser)
            );
        }

        return new JsonHttpResponse(
                JsonHttpResponse.createdStatus,
                "User '" + _userModel.name + "' created successfully"
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
                                    u.getMail(),
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

    public JsonHttpResponse<RoleModel> readRole(Long _id) throws Exception {

        Role role
                = roleDAO.read(_id);
        String status
                = (role != null && role.getId() != null)
                ? JsonHttpResponse.fetchedStatus
                : JsonHttpResponse.warningStatus;
        String message
                = (role != null && role.getId() != null)
                ? "The role fetched successfully"
                : "Not found";
        RoleModel roleModel = new RoleModel(role.getId(), role.getName());
        return new JsonHttpResponse(
                status,
                message,
                roleModel
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
                            user.getMail(),
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
                            user.getMail(),
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
        JsonHttpResponse response
                = new JsonHttpResponse();
        response.status = JsonHttpResponse.successStatus;
        response.message = "Signed out";
        return response;
    }

    public JsonHttpResponse onError() {
        JsonHttpResponse response
                = new JsonHttpResponse();
        response.status = JsonHttpResponse.errorStatus;
        response.message = "Auth error";
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
