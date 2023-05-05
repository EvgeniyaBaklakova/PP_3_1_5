package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService service, RoleService roleService) {
        this.userService = service;
        this.roleService = roleService;
    }
    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "showUsers";
    }
    @GetMapping("/addUser")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "addUser";
    }
    @PostMapping("addUser")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam("roles") String[] roles) {
            userService.addUser(user, roles);
        return "redirect:/admin";
    }
    @GetMapping("/edit/{id}")
    public String updateUserForm(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "updateUser";
    }
    @PatchMapping("edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id,
                             @RequestParam("roles") String[] roles){
        userService.updateUser(user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}